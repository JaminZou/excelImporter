package com.zorbit.utils.eImporter.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 错误信息
 * 
 * @since 2013-8-5 上午10:27:29
 * @author biller zou
 */
public class Erros implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9110622596282836939L;
    
    private Map<String, String> erroMap=new TreeMap<String, String>(new Comparator<String>(){ 
        public int compare(String obj1,String obj2){ 
            //升序排序 
            return obj1.compareTo(obj2); 
           } 
     });
    
    public Map<String, String> getErroMap() {
        return erroMap;
    }
    public void setErroMap(Map<String, String> erroMap) {
        this.erroMap = erroMap;
    }
    public int size() {
        return erroMap.size();
    }
    
    /**
     * 得到一个按行号降序排列的错误集合。list
     * @return
     * @author jamin zou
     */
    public List<String> getSortErros(){
    /*        List<Map.Entry<String,String>> mappingList  = new ArrayList<Map.Entry<String,String>>(erroMap.entrySet()); 
            
            Collections.sort(mappingList, new Comparator<Map.Entry<String,String>>() {
                @Override
                public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
    */        
        return new ArrayList<String>(erroMap.values());
    }
    
    
    
}
