package com.example.springCloud.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreatRowTest {
    //当前文件已经存在
    private  static String excelPath ="/Users/hejun/Documents/企业所得税纳税申报表-（汇算清缴）模板.xlsx";
    //从第几行插入进去
    private int insertStartPointer = 0;
    //在当前工作薄的那个工作表单中插入这行数据
    private String sheetName = "A000000企业基础信息表";

    /**
     * 总的入口方法
     */
    public static void main(String[] args) throws Exception {
        FileInputStream inputStream = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int insertRowIndex = 0; // 指定的行号，例如在第3行前插入新行
        sheet.shiftRows(insertRowIndex, sheet.getLastRowNum(), 1, true, false);

        Row newRow = sheet.createRow(insertRowIndex);
        for (int i = 0; i < 3; i++) {
            Cell newCell = newRow.createCell(i);
            newCell.setCellValue("New Cell " + i);
        }

        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream("/Users/hejun/Documents/企业所得税纳税申报表-（汇算清缴）模板1.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
