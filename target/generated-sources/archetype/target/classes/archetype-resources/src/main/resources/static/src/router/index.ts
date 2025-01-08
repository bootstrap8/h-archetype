import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: '主页面(菜单在顶部)',
        component: () => import('@/views/main/index.vue')
    },
    {
        path: '/app/login',
        name: '登录',
        component: () => import('@/views/login/login.vue')
    },
    {
        path: '/main/index',
        name: '主页面(菜单在顶部)',
        component: () => import('@/views/main/index.vue')
    }
]

const router = createRouter({
    // history: createWebHistory(process.env.BASE_URL),
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router