<template>
  <div >
    <el-page-header @back="goBack" :content="convertPageData.title" title="返回">
    </el-page-header>

    <el-upload
        ref="upload"
        class="upload-demo"
        :accept="convertPageData.accept"
        :http-request="uploadFile"
        :on-success="handleSuccess"
        :on-error="handleError"
        :show-file-list="false"
    >
<!--      :before-upload="beforeUpload"-->
<!--      :on-preview="handlePreview"-->
<!--      :on-remove="handleRemove"-->
<!--      :on-exceed="handleExceed"-->
<!--      :file-list="convertPageData.fileList"-->
      <!--        :before-remove="beforeRemove"-->
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

    <ConvertResult/>

</div>

</template>

<script setup lang="ts">
// import {type ComponentInternalInstance, defineEmits, getCurrentInstance, onMounted, ref} from 'vue'
import { defineEmits, onMounted, ref} from 'vue'
import type {UploadProps} from 'element-plus'
import {ElMessage, ElMessageBox, type UploadFile, type UploadFiles, type UploadRawFile} from 'element-plus'
import {formUploadFile} from "@/api/api.js";
import getCurrentTime from "@/util/zdate.js";
import {convertPageData} from '@/stores/fileConvert'
// const {proxy} = getCurrentInstance() as ComponentInternalInstance
import ConvertResult from '@/components/result/ConvertResult.vue'
// 定义转换方式
const value = ref('')
const options = [
  {
    value: 'Aspose',
    label: '方式1：Aspose（推荐）',
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
    label: '方式4：Spire（免费版）',
  }
]
// 定义接收页面传参
// const props = defineProps({
//   convertPageData: {
//     type: Object,
//     default() {
//       return {
//         title: '',
//         accept:''
//       };
//     },
//     required: false
//   }
//
// });
// const {convertPageData} = toRefs(props);
// let fileList = ref<UploadUserFile[]>(convertPageData.fileList)
const emit = defineEmits(['go-back'])
// 页面加载函数
onMounted(() => {
  //打印父组件传递的值
  // console.log(props.convertPageDataTitle, 'convertPageDataTitle')
  // console.log(convertPageData.accept, 'convertPageDataAccept')
  // console.log("convertPageData 0000.fileList",convertPageData.fileList)

})

const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
  // console.log('文件移除：',file, uploadFiles)
}

const handlePreview: UploadProps['onPreview'] = (uploadFile:any) => {
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
// const beforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
//   return ElMessageBox.confirm(
//       `请确认是否要删除文件： ${uploadFile.name} ?`,{
//     confirmButtonText: '确认',
//         cancelButtonText: '取消',
//         type: 'warning',
//   }
//   ).then(
//       () => true,
//       () => false
//   )
// }
const handleSuccess: UploadProps['onSuccess']=(response: any, uploadFile: UploadFile, uploadFiles: UploadFiles)=>{

  console.log("上传成功返回:",response,uploadFile,uploadFiles)
}
interface fileDetail {
  date: string
  name: string
  time: string
  result: string
  url: string
}
// 文件上传
const uploadFile = async (fileReqOpt) => {
  let fd = new FormData()
  fd.append("file",fileReqOpt.file)
  fd.append("type",convertPageData.type)
  fd.append("targetFileType",convertPageData.targetFileType)
  fd.append("convertFileType",value.value===''?"Aspose":value.value)
  let result = await formUploadFile(fd)
  if (result.data.code === 500){
    // 调用组件ref删除文件默认列表 start
    // const fileUpload:any = proxy
    // fileUpload.$refs.upload.handleRemove(fileReqOpt.file)
    // 调用组件ref删除文件默认列表 end
    ElMessageBox.alert('转换失败', '转换结果', {
      // if you want to disable its autofocus
      autofocus: true,
      confirmButtonText: 'OK',
      callback: (action: Action) => {
        ElMessage({
          type: 'info',
          message: `action: ${action}`,
        })
      },
    })
    let fl:any = convertPageData.fileList
    const fileResult :fileDetail={
      date: getCurrentTime(),
      name: fileReqOpt.file.name,
      time: '0',
      result: '失败',
      url: '',
      type: value.value===''?"Aspose":value.value
    }
    console.log("错误结果打印",fileResult)
    fl.push(fileResult)
    convertPageData.setFileList(fl)

    return
  }
  const uploadResult:Object = result.data.data
  let fl:any = convertPageData.fileList
  const fileResult :fileDetail={
    date: uploadResult.date,
    name: uploadResult.name,
    time: uploadResult.time,
    result: uploadResult.result,
    url: uploadResult.url,
    type: uploadResult.type
  }
  fl.push(fileResult)
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