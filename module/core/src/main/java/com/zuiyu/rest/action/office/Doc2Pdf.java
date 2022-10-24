package com.zuiyu.rest.action.office;

import com.zuiyu.rest.BaseRestHandler;
import com.zuiyu.rest.RestRequest;
import com.zuiyu.rest.action.HandlerEnum;
import com.zuiyu.service.AsposeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author zuiyu
 * @date 2022/10/22
 * @description
 * @link https://github.com/zuiyu-main
 */
public class Doc2Pdf extends AbstractWordAction {
    public final Logger log = LoggerFactory.getLogger(getClass());

    private String sourceFileType;
    private File sourceFile;
    private File targetFile;

    private List<String> include;
    private List<String> exclude;


    public final static String DOC2PDF = "DOC2PDF";

    @Override
    public String getName() {
        return DOC2PDF;
    }

    @Override
    public String getSourceFileType() {
        return sourceFileType;
    }

    @Override
    public String getTargetFileType() {
        return HandlerEnum.PDF.name();
    }

    @Override
    public File getSourceFile() {
        return sourceFile;
    }

    @Override
    public File getTargetFile() {
        return targetFile;
    }

    @Override
    public List<String> getIncludeType() {
        return include;
    }

    @Override
    public List<String> getExcludeType() {
        return exclude;
    }


    @Override
    public void doRequest(RestRequest request) throws IOException {
        log.info("doRequest ======> info");
        System.out.println(" w2pdf doReq:"+request.getFile().getAbsolutePath());

//        String path = "/Users/cxt/Downloads/tmp/test/";
//        File file = new File(path);
//        File[] files = file.listFiles();
//        System.out.println(files.length);
//        for (int i = 0; i < 20001; i++) {
//            System.out.println("当前第:"+i);

//            File file = new File("/Users/cxt/Downloads/tmp/test/"+i+".txt");
//            file.delete();
//            file.createNewFile();
//            FileOutputStream fos = new FileOutputStream(file);
//            fos.write(("sjfksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsk" +
//                    "sjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjfksjakfjkakjkfdksajkfdjsksjakfjkakjkfdksajkfdjsk测试一万个文件读取:"+i).getBytes());
//            fos.close();

//        }


    }

}
