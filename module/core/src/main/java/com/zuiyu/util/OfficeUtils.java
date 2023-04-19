package com.zuiyu.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

/**
 * @Author zuiyu
 * @Date 2023/4/18 12:23
 */
public class OfficeUtils {
    public static boolean isOldWordDocument(String filename) {
        return filename.toLowerCase(Locale.ROOT).endsWith(".doc");
    }

    public static boolean isNewWordDocument(String filename) {
        return filename.toLowerCase(Locale.ROOT).endsWith(".docx");
    }
    /**
     * 97-2003
     * doc
     * @param filePath
     * @return
     */
    public static boolean isOldWordDocumentDoc(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            new HWPFDocument(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 2007
     * docx
     * @param filePath
     * @return
     */
    public static boolean isNewWordDocumentDocx(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            new XWPFDocument(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isOldWordDocumentDoc(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            new HWPFDocument(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNewWordDocumentDocx(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            new XWPFDocument(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static boolean isOldExcelDocumentXls(String filename) {
        return filename.endsWith(".xls");
    }

    public static boolean isNewExcelDocumentXlsx(String filename) {
        return filename.endsWith(".xlsx");
    }

    public static boolean isOldExcelDocumentXls(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isNewExcelDocumentXlsx(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
