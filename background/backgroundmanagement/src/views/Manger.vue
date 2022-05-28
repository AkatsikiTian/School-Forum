<template>
  <div>
    <el-container style="min-height: 100vh;">
      <el-aside :width="sidewidth+'px'" style="background-color: rgb(238, 241, 246);">
        <Aside :isCollapsed="isCollapsed" :textshow="textshow"/>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapse="collapse" :collapseButtonIcon="collapseButtonIcon"/>
        </el-header>

        <el-main>
<!--          表示当前页面的子路由会在router-view里展示-->
          <div v-if="$route.meta.title!='首页'">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/manger' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{$route.meta.title}}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>
          <!--主页-->
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<style>
.el-header {
  background-color: #ebeff3;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

</style>
<script>
import request from "@/utils/request";
import Aside from "@/components/Mangerment/Aside";
import Header from "@/components/Mangerment/Header";
export default {
  data() {
    return {
      isCollapsed: false,
      sidewidth: 200,
      collapseButtonIcon: "el-icon-s-fold",
      textshow: true,
    }
  },
  methods: {
    collapse() {//点击收缩
      this.isCollapsed = !this.isCollapsed;
      this.textshow = !this.textshow;
      if (this.isCollapsed) {
        this.sidewidth = 64;
        this.collapseButtonIcon = "el-icon-s-unfold"
      } else {
        this.sidewidth = 200;
        this.collapseButtonIcon = "el-icon-s-fold"
      }
    }
  },
  components: {
    Header,
    Aside
  }
};
</script>
