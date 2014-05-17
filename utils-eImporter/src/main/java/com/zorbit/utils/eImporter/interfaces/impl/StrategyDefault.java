package com.zorbit.utils.eImporter.interfaces.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zorbit.utils.eImporter.Strategy;
import com.zorbit.utils.eImporter.interfaces.IConfiguration;
import com.zorbit.utils.eImporter.interfaces.IPersistence;
import com.zorbit.utils.eImporter.interfaces.IReadExcel;
import com.zorbit.utils.eImporter.interfaces.IValidateConfiguration;
import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Erros;
import com.zorbit.utils.eImporter.vo.SheetList;
import com.zorbit.utils.eImporter.vo.Validate;
import com.zorbit.utils.eImporter.vo.ValidateConfiguration;

/**
 * excel读取和数据持久化策略接口默认实现
 * @since 2013-6-24 下午3:53:58
 * @author biller zou
 */
public class StrategyDefault implements Strategy {
    
    static Logger logger = Logger.getLogger(StrategyDefault.class.getName());
    
    private IConfiguration iconfiguration;
    
    private IPersistence ipersistence;
    
    private IReadExcel ireadExcel;
    
    private IValidateConfiguration ivalidateConfiguration;
    
    /**
     * 接口全部替换 （不推荐）
     * @param iconfiguration excel配置文件读取类
     * @param ipersistence 数据持久化实现类
     * @param ireadExcel excel读取实现类
     * @param ivalidate 数据验证实现类
     * @param ivalidateConfiguration 验证配置读取实现类
     * @author biller zou
     */
    public StrategyDefault(IConfiguration iconfiguration, IPersistence ipersistence, IReadExcel ireadExcel, IValidateConfiguration ivalidateConfiguration) {
        super();
        this.iconfiguration = iconfiguration;
        this.ipersistence = ipersistence;
        this.ireadExcel = ireadExcel;
        this.ivalidateConfiguration = ivalidateConfiguration;
    }
    
    /**
     * 值替换数据持久成接口（推荐） 一般用这个
     * @param ipersistence 数据持久化实现类，可以为空，为空时默认实现。<br/>
     *            如果ipersistence 参数为空，则不能使用通用封装数据存储数据方法，只能使用，public Map<String, List<Object>> convertDatas(Context context) 该方法 得到数据后自行实现数据持久化
     * @author biller zou
     */
    public StrategyDefault(IPersistence ipersistence) {
        if (ipersistence == null) {
            this.ipersistence = new Persistence();
        } else {
            this.ipersistence = ipersistence;
        }
        this.iconfiguration = new ConfigurationImpl();
        this.ireadExcel = new ReadExcelImpl();
        this.ivalidateConfiguration = new ValidateConfigurationImpl();
    }
    
    
    public IConfiguration getIconfiguration() {
        return iconfiguration;
    }
    
    public void setIconfiguration(IConfiguration iconfiguration) {
        this.iconfiguration = iconfiguration;
    }
    
    public IPersistence getIpersistence() {
        return ipersistence;
    }
    
    public void setIpersistence(IPersistence ipersistence) {
        this.ipersistence = ipersistence;
    }
    
    public IReadExcel getIreadExcel() {
        return ireadExcel;
    }
    
    public void setIreadExcel(IReadExcel ireadExcel) {
        this.ireadExcel = ireadExcel;
    }
    public IValidateConfiguration getIvalidateConfiguration() {
        return ivalidateConfiguration;
    }
    
    public void setIvalidateConfiguration(IValidateConfiguration ivalidateConfiguration) {
        this.ivalidateConfiguration = ivalidateConfiguration;
    }
    
/////////////////////////////////////
    @Override
    public String[]  loadData(Configuration configuration,Map< String, Validate> validsmap, Map<String, Map<String, SheetList>> datas, Erros erros, InputStream excleInp) {
        
        return ireadExcel.loadData( configuration,validsmap,datas,  erros,  excleInp);
    }
    
    @Override
    public <M> List<M> convertDatas(Class<M> m, Map<String, SheetList> data, String key, Map<String,Object> extFields, Boolean containInvalid) {
        return ipersistence.convertDatas(m, data, key,extFields, containInvalid);
    }

    @Override
    public List<Map<String,Object>> convertDatas(Map<String, SheetList> data, String key, Map<String, Object> extFields, Boolean containInvalid) {
        return ipersistence.convertDatas(data, key,extFields, containInvalid);
    }

    @Override
    public String getExcelTempleAsFilePath(Configuration configuration) {
        return ireadExcel.getExcelTempleAsFilePath(configuration);
    }

    @Override
    public HSSFWorkbook getExcelTempleAsHSSFWorkbook(Configuration configuration) {
        return ireadExcel.getExcelTempleAsHSSFWorkbook(configuration);
    }
    
    
    @Override
    public Configuration getConfiguration(String configurationPath, String excelid) {
        return iconfiguration.getConfiguration(configurationPath, excelid);
    }
    
    @Override
    public ValidateConfiguration getValidateConfiguration(String validateConfigurationPath) {
        return ivalidateConfiguration.getValidateConfiguration(validateConfigurationPath);
    }
//////////////////////////////
}
