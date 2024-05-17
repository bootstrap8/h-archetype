<script lang="ts" setup>
import type Node from 'element-plus/es/components/tree/src/model/node'
import {ArrowRight} from '@element-plus/icons-vue'
import {
  Document,
  DocumentCopy,
  Expand,
  Download,
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
  SwitchButton
} from '@element-plus/icons-vue'
import {UploadInstance, UploadProps, UploadFile, FormInstance, FormRules, ElMessage} from 'element-plus'
import {ref, reactive, computed, onMounted} from 'vue'
import router from '@/router/index'
import axios from '@/network/index'
import {msg, downFile} from '@/utils/Utils'

const form = reactive({
});

const data = reactive({
});

// 查询字典列表
const test = () => {
  axios({
    url: '/example/test',
    method: 'get',
  }).then((res: any) => {
    //if (res.data.state == "OK") {
    //}
    console.log('',res)
  }).catch((err: Error) => {
    msg('请求异常', 'error')
  })
}


</script>

<template>
  <div class="container">

  </div>
</template>

<style scoped>
.container {
  flex-grow: 1;
  padding: 20px 15%;
  overflow: auto;
  width: 60%;

}
</style>