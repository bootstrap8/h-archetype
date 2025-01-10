import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [
    {
        path: '',
        component: () => import('@/views/example/main.vue')
    }
]

const router = createRouter({
    // history: createWebHistory(process.env.BASE_URL),
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router