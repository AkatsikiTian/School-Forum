import Vue from 'vue'
import Vuex from 'vuex'
import request from "@/utils/request";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token:'',
    account:''
  },
  getters: {
    //get方法
    getUser: state => {
      return state.account
    }
  },
  mutations: {
    //set方法
    SET_TOKEN: ((state, token) => {
      state.token = token
      localStorage.setItem("token",token)
    }),
    SET_ACCOUNT: ((state, account) => {
      state.account = account
      localStorage.setItem("account",account)
    }),
    REMOVE_INFO:(state => {
      state.token=''
      state.account=''
      localStorage.setItem("token",'')
      localStorage.setItem("account",'')
    })
  },
  actions: {
  },
  modules: {
  }
})
