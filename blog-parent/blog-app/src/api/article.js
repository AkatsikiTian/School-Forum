import request from '@/request'


export function getArticles(query, page) {
  return request({
    url: '/articles',
    method: 'post',
    data: {
      page: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getHotArtices() {
  return request({
    url: '/articles/hot',
    method: 'post'
  })
}

export function getNewArtices() {
  return request({
    url: '/articles/new',
    method: 'post'
  })
}

export function viewArticle(id) {
  return request({
    url: `/articles/view/${id}`,
    method: 'post'
  })
}

export function getArticlesByCategory(id) {
  return request({
    url: `/articles/category/${id}`,
    method: 'post'
  })
}

export function getArticlesByTag(id) {
  return request({
    url: `/articles/tag/${id}`,
    method: 'post'
  })
}


export function publishArticle(article,token) {
  return request({
    headers: {'Authorization': token},
    url: '/articles/publish',
    method: 'post',
    data: article
  })
}

export function listArchives() {
  return request({
    url: '/articles/listArchives',
    method: 'post'
  })
}

export function getArticleById(id) {
  return request({
    url: `/articles/${id}`,
    method: 'post'
  })
}

export function deleteArticleById(id) {
  return request({
    url: `/articles/delete/${id}`,
    method: 'post'
  })
}

export function deleteArticleBody(id) {
  return request({
    url: `/articles/delete/body/${id}`,
    method: 'post'
  })
}

export function deleteArticleTag(id) {
  return request({
    url: `/articles/delete/Tag/${id}`,
    method: 'post'
  })
}

export function deleteArticleComment(id) {
  return request({
    url: `/articles/delete/Comment/${id}`,
    method: 'post'
  })
}

export function myNewArticle(id) {
  return request({
    url: `/articles/viewme/${id}`,
    method: 'post'
  })
}

export function searchArticle(search) {
  return request({
    url: '/articles/search',
    method: 'post',
    data:{"search":search}
  })
}
