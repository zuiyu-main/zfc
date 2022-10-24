package com.zuiyu.service;

import com.aspose.words.License;

import java.io.InputStream;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class AsposeService {

    public static boolean getLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeService.class.getClassLoader().getResourceAsStream("aspose.xml"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
