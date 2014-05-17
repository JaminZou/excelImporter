package com.zorbit.utils.eImporter.validate;
import org.apache.log4j.Logger;

import com.zorbit.utils.commonz.DataUtils;
import com.zorbit.utils.commonz.FormatUtils;

/**
 * 数据验证
 * @since 2013-6-25 上午9:39:55
 * @author biller zou
 */
public class Validate {
    static Logger logger = Logger.getLogger(Validate.class.getName());
    public boolean idCard(String idcard){
        logger.debug("验证身份证："+idcard);
        if(DataUtils.isNullOrEmpty(idcard, true)){
            return true;
        }
        return DataUtils.convertCardID(idcard);
    }
    public boolean isDate(String date){
        logger.debug("验证日期："+date);
        if(DataUtils.isNullOrEmpty(date, true)){
            return true;
        }
        boolean falg=false;
        if(FormatUtils.toDate(date)!=null){
            falg=true;
        }
        return falg;
    }
    public boolean isInteger(String str){
        logger.debug("验证整数："+str);
        if(DataUtils.isNullOrEmpty(str, true)){
            return true;
        }
        return DataUtils.isInteger(str);
    }
    public boolean isLong(String str){
        logger.debug("验证Long："+str);
        if(DataUtils.isNullOrEmpty(str, true)){
            return true;
        }
        return isInteger(str);
    }
    public boolean isDouble(String str){
        logger.debug("验证Double："+str);
        return isFloat(str);
    }
    public boolean isFloat(String str){
        logger.debug("验证Float："+str);
        if(DataUtils.isNullOrEmpty(str, true)){
            return true;
        }
        return DataUtils.isFloat(str);
    }
    
    public boolean isEmail(String str){
       logger.debug("验证Email："+str);
        if(DataUtils.isNullOrEmpty(str, true)){
            return true;
        }
        boolean falg=false;
        if(DataUtils.isEmail(str)){
            falg=true;
        }
        return falg;
     }
    
    public boolean required (String str){
        logger.debug("验证required："+str);
        boolean falg=false;
        if(!DataUtils.isNullOrEmpty(str,true)){
            falg=true;
        }
        return falg;
    }
    
    public boolean integerRange(String v,Integer min,Integer max){
        logger.debug("验证整数范围："+v);
        boolean falg=false;
        if(DataUtils.isNullOrEmpty(v,true)){
            return true;
        }
        if(!DataUtils.isInteger(v)){
            return false;
        }
        Integer v1 = Integer.valueOf(v);
        if(v1>=min&&v1<=max){
            return true;
        }
        return falg;
    }
}
