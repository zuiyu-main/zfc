import service from '@/util/request.js'

/**
 * 文件转换
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const formUploadFile =
    (params) => service.post('/convert/file',params,{
        headers: {"Content-Type": "multipart/form-data"}
    })
/**
 * 获取转换方式支持的文件类型
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getSupportType =
    (params) => service.post('/convert/getSupportType',params,{
        headers: {"Content-Type": "multipart/form-data"}
    })