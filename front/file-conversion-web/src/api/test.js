import service from '@/util/request.js'

export const testGetMethod = () => service.get('/test/get')
export const testGetHaveParamsMethod =
    (params) => service.get('/test/getHaveParams',{params:params})

export const testPost = (params) =>
    service.post('/test/post',
        params,
        {
            headers: {"Content-Type": "application/x-www-form-urlencoded"}
        }
)
export const testPostJson =
    (params) => service.post('/test/postJson',params)

export const testPostFile =
    (params) => service.post('/test/postFile',params,{
        headers: {"Content-Type": "application/x-www-form-urlencoded"}
    })