package com.zorbit.utils.eImporter.utils;

import com.zorbit.utils.commonz.FormatUtils;



public class Util {
    
    /**
     * 字符串转换为java基本类型
     * 如果无法转换，返回原字符串
     * @param type 类型
     * @param value 原字符串
     * @return
     * @author biller zou
     */
    public static Object dataTypeConvert(String type,String value){
        if(value==null){
            return null;
        }
        try {
            if("java.lang.Integer".equals(type)){
                return Integer.parseInt(value);
            }else if("java.lang.Double".equals(type)){
                return Double.parseDouble(value);
            }else if("java.lang.Float".equals(type)){
                return Float.parseFloat(value);
            }else if("java.lang.Boolean".equals(type)){
                return Boolean.parseBoolean(value);
            }else if("java.lang.Short".equals(type)){
                return Short.parseShort(value);
            }else if("java.lang.Long".equals(type)){
                return  Long.parseLong(value);
            }else if("java.util.Date".equals(type)){
                if(value.isEmpty()||value.length()<5){
                    return null;
                }
                return FormatUtils.toDate(value);
            }else if("java.lang.String".equals(type)){
                return value;
            }else if(type==null||"".equals(type)){
                return value;
            }
        } catch (Exception e) {
            return value;
        }
        
        return null;
    }
}
