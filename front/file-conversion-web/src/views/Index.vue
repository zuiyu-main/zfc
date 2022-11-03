<template>
  <div class="common-layout">
    <el-container >
      <el-aside class="aside" ><Menu @menu-click="menuClick"/></el-aside>
      <el-container>
        <el-header class="header">{{pageData.title}}</el-header>
        <el-main class="main"><FileConvert v-if="doc2pdfStatus"/></el-main>
        <el-footer class="footer">公众号:醉鱼Java </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
  import Menu from '@/components/Menu.vue'
  import FileConvert from '@/views/FileConvert.vue'
  import { ref } from 'vue'
  import { reactive } from 'vue'
  import { testGetHaveParamsMethod } from "@/api/api";


  async function getConvertInfo() {
    let result = await testGetHaveParamsMethod("testP");
    console.log('testGetHaveParamsMethod',result)
  }  
  const doc2pdfStatus = ref(false)
  const pageData = reactive({
    title:'专注文件转换'
  })
  
  const menuClick = (v: string)=>{
      pageData.title=v
      doc2pdfStatus.value=true
      getConvertInfo()
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
    /*color: #589EF8;*/
    text-align: center;
    line-height: 60px;
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

</style>
