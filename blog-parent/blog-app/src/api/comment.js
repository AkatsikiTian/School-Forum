import request from '@/request'


export function getCommentsByArticle(id) {
  return request({
    url: `/comments/article/${id}`,
    method: 'get'
  })
}

export function publishComment(comment,token) {
  return request({
    headers: {'Authorization': token},
    url: '/comments/create/change',
    method: 'post',
    data: comment
  })
}

export function deleteMyCommentById(id,article) {
  return request({
    url: `/comments/delete/`,
    method: 'post',
    data: {
      id: id,
      articleid: article
    }
  })
}
