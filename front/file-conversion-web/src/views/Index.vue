<template>
  <div class="common-layout" >
    <el-container >
      <el-aside class="aside" ><Menu @menu-click="menuClick"/></el-aside>
      <el-container>
<!--        <el-header class="header">{{pageData.title}}</el-header>-->
        <el-main class="main">
          <FileConvert v-if="doc2pdfStatus"
                       @go-back="goBack"
          />
          <div v-else>最方便的文件转换工具</div>
        </el-main>
        <el-footer class="footer">公众号:醉鱼Java </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Menu from '@/components/Menu.vue'
import FileConvert from '@/components/convert/FileConvert.vue'
// ref 参数，reactive 表单
import {ref} from 'vue'
import {convertPageData} from '@/stores/fileConvert'


const doc2pdfStatus = ref(false)


  const menuClick = (v: string)=>{
    // 设置转换文件页面显示状态
    doc2pdfStatus.value=true
    // 设置当前页标题
    convertPageData.setTitle(v)
    convertPageData.setAccept(getFileType(v))
    convertPageData.setFileList([])
    // getConvertInfo()

  }

  const  getFileType = (v)=>{
    if (v==='DOC2PDF'){
      return ".doc,.docx,.txt,.xml"
    }else if (v==='PDF2DOC'){
      return ".pdf"
    }else{
      return ""
    }
  }

  const goBack = ()=>{
    doc2pdfStatus.value=false
  }

</script>
<style scoped>
  html,body,#app,.homeBox,.el-container{
    /*设置内部填充为0，几个布局元素之间没有间距*/
    padding:  0px;
    /*外部间距也是如此设置*/
    margin: 0px;
    /*统一设置高度为100%*/
    height: 100%;
  }
  .el-header, .el-footer {
    background-color: #EAEEF3;
    text-align: center;
    line-height: 60px;
    font-weight: 500;
  }
.common-layout{
  width: 100%;
  height: 100%;
}
  .el-aside {
    background-color: #EAEEF3;
    /*color: #2154FA;*/
    text-align: center;
    line-height: 100%;
  }

  .el-main {
    background-color:  #ecf5ff;
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .el-main{
    background: url('../assets/bg.jpg') no-repeat fixed center;
    background-size: cover;
    display: flex;
    flex-direction: column;

  }
</style>
