//package com.example.springCloud.util.work;
//
//import com.alibaba.fastjson.JSON;
//import com.mysql.jdbc.Driver;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StopWatch;
//
//import java.math.BigDecimal;
//import java.sql.*;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;
//
///**
// * 同步立项数据
// */
//
//@Slf4j
//public class ProjectDataTransfer {
//
//
//    private final static String secondaryProjectTypes = "1,1002,1155,1387,174,1379,184,1383,1133,1391,1359,1369,213,197,1228,1220,1216,962,1250";
//    private final static String secondaryProjectTypeName = "工程咨询,监理,检测,专项评价,水土保持(3),房屋排查,工程造价(4),房屋评估,测绘,消防检测,规划,地质灾害防治,工程设计,勘察,入库,电力设施,EPC,施工总承包,专业施工承包";
//
//
//    /**
//     * 非必填字段先不处理
//     */
//    @Test
//    public void transferDataDev() {
//
//        //查询修复数据
//        List<Project_old_data_transfer_log> project_old_data_transfer_logs = getProjectLogList();
//        List<String> oldProjectIds = project_old_data_transfer_logs.stream().map(Project_old_data_transfer_log::getOld_project_id).collect(Collectors.toList());
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("查询旧数据");
//        List<Market_clue> market_clues = getOldProjectList(oldProjectIds);
//        stopWatch.stop();
//        Map<String, Integer> secondaryProjectTypeNamesMap = new HashMap<>();
//        String[] strings = secondaryProjectTypeName.split(",");
//        for (int i = 0; i < strings.length; i++) {
//            secondaryProjectTypeNamesMap.put(strings[i], i);
//        }
//        AtomicInteger atomicInteger = new AtomicInteger(1);
//        //组装新系统立项数据
//        market_clues.forEach(market_clue -> {
//            log.info("编号{}-开始查询数据{}", atomicInteger.get(), JSON.toJSONString(market_clue));
//            try{
//                Project_register project_register = buildProjectRegister(market_clue, secondaryProjectTypeNamesMap);
//                log.info("编号{}-组装完成{}", atomicInteger.get(), JSON.toJSONString(project_register));
//            }catch (Exception e){
//                log.info("编号{}-组装异常",atomicInteger.get());
//            }
//            atomicInteger.incrementAndGet();
//        });
//    }
//
//
//    @Test
//    public void transferDataPro() {
//        //查询修复数据
//        List<Project_old_data_transfer_log> project_old_data_transfer_logs = getProjectLogList();
//        List<String> oldProjectIds = project_old_data_transfer_logs.stream().map(Project_old_data_transfer_log::getOld_project_id).collect(Collectors.toList());
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("查询旧数据");
//        List<Market_clue> market_clues = getOldProjectList(oldProjectIds);
//        stopWatch.stop();
//        System.out.println("数据总数:" + market_clues.size());
//        System.out.println("执行时间:" + stopWatch.prettyPrint());
//
//
//    }
//
//
//    /**
//     * 组装新系统立项数据
//     * todo crm 客户数据 ca 招标平台信息需要创建 使用feign调用
//     *
//     * @param market_clue
//     * @return
//     */
//    public Project_register buildProjectRegister(Market_clue market_clue, Map<String, Integer> secondaryProjectTypeNamesMap) {
//        Project_register project_register = new Project_register();
//        //是否招标
//        project_register.setIs_bid(("1".equals(market_clue.getIs_bid()) || "是".equals(market_clue.getIs_bid())));
//        //是否预立项
//        project_register.setIs_pre_approval(false);
//        //模版id
//        project_register.setTemplate_id(42L);
//        project_register.setTemplate_name("招投定制模板");
//        //招标公告--不处理旧数据 仅有一条具有招标公告ID
//        //查询租户信息
//        String tenantId = "";
//        if (StringUtils.isNoneBlank(market_clue.getSys_org_code())) {
//            //查询租户信息
//            tenantId = queryTenantParentId(market_clue.getSys_org_code());
//        }
//        //获取客户数据
//        if (StringUtils.isNoneBlank(market_clue.getCustomer_info_sub()) && StringUtils.isNoneBlank(tenantId)) {
//            //查询旧系统客户信息
//            Market_customer market_customer = queryOldMarketCustomer(market_clue.getCustomer_info_sub());
//            if (Objects.nonNull(market_customer)) {
//                //todo 在crm 创建客户信息
//            }
//        }
//        //
//        project_register.setBid_start_date(market_clue.getStart_date());
//        project_register.setBid_end_date(market_clue.getEnd_date());
//        project_register.setBid_open_date(market_clue.getBid_start_date());
//        project_register.setRegiste_date(market_clue.getRegiste_date());
//        project_register.setFloorage(market_clue.getFloorage());
//        project_register.setSource(market_clue.getSource());
//        if (StringUtils.isNoneBlank(market_clue.getProject_price())) {
//            project_register.setProject_price(new BigDecimal(market_clue.getProject_price()));
//        }
//        project_register.setEstimate_amount(market_clue.getEstimated_contract_amount());
//        project_register.setScale(market_clue.getProject_scale());
//        project_register.setRemark(market_clue.getRemark());
//        project_register.setRevoked("2".equals(market_clue.getRevoked()));
//        project_register.setTransfer(market_clue.getTransfer() != null && 1 == market_clue.getTransfer());
//        project_register.setAct_status(market_clue.getAct_status());
//        project_register.setBuild_room(market_clue.getBuild_room() != null && 1 == market_clue.getBuild_room());
//        project_register.setTenant_id(Long.valueOf(tenantId));
//        //责任公司所在地址
//        project_register.setCompany_addr(market_clue.getFz_province() + market_clue.getFz_city());
//        //所属公司
//        project_register.setBelong_company("首辅工程设计有限公司");
//        project_register.setBelong_company_id(0L);
//        //项目名称
//        project_register.setProject_name(market_clue.getName());
//        //项目名称简称
//        project_register.setSub_name(market_clue.getSub_name());
//        //项目编号
//        project_register.setSerial_number(market_clue.getSerial_number());
//        //项目类型
//        List<String> secondaryProjectTypeNames = Arrays.asList(secondaryProjectTypes.split(",").clone());
//        if (StringUtils.isNoneBlank(market_clue.getParams_stage())) {
//            //进行切割获取id列表
//            project_register.setParams_one_name(market_clue.getParams_stage());
//            String[] strings = market_clue.getParams_stage().split(",");
//            StringBuilder stringBuilder = new StringBuilder();
//            for (int i = 0; i < strings.length; i++) {
//                if (secondaryProjectTypeNamesMap.get(strings[i]) != null) {
//                    stringBuilder.append(secondaryProjectTypeNames.get(secondaryProjectTypeNamesMap.get(strings[i])));
//                    stringBuilder.append(",");
//                }
//            }
//            if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1 && stringBuilder.length() > 0) {
//                project_register.setParams_one_id(stringBuilder.substring(0, stringBuilder.length() - 1));
//            } else {
//                project_register.setParams_one_id(stringBuilder.toString());
//            }
//        }
//        return project_register;
//    }
//
//
//    //查询旧系统客户数据
//    public Market_customer queryOldMarketCustomer(String customer_info_sub) {
//        Market_customer market_customer = new Market_customer();
//        try {
//            Connection connection = getOldDataConnection();
//            Statement statement = connection.createStatement();
//            String sql = "select *from market_customer where id='" + customer_info_sub + "'";
//            ResultSet resultSet = statement.executeQuery(sql);
//            Map<String, Object> stringObjectMap = new HashMap<>();
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            int count = resultSetMetaData.getColumnCount();
//            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
//                for (int i = 1; i < count; i++) {
//                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
//                }
//                String json = JSON.toJSONString(stringObjectMap);
//                market_customer = JSON.parseObject(json, Market_customer.class);
//            }
//            statement.close();
//            connection.close();
//            return market_customer;
//        } catch (SQLException e) {
//            log.error("异常数据customer_info_sub={},e={}", customer_info_sub, e);
//            return null;
//        }
//    }
//
//    //通过父部门信息查询租户信息
//    public String queryTenantParentId(String orgCode) {
//        String tenantId = "";
//        try {
//            Connection connection = getOldMarketDataConnection();
//            Statement statement = connection.createStatement();
//            String sql = "select  id from sys_depart where org_code='" + orgCode + "'";
//            ResultSet resultSet = statement.executeQuery(sql);
//            resultSet.next();
//            String id = resultSet.getString(1);//第一行的第一列数据，我们知道是id，也知道是int类型，
//            if (StringUtils.isNoneBlank(id)) {
//                String sqlTemp = "select  tenant_id from operator_org_v2 where dept_id='" + id + "'";
//                ResultSet resultSetTemp = statement.executeQuery(sqlTemp);
//                resultSetTemp.next();
//                tenantId = resultSetTemp.getString(1);
//            }
//            statement.close();
//            connection.close();
//            return tenantId;
//        } catch (SQLException e) {
//            log.error("异常数据orgCode={},e={}", orgCode, e);
//            return tenantId;
//        }
//    }
//
//    //通过部门code 查询父节点信息
////    public String queryParentId(String orgCode) {
////        String parentId = "";
////        try {
////            Connection connection = getOldMarketDataConnection();
////            Statement statement = connection.createStatement();
////            String sql = "select  id from sys_depart where org_code='" + orgCode + "'";
////            ResultSet resultSet = statement.executeQuery(sql);
////            resultSet.next();
////            String id = resultSet.getString(1);//第一行的第一列数据，我们知道是id，也知道是int类型，
////            if (StringUtils.isNoneBlank(id)) {
////                //查询父节点信息
////                String sqlParentString = "SELECT T1.id,T1.depart_name,T1.parent_id\n" +
////                        "FROM sys_depart T1 \n" +
////                        "INNER JOIN (\n" +
////                        "\tSELECT\n" +
////                        "\t\t@id AS _id,\n" +
////                        "\t\t(SELECT @id := parent_id FROM sys_depart WHERE id = _id) AS parent_id,\n" +
////                        "\t\t@sort := @sort + 1 AS sort\n" +
////                        "\tFROM\n" +
////                        "\t\t(SELECT @id := '%s',@sort := 0) T2_1,sys_depart T2_2\n" +
////                        "\tWHERE\n" +
////                        "\t\t@id IS NOT NULL and @id != ''\n" +
////                        ") T2 ON T1.id = T2._id \n" +
////                        "ORDER BY T2.sort DESC;\n";
////
////                ResultSet resultSetTemp = statement.executeQuery(String.format(sqlParentString, id));
////                resultSetTemp.next();
////                parentId = resultSetTemp.getString(1);
////            }
////            statement.close();
////            connection.close();
////            return parentId;
////        } catch (SQLException e) {
////            e.printStackTrace();
////            return parentId;
////        }
////
////    }
//
//
//    /**
//     * 查询旧系统立项数据
//     *
//     * @param oldProjectIds
//     * @return
//     */
//    public static List<Market_clue> getOldProjectList(List<String> oldProjectIds) {
//        try {
//            Connection connection = getOldDataConnection();
//            Statement statement = connection.createStatement();
//            String sql = "";
//            if (CollectionUtils.isEmpty(oldProjectIds)) {
//                sql = "SELECT * from market_clue where del_flag=0";
//            } else {
//                sql = String.format("SELECT * from market_clue where del_flag=0 and id  not in (%s)", oldProjectIds.stream().collect(Collectors.joining()));
//            }
//            ResultSet resultSet = statement.executeQuery(sql);
//            List<Market_clue> market_clues = new ArrayList<>();
//            Map<String, Object> stringObjectMap = new HashMap<>();
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            int count = resultSetMetaData.getColumnCount();
//            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
//                for (int i = 1; i < count; i++) {
//                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
//                }
//                String json = JSON.toJSONString(stringObjectMap);
//                Market_clue market_clue = JSON.parseObject(json, Market_clue.class);
//                market_clues.add(market_clue);
//            }
//            statement.close();
//            connection.close();
//            return market_clues;
//        } catch (SQLException e) {
//            return null;
//        }
//    }
//
//    /**
//     * 查询已处理旧立项数据
//     *
//     * @return
//     */
//    public static List<Project_old_data_transfer_log> getProjectLogList() {
//        try {
//            Connection connection = getNewDataConnection();
//            Statement statement = connection.createStatement();
//            String sql = "SELECT * from project_old_data_transfer_log";
//            ResultSet resultSet = statement.executeQuery(sql);
//            List<Project_old_data_transfer_log> project_old_data_transfer_logs = new ArrayList<>();
//            Map<String, Object> stringObjectMap = new HashMap<>();
//            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
//            int count = resultSetMetaData.getColumnCount();
//            while (resultSet.next()) {//resultSet对象可能包含很多行数据，所以要是有while循环。
//                for (int i = 1; i < count; i++) {
//                    stringObjectMap.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
//                }
//                String json = JSON.toJSONString(stringObjectMap);
//                Project_old_data_transfer_log project_old_data_transfer_log = JSON.parseObject(json, Project_old_data_transfer_log.class);
//                project_old_data_transfer_logs.add(project_old_data_transfer_log);
//            }
//            statement.close();
//            connection.close();
//            return project_old_data_transfer_logs;
//        } catch (SQLException e) {
//            log.error("异常{}",e);
//            return null;
//        }
//    }
//
//
//    /**
//     * 获取旧系统数据库连接
//     *
//     * @return
//     */
//    public static Connection getOldDataConnection() {
//        try {
//            // 1.通过DriverManger注册驱动，注意此时Driver是在com.mysql.jdbc包中
//            DriverManager.registerDriver(new Driver());
//            /**
//             * 2.通过DriverManager获取连接对象
//             *
//             * jdbc:mysql://：这是固定的写法，表示使用jdbc连接mysql数据库
//             * localhost：ip地址，本地可以写成localhost。
//             * 3306：mysql的端口号。
//             * xia：数据库的名字。
//             * 第一个root：mysql的用户名
//             * 第二个root：mysql的密码。
//             */
//            Connection connection = DriverManager.getConnection("jdbc:mysql://172.27.115.201:3666/sfsj_market_dev?useUnicode=true&useSSL=false&characterEncoding=utf8", "superuser", "c!0bw*2S88");
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public static Connection getOldMarketDataConnection() {
//        try {
//            // 1.通过DriverManger注册驱动，注意此时Driver是在com.mysql.jdbc包中
//            DriverManager.registerDriver(new Driver());
//            /**
//             * 2.通过DriverManager获取连接对象
//             *
//             * jdbc:mysql://：这是固定的写法，表示使用jdbc连接mysql数据库
//             * localhost：ip地址，本地可以写成localhost。
//             * 3306：mysql的端口号。
//             * xia：数据库的名字。
//             * 第一个root：mysql的用户名
//             * 第二个root：mysql的密码。
//             */
//            Connection connection = DriverManager.getConnection("jdbc:mysql://172.27.115.201:3666/sfsj?useUnicode=true&useSSL=false&characterEncoding=utf8", "superuser", "c!0bw*2S88");
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 获取新系统数据库连接
//     *
//     * @return
//     */
//    public static Connection getNewDataConnection() {
//        try {
//            // 1.通过DriverManger注册驱动，注意此时Driver是在com.mysql.jdbc包中
//            DriverManager.registerDriver(new Driver());
//            /**
//             * 2.通过DriverManager获取连接对象
//             *
//             * jdbc:mysql://：这是固定的写法，表示使用jdbc连接mysql数据库
//             * localhost：ip地址，本地可以写成localhost。
//             * 3306：mysql的端口号。
//             * xia：数据库的名字。
//             * 第一个root：mysql的用户名
//             * 第二个root：mysql的密码。
//             */
//            Connection connection = DriverManager.getConnection("jdbc:mysql://172.27.115.201:3666/wygtech_projectapproval_dev?useUnicode=true&useSSL=false&characterEncoding=utf8", "superuser", "c!0bw*2S88");
//            return connection;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//}
