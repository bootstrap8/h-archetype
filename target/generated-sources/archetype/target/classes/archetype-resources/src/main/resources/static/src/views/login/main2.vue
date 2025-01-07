<script lang="ts" setup>
import {
  HomeFilled,
  User,
  Setting,
  Expand,
  Fold,
  Close,
  SwitchButton,
  UserFilled,
  Menu
} from '@element-plus/icons-vue';
import {ref, reactive, onMounted, defineAsyncComponent} from 'vue';
import axios from '@/network';
import {msg} from '@/utils/Utils';
import router from '@/router/index'

const loginName = ref('-')
const isADMIN = ref(false)
const menuMap = reactive({})
const logout = () => {
  axios({
    url: '/system/logout',
    method: 'post'
  }).then((res: any) => {
    if (res.data.state == 'OK') {
      router.push({path: '/app/login'})
    } else {
      msg(res.data.errorMessage, 'warning')
    }
  }).catch((err: Error) => {
    msg('请求异常', 'error')
  })
}
onMounted(() => {
  axios({
    url: '/system/user',
    method: 'get'
  }).then((res: any) => {
    if (res.data.state == 'OK') {
      loginName.value = res.data.body.userName
      isADMIN.value = (res.data.body.roleName == 'ADMIN')
      if (res.data.body.menus && res.data.body.menus.length > 0) {
        res.data.body.menus.forEach(item => {
          menuMap[item.url] = 'Y'
        })
      }
    }
  }).catch((err: Error) => {
    msg('请求异常', 'error')
  })
})

// 菜单收起状态
const isCollapse = ref(false);

// 当前激活的菜单项
const activeMenu = ref('1');

// 当前激活的 Tab 页
const activeTab = ref('');

// Tab 页列表
const tabs = ref<{ name: string; label: string; component: any }[]>([]);

// 切换菜单收起状态
const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value;
};

// 处理菜单项选择
const handleMenuSelect = (index: string) => {
  activeMenu.value = index;
  const tab = tabs.value.find((tab) => tab.name === index);
  if (!tab) {
    const component = defineAsyncComponent(() =>
        import(`@/views/login/${index}.vue`) // 动态加载组件
    );
    tabs.value.push({
      name: index,
      label: `页面 ${index}`,
      component,
    });
    console.log('新增 Tab:', tabs.value); // 打印 tabs 数据
  }
  activeTab.value = index;
};

// 移除 Tab 页
const removeTab = (tabName: string) => {
  const index = tabs.value.findIndex((tab) => tab.name === tabName);
  tabs.value.splice(index, 1);
  if (tabName === activeTab.value) {
    activeTab.value = tabs.value[0]?.name || '';
  }
};

// 表单标签宽度
const formLabelWidth = ref('140px');

// 数据对象
const data = reactive({
  actions: [] as any[],
});

// 表头样式
const headerCellStyle = () => {
  return {backgroundColor: '#f5f5f5', color: '#333', fontWeight: 'bold'};
};

// 页面加载后执行
onMounted(() => {
  console.log('页面加载后');
});

// 防抖函数
const debounce = (callback: (...args: any[]) => void, delay: number) => {
  let tid: any;
  return function (...args: any[]) {
    const ctx = self;
    tid && clearTimeout(tid);
    tid = setTimeout(() => {
      callback.apply(ctx, args);
    }, delay);
  };
};

// 重写 ResizeObserver
const _ = (window as any).ResizeObserver;
(window as any).ResizeObserver = class ResizeObserver extends _ {
  constructor(callback: (...args: any[]) => void) {
    callback = debounce(callback, 20);
    super(callback);
  }
};
</script>

<template>
  <div class="container">
    <el-container
        style="height: calc(100vh - 5px); margin: 5px; overflow: hidden; border-radius: 8px; box-shadow: 0 1px 3px 0;">
      <!-- 左侧菜单 -->
      <el-aside
          :width="isCollapse ? '64px' : '200px'"
          style="overflow: hidden; transition: width 0.3s ease;margin-top: 0px"
      >
        <div class="menu-header">
          <el-icon @click="toggleCollapse" style="cursor: pointer; color: #fff;">
            <component :is="isCollapse ? Expand : Fold"/>
          </el-icon>
        </div>
        <el-menu
            :default-active="activeMenu"
            :collapse="isCollapse"
            @select="handleMenuSelect"
            mode="vertical"
            :ellipsis="false"
            popper-effect="dark"
            style="height: calc(100vh - 120px); overflow-y: auto;"
        >
          <el-menu-item index="Index">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            <template #title>首页</template>
          </el-menu-item>
          <el-sub-menu index="System" v-if="isADMIN">
            <template #title>
              <el-icon>
                <Setting/>
              </el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="Role">
              <el-icon>
                <UserFilled/>
              </el-icon>
              <template #title>角色管理</template>
            </el-menu-item>
            <el-menu-item index="User">
              <el-icon>
                <User/>
              </el-icon>
              <template #title>用户管理</template>
            </el-menu-item>
            <el-menu-item index="Menu">
              <el-icon>
                <Menu/>
              </el-icon>
              <template #title>菜单管理</template>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!-- 右侧内容区域 -->
      <el-container style="overflow: hidden; background-color: #f5f7fa;">
        <div style="width: 100%">
          <!-- 顶部区域 -->
          <div
              style="height: 50px; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; background-color: #fff; border-bottom: 1px solid #e4e7ed;">
            <!-- 导航面包屑 -->
            <el-breadcrumb separator="/">
              <el-breadcrumb-item>首页</el-breadcrumb-item>
              <el-breadcrumb-item>当前页面</el-breadcrumb-item>
            </el-breadcrumb>

            <!-- 用户信息 -->
            <div style="display: flex; align-items: center;">
              <span style="margin-right: 10px;color: #409eff">{{ loginName }}</span>
              <el-icon @click="logout" style="cursor: pointer;color: black">
                <SwitchButton/>
              </el-icon>
            </div>
          </div>

          <!-- Tab 页区域 -->
          <div style="height: calc(100% - 50px);">
            <el-tabs
                v-model="activeTab"
                type="card"
                closable
                @tab-remove="removeTab"
                style="height: 100%;"
            >
              <el-tab-pane
                  v-for="tab in tabs"
                  :key="tab.name"
                  :label="tab.label"
                  :name="tab.name"
                  style="height: 100%; overflow-y: auto;"
              >
                <component :is="tab.component"/>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.container {
  flex-grow: 1;
  padding: 0;
  overflow: hidden;
  width: 100%;
  height: 100vh;
}

.menu-header {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
  height: 50px;
  line-height: 60px;
  background-color: #409eff; /* 蓝色背景 */
  color: #fff; /* 白色文字 */
}
</style>