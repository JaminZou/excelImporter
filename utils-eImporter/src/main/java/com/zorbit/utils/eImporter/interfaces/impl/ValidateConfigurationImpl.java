package com.zorbit.utils.eImporter.interfaces.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.zorbit.utils.eImporter.interfaces.IValidateConfiguration;
import com.zorbit.utils.eImporter.vo.Validate;
import com.zorbit.utils.eImporter.vo.ValidateConfiguration;

/**
 * 读取数据验证配置信息
 * @since 2013-6-26 下午5:48:55
 * @author biller zou
 */
public class ValidateConfigurationImpl implements IValidateConfiguration {
    static Logger logger = Logger.getLogger(ValidateConfigurationImpl.class.getName());
    @SuppressWarnings("unchecked")
    @Override
    public ValidateConfiguration getValidateConfiguration(String validateConfigurationPath) {
        ValidateConfiguration configuration = new ValidateConfiguration();
        Map<String, Validate> validates =new HashMap<String, Validate>();
        //读取xm    封装
        File file=new File(validateConfigurationPath);
        if(file.exists()){
            try {
                SAXReader saxReader = new SAXReader();
                Document document = saxReader.read(file);
                List<Element> validateels = document.selectNodes("/validates/validate" );
                for (Element element : validateels) {
                    Validate validate=new Validate();
                    validate.setClassz(element.attributeValue("class"));
                    validate.setMethod(element.attributeValue("method"));
                    List<Element> pas =  element.selectNodes("param");
                    List<String>params =  new ArrayList<String>();
                    for (Element element2 : pas) {
                        params.add(element2.attributeValue("dataType"));
                    }
                    validate.setParam(params);
                    validates.put(validate.getMethod(), validate);
                }
            } catch (DocumentException e) {
                logger.error(e);
                e.printStackTrace();
            }
            configuration.setValidates(validates);
        }else{
            logger.error("找不到配置文件.................................................");
        }
      
        return configuration;
    }
}
