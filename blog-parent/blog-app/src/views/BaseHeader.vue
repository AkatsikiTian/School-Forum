<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/img/logo.png" />
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="10">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/"><i class="iconfont1 icon-shouye"></i>首页</el-menu-item>
          <el-menu-item index="/category/all"><i class="iconfont3 icon-sort"></i>文章分类</el-menu-item>
          <el-menu-item index="/tag/all"><i class="iconfont2 icon-biaoqian"></i>标签</el-menu-item>
          <el-menu-item index="/archives"><i class="iconfont4 icon-archive"></i>文章归档</el-menu-item>

          <el-col :span="4" :offset="2">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>写文章</el-menu-item>
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>
<!--      搜索-->
      <el-col :span="6">
        <el-menu mode="horizontal" active-text-color="#5FB878">
          <el-menu-item>


            <template>
              <el-autocomplete
                v-model="search"
                :fetch-suggestions="querySearchAsync"
                placeholder="请输入搜索内容"
                @select="handleSelect"
              ></el-autocomplete>
            </template>
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="4">
        <el-menu :router=true menu-trigger="click" mode="horizontal"
                 active-text-color="#5FB878">

          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">登录</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else>
            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>
              </template>
              <el-menu :default-active="activeIndex" class="el-menu-demo" mode="vertical">
                <el-row>
                  <el-col>
                    <el-menu-item @click="gainMyInfo"><i></i>个人信息 </el-menu-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-menu-item @click="changePassword"><i></i>修改密码 </el-menu-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col>
                    <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
                  </el-col>
                </el-row>
              </el-menu>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
  import {searchArticle} from "../api/article";

  export default {
    name: 'BaseHeader',
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        search: '',
        articles: []
      }
    },
    computed: {
      user() {
        let login = this.$store.state.account.length != 0
        let avatar = this.$store.state.avatar
        return {
          login, avatar
        }
      }
    },
    methods: {
      logout() {
        let that = this
        this.$store.dispatch('logout').then(() => {
          this.$router.push({path: '/'})
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      },
      querySearchAsync(queryString, cb){
        searchArticle(this.search).then((res)=>{
          if (res.success){
            var results = [];
            for (const item of res.data){
              results.push({
                id:item.id,
                value:item.title+"   by  "+item.author
              });
            }
            cb(results)
          }
        })
      },
      handleSelect(item) {
        this.$router.push({path: '/view/' + item.id})
      },
      gainMyInfo() {
        let that = this
        that.$router.push({path: '/MyInfo1'})
      },
      changePassword(){
        this.$router.push({path:'/changePassword'})
      }
    }
  }
</script>

<style>

  .el-header {
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
  }

  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 10px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }
</style>
