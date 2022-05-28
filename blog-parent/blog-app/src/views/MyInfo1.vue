<template>
  <el-main>
      <el-row>
        <el-col :span="3">
          <div @click="gotoShouYe"><i class="iconfont5 icon-fanhui" style="font-size: 25px; "></i></div>
        </el-col>
        <el-col :span="12" :offset="11" style="font-family:STHupo;font-size: 30px;">
          <div>基本资料</div>
        </el-col>
      </el-row>



  <el-card class="box-card" >
    <div slot="header" class="clearfix" style="font-family:Helvetica Neue,serif;">
      <!--
      <img class="me-header-picture" :src=" "/>
      -->

      <el-row>
        <el-col  :span="2">
          <el-upload
            action="#"
            :headers="headers"
            :http-request="uploadFile1"
            :show-file-list="false"
            class="avatar-uploader"
            :before-upload="beforeAvatarUpload"
            name="file"
            :file-list="fileList">
            <div style="width:35px;height:35px;">
              <img v-if="info.avatar" :src="info.avatar" style="object-fit:contain;width:35px;height:35px; border-radius: 50%;" ></img>
              <div v-else></div>
            </div>


          </el-upload>
        </el-col>
        <el-col :span="12" :offset="2">
              <div style="vertical-align: center;"><span>{{info.account}}</span></div>
        </el-col>

      </el-row>
    </div>
    <div>
      <el-form label-width="80px" v-model="info" size="small" label-position="right">
        <el-form-item  prop="nickname">
           <span slot="label" style="width:150px">
                     <span class="span-box">
                      <i class="iconfont4 icon-user"></i><el-avatar icon="el-icon-user-solid"></el-avatar>昵称
                     </span>
           </span>
          <el-input  auto-complete="off" v-model="info.nickname" :disabled="!editable"></el-input>
        </el-form-item>
        <el-form-item  prop="email">
           <span slot="label" style="width:150px">
                     <span class="span-box">
                          <i class="iconfont4 icon-email"></i>
                       <span>email</span>
                     </span>
           </span>

          <el-input  auto-complete="off" v-model="info.email" :disabled="!editable"></el-input>
        </el-form-item>

        <el-form-item  prop="mobilePhoneNumber">
           <span slot="label" style="width:150px">
                     <span class="span-box">
                      <i class="iconfont4 icon-phone"></i>手机号
                     </span>
           </span>
          <el-input  auto-complete="off" v-model="info.mobilePhoneNumber" :disabled="!editable"></el-input>
        </el-form-item>

      </el-form>
    </div>
    <div>
      <el-main>
        <el-row>
          <el-col :span="5" :offset="3"> <el-button type="info" icon="el-icon-edit" circle v-on:click="editInfo"></el-button></el-col>
          <el-col :span="5" :offset="8"><el-button type="success" icon="el-icon-check" circle @click="saveInfo"></el-button></el-col>
        </el-row>
      </el-main>
    </div>
  </el-card>
  </el-main>

</template>
<style scoped>
.el-card{
  margin:40px auto;
  width:350px;
  height:370px;
  background-color: #41e0bb;
  transition: all .5s;

}

</style>

<script>
import axios from "axios";


import {editInfo, gainMyInfo, imageUpload} from "../api/login";

export default {
  name: "MyInfo",
  data() {
    return {
      input1: '1',
      info:{},
      editable:false,
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      fileList:[]

    }
  },
  methods:{

    gotoShouYe(){
      this.$router.push({path:"/"});
    },
    gainMyUserInfo(){
      let that = this
      gainMyInfo(localStorage.getItem("token")).then((data) => {
        that.info = data.data
        console.log(that.info)
      }).catch((error) => {
        if (error !== 'error') {
          that.$message({message: error, type: 'error', showClose: true});
        }
      })
    },
    editInfo(){
      let that = this;
      that.editable = true;
    },
    saveInfo(){
      if(this.editable) {

        let that = this;
        that.editable = false;
        editInfo(localStorage.getItem("token"), that.info).then((data) => {
          alert("修改成功");
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      }

    },
    beforeAvatarUpload(file)
    {
      const isIMAGE = file.type === 'image/jpeg'||'image/gif'||'image/png';
      const isLt1M = file.size / 1024 / 1024 < 1;

      if (!isIMAGE) {
        this.$message.error('上传文件只能是图片格式!');
      }
      if (!isLt1M) {
        this.$message.error('上传文件大小不能超过 1MB!');
      }
      return isIMAGE && isLt1M;
    },
    handlerAvatarSuccess(response,file,filelist){
      this.info.avatar = response.data.avatar
      alert("图片上传成功")

    },

    uploadFile1(item){
      //上传文件的需要form-data类型;所以要转
      const file = item.file,
        fileType = file.type,
      isImage = fileType.indexOf("image") != -1,
        isLt2M = file.size / 1024 / 1024 < 2;
      // 这里常规检验，看项目需求而定
      if (!isImage) {
        this.$message.error("只能上传图片格式png、jpg、gif!");
        return;
      }
      if (!isLt2M) {
        this.$message.error("只能上传图片大小小于2M");
        return;
      }
      let FormDatas = new FormData()
      let that = this;
      FormDatas.append('file',file)

      this.$http({
        method:"Post",
        url:"/changeUserInfo/savePicture",
        headers: {
          'Content-Type': 'multipart/form-data','Authorization': localStorage.getItem("token")
        },
        data:FormDatas
      }).then(function (res) {
        that.info.avatar = res.data.data
        console.log(res)
      }).catch(function (error){
        console.log(error)
      })

    },

  },
  created(){
    this.gainMyUserInfo();
  }

}

</script>

<style scoped>

</style>











