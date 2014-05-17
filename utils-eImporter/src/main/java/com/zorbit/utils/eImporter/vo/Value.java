package com.zorbit.utils.eImporter.vo;

import java.io.Serializable;

/**
 *内存中保存的 值对象
 * @since 2013-6-24 下午6:05:16
 * @author biller zou
 */
public class Value implements Serializable{
    private static final long serialVersionUID = 2804045290617812120L;
    
    /***值*/
    private String value;
    /*** 验证信息*/
    private String validateInfo;
    /**
     * 是否验证通过 0-未通过，1-通过
     */
    private String valid;
    /**数据类型**/
    private String dataType;
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValidateInfo() {
        return validateInfo;
    }
    
    public void setValidateInfo(String validateInfo) {
        this.validateInfo = validateInfo;
    }
    
    public String getValid() {
        return valid;
    }
    
    public void setValid(String valid) {
        this.valid = valid;
    }

    
    public String getDataType() {
        return dataType;
    }

    
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    
}
