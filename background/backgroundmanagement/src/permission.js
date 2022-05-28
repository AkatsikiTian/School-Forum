import router from "@/router";

//路由判断登录 根据路由配置文件参数
router.beforeEach((to,from,next)=>{
    if (to.matched.some(record=>record.meta.requireAuth)){//判断当前token是否存在
        const token = localStorage.getItem("token")
        if (token){
            if(to.path === '/login'){

            }else{
                next()
            }
        }else{
            next({
                path:'/login'
            })
        }
    }else{
        next()
    }
})