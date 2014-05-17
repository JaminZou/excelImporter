package com.zorbit.utils.eImporter.vo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.zorbit.utils.eImporter.utils.Util;

/**
 * 在sheet中行的数据集
 * @since 2013-6-26 上午10:04:52
 * @author biller zou
 */
public class RowMap implements Map<String, Value> {
    
    private Map<String, Value> map = new HashMap<String, Value>();
    
    public Map<String, Value> getMap() {
        return map;
    }
    
    public void setMap(Map<String, Value> map) {
        this.map = map;
    }
    
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }
    
    @Override
    public Value get(Object key) {
        return map.get(key);
    }
    
    @Override
    public Value remove(Object key) {
        return map.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends String, ? extends Value> m) {
        map.putAll(m);
    }
    
    @Override
    public void clear() {
        map.clear();
    }
    
    @Override
    public Collection<Value> values() {
        return map.values();
    }
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public Value put(String key, Value value) {
        return map.put(key, value);
    }
    
    @Override
    public Set<String> keySet() {
        return map.keySet();
    }
    
    @Override
    public Set<java.util.Map.Entry<String, Value>> entrySet() {
        return map.entrySet();
    }
    /**
     * 得到数据库字段与值的映射map
     * @param isContainInvalid 没通过验证的数据是否需要封装，如果为true 则包含，如果为false，验证没通过 ，则返回null
     * @return
     * @author biller zou
     */
    public Map<String, Object> getValues(boolean isContainInvalid){
        Map<String, Object> map1 = new HashMap<String, Object>();
        boolean flag =false;
        for (String key : map.keySet()) {
            Value value=map.get(key);
            if(!isContainInvalid){
                if("1".equals(value.getValid())){
                    String v= value.getValue();
                    if(v!=null&&!"".equals(v)){
                        Object val=Util.dataTypeConvert(value.getDataType(), value.getValue()) ;
                        if(val!=null){
                            map1.put(key, val);
                        }
                    }
                }else{
                    flag = true;
                    break;
                }
            }else{
                String v= value.getValue();
                if(v!=null&&!"".equals(v)){
                    Object val=Util.dataTypeConvert(value.getDataType(), value.getValue()) ;
                    if(val!=null){
                        map1.put(key, val);
                    }
                }
            }
        }
        if(flag){
            return null;
        }
        return map1;
    }
}
