import service from '@/util/request.js'
export const testGetMethod = () => service.get('/test/get')
export const testGetHaveParamsMethod = (params) => service.get('/test/getHaveParams',params)
export const testPost = (params) => service.post('/test/post',params)
export const testPostJson = (params) => service.post('/test/postJson',params)
export const testPostFile = (params) => service.post('/test/postFile',params)