package com.zuiyu.boot.util;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

/**
 * @Author zuiyu
 * @Date 2023/4/11 14:17
 */
public class ZRequestUtils {
    public static ZwResultVo sendPostRequest(String url, MultiValueMap<String, String> params){
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<ZwResultVo> response = client.exchange(url, method, requestEntity, ZwResultVo.class);
        return response.getBody();
    }

    public static ZwResultVo sendPostJsonRequest(String url, MultiValueMap<String, Object> params){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(params, headers);
        ZwResultVo response = restTemplate.postForObject(url, entity, ZwResultVo.class);
        return response;
    }

    public static ZwResultVo sendPostFileRequest(String url,File file){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource(file));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(url, requestEntity, ZwResultVo.class);

    }

    public class ZwResultVo{
        private Integer code;
        private Object data;
    }
}
