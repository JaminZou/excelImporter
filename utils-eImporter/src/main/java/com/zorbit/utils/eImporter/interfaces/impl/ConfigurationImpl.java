package com.zorbit.utils.eImporter.interfaces.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zorbit.utils.eImporter.interfaces.IConfiguration;
import com.zorbit.utils.eImporter.vo.Column;
import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Excel;
import com.zorbit.utils.eImporter.vo.Sheet;

public class ConfigurationImpl implements IConfiguration {
    
    static Logger logger = Logger.getLogger(ConfigurationImpl.class.getName());
    
    @SuppressWarnings("unchecked")
    @Override
    public Configuration getConfiguration(String configurationPath, String excelid) {
        Configuration configuration = new Configuration();
        Excel excel = new Excel();
        // 读取xm 封装
        File file = new File(configurationPath);
        if (file.exists() && excelid != null && !excelid.trim().equals("")) {
            SAXReader saxReader = null;
            try {
                Element excelel = null;
                saxReader = new SAXReader();
                Document document = saxReader.read(file);
                // 得到excel配置
                List<Element> excelels = document.selectNodes("/excels/excel");
                for (Element el : excelels) {
                    if (excelid.equals(el.attributeValue("id"))) {
                        excelel = el;
                        break;
                    }
                }
                // 得到excel下对应的sheet配置
                if (excelel != null) {
                    excel.setId(excelel.attributeValue("id"));
                    excel.setName(excelel.attributeValue("name"));
                    List<Element> sheetels = excelel.selectNodes("sheet");
                    if (sheetels != null && sheetels.size() > 0) {
                        Map<String, Sheet> sheets = new HashMap<String, Sheet>();
                        // sheet
                        for (Element element : sheetels) {
                            Sheet sheet = new Sheet();
                            sheet.setExcelid(excel.getId());
                            sheet.setIndex(Integer.parseInt(element.attributeValue("index") == null ? "0" : "".equals(element.attributeValue("index")) ? "0" : element.attributeValue("index")));
                            sheet.setHeaderRowNum(Integer.parseInt(element.attributeValue("headerRowNum") == null ? "0"
                                                                                                                 : "".equals(element.attributeValue("headerRowNum")) ? "0"
                                                                                                                                                                    : element.attributeValue("headerRowNum")));
                            sheet.setId(element.attributeValue("id"));
                            sheet.setName(element.attributeValue("name"));
                            //sheet.setTableName(element.attributeValue("tableName"));
                            sheet.setTitle(element.attributeValue("title"));
                            //sheet.setClassz(element.attributeValue("class"));
                            //sheet.setDao(element.attributeValue("dao"));
                            sheets.put(sheet.getIndex() + "", sheet);
                            List<Element> columnels = element.selectNodes("column");
                            // column
                            Map<String, Column> columns = new HashMap<String, Column>();
                            for (Element element2 : columnels) {
                                Column column = new Column();
                                column.setSheetId(element2.attributeValue("name"));
                                column.setDataType(element2.attributeValue("dataType"));
                                column.setColumnName(element2.attributeValue("columnName"));
                                column.setName(element2.attributeValue("name"));
                                column.setSheetId(sheet.getId());
                                column.setValueRange(element2.attributeValue("valueRange"));
                                column.setValidateMethod(element2.attributeValue("validateMethod"));
                                column.setIndex(Integer.parseInt(element2.attributeValue("index") == null ? "0" : "".equals(element2.attributeValue("index")) ? "0" : element2.attributeValue("index")));
                                //验证提示信息
                                String strvalidmsg=element2.attributeValue("validateMsg");
                                if(strvalidmsg!=null&&!strvalidmsg.equals("")){
                                    String validmsg[] = strvalidmsg.split(",");
                                    if(validmsg.length>0){
                                        for (String msg : validmsg) {
                                            String val[]= msg.split(":");
                                            if(val.length>=2){
                                                column.getValidateMsg().put(val[0], val[1]);
                                            }
                                        }
                                    }
                                }
                                columns.put(column.getIndex() + "", column);
                            }
                            sheet.setColumns(columns);
                        }
                        excel.setSheets(sheets);
                        configuration.setExcel(excel);
                    }
                }
            } catch (DocumentException e) {
                logger.error(e);
            } finally {}
        } else {
            logger.error("找不到配置文件或id为空.................................................");
        }
        return configuration;
    }
    
}
