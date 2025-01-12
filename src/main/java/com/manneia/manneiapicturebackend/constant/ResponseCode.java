package com.manneia.manneiapicturebackend.constant;

import lombok.Getter;

/**
 * @author luokaixuan
 * @description 错误码
 * @created 2024/12/8 17:14
 */
@Getter
@SuppressWarnings("unused")
public enum ResponseCode {

    SUCCESS("20000", "操作成功"),
    INVOKE_SUCCESS_CODE("20001", "接口调用成功"),
    INVOKE_ERROR_CODE("20002", "接口调用失败"),
    PARAMS_ERROR("40000", "请求参数错误"),
    PARAMS_NO_EXISTS_CODE("40020","请求参数不能为空"),
    NOT_LOGIN_ERROR("40100", "未登录"),
    NO_AUTH_ERROR("40101", "无权限"),
    FORBIDDEN_ERROR("40300", "禁止访问"),
    NOT_FOUND_ERROR("40400", "请求数据不存在"),
    USER_EXIST_ERROR("40401", "账号已存在"),
    SYSTEM_ERROR("50000", "系统错误"),
    OPERATION_ERROR("50001", "操作失败"),
    INSTANTIATION_ERROR("50002", "工具类，禁止实例化"),
    INTERIOR_SYSTEM_ERROR("500003", "内部系统错误"),
    SQL_ERROR("50004", "数据库内部异常"),
    EXPORT_EXCEL_ERROR("50005", "导出excel失败"),
    IMPORT_EXCEL_ERROR("50006", "导入excel失败"),
    INPUT_DATE_FORMAT_NO_EXISTS("50007", "输入日期格式不存在"),
    OUTPUT_DATE_FORMAT_NO_EXISTS("50008", "输出日期格式不存在");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
