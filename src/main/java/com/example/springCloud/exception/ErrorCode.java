package com.example.springCloud.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, 10000, "服务异常，请稍后重试"),

    BAD_REQUEST(500, 10001, "请求失败，请重试"),

    UNAUTHORIZED(401, 10002, "权限不足"),

    FORBIDDEN(403, 10003, "禁止访问"),

    PARAM_ERROR(400, 10004, "参数错误"),

    FILE_CHECK_ERROR(400, 10009, "Excel数据检查失败"),

    FILE_PARSING_ERROR(400, 10010, "Excel解析失败"),

    NOT_LOGIN(401, 10005, "用户未登录"),

    RESOURCE_NOT_FOUND(404, 10008, "请求资源不存在"),

    REQUEST_TIMEOUT(504, 10006, "请求超时，请重试"),

    PARTNER_SYSTEM_ERROR(500, 10007, "请求合作方系统失败"),

    ACTIVITY_RULE_VIOLATE(400,40001,"不符合活动规则"),

    ACTIVITY_RESOURCE_LOW(400,40002,"活动资源有限"),

    ACTIVITY_REMAIN_TIMES_OVER_ERROR(400,40010,"已达每日上限"),

    ACTIVITY_ACTION_ERROR(400,40011,"操作失败"),

    ACTIVITY_CREDIT_LACK_ERROR(400,40012,"小帮币余额不足啦，去赚更多吧～"),

    ORDER_CENTER_GOODS_SKU_NOT_EXIST(400, 50001, "商品信息不存在或已下架"),

    HAS_GOT_INSURANCE_SERVICE_ERROR(400, 40020, "已领取保险服务"),

    HAS_GOT_COURSE_ERROR(400, 40021, "已购买过课程"),

    CODE_DUPLICATE(400, 40022, "code重复，请重新创建渠道"),

    QA_CODE_FAIL(400, 40023, "二维码创建失败"),

    NO_DATA_DOWNLOAD(400, 40024, "没有下载的数据"),
    CHANNEL_NAME_DUPLICATE(400, 40025, "渠道名称重复"),

    FLOW_RESERVATION_ORDER_CREATE_ERROR(400, 40030, "生成预约订单异常"),

    FLOW_NOT_EXIST_ERROR(400, 40031, "流程不存在或已禁用"),

    ORDER_PRICE_ERROR(400, 40040, "订单金额错误"),

    NO_PERMISSION_CHANNEL(400, 40041, "没有该渠道权限"),

    USER_REPOST_OVERSTEP(400, 50041, "用户转介绍名额超出限制"),

    TRACK_VARIABLE_IMPORT_SN_IS_NULL_ERROR(400, 50101, "变量标识符不能为空"),

    TRACK_VARIABLE_IMPORT_NAME_IS_NULL_ERROR(400, 50102, "变量显示名不能为空"),

    TRACK_VARIABLE_IMPORT_SN_CONTAIN_SPACE_ERROR(400, 50104, "变量标识符不能包含空格"),

    TRACK_VARIABLE_IMPORT_DATA_FORMAT_IS_NULL_ERROR(400, 50103, "变量数据类型有误"),

    TRACK_VARIABLE_IMPORT_SN_OR_NAME_REPEATED_ERROR(400, 50105, "变量标识符或名称重复"),

    TRACK_VARIABLE_IMPORT_ERROR(400, 50106, "变量导入错误"),

    TRACK_VARIABLE_HAS_BEEN_REF_ERROR(400, 50107, "此变量有关联的事件，请先删除事件"),

    TRACK_EVENT_IMPORT_SN_OR_NAME_REPEATED_ERROR(400, 50108, "事件标识符或名称重复"),

    TRACK_EVENT_IMPORT_ERROR(400, 50109, "事件导入错误"),

    TRACK_EVENT_IMPORT_SN_IS_NULL_ERROR(400, 50110, "事件标识符不能为空"),

    TRACK_EVENT_IMPORT_NAME_IS_NULL_ERROR(400, 50111, "事件显示名不能为空"),

    TRACK_EVENT_IMPORT_PLATFORM_IS_NULL_ERROR(400, 50112, "事件平台不能为空"),

    TRACK_EVENT_BUSINESS_ERROR(400, 50113, "事件业务有误"),

    TRACK_EVENT_PLATFORM_ERROR(400, 50114, "事件平台有误"),

    TRACK_EVENT_DATA_GATE_ERROR(400, 50116, "事件数据通道有误"),

    TRACK_EVENT_VARIABLE_ERROR(400, 50115, "事件变量有误"),

    TRACK_IMPORT_OVER_SIZE_ERROR(400, 50116, "单次导入数据量不能超过1000条"),

    TRACK_EVENT_IMPORT_SN_OR_NAME_REPEATED_EXCEL_ERROR(400, 50117, "表内事件标识符或名称重复"),

    TRACK_EVENT_IMPORT_SN_CONTAIN_SPACE_ERROR(400, 50118, "事件标识符不能包含空格和回车"),

    NO_SUBMIT_FORM(400, 60041, "表单未提交"),


    ;

    private int status;
    private int code;
    private String message;
    
}
