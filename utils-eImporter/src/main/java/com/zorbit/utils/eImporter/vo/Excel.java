package com.zorbit.utils.eImporter.vo;

import java.util.Map;

public class Excel {
    
    private Map<String, Sheet> sheets;
    
    /**xml中配置的id*/
    private String id;
    
    private String name;
    
    public Map<String, Sheet> getSheets() {
        return sheets;
    }
    
    public void setSheets(Map<String, Sheet> sheets) {
        this.sheets = sheets;
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
    
}
