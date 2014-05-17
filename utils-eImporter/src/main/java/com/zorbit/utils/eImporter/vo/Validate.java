package com.zorbit.utils.eImporter.vo;

import java.util.List;

/**
 * 验证配置对象
 * @since 2013-6-25 上午11:33:53
 * @author biller zou
 */
public class Validate {
    
    private String classz;
    
    private String method;
    
    private List<String> param;
    
    public String getClassz() {
        return classz;
    }
    
    public void setClassz(String classz) {
        this.classz = classz;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public List<String> getParam() {
        return param;
    }
    
    public void setParam(List<String> param) {
        this.param = param;
    }
    
}
