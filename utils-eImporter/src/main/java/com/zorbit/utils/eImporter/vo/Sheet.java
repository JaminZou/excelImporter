package com.zorbit.utils.eImporter.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * excel sheet 定义 po
 * @since 2013-6-24 下午4:34:38
 * @author biller zou
 */
public class Sheet {
    
    private Map<String, Column> columns;
    
    /** xml中配置的id */
    private String id;
    
    /** excle中的sheet 名称 */
    private String name;
    
    /** sheet中标题列行号 */
    private int headerRowNum;
    
    /** 所属excle在配置文件xml中的id */
    private String excelid;
    
    /**
     * 映射数据库表名称
     */
    private String tableName;
    
    /**
     * 标题
     */
    private String title;
    
    /** 下标 */
    private int index;
    
    /** 对应的java类 */
    private String classz;
    
    /**dao*/
    private String dao;
    
    public Map<String, Column> getColumns() {
        return columns;
    }
    
    
    public String getDao() {
        return dao;
    }

    
    public void setDao(String dao) {
        this.dao = dao;
    }

    /**
     * 已list形式得到sheet的列，并已index排序
     * @return
     * @author biller zou
     */
    public List<Column> getColumnAsListOrderByIndex() {
        List<Column> columns2 = new ArrayList<Column>();
        for (Entry<String, Column> entry : columns.entrySet()) {
            columns2.add(entry.getValue());
        }
        Collections.sort(columns2);
        return columns2;
    }
    
    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getHeaderRowNum() {
        return headerRowNum;
    }
    
    public void setHeaderRowNum(int headerRowNum) {
        this.headerRowNum = headerRowNum;
    }
    
    public String getExcelid() {
        return excelid;
    }
    
    public void setExcelid(String excelid) {
        this.excelid = excelid;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public String getClassz() {
        return classz;
    }
    
    public void setClassz(String classz) {
        this.classz = classz;
    }
    
}
