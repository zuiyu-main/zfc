<template>
  <div class="main">
    <!-- 文件上传按钮 -->
    <el-upload
        action="#"
        :http-request="upload"
        :before-upload="beforeUpload"
        :show-file-list="false"
    >
      <el-button type="primary">选择上传文件</el-button>
    </el-upload>

    <el-divider content-position="left">上传列表</el-divider>
    <!-- 正在上传的文件列表 -->
    <div class="uploading" v-for="uploadFile in uploadFileList">
      <span class="fileName">{{ uploadFile.name }}</span>
      <span class="fileSize">{{ formatSize(uploadFile.size) }}</span>

      <div class="parse">
        <span>解析进度： </span>
        <el-progress
            :text-inside="true"
            :stroke-width="16"
            :percentage="uploadFile.parsePercentage"
        >
        </el-progress>
      </div>
      <div class="progress">
        <span>上传进度：</span>

        <el-progress
            :text-inside="true"
            :stroke-width="16"
            :percentage="uploadFile.uploadPercentage"
        >
        </el-progress>
        <span
            v-if="
            (uploadFile.uploadPercentage > 0) &
            (uploadFile.uploadPercentage < 100)
          "
        >
          <span class="uploadSpeed">{{ uploadFile.uploadSpeed }}</span>

          <el-button circle link @click="changeUploadingStop(uploadFile)">
            <el-icon size="20" v-if="uploadFile.uploadingStop == false"
            ><VideoPause
            /></el-icon>
            <el-icon size="20" v-else><VideoPlay /></el-icon>
          </el-button>
        </span>
      </div>
    </div>
  </div>
</template>

<script setup>
import zmitter from "@/util/mitt.js";
import { ElMessage } from "element-plus";
import SparkMD5 from "spark-md5";
import { VideoPause, VideoPlay } from "@element-plus/icons-vue";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
const { appContext } = getCurrentInstance();
const request = appContext.config.globalProperties.request;
var uploadFileList = ref([]);

//换算文件的大小单位
function formatSize(size) {
  //size的单位大小k

  var unit;
  var units = [" B", " K", " M", " G"];
  var pointLength = 2;
  while ((unit = units.shift()) && size > 1024) {
    size = size / 1024;
  }
  return (
      (unit === "B"
          ? size
          : size.toFixed(pointLength === undefined ? 2 : pointLength)) + unit
  );
}
//计算文件的md5值
function computeMd5(file, uploadFile) {
  return new Promise((resolve, reject) => {
    //分片读取并计算md5

    const chunkTotal = 100; //分片数
    const chunkSize = Math.ceil(file.size / chunkTotal);
    const fileReader = new FileReader();
    const md5 = new SparkMD5();
    let index = 0;
    const loadFile = (uploadFile) => {
      uploadFile.parsePercentage.value = parseInt((index / file.size) * 100);
      const slice = file.slice(index, index + chunkSize);

      fileReader.readAsBinaryString(slice);
    };
    loadFile(uploadFile);
    fileReader.onload = (e) => {
      md5.appendBinary(e.target.result);
      if (index < file.size) {
        index += chunkSize;
        loadFile(uploadFile);
      } else {
        // md5.end() 就是文件md5码
        resolve(md5.end());
      }
    };
  });
}
//检查文件是否存在
function checkFile(md5) {
  return request({
    url: "/check",
    method: "get",
    params: {
      md5: md5,
    },
  });
}
//文件上传之前,el-upload自动触发
async function beforeUpload(file) {
  console.log("2.上传文件之前");


  var uploadFile = {};
  uploadFile.name = file.name;
  uploadFile.size = file.size;
  uploadFile.parsePercentage = ref(0);
  uploadFile.uploadPercentage = ref(0);
  uploadFile.uploadSpeed = "0 M/s";
  uploadFile.chunkList = null;
  uploadFile.file = file;
  uploadFile.uploadingStop = false;
  uploadFileList.value.push(uploadFile);

  var md5 = await computeMd5(file, uploadFile);//async 和 await配可以实现等待异步函数计算完成
  uploadFile.md5 = md5;

  var res = await checkFile(md5);  //上传服务器检查，以确认是否秒传
  var data = res.data.data;

  if (!data.isUploaded) {
    uploadFile.chunkList = data.chunkList;
    uploadFile.needUpload = true;
  } else {
    uploadFile.needUpload = false;
    uploadFile.uploadPercentage.value = 100;

    console.log("文件已秒传");
    ElMessage({
      showClose: true,
      message: "文件已秒传",
      type: "warning",
    });
  }


}
//点击暂停或开始上传
function changeUploadingStop(uploadFile) {

  uploadFile.uploadingStop = !uploadFile.uploadingStop;
  if (!uploadFile.uploadingStop) {
    uploadChunk(uploadFile.file, 1, uploadFile);
  }
}
//上传文件,替换el-upload的action
function upload(xhrData) {
  var uploadFile = null;


  for (var i = 0; i < uploadFileList.value.length; i++) {

    if (
        (xhrData.file.name == uploadFileList.value[i].name) &
        (xhrData.file.size == uploadFileList.value[i].size)
    ) {
      uploadFile = uploadFileList.value[i];

      break;
    }
  }


  if (uploadFile.needUpload) {
    console.log("3.上传文件");

    // 分片上传文件
    // 确定分片的大小
    uploadChunk(xhrData.file, 1, uploadFile);
  }
}
//上传文件分片
function uploadChunk(file, index, uploadFile) {
  var chunkSize = 1024 * 1024 * 10; //10mb
  var chunkTotal = Math.ceil(file.size / chunkSize);
  if (index <= chunkTotal) {
    // 根据是否暂停，确定是否继续上传

    // console.log("4.上传分片");

    var startTime = new Date().valueOf();


    var exit = uploadFile.chunkList.includes(index);
    // console.log("是否存在",exit);


    if (!exit) {
      //    console.log("3.3上传文件",uploadingStop);
      if (!uploadFile.uploadingStop) {
        // 分片上传，同时计算进度条和上传速度
        // 已经上传的不在上传、
        // 上传完成后提示，上传成功
        // console.log("上传分片1",index);
        var form = new FormData();
        var start = (index - 1) * chunkSize;
        let end =
            index * chunkSize >= file.size ? file.size : index * chunkSize;
        let chunk = file.slice(start, end);
        //  downloadBlob(chunk,file)
        //  console.log("chunk",chunk);

        form.append("chunk", chunk);
        form.append("index", index);
        form.append("chunkTotal", chunkTotal);
        form.append("chunkSize", chunkSize);
        form.append("md5", uploadFile.md5);
        form.append("fileSize", file.size);
        form.append("fileName", file.name);
        // console.log("上传分片", index);

        request({
          url: "/upload/chunk",
          method: "post",
          data: form,
        }).then((res) => {
          var endTime = new Date().valueOf();
          var timeDif = (endTime - startTime) / 1000;
          // console.log("上传文件大小",formatSize(chunkSize));
          // console.log("耗时",timeDif);
          // console.log("then",index);

          // uploadSpeed = (chunkSize/(1024*1024))  / timeDif +" M / s"

          uploadFile.uploadSpeed = (10 / timeDif).toFixed(1) + " M/s";
          // console.log(res.data.data);
          //  console.log("f2",uploadFile);
          uploadFile.chunkList.push(index);
          //  console.log("f3",uploadFile);

          uploadFile.uploadPercentage = parseInt(
              (uploadFile.chunkList.length / chunkTotal) * 100
          );
          // console.log("上传进度",uploadFile.uploadPercentage);

          if (index == chunkTotal) {
            zmitter.emit("reloadFileList");
          }

          uploadChunk(file, index + 1, uploadFile);
        });
      }
    } else {
      uploadFile.uploadPercentage = parseInt(
          (uploadFile.chunkList.length / chunkTotal) * 100
      );

      uploadChunk(file, index + 1, uploadFile);
    }
    // }
  }
}
</script>

<style  scoped>
.main {
  margin-top: 40px;
  margin-bottom: 40px;
}
.uploading {
  padding-top: 27px;
}
.progress {
  /* width: 700px; */
  display: flex;
}
.uploading .parse {
  display: flex;
}
.parse .el-progress {
  /* font-size: 18px; */
  width: 590px;
}
.progress .el-progress {
  /* font-size: 18px; */
  width: 590px;
}
.uploading .fileName {
  font-size: 17px;
  margin-right: 40px;
  margin-left: 80px;

  /* width: 80px; */
}
.uploading .fileSize {
  font-size: 17px;

  /* width: 80px; */
}

.progress .uploadSpeed {
  font-size: 17px;
  margin-left: 5px;
  padding-left: 5px;
  padding-right: 10px;
}
</style>