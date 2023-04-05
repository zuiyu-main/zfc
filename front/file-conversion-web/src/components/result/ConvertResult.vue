<template>
  <el-table :data="tableData"
            style="width: 100%"
            max-height="500"
            :row-class-name="tableRowClassName"
            empty-text="还没有数据哦,快去转换一个文件试试吧!!!">
    <el-table-column label="创建时间" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <el-icon><timer /></el-icon>
          <span style="margin-left: 10px">{{ scope.row.date }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="文件名称" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.name }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="转换方式" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.type }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="转换时间" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.time }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="转换结果" width="180">
      <template #default="scope">
        <div style="display: flex; align-items: center">
          <span style="margin-left: 10px">{{ scope.row.result }}</span>
        </div>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)"
        >预览</el-button
        >
        <el-button
            size="small"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
        >删除</el-button
        >
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts">
import { Timer } from '@element-plus/icons-vue'
import {convertPageData} from '@/stores/fileConvert'
import {onMounted} from "vue";
import {ElMessage} from "element-plus";
interface fileDetail {
  date: string
  name: string
  time: string
  result: string
  url: string
  type: string
}

const handleEdit = (index: number, row: fileDetail) => {
  console.log(index, row)
  console.log("页面加载 convertResult-handleEdit:",convertPageData.fileList)
  let fileUrl = row.url
  if (fileUrl === undefined || fileUrl === ''){
    ElMessage.error("文件转换失败，请删除重试！！！")
    return
  }
  window.open(fileUrl)
}
const handleDelete = (index: number, row: fileDetail) => {
  console.log(index, row)
}
const tableData: fileDetail[]=convertPageData.fileList

// 页面加载函数
onMounted(() => {
  console.log("页面加载convertResult:",convertPageData.fileList)
})
// 切换表格展示样式
const tableRowClassName = ({
                             row,
                             rowIndex,
                           }: {
  row: fileDetail
  rowIndex: number
}) => {
  if (rowIndex === 1) {
    return 'warning-row'
  } else if (rowIndex === 3) {
    return 'success-row'
  }
  return ''
}

</script>

<style>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>