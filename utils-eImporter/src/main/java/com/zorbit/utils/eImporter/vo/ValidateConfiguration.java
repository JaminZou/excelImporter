package com.zorbit.utils.eImporter.vo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zorbit.utils.eImporter.utils.Util;

/**
 *验证方法的配置信息 
 * @since 2013-6-24 下午4:05:23
 * @author biller zou
 */
public class ValidateConfiguration {
    static Logger logger = Logger.getLogger(ValidateConfiguration.class.getName());
    private Map<String, Validate> validates = new HashMap<String, Validate>();

    public Map<String, Validate> getValidates() {
        return validates;
    }
    
    public void setValidates(Map<String, Validate> validates) {
        this.validates = validates;
    }
    public Validate get(String key){
        return validates.get(key);
    }
    public void put(String key ,Validate validate){
        validates .put(key, validate);
    }
    /**
     * 调用验证方法
     * @param key
     * @param values
     * @return
     * @author Jamin Zou
     */
    public static boolean validate(String key, List<String> values,Map< String, Validate> map) {
        boolean falg=false;
        if(map!=null){
           Validate validate= map.get(key);
           Class<?> c=null;
           try {
               c= Class.forName(validate.getClassz());
               List<String> list =validate.getParam();
               Class<?>[] parameterTypes=new Class<?>[list.size()];
               for (int i = 0; i < parameterTypes.length; i++) {
                   parameterTypes[i]=Class.forName(list.get(i));
                }
               //Collections.
              Method method = c.getMethod(validate.getMethod(), parameterTypes);
              Object ins=c.newInstance();
              List<Object> ps = convertDataTpey(values,validate.getParam());
              Object[] vals=ps.toArray();
              falg=(Boolean) method.invoke(ins,vals);
            } catch (ClassNotFoundException e) {
                logger.info(e.getMessage());
            } catch (IllegalAccessException e) {
                logger.info(e.getMessage());
            } catch (SecurityException e) {
                logger.info(e.getMessage());
            } catch (NoSuchMethodException e) {
                logger.info(e.getMessage());
            } catch (IllegalArgumentException e) {
                logger.info(e.getMessage());
            } catch (InvocationTargetException e) {
                logger.info(e.getMessage());
            } catch (InstantiationException e) {
                logger.info(e.getMessage());
            }
        }
        return falg;
    }
   
    /**
     * 将字符串转换成java的基类，如果无法转换，返回原字符串
     * 且集合长度需要一直，不一致直接返回原集合
     * @param vlaues 原集合
     * @param types 类型集合
     * @return
     * @author Jamin Zou
     */
   public static List<Object> convertDataTpey(List<String> vlaues,List<String> types){
       List<Object> objs = new ArrayList<>();
       if(vlaues.size()!=types.size()){
           for (String string : vlaues) {
               objs.add(string);
           }
       }else{
           for (int i = 0; i < vlaues.size(); i++) {
               Object val=Util.dataTypeConvert(types.get(i), vlaues.get(i)) ;
               objs.add(val);
           }
       }
       return objs;
   }
}
