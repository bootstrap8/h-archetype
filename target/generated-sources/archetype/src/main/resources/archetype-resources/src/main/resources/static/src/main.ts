import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/base.css'
import DashboardIcon from "@/components/icon/DashboardIcon.vue";
import FolderIcon from "@/components/icon/FolderIcon.vue";
import MenuIcon from "@/components/icon/MenuIcon.vue";
import LogIcon from "@/components/icon/LogIcon.vue";
import PermissionIcon from "@/components/icon/PermissionIcon.vue";
import RoleIcon from "@/components/icon/RoleIcon.vue";
import SettingsIcon from "@/components/icon/SettingsIcon.vue";
import UserIcon from "@/components/icon/UserIcon.vue";

const zhCn = require('element-plus/dist/locale/zh-cn.min.js')

const app = createApp(App)
app.component('DashboardIcon', DashboardIcon);
app.component('FolderIcon', FolderIcon);
app.component('MenuIcon', MenuIcon);
app.component('LogIcon', LogIcon);
app.component('PermissionIcon', PermissionIcon);
app.component('RoleIcon', RoleIcon);
app.component('SettingsIcon', SettingsIcon);
app.component('UserIcon', UserIcon);
app.use(ElementPlus, {locale: zhCn})
app.use(store)
app.use(router)
app.mount('#app')
