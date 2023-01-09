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
  type:"0",
  targetFileType:"",
  /**
   * 设置显示名称
   * @param v
   */
  setTitle(v) {
    this.title = v
  },
  /**
   * 设置允许用户上传的文件类型
   * @param v
   */
  setAccept(v) {
    this.accept = v
  },
  /**
   * 保存后端转换成功返回的文件地址
   * @param v
   */
  setFileList(v){
    this.fileList = v
  },
  /**
   * 设置转换文件类型，例：doc2pdf，pdf2doc
   * @param v
   */
  setType(v) {
    this.type = v
  },
  /**
   * 设置输出文件类型，例：pdf，doc，docx
   * @param v
   */
  setTargetFileType(v){
    this.targetFileType=v
  }
})