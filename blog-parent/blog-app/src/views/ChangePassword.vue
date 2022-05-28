<template>
<el-main>
  <el-row>
    <el-col :span="3">
      <div @click="gotoShouYe"><i class="iconfont5 icon-fanhui" style="font-size: 25px; "></i></div>
    </el-col>
      <el-col :span="3" :offset="8">
            <div style="width:80px;margin:20px auto;font-family:STHupo" >修改密码</div>
      </el-col>

  </el-row>
  <div style="width:350px;margin:10px auto;">
        <el-form label-width="80px"  ref="ruleForm" size="small" label-position="right" :rules="rules" :model="passwordParams" >
          <el-form-item  prop="oldPassword">
                   <span slot="label" style="width:150px">
                             <span class="span-box">
                              密码
                             </span>
                   </span>
                   <el-input  auto-complete="off" v-model="passwordParams.oldPassword" show-password></el-input>
          </el-form-item>
          <el-form-item  prop="newPassword">
                   <span slot="label" style="width:150px">
                             <span class="span-box">
                              新密码
                             </span>
                   </span>
                   <el-input  auto-complete="off" v-model="passwordParams.newPassword" show-password></el-input>
          </el-form-item>
      </el-form>
    <el-row>
      <el-col :span="3" :offset="3"><el-button v-on:click="confirmPassword">修改密码</el-button></el-col>
      <el-col :span="3" :offset="9"><el-button>重置密码</el-button></el-col>
    </el-row>
  </div>

</el-main>
</template>

<script>
export default {
  name: "ChangePassword.vue",

  data(){
    return {
      editable:false,
      password:"",
      passwordParams:{
        oldPassword:"",
        newPassword:"",
      },
      rules: {
        newPassword: [
          {required: true, message: '不能为空', trigger: 'blur'},
          {min: 1, max: 9, message: '长度在 1 到 9 个字符', trigger: 'blur'}
        ],
        oldPassword:[
          {required: true, message: '不能为空', trigger: 'blur'},
          {min: 1, max: 9, message: '长度在 1 到 9 个字符', trigger: 'blur'}
        ],

      },
    }
  },

  methods:{
    gotoShouYe(){
      this.$router.push({path:"/"});
    },
    confirmPassword(){
      this.$refs.ruleForm.validate((valid)=>{
          if(!valid){
            this.$message.error("密码填写有误")
            return false
          }
          let that = this
          //提交表单的
        this.$http({
          method:'Post',
          url:'/changePassword',
          data:that.passwordParams,
          headers:{
            'Authorization': localStorage.getItem("token")
          }
        }).then(res=>
        {
          if(res.data.code == 200) {
            this.$message.success("修改密码成功")
            this.$store.dispatch('logout').then(() => {
              this.$router.push({path: '/'})
            }).catch((error) => {
              if (error !== 'error') {
                that.$message({message: error, type: 'error', showClose: true});
              }
            })
          }
          else
            this.$message.error("密码不正确")

        }).catch(error=>{


        })

        })
    }


  }
}
</script>

<style scoped>

</style>
