package com.zorbit.utils.eImporter.interfaces.impl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import com.zorbit.utils.eImporter.interfaces.IPersistence;
import com.zorbit.utils.eImporter.vo.RowMap;
import com.zorbit.utils.eImporter.vo.SheetList;
public class Persistence  implements IPersistence{
    static Logger logger = Logger.getLogger(Persistence.class.getName());
    ////////////////////////////////
    @Override
    public <M> List<M> convertDatas(Class<M> m, Map<String, SheetList> data,String key, Map<String,Object> extFields,Boolean containInvalid) {
        // 所有未封装的数据
        List<M> guests = new ArrayList<M>();
        SheetList list = data.get(key);
        for (RowMap rowMap : list) {
            Map<String, Object> map2 = rowMap.getValues( containInvalid==null?false: containInvalid);
            if (map2 != null && map2.size() > 0) {
                //map2.put("uid", UUID.randomUUID().toString());
                if(extFields!=null){
                    map2.putAll(extFields);
                }
                try {
                    M t1 = m.newInstance();
                    BeanUtils.populate(t1, map2);
                    guests.add(t1);
                } catch (IllegalAccessException e) {
                    logger.error(e);
                } catch (InvocationTargetException e) {
                    logger.error(e);
                } catch (InstantiationException e) {
                    logger.error(e);
                }
                
            }
        }
        return guests;
    }
    @Override
    public List<Map<String,Object>> convertDatas(Map<String, SheetList> data,String key, Map<String,Object> extFields,Boolean containInvalid) {
        // 所有未封装的数据
        List<Map<String,Object>> guests = new ArrayList<Map<String,Object>>();
        SheetList list = data.get(key);
        if(list!=null){
            for (RowMap rowMap : list) {
                Map<String, Object> map2 = rowMap.getValues( containInvalid==null?false: containInvalid);
                if (map2 != null && map2.size() > 0) {
                    if(extFields!=null){
                        map2.putAll(extFields);
                    }
                    guests.add(map2);
                    
                }
            }
        }
        return guests;
    }
}
