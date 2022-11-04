<template>
  <div >
    <el-upload
        v-model:file-list="fileList"
        class="upload-demo"
        multiple
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :before-remove="beforeRemove"
        :limit="3"
        :on-exceed="handleExceed"
        :http-request="uploadFile"
    >
      <el-button type="primary">Click to upload</el-button>
      <template #tip>
        <div class="el-upload__tip">
          jpg/png files with a size less than 500KB.
        </div>
      </template>
    </el-upload>
</div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { UploadProps, UploadUserFile } from 'element-plus'
import {testPostFile} from "@/api/api";

const fileList = ref<UploadUserFile[]>([])

const handleRemove: UploadProps['onRemove'] = (file, uploadFiles) => {
  console.log(file, uploadFiles)
}

const handlePreview: UploadProps['onPreview'] = (uploadFile) => {
  console.log(uploadFile)
}

const handleExceed: UploadProps['onExceed'] = (files, uploadFiles) => {
  ElMessage.warning(
      `The limit is 3, you selected ${files.length} files this time, add up to ${
          files.length + uploadFiles.length
      } totally`
  )
}

const beforeRemove: UploadProps['beforeRemove'] = (uploadFile, uploadFiles) => {
  return ElMessageBox.confirm(
      `Cancel the transfert of ${uploadFile.name} ?`
  ).then(
      () => true,
      () => false
  )
}



let upParams ={
  "params":"doc2pdf"
}




const uploadFile = (fileReqOpt) => {
  let fd = new FormData()
  fd.append("file",fileReqOpt.file)
  fd.append("params","file.file")
  testPostFile(fd)
}

</script>
