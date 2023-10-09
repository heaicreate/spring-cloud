package com.example.springCloud.excel;

import com.example.springCloud.util.MysqlUtil;
import com.example.springCloud.util.model.PersonnelCertificateEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ReadExcel {

    //    static List<String> strings = new ArrayList<>();
    static List<excelModel> excelModels = new ArrayList<>();

    public static void main(String[] args) {
        new ReadExcel().excelRegionReadStart(new File("/Users/hejun/Downloads/人资关系-dev.xlsx"));
        Connection connection = MysqlUtil.getConnection();
        List<addressRelation> addressRelations = new ArrayList<>();
        Map<String,String> valueMap=new HashMap<>();
        excelModels.forEach(s -> {
            addressRelation addressRelation=new addressRelation();
            String qualification_name = "0";
            String first_major = "";
            String qualification_name_name = "0";
            String first_major_name = "";
            if (StringUtils.isNoneBlank(s.getFirstName())) {
                PersonnelCertificateEnum personnelCertificateEnum = MysqlUtil.selectPersonnelCertificateEnum(s.getFirstName(), qualification_name, connection);
                if (Objects.nonNull(personnelCertificateEnum)) {
                    qualification_name = personnelCertificateEnum.getCode();
                    qualification_name_name=personnelCertificateEnum.getName();
                }
            }
            if (StringUtils.isNoneBlank(s.getSecondName())) {
                PersonnelCertificateEnum personnelCertificateEnum = MysqlUtil.selectPersonnelCertificateEnum(s.getSecondName(), qualification_name, connection);
                if (Objects.nonNull(personnelCertificateEnum)) {
                    qualification_name = personnelCertificateEnum.getCode();
                    qualification_name_name=personnelCertificateEnum.getName();
                }
            }
            if (StringUtils.isNoneBlank(s.getThirdName())) {
                PersonnelCertificateEnum personnelCertificateEnum = MysqlUtil.selectPersonnelCertificateEnum(s.getThirdName(), qualification_name, connection);
                if (Objects.nonNull(personnelCertificateEnum)) {
                    qualification_name = personnelCertificateEnum.getCode();
                    qualification_name_name=personnelCertificateEnum.getName();
                }
            }
            if (StringUtils.isNoneBlank(s.getMajor())) {
                PersonnelCertificateEnum personnelCertificateEnum = MysqlUtil.selectPersonnelCertificateEnum(s.getMajor(), qualification_name, connection);
                if (Objects.nonNull(personnelCertificateEnum)) {
                    first_major = personnelCertificateEnum.getCode();
                    qualification_name_name=personnelCertificateEnum.getName();
                }
            }

            addressRelation.setQualification_name_name(qualification_name_name);
            addressRelation.setQualification_name(qualification_name);
            addressRelation.setLicense_issuing_authority(s.getAddress());
            if (StringUtils.isNoneBlank(first_major_name)){
                addressRelation.setFirst_major_name(first_major_name);
            }else {
                addressRelation.setFirst_major_name(qualification_name_name);
            }
            if (StringUtils.isNoneBlank(first_major)){
                addressRelation.setFirst_major(first_major);
            }else {
                addressRelation.setFirst_major(qualification_name);
            }
            if (!valueMap.containsKey(addressRelation.getFirst_major())){
                addressRelations.add(addressRelation);
            }else{
                System.out.println("重复数据:"+s);
                return;
            }
            valueMap.put(addressRelation.getFirst_major(),"");


        });

        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        addressRelations.forEach(s -> {
            String insertSql = "    INSERT INTO `hr_employee_qualifications_organization_relation` (`id`, `qualification_name`, `qualification_name_name`, `registration_organization`, `first_major`, `first_major_name`, `create_by`, `create_time`, `update_by`, `update_time`, `license_issuing_authority`) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', NULL, NULL, NULL, NULL, '%s');";
            System.out.println(String.format(insertSql, UUID.randomUUID().toString(), s.getQualification_name(), s.getQualification_name_name(), "", s.getFirst_major(), s.getFirst_major_name(), s.getLicense_issuing_authority()));
        });
        System.out.println(addressRelations);
    }

    @Data
    class excelModel {

        private String firstName;
        private String secondName;
        private String thirdName;
        private String major;
        private String address;

    }

    public void excelRegionReadStart(File file) {
        long start = System.currentTimeMillis();
        InputStream inputStream = null;// 输入流对象
        XSSFWorkbook xssfWorkbook = null; //工作簿
        try {
            inputStream = new FileInputStream(file.getAbsolutePath());
            ;
            //定义工作簿
            xssfWorkbook = new XSSFWorkbook(inputStream);
            //获取第一个sheet
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //获取合并单元格信息的hashmap
            Map<String, Integer[]> mergedRegionMap = getMergedRegionMap(sheet);
            //拿到excel的最后一行的索引
            int lastRowNum = sheet.getLastRowNum();
            //从excel的第二行索行开始，遍历到最后一行（第一行是标题，直接跳过不读取）

            for (int i = 1; i <= lastRowNum; i++) {
                //拿到excel的行对象
                XSSFRow row = sheet.getRow(i);
                //获取excel的行中有多个列
                int cellNum = row.getLastCellNum();
//                StringBuilder stringBuilder = new StringBuilder();
                int num = 0;
                excelModel excelModel = new excelModel();
                //对每行进行列遍历，即一列一列的进行解析
                for (int j = 0; j < cellNum; j++) {
                    //拿到了excel的列对象
                    Cell cell = row.getCell(j);
                    String value = "";
                    //将列对象的行号和列号+下划线组成key去hashmap中查询，不为空说明当前的cell是合并单元列
                    Integer[] firstRowNumberAndCellNumber = mergedRegionMap.get(i + "_" + j);
                    //如果是合并单元列，就取合并单元格的首行和首列所在位置读数据，否则就是直接读数据
                    if (firstRowNumberAndCellNumber != null) {
                        XSSFRow rowTmp = sheet.getRow(firstRowNumberAndCellNumber[0]);
                        Cell cellTmp = rowTmp.getCell(firstRowNumberAndCellNumber[1]);
                        value = getCellValue(cellTmp);

                    } else {
                        value = getCellValue(cell);
                    }
                    if (num == 0) {
                        excelModel.setFirstName(value);
                        num++;
                        continue;
                    }

                    if (num == 1) {
                        excelModel.setSecondName(value);
                        num++;
                        continue;
                    }
                    if (num == 2) {
                        excelModel.setThirdName(value);
                        num++;
                        continue;
                    }
                    if (num == 3) {
                        excelModel.setMajor(value);
                        num++;
                        continue;
                    }
                    if (num == 4) {
                        excelModel.setAddress(value);
                        num++;
                        continue;
                    }
                }
//                strings.add(stringBuilder.substring(0, stringBuilder.length() - 1));
                excelModels.add(excelModel);
            }

        } catch (Exception e) {
            System.out.println(e);

        } finally {

            // 关闭文件流
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }

            // 关闭工作簿
            if (xssfWorkbook != null) {
                try {
                    xssfWorkbook.close();
                } catch (IOException e) {
                }
            }

        }

        long end = System.currentTimeMillis();
        System.out.println("spend ms: " + (end - start) + " ms.");


    }

    //将存在合并单元格的列记录入put进hashmap并返回
    public Map<String, Integer[]> getMergedRegionMap(Sheet sheet) {
        Map<String, Integer[]> result = new HashMap<String, Integer[]>();
        //获取excel中的所有合并单元格信息
        int sheetMergeCount = sheet.getNumMergedRegions();
        //遍历处理
        for (int i = 0; i < sheetMergeCount; i++) {
            //拿到每个合并单元格，开始行，结束行，开始列，结束列
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            //构造一个开始行和开始列组成的数组
            Integer[] firstRowNumberAndCellNumber = new Integer[]{firstRow, firstColumn};
            //遍历，将单元格中的所有行和所有列处理成由行号和下划线和列号组成的key，然后放在hashmap中
            for (int currentRowNumber = firstRow; currentRowNumber <= lastRow; currentRowNumber++) {

                for (int currentCellNumber = firstColumn; currentCellNumber <= lastColumn; currentCellNumber++) {
                    result.put(currentRowNumber + "_" + currentCellNumber, firstRowNumberAndCellNumber);
                }

            }

        }

        return result;
    }

    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    public String getCellValue(Cell cell) {

        if (cell == null) return "";

        if (cell.getCellType() == CellType.STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == CellType.BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == CellType.FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == CellType.NUMERIC) {

            return String.valueOf(cell.getNumericCellValue());

        }

        return "";

    }

    @Data
    static class addressRelation {
        private String qualification_name;
        private String qualification_name_name;
        private String first_major;
        private String first_major_name;
        private String license_issuing_authority;
    }

}