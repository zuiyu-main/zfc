<template>
  <div>
    <el-row class="tac">
      <el-col :span="12" class="my_el_col">
        <el-menu
        default-active="2"
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        background-color="#EAEEF3"
        >
          <el-sub-menu index="1">
            <template #title>
              <el-icon><document /></el-icon>
              <span>文本转PDF</span>
            </template>
            <el-menu-item-group >
              <el-menu-item index="1-1" @click="menuClick" >TEXT2PDF</el-menu-item>
              <!-- <el-menu-item index="1-2" @click="menuClick">test</el-menu-item> -->
            </el-menu-item-group>
          </el-sub-menu>

          <el-sub-menu index="2">
            <template #title>
              <el-icon><document /></el-icon>
              <span>PDF转文本</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1" @click="menuClick">PDF2DOC</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
 
</template>

<script lang="ts" setup>
import {Document} from '@element-plus/icons-vue'
import {defineEmits, defineProps, toRefs} from 'vue';

const props = defineProps({
  item: {
    type: Object,
    default() {
      return {
        title: '',
        items: [],
      };
    },
  },
});
const {item} = toRefs(props);

const emit = defineEmits(['menu-click'])
const handleOpen = (key: string, keyPath: string[]) => {
  //console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
 // console.log(key, keyPath)
}

const menuClick = (v) => {
  let nv = ""
  if(v.index==='1-1'){
    nv = "TEXT2PDF"
  }else {
    nv = "PDF2DOC"
  }
  emit('menu-click',nv)
}

</script>
<style scoped>

.my_el_col{
  max-width: 100%;
  flex: 0 0 100%;
}


</style>
