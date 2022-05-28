import Vue from 'vue'
import VueRouter from 'vue-router'
import Manger from '../views/Manger.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/manger',
    name: 'manger',
    component: Manger,
    redirect: "/manger/home",
    meta:{
      requireAuth: true
    },
    children: [
        {
      path:'home',
      name:'home',
      component: () => import(/* webpackChunkName: "about" */ '../views/Home.vue'),
      meta: {title: '首页'}
    },
    {
      path:'user',
      name:'User',
      component: () => import(/* webpackChunkName: "about" */ '../views/User.vue'),
      meta: {title: '用户管理'}
    },
    {
      path:'tag',
      name:'Tag',
      component: () => import(/* webpackChunkName: "about" */ '../views/Tag.vue'),
      meta: {title: '标签管理'}
    },
    {
      path:'category',
      name:'Category',
      component: () => import(/* webpackChunkName: "about" */ '../views/Category.vue'),
      meta: {title: '文章分类管理'}
    },
      {
        path:'article',
        name:'Article',
        component: () => import(/* webpackChunkName: "about" */ '../views/Article.vue'),
        meta: {title: '帖子管理'}
      },
      {
        path:'waitarticle',
        name:'WaitArticle',
        component: () => import(/* webpackChunkName: "about" */ '../views/WaitArticle.vue'),
        meta: {title: '帖子审核'}
      },
    ]
  },
  ,{
    path: '/login',
    name: 'login',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/',
    redirect: "/login",
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
