<script lang="ts" setup>
import {
  Expand,
  Fold,
  SwitchButton,
} from '@element-plus/icons-vue';
import {ref, reactive, onMounted, defineAsyncComponent, markRaw, resolveComponent} from 'vue';
import axios from '@/network';
import {msg} from '@/utils/Utils';
import router from '@/router'
import TimeComponent from '@/components/TimeComponent.vue';

const data = reactive({
  currentPage: '主页',
  user: {},
  adminMenus: [],
  menus: []
})
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

      let user = res.data.body.user
      data.user.userName = user.userName
      data.user.isAdmin = user.admin
      data.menus = user.menus
      data.adminMenus = res.data.body.allMenus

      if (data.adminMenus && data.adminMenus.length > 0) {
        data.adminMenus.forEach(item => {
          menuMap[item.url] = item.name
          if (item.menus && item.menus.length > 0) {
            item.menus.forEach(sItem => {
              menuMap[sItem.url] = sItem.name
            })
          }
        })
      }
    }
  }).catch((err: Error) => {
    msg('请求异常', 'error')
  })
})

const getIconComponent = (iconName: string) => {
  return resolveComponent(iconName);
};

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
  if (index.startsWith('inner:')) {
    window.open(window.location.protocol + '//' + window.location.host + index.substring(6), '_blank')
  } else if (index.startsWith('http:') || index.startsWith('https:')) {
    window.open(index, '_blank')
  } else {
    activeMenu.value = index;
    let menuName = menuMap[index]
    data.currentPage = menuName
    const tab = tabs.value.find((tab) => tab.name === index);
    if (!tab) {
      const component = defineAsyncComponent(() =>
          import(`@/views${index}.vue`).then((module) => {
            console.log('组件加载成功:', module.default); // 打印加载的组件
            return module;
          }).catch((err) => {
            console.error('组件加载失败:', err); // 打印加载错误
            throw err;
          })
      );
      tabs.value.push({
        name: index,
        label: menuName,
        component: markRaw(component),
      });
    }
    activeTab.value = index;
  }
};

// 移除 Tab 页
const removeTab = (tabName: string) => {
  const index = tabs.value.findIndex((tab) => tab.name === tabName);
  tabs.value.splice(index, 1);
  if (tabName === activeTab.value) {
    activeTab.value = tabs.value[0]?.name || '';
  }
};

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
        style="height: calc(100vh - 10px); margin: 5px; overflow: hidden; border-radius: 8px; box-shadow: 0 1px 3px 0;">
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
            style="height: 100vh; overflow-y: auto;overflow-x:hidden"
        >
          <template v-for="menu in data.adminMenus" v-if="data.user.isAdmin">
            <el-sub-menu v-if="menu.menus && menu.menus.length > 0" :index="menu.url">
              <template #title>
                <el-icon>
                  <component :is="getIconComponent(menu.iconName)"/>
                </el-icon>
                <span>{{ menu.name }}</span>
              </template>
              <template v-for="subMenu in menu.menus">
                <el-menu-item :index="subMenu.url">
                  <el-icon>
                    <component :is="getIconComponent(subMenu.iconName)"/>
                  </el-icon>
                  <template #title>{{ subMenu.name }}</template>
                </el-menu-item>
              </template>
            </el-sub-menu>
            <el-menu-item v-else :index="menu.url">
              <el-icon>
                <component :is="getIconComponent(menu.iconName)"/>
              </el-icon>
              <template #title>{{ menu.name }}</template>
            </el-menu-item>
          </template>
          <template v-for="menu in data.menus" v-else>
            <el-sub-menu v-if="menu.menus && menu.menus.length > 0" :index="menu.url">
              <template #title>
                <el-icon>
                  <component :is="getIconComponent(menu.iconName)"/>
                </el-icon>
                <span>{{ menu.name }}</span>
              </template>
              <template v-for="subMenu in menu.menus">
                <el-menu-item :index="subMenu.url">
                  <el-icon>
                    <component :is="getIconComponent(subMenu.iconName)"/>
                  </el-icon>
                  <template #title>{{ subMenu.name }}</template>
                </el-menu-item>
              </template>
            </el-sub-menu>
            <el-menu-item v-else :index="menu.url">
              <el-icon>
                <component :is="getIconComponent(menu.iconName)"/>
              </el-icon>
              <template #title>{{ menu.name }}</template>
            </el-menu-item>
          </template>
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
              <el-breadcrumb-item>{{ data.currentPage }}</el-breadcrumb-item>
            </el-breadcrumb>

            <!-- 用户信息 -->
            <div style="display: flex; align-items: center;">
              <TimeComponent/>
              <span style="margin-right: 5px;font-size: 0.5em;">| {{ data.user.userName }} | </span>
              <el-icon @click="logout" style="cursor: pointer;color: red;" size="large">
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