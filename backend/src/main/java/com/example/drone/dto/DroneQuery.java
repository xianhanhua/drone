/**
 * 文件说明：无人机查询条件对象，保存关键词和状态筛选条件。
 */
package com.example.drone.dto;

public class DroneQuery {

    // 前端输入的原始搜索关键词。
    private String keyword;

    // 给 SQL LIKE 查询使用的关键词，会自动加上 %。
    private String keywordLike;

    // 状态筛选条件，为空时表示查询全部状态。
    private String status;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        // 用户输入空字符串时不加查询条件；否则转成小写并包上 %，方便模糊查询。
        this.keywordLike = keyword == null || keyword.trim().isEmpty()
                ? null
                : "%" + keyword.trim().toLowerCase() + "%";
    }

    public String getKeywordLike() {
        return keywordLike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // 状态为空时不筛选；有值时去掉前后空格。
        this.status = status == null || status.trim().isEmpty() ? null : status.trim();
    }
}
