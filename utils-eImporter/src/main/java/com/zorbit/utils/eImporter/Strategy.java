package com.zorbit.utils.eImporter;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Erros;
import com.zorbit.utils.eImporter.vo.SheetList;
import com.zorbit.utils.eImporter.vo.Validate;
import com.zorbit.utils.eImporter.vo.ValidateConfiguration;

/**
 * excel读取和数据持久化策略接口
 * 方便后续升级
 * @since 2013-6-24 下午3:52:46
 * @author biller zou
 */
public interface Strategy {
    
    
    //--------------new-------------------------------------------------
    /**
     * 得到excle配置信息
     * @param configurationPath
     * @return
     * @author biller zou
     */
    public Configuration getConfiguration(String configurationPath,String excleid);
    
    /**
     * 得到数据验证配置信息
     * @param validateConfiguration
     * @return
     * @author biller zou
     */
    public ValidateConfiguration getValidateConfiguration(String validateConfiguration);
    /**
     * 
     * @param configuration excel配置信息
     * @param validsmap 验证方法配置信息
     * @param datas 数据存储Map
     * @param erros 错误信息对象
     * @param excleInp excel输入流
     * @return {succes/failure,dataid} 读取成功/失败,数据id
     * @author Jamin Zou
     */
    public String[] loadData(Configuration configuration,Map< String, Validate> validsmap,Map<String, Map<String, SheetList>> datas,Erros erros,InputStream excleInp);
    
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
    public <M> List<M> convertDatas(Class<M> m,Map<String, SheetList> data,String key, Map<String,Object> extFields,Boolean containInvalid);
    
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
    
    /**
     * 根据配置文件得到导入数据的模板，通过Configuration获得，返回生成模板后的存放的路径
     * @param configuration 配置对象
     * @return
     * @author Jamin Zou
     */
    public String getExcelTempleAsFilePath(Configuration configuration);
    
    /**
     *  根据配置文件得到导入数据的模板，通过Configuration获得，返回HSSFWorkbook，可以用流直接写出
     * @param configuration 配置对象
     * @return
     * @author Jamin Zou
     */
    public HSSFWorkbook getExcelTempleAsHSSFWorkbook(Configuration configuration);
    //-------------------------------------------------------------
    

}
