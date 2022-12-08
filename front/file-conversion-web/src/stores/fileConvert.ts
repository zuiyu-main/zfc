import {reactive} from 'vue'


export const convertPageData = reactive({
  title: "专注文件转换",
  accept:".txt",
  fileList:[
    // {
    //   name: 'food.jpeg',
    //   url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
    // },
    // {
    //   name: 'food2.jpeg',
    //   url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
    // },
  ],
  setTitle(v) {
    this.title = v
  },
  setAccept(v) {
    this.accept = v
  },
  setFileList(v){
    this.fileList = v
  }
})