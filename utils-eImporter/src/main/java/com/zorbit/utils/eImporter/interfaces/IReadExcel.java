package com.zorbit.utils.eImporter.interfaces;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Erros;
import com.zorbit.utils.eImporter.vo.SheetList;
import com.zorbit.utils.eImporter.vo.Validate;
/**
 * 读取接口和模板生成接口
 * @since 2014年5月17日 下午3:03:30
 * @author Jamin Zou
 */
public interface IReadExcel {
    /////////////////////////////////    
    /**
     * 获得放置excel模版文件的目录
     * @return
     * @author biller zou
     */
    public  File getExcelTempDirectory();
    
    /**
     * 通过配置信息得到  模板对象
     * @param configuration
     * @return
     * @author Jamin Zou
     */
    public HSSFWorkbook getExcelTempleASWorkbook(Configuration configuration);
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
    public String[]  loadData(Configuration configuration,Map< String, Validate> map, Map<String, Map<String, SheetList>> datas, Erros erros, InputStream excleInp);

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
    /////////////////////////////////
}
