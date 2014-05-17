package com.zorbit.utils.eImporter.interfaces;

import com.zorbit.utils.eImporter.vo.Configuration;


/**
 * 去掉excle配置接口
 * @since 2013-6-25 上午9:58:33
 * @author biller zou
 */
public interface IConfiguration {
    /**
     * 生成并得到excle配置信息
     * @param configurationPath 配置文件路径
     * @param excleid 配置文件中 excel ID
     * @return
     * @author Jamin Zou
     */
    public Configuration getConfiguration(String configurationPath,String excleid);
}
