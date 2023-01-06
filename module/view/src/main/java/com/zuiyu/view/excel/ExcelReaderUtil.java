package com.zuiyu.view.excel;

import com.alibaba.fastjson.JSONObject;
import com.zuiyu.view.ResponseListener;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zuiyu
 * @date 2023/1/6
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class ExcelReaderUtil {
	//excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	//excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";


	private static final Logger log = LoggerFactory.getLogger(ExcelReaderUtil.class);

	/**
	 * 每获取一条记录，即打印
	 * 在flume里每获取一条记录即发送，而不必缓存起来，可以大大减少内存的消耗，这里主要是针对flume读取大数据量excel来说的
	 * @param sheetName
	 * @param sheetIndex
	 * @param curRow
	 * @param cellList
	 */
	public static JSONObject sendRows(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList, Map<Integer,String> titleMap) {
		JSONObject res = new JSONObject();
		res.put("filePath",filePath);
		res.put("sheetName",sheetName);
		res.put("sheetIndex",sheetIndex);
		res.put("curRow",curRow);
		JSONObject data = new JSONObject();
		for (int i = 0; i < cellList.size(); i++) {
			data.put(titleMap.get(i+1),cellList.get(i).trim());
		}
		res.put("data",data);
		return res;
	}
	public static void readExcel(String fileName, ResponseListener excelResponseListener) throws Exception {
		int totalRows =0;
		if (fileName.endsWith(EXCEL03_EXTENSION)) { //处理excel2003文件
			ExcelXlsReader excelXls=new ExcelXlsReader(excelResponseListener);
			totalRows =excelXls.process(fileName);
		} else if (fileName.endsWith(EXCEL07_EXTENSION)) {//处理excel2007文件
			// 使用自定义返回结果
			ExcelXlsxReaderWithDefaultHandler excelXlsxReader = new ExcelXlsxReaderWithDefaultHandler(excelResponseListener);
			totalRows = excelXlsxReader.process(fileName);
			// 使用默认返回结果
//			ExcelXlsxReaderWithSheetContentsHandler excelXlsxReaderWithSheetContentsHandler = new ExcelXlsxReaderWithSheetContentsHandler(fileName);
//			excelXlsxReaderWithSheetContentsHandler.setHandler(new ExcelXlsxReaderWithSheetContentsHandler.SimpleSheetContentsHandler());
//			使用自定义详细返回结果
//			excelXlsxReaderWithSheetContentsHandler.setHandler(new XSSFSheetXMLHandler.SheetContentsHandler() {
//				@Override
//				public void startRow(int i) {
//					log.info("startRow [{}]",i);
//				}
//
//				@Override
//				public void endRow(int i) {
//					log.info("endRow [{}]",i);
//
//				}
//
//				@Override
//				public void cell(String s, String s1, XSSFComment xssfComment) {
//					log.info("cell [{}:{}:{}]",s,s1,xssfComment);
//
//				}
//
//				@Override
//				public void headerFooter(String s, boolean b, String s1) {
//					log.info("headerFooter [{}:{}:{}]",s,b,s1);
//
//				}
//			});
//			excelXlsxReaderWithSheetContentsHandler.parse();
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
		log.info("记录总数：[{}]",totalRows);
	}

	public static void copyToTemp(File file,String tmpDir) throws Exception{
		FileInputStream fis=new FileInputStream(file);
		File file1=new File(tmpDir);
		if (file1.exists()){
			file1.delete();
		}
		FileOutputStream fos=new FileOutputStream(tmpDir);
		byte[] b=new byte[1024];
		int n=0;
		while ((n=fis.read(b))!=-1){
			fos.write(b,0,n);
		}
		fis.close();
		fos.close();
	}

}
