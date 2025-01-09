<template>
  <div class="common-layout">
    <el-container>

      <el-header class="header">

        <el-menu class="el-menu-demo"
                 :default-active="activeMenu"
                 :collapse="isCollapse"
                 @select="handleMenuSelect"
                 mode="horizontal"
                 :ellipsis="false"
                 popper-effect="dark"
                 style="overflow-x:auto"
        >
          <el-menu-item @click="refreshPage">
            <el-icon size="20">
              <component :is="getIconComponent('HomeIcon')"/>
            </el-icon>
            <template #title>主页</template>
          </el-menu-item>
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

        <div style="display: flex; align-items: center;">
          <TimeComponent/>
          <span style="margin-right: 10px;margin-left:0px; padding:0;font-size: 0.6em;">,
            {{ data.user.userName }}
          </span>
          <el-icon @click="logout" style="cursor: pointer;color: red;" size="20">
            <SwitchButton/>
          </el-icon>
        </div>
      </el-header>

      <el-container class="container">
        <!-- Tab 页区域 -->
        <div style="height: calc(100% - 60px);width: 100%">
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
      </el-container>

    </el-container>
  </div>
</template>

<script lang="ts" setup>
import type Node from 'element-plus/es/components/tree/src/model/node'
import {HomeFilled, SwitchButton, Menu, Setting, User, UserFilled} from '@element-plus/icons-vue'
import {
  defineAsyncComponent,
  markRaw,
  onMounted,
  reactive,
  ref,
  resolveComponent,
  defineComponent,
  h,
  nextTick
} from 'vue';
import router from '@/router'
import axios from '@/network'
import {msg} from '@/utils/Utils'
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
      router.push({path: '/login'})
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
  let component;

  if (index.startsWith('http:') || index.startsWith('https:')) {
    // 如果是外部链接，直接在新标签页打开
    window.open(index, '_blank');
  } else {
    activeMenu.value = index;
    let menuName = menuMap[index];
    data.currentPage = menuName;

    // 检查是否已经存在该 Tab
    const tab = tabs.value.find((tab) => tab.name === index);
    if (!tab) {
      if (index.startsWith('inner:')) {
        // 处理内部 iframe
        const iframeUrl = index.substring(6);
        console.log('iframeUrl: ', iframeUrl);

        component = defineComponent({
          setup() {
            const iframe = ref<HTMLIFrameElement | null>(null);
            const isLoading = ref(true); // 加载状态

            const onIframeLoad = () => {
              if (iframe.value && iframe.value.contentWindow?.document.body) {
                // 动态设置 iframe 的高度
                const contentHeight = iframe.value.contentWindow.document.body.scrollHeight;
                iframe.value.style.height = `${contentHeight}px`;
                console.log('iframe 高度已调整:', contentHeight);

                // 监听内容变化
                const observer = new MutationObserver(() => {
                  const newHeight = iframe.value.contentWindow.document.body.scrollHeight;
                  iframe.value.style.height = `${newHeight}px`;
                  console.log('iframe 高度动态调整:', newHeight);
                });

                observer.observe(iframe.value.contentWindow.document.body, {
                  childList: true,
                  subtree: true,
                });

                // 关闭加载状态
                isLoading.value = false;
              }
            };

            return {iframeUrl, onIframeLoad, iframe, isLoading};
          },
          render() {
            return h('div', {style: {width: '100%', height: '100%', position: 'relative'}}, [
              // 加载动画
              this.isLoading
                  ? h('div', {
                    style: {
                      position: 'absolute',
                      top: '50%',
                      left: '50%',
                      transform: 'translate(-50%, -50%)'
                    }
                  }, '加载中...')
                  : null,
              // iframe
              h('iframe', {
                src: this.iframeUrl,
                style: {width: '100%', height: '100%', border: 'none'},
                onLoad: this.onIframeLoad,
                ref: 'iframe',
              }),
            ]);
          },
        });
      } else {
        // 处理内部 Vue 组件
        component = defineAsyncComponent(() =>
            import(`@/views${index}.vue`)
                .then((module) => {
                  console.log('组件加载成功:', module.default);
                  return module;
                })
                .catch((err) => {
                  console.error('组件加载失败:', err);
                  throw err;
                })
        );
      }

      // 将组件添加到 tabs
      tabs.value.push({
        name: index,
        label: menuName,
        component: markRaw(component),
      });
    }

    // 激活当前 Tab
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

const refreshPage = () => {
  window.location.reload();
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

<style scoped>
.header {
  position: fixed;
  top: 0px;
  left: 10px;
  right: 10px;
  z-index: 1000;
  background-color: #FAFCFF;
  color: #333;
  padding: 10px 20px;
  border-bottom: 1px solid #eaeaea;
}

.common-layout {
  margin: 80px 10px 0px 10px;
  background-color: #f9f9f9;
}

.el-menu-demo {
  background-color: transparent;
}

.el-menu-item {
  transition: background-color 0.3s;
}

.el-menu-item:hover {
  background-color: #f0f0f0;
}

.toggle-button {
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 5px;
  background-color: #eaeaea;
  border-radius: 4px;
  margin-bottom: 10px;
  transition: background-color 0.3s;
  width: 100%;
}

.toggle-button:hover {
  background-color: #dcdcdc;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 100%;
  min-height: 600px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: border-color 0.3s;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.container {
  left: 10px;
  right: 10px;
}
</style>
