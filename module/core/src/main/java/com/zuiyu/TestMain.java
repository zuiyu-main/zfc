package com.zuiyu;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.office.Word2Pdf;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        Word2Pdf word2Pdf = new Word2Pdf();
        word2Pdf.handleRequest(
                new RestRequest(Word2Pdf.DOC2PDF,
                        new File("/Users/cxt/Downloads/tmp/zfc/aspose.xml")));
    }
}
