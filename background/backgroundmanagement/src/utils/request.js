import axios from 'axios'
import Vue from "vue";

const request = axios.create({
    baseURL: 'http://localhost:8182',  // 注意！！ 这里是全局统一加上了 后端接口前缀 前缀，后端必须进行跨域配置！
    timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    var token = localStorage.getItem("token");
    // config.headers['token'] = token;  // 设置请求头
    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response;
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res.data === 'string') {
            res.data = res.data ? JSON.parse(res.data) : res.data
        }
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        Vue.prototype.$message.error('请求超时')
        return Promise.resolve(error.response)
    }
)


export default request