package com.zorbit.utils.eImporter.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * excel sheet column定义 po
 * @since 2013-6-24 下午4:35:31
 * @author biller zou
 */
public class Column implements Comparable<Column>{
    /**xml中配置的id*/
    private String id;
    
    /** 字段别名，excel中的表头列名称*/
    private String name;
    
    /**数据类型**/
    private String dataType;
    
    /**所属shee id*/
    private String sheetId;
    
    /**数据库字段名*/
    private String columnName;
    
    /**
     * 列下标
     */
    private int index;
    
    /**
     * 下拉框值的范围，用英文逗号隔开
     */
    private String valueRange;
    
    /***数据验证方法***/
    private String validateMethod;
    
    /**数据验证提示信息*/
    private Map<String, Object> validateMsg = new HashMap<String, Object>();
    
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
    
    public String getDataType() {
        return dataType;
    }
    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    public String getSheetId() {
        return sheetId;
    }
    
    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    
    public String getValueRange() {
        return valueRange;
    }

    public void setValueRange(String valueRange) {
        this.valueRange = valueRange;
    }

    
    public String getValidateMethod() {
        return validateMethod;
    }

    
    public void setValidateMethod(String validateMethod) {
        this.validateMethod = validateMethod;
    }

    
    public int getIndex() {
        return index;
    }

    
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(Column o) {
        return this.getIndex()-o.getIndex();
    }

    
    public Map<String, Object> getValidateMsg() {
        return validateMsg;
    }

    
    public void setValidateMsg(Map<String, Object> validateMsg) {
        this.validateMsg = validateMsg;
    }
    
    
}
