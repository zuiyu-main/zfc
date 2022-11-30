<template>
  <div >
    <el-page-header @back="goBack" :content="convertPageData.title" title="返回">
    </el-page-header>

    <el-upload
        ref="upload"
        :file-list="convertPageData.fileList"
        class="upload-demo"
        :accept="convertPageData.accept"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :on-exceed="handleExceed"
        :http-request="uploadFile"
        :on-error="handleError"
        :before-upload="beforeUpload"
    >
      <el-select v-model="value" clearable placeholder="请选择文件转换方式">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
      <el-button type="primary">点击上传待转换文件
      </el-button>
      <template #tip>
        <div class="el-upload__tip">
          支持【{{convertPageData.accept}}】文件,
          点击原文名称获取转换之后的文件
        </div>
      </template>
    </el-upload>
</div>
</template>

<script setup lang="ts">
import { ref,defineEmits,defineProps,toRefs,onMounted,getCurrentInstance } from 'vue'
import {ElMessage, ElMessageBox,type UploadFile, type UploadFiles, type UploadRawFile} from 'element-plus'
import type { UploadProps, UploadUserFile } from 'element-plus'
import {formUploadFile} from "@/api/api.js";
const {proxy} = getCurrentInstance()
import  {convertPageData} from '@/stores/fileConvert'
// 定义转换方式
const value = ref('')
const options = [
  {
    value: 'Aspose',
    label: '方式1：Aspose',
  },
  {
    value: 'Itext',
    label: '方式2：Itext',
  },
  {
    value: 'PDFBOX',
    label: '方式3：PdfBox',
  },
  {
    value: 'Spire',
    label: '方式4：Spire',
  }
]
// 定义接收页面传参
const props = defineProps({
  convertPageData: {
    type: Object,
    default() {
      return {
        title: '',
        accept:''
      };
    },
    required: false
  }

});
// const {convertPageData} = toRefs(props);
// let fileList = ref<UploadUserFile[]>(convertPageData.fileList)
const emit = defineEmits(['go-back'])
// 页面加载函数
onMounted(() => {
  //打印父组件传递的值
  // console.log(props.convertPageDataTitle, 'convertPageDataTitle')
  // console.log(props.convertPageDataAccept, 'convertPageDataAccept')
  console.log("convertPageData 0000.fileList",convertPageData.fileList)

})

const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
  // console.log('文件移除：',file, uploadFiles)
}

const handlePreview: UploadProps['onPreview'] = (uploadFile) => {
  let fileUrl = uploadFile.raw.url
  if (fileUrl === undefined || fileUrl === ''){
    ElMessage.warning("文件转换失败，请删除重试！！！")
    return
  }
  window.open(fileUrl)

  // var a = document.createElement('a');
  // var event = new MouseEvent('click');
  // a.download = uploadFile.name;
  // a.href = fileUrl;
  // a.dispatchEvent(event);
}
const handleError: UploadProps['onError'] = (error: Error, uploadFile: UploadFile,
                                             uploadFiles: UploadFiles)=>{
  console.log("文件上传失败：",error)
}
const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(
      `The limit is 3, you selected ${files.length} files this time, add up to ${
          files.length + uploadFiles.length
      } totally`
  )
}
const  beforeUpload: UploadProps['beforeUpload'] = (rawFile: UploadRawFile)=>{
}
const beforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(
      `请确认是否要删除文件： ${uploadFile.name} ?`,{
    confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
  }
  ).then(
      () => true,
      () => false
  )
}

// 文件上传
const uploadFile = async (fileReqOpt) => {
  let fd = new FormData()
  fd.append("file",fileReqOpt.file)
  fd.append("type","1")
  fd.append("convertFileType",value.value===''?"Aspose":value.value)
  let result = await formUploadFile(fd)
  if (result.data.code === 500){
    return
    // proxy.$refs.upload.handleRemove(fileReqOpt.file)
  }
  fileReqOpt.file.url=result.data.data
  console.log("fileReqOpt",fileReqOpt)
  let fl = convertPageData.fileList
  fl.push(fileReqOpt.file)
  convertPageData.setFileList(fl)

}
// 关闭该页面、上传状态值到父页面
const goBack = ()=>{
  emit("go-back")
}
</script>

<style scoped>
.upload-demo{
  line-height:80px;
}
</style>