
import Vue from 'vue'
import App from './App'

import router from './router'
import store from './store'

import lodash from 'lodash'

import ElementUI from 'element-ui'
import '@/assets/theme/index.css'

import '@/assets/icon/iconfont.css'

import {formatTime} from "./utils/time";

import '@/assets/icon/shouye/iconfont.css'
import '@/assets/icon/biaoqian/iconfont.css'
import '@/assets/icon/classification/iconfont.css'
import '@/assets/icon/archive/iconfont.css'
import '@/assets/icon/return/iconfont.css'
import axios from 'axios'
Vue.prototype.$http= axios

Vue.config.productionTip = false

Vue.use(ElementUI)

Object.defineProperty(Vue.prototype, '$_', { value: lodash })


Vue.directive('title',  function (el, binding) {
  document.title = el.dataset.title
})
// 格式话时间
Vue.filter('format', formatTime)

new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
