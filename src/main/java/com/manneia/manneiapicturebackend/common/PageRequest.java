package com.manneia.manneiapicturebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luokaixuan
 * @description 通用分页请求参数
 * @created 2024/12/8 18:03
 */
@Data
public class PageRequest implements Serializable {

    /**
     * 当前页码
     */
    private String currentPage = "1";

    /**
     * 页面大小
     */
    private String pageSize = "10";

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序方式
     */
    private String sortOrder = "descend";

    private static final long serialVersionUID = -5473666511402316592L;

}
