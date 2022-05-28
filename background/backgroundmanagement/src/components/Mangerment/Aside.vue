<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;
        overflow-x:hidden;" background-color="#304156" text-color="#fff" :collapse="isCollapsed"
           :collapse-transition="false" router :default-active="$route.path">
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../../assets/logo.png" style="width: 15px;margin-right: 5px">
      <b style="color: #ebeff3" v-show="textshow">后台管理系统</b>
    </div>
    <el-menu-item index="/manger/home">
        <i class="el-icon-s-home"></i>
        <span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/manger/user">
          <i class="el-icon-s-custom"></i>
          <span slot="title">成员管理</span>
        </el-menu-item>
        <el-menu-item index="/manger/tag">
          <i class="el-icon-collection-tag"></i>
          <span slot="title">标签管理</span>
        </el-menu-item>
        <el-menu-item index="/manger/category">
          <i class="el-icon-s-grid"></i>
          <span slot="title">文章分类管理</span>
        </el-menu-item>
        <el-menu-item index="/manger/article">
          <i class="el-icon-s-management"></i>
          <span slot="title">帖子管理</span>
        </el-menu-item>
        <el-menu-item index="/manger/waitarticle">
          <i class="el-icon-s-management"></i>
          <span slot="title">帖子审核</span>
        </el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapsed: Boolean,
    textshow: Boolean
  },
  data(){
    return{
  }
  },methods:{
  }
}
</script>

<style scoped>

</style>