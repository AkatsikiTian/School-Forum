<template>
  <div style="font-size: 12px; line-height: 60px;display: flex;">
    <div style="flex: 1;font-size: 20px">
      <span :class="collapseButtonIcon" style="cursor: pointer" @click="collapse"></span>
    </div>
    <el-dropdown style="width:70px; cursor: pointer">
      <span>{{userInfo}}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item  @click.native="logout">退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseButtonIcon: String,
    collapse: Function
  },
  data(){
    return{
      userInfo:''
    }
  },
  methods:{
    getuserInfo(){
      this.userInfo=localStorage.getItem("account")
      console.log(this.userInfo)
    },
    logout(){
      console.log("执行了")
      this.request.get("/logout",{
        headers:{
          "Authorization": localStorage.getItem("token")
        }
      }).then(res=>{
        this.$store.commit("REMOVE_INFO")
        this.$router.push("/login")
      })
    }
  },created() {
    this.getuserInfo()
  }
}
</script>

<style scoped>

</style>