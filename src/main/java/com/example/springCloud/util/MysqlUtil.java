package com.example.springCloud.util;

import com.example.springCloud.util.model.DateModel;
import com.example.springCloud.util.model.PersonnelCertificateEnum;
import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtil {
    public static Connection getConnection() {
        try {
            // 1.通过DriverManger注册驱动，注意此时Driver是在com.mysql.jdbc包中
            DriverManager.registerDriver(new Driver());
            /**
             * 2.通过DriverManager获取连接对象
             *
             * jdbc:mysql://：这是固定的写法，表示使用jdbc连接mysql数据库
             * localhost：ip地址，本地可以写成localhost。
             * 3306：mysql的端口号。
             * xia：数据库的名字。
             * 第一个root：mysql的用户名
             * 第二个root：mysql的密码。
             */
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "123456");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




    public static void insertData(List<DateModel> dateModels, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            StringBuilder stringBuilder=new StringBuilder("insert into data (blue,blue2,date,red,code) values");
            for (int i=0;i<dateModels.size();i++){
                if (i==dateModels.size()-1){
                    stringBuilder.append(String.format("('%s','%s','%s','%s','%s');",dateModels.get(i).getBlue(),dateModels.get(i).getBlue2(),dateModels.get(i).getDate(),dateModels.get(i).getRed(),dateModels.get(i).getCode()));
                }else {
                    stringBuilder.append(String.format("('%s','%s','%s','%s','%s'),",dateModels.get(i).getBlue(),dateModels.get(i).getBlue2(),dateModels.get(i).getDate(),dateModels.get(i).getRed(),dateModels.get(i).getCode()));

                }
            }
            statement.execute(stringBuilder.toString());
        } catch (Exception e) {

        }

    }

    public static PersonnelCertificateEnum selectPersonnelCertificateEnum(String name, String parent_code, Connection connection) {
        try {
            //1，得到Connection对象，
            //2，通过Connection获取一个操作sql语句的对象Statement
            Statement statement = connection.createStatement();
            //3，拼接sql语句
            String sql = "select `code`,name,parent_code from personnel_certificate_enum where name='%s' and parent_code='%s'";
            sql = String.format(sql, name, parent_code);
            //4，查询，返回的结果放入ResultSet对象中。
            ResultSet resultSet = statement.executeQuery(sql);
            //5，将游标后移一位
            resultSet.next();
            //6，获取数据
            String code = resultSet.getString(1);//第一行的第一列数据，我们知道是id，也知道是int类型，
            String nameVale = resultSet.getString(2);//第二个数据对应name
            String parentCode = resultSet.getString(3);//第三个数据对应age
            PersonnelCertificateEnum personnelCertificateEnum = new PersonnelCertificateEnum();
            personnelCertificateEnum.setCode(code);
            personnelCertificateEnum.setName(nameVale);
            personnelCertificateEnum.setParentCode(parentCode);
            statement.close();

            return personnelCertificateEnum;
        } catch (SQLException e) {
//            e.printStackTrace();
            return null;
        }
    }


    public static List<PersonnelCertificateEnum> getList() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT  `code`,name,parent_code from personnel_certificate_enum_filter where name !=''";
            ResultSet resultSet = statement.executeQuery(sql);
            List<PersonnelCertificateEnum> personnelCertificateEnums = new ArrayList<>();
            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
                String code = resultSet.getString(1);//第一行的第一列数据，我们知道是id，也知道是int类型，
                String nameTemp = resultSet.getString(2);//第二个数据对应name
                String parentCode = resultSet.getString(3);//第三个数据对应age
                PersonnelCertificateEnum personnelCertificateEnum = new PersonnelCertificateEnum();
                personnelCertificateEnum.setCode(code);
                personnelCertificateEnum.setName(nameTemp);
                personnelCertificateEnum.setParentCode(parentCode);
                personnelCertificateEnums.add(personnelCertificateEnum);
            }
            statement.close();
            connection.close();
            return personnelCertificateEnums;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
