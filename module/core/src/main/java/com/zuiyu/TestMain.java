package com.zuiyu;

import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.office.Doc2Pdf;

import java.io.File;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        Doc2Pdf doc2Pdf = new Doc2Pdf();
        doc2Pdf.handleRequest(new RestRequest("doc2pdf",new File("/opt/sourceFile")));
    }
}
