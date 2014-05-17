package com.zorbit.utils.eImporter.interfaces;

import java.util.List;
import java.util.Map;
import com.zorbit.utils.eImporter.vo.SheetList;
/**
 * 数据转换类
 * 
 * @since 2014年5月17日 下午3:03:06
 * @author Jamin Zou
 */
public interface IPersistence {
    
    /**
     * 把数据转换成指定的dto实例集合
     * @param m 指定的dto
     * @param data 需要转换的数据，一般通过dataid 在datas中获得
     * @param key sheet id
     * @param extFields 扩展字段Map，在excel中没有的，需要手动设置，如，uuserid，uroleid等
     * @param containInvalid 是否转换验证不通过的数据
     * @return
     * @author Jamin Zou
     */
    public <M> List<M> convertDatas(Class<M> m, Map<String, SheetList> data,String key, Map<String,Object> extFields,Boolean containInvalid);
   
    
    /**
     * 读取指定sheet的数据，以list-->map键值对返回
     * @param data 需要转换的数据，一般通过dataid 在datas中获得
     * @param key 配置文件中对应 sheet 名称
     * @param extFields 扩展字段Map，在excel中没有的，需要手动设置，如，uuserid，uroleid等
     * @param containInvalid 是否包含验证错误的数据
     * @return 封装好数据的对象集合
     * @author Jamin Zou
     */
    public List<Map<String,Object>> convertDatas(Map<String, SheetList> data,String key, Map<String,Object> extFields,Boolean containInvalid);
    
}
