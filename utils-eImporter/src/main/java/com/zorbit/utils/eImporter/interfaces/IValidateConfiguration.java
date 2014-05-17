package com.zorbit.utils.eImporter.interfaces;

import com.zorbit.utils.eImporter.vo.ValidateConfiguration;


/**
 * 验证读取配置接口
 * @since 2013-6-25 上午9:58:16
 * @author biller zou
 */
public interface IValidateConfiguration {
    
    /**
     * 得到验证配置信息
     * @param validateConfigurationPath
     * @return
     * @author biller zou
     */
    public ValidateConfiguration getValidateConfiguration(String validateConfigurationPath);
}
