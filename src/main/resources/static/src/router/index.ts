import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
  {
    path: '/main',
    name: '字典管理',
    component: () => import('@/views/dict/main.vue')
  },
  {
    path: '/app/login',
    name: '登录',
    component: () => import('@/views/login/login.vue')
  },
  {
    path: '/app/main',
    name: '主页面',
    component: () => import('@/views/login/main.vue')
  },
  {
    path: '/app/system/role',
    name: '角色管理',
    component: () => import('@/views/login/Role.vue')
  },
  {
    path: '/app/system/user',
    name: '用户管理',
    component: () => import('@/views/login/User.vue')
  },
  {
    path: '/app/system/menu',
    name: '菜单管理',
    component: () => import('@/views/login/Menu.vue')
  }
]

const router = createRouter({
  // history: createWebHistory(process.env.BASE_URL),
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
