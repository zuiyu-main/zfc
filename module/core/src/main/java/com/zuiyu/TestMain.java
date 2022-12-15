package com.zuiyu;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.ConvertTypeEnum;
import com.zuiyu.rest.action.office.Text2Pdf;
import com.zuiyu.rest.channel.DefaultRestChannel;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        Text2Pdf text2Pdf = new Text2Pdf();
        RestRequest restRequest = new RestRequest("",
                new File("/Users/cxt/Downloads/tmp/zfc/test-word2pdf.docx"), ConvertTypeEnum.ITEXT.name());


        DefaultRestChannel defaultRestChannel = new DefaultRestChannel(restRequest);
        text2Pdf.handleRequest(
                restRequest,
                defaultRestChannel
        );

        System.out.println("result========");
        System.out.println(defaultRestChannel.content);
        System.out.println(defaultRestChannel.status);
    }
}
