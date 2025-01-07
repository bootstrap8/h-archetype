<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <el-menu class="el-menu-demo" mode="horizontal" :ellipsis="false" @select="handleSelect">
          <el-menu-item index="0" @click="refreshPage">
            <el-icon size="large">
              <HomeFilled/>
            </el-icon>
            主页
          </el-menu-item>
          <div class="flex-grow"/>
          <el-sub-menu index="system" v-if="isADMIN">
            <template #title>
              <el-icon size="large">
                <Setting/>
              </el-icon>
              系统管理
            </template>
            <el-menu-item index="roleManagement" @click="router.push({path: '/app/system/role'})">
              <el-icon size="large">
                <UserFilled/>
              </el-icon>
              角色管理
            </el-menu-item>
            <el-menu-item index="userManagement" @click="router.push({path: '/app/system/user'})">
              <el-icon size="large">
                <User/>
              </el-icon>
              用户管理
            </el-menu-item>
            <el-menu-item index="menuManagement" @click="router.push({path: '/app/system/menu'})">
              <el-icon size="large">
                <Menu/>
              </el-icon>
              菜单管理
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
        <span style="right: 4%;position: fixed;font-size: 12px;color: blue;cursor: pointer">{{ loginName }}</span>
        <el-icon @click="logout" style="cursor: pointer">
          <SwitchButton/>
        </el-icon>
      </el-header>
      <el-container class="container">
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import type Node from 'element-plus/es/components/tree/src/model/node'
import {
  Document,
  DocumentCopy,
  Expand,
  Upload,
  Search,
  Clock,
  Plus,
  Folder,
  Delete,
  WarningFilled,
  UploadFilled,
  HomeFilled,
  Refresh,
  Edit,
  SwitchButton,
  Fold, Menu, Setting, User, UserFilled
} from '@element-plus/icons-vue'
import {UploadInstance, UploadProps, UploadFile, FormInstance, FormRules, ElMessage} from 'element-plus'
import {ref, reactive, computed, onMounted} from 'vue'
import router from '@/router/index'
import axios from '@/network/index'
import {msg, downFile} from '@/utils/Utils'

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

const headerCellStyle = () => {
  // 添加表头颜色
  return {backgroundColor: '#f5f5f5', color: '#333', fontWeight: 'bold'};
}
// 顶部菜单
const handleSelect = (key: string, keyPath: string[]) => {
}

const refreshPage = () => {
  window.location.reload();
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

.el-icon.avatar-uploader-icon {
  font-size: 16px;
  color: #8c939d;
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
}

.container {
  left: 10px;
  right: 10px;
}
</style>
