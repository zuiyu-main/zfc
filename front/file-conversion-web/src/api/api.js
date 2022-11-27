import service from '@/util/request.js'

export const formUploadFile =
    (params) => service.post('/convert/file',params,{
        headers: {"Content-Type": "multipart/form-data"}
    })