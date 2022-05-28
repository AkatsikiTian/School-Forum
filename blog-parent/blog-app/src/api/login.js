import request from '@/request'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout(token) {
  return request({
    headers: {'Authorization': token},
    url: '/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  return request({
    headers: {'Authorization': token},
    url: '/users/currentUser',
    method: 'get'
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

export function gainMyInfo(token){

  return request({
    headers: {'Authorization': token},
    url: '/users/getUserInfo',
    method: 'get'
  })
}


export  function editInfo(token,info){
  return request({
    headers: {'Authorization': token},
    url: '/changeUserInfo',
    method: 'post',
    data:info
  })


}
