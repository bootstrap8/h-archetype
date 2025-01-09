import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: '/login',
        component: () => import('@/views/login/login.vue')
    },
    {
        path: '/main_top',
        component: () => import('@/views/main/top.vue')
    },
    {
        path: '/main_left',
        component: () => import('@/views/main/left.vue')
    }
]

const router = createRouter({
    // history: createWebHistory(process.env.BASE_URL),
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router