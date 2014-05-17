package com.zorbit.utils.eImporter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zorbit.utils.eImporter.interfaces.impl.StrategyDefault;
import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Erros;
import com.zorbit.utils.eImporter.vo.SheetList;
import com.zorbit.utils.eImporter.vo.Validate;
import com.zorbit.utils.eImporter.vo.ValidateConfiguration;

/**
 *  <pre>
 * 导入excel工具类
 * 需要导入jar包:
 *   commons-lang,commons-beanutils,log4j,dom4j,jaxen,org.apache.poi
 * 用法：
 * 1、读取配置文件：{@code 
 *   方法一：采用默认数据验方法
 *   //configurationPath excel配置文件路径
 *   //configurationId 为配置文件中指定的excel id的值
 *   String configurationId =  ExcelImportUtil.laodConfiguration(configurationPath,configurationId);
 *   方法二：自定义数据验证方法
 *   //configurationPath excel配置文件路径
 *   //validateconfigurationPath 数据验证方法配置路径
 *   //configurationId 为配置文件中指定的excel id的值
 *   String configurationId =  ExcelImportUtil.laodConfiguration(configurationPath,validateconfigurationPath,configurationId);
 *      }
 * 2，下载导入excel的模板 ：{@code 
 *   //得到配置信息，然后可以得到配置信息Excel对象，Excel对象在下载模板的时候可以用来获取模板文件的名字
 *   Excel excel = ExcelImportUtil.getConfiguration(configurationId);
 *   //得到HSSFWorkbook对象，可以用流直接写出
 *   HSSFWorkbook hsw=ExcelImportUtil.getExcelTempleASWorkbook(configurationId); 
 *   String fileName1 = excel.getName() + ".xls";
 *   BufferedOutputStream toClient = new BufferedOutputStream(new FileOutputStream(new File("D://"+fileName1)));
 *   hsw.write(toClient);
 *   toClient.flush();
 *   toClient.close();
 *   }
 * 3、加载数据 ：{@code 
 *   //excleInp 为InputStream--文件流，
 *   //configurationId 为配置文件中指定的excel id的值
 *   //返回值result---{'success/failure',dataId} dataId在内存中映射的id，可以通过它来获取数据
 *   String[] result = ExcelImportUtil.LoadData(configurationId,excleInp);} 
 *   String dataId = result[1]; 
 * 4、添加扩展字段，在调用convertData方法时会自动把这些键值对封装到指定的dto中：{@code  
 *   //注意要先调用 LoadData加载数据，得到数据id dataId
 *   //然后把扩展字段map设置进去 如设置uroleid
 *   Map<String,String> extFs = new HashMap<>();
 *   extfs.put("uroleid","acbsw1werwersdfweflksdjtemsldwle");
 *   ExcelImportUtil.putExtFields(dataId,extFs);} 
 * 5、数据集操作:{@code 
 *   a.转换数据 如把数据封装成GuestDTO list
 *     //sheetKey--在配置文件中，sheet的id的值，containInvalid--是否包含出错的数据行
 *     List<GuestDTO> ms=  ExcelImportUtil.convertData(dataId,sheetKey,GuestDTO.class,containInvalid); 
 *     } 
 *   b.得到数据:  {@code 
 *     List<Map<String,Object>> maps= ExcelImportUtil.convertData(dataId,sheetKey,containInvalid);
 *     }
 *   c.得到验证错误信息:{@code
 *     // erros--错误对象，可以通过erros.getErroMap()得到一个错误map集合
 *     //还可用getSortErros()对错误进行排序,注意getSortErros()返回的是list<String>
 *     Erros erros= ExcelImportUtil. getErros(dataId);
 *     }       
 * 6、数据删除：操作完后，必须调用此方法释放内存：{@code
 *   ExcelImportUtil.removeData(dataId);
 *   } 
 * </pre>
 * @since 2014年5月15日 下午1:12:27
 * @author Jamin Zou
 */
public class ExcelImportUtil {
    static Logger logger = Logger.getLogger(ExcelImportUtil.class.getName());
    /**
     * 配置文件集合
     */
    private static Map<String, Configuration> configurationMap = new ConcurrentHashMap<String, Configuration>();
    /**
     * 验证方法配置文件集合
     */
    private static Map<String, ValidateConfiguration> validateConfigurationMap = new ConcurrentHashMap<String, ValidateConfiguration>();
    
    /**
     * 存储数据集合
     */
    private static Map<String, Map<String, SheetList>> datas = new HashMap<String, Map<String, SheetList>>();
    /**
     * 存储数据验证方法集合
     */
    private static Map<String, Map<String, Validate>> validate = new HashMap<String, Map<String, Validate>>();
    
    /**
     * 错误信息集合
     */
    private static Map<String, Erros> errosMap = new HashMap<String, Erros>();
    
    /**
     * 扩展字段
     */
    private static Map<String,Map<String,Object>> extFields = new HashMap<String,Map<String,Object>>();
    
    /**
     * 处理策略
     */
    private static Strategy strategy= new StrategyDefault(null);
    
    /**
     *<pre> 
     *加载配置文件，如果已经存在，则不重新读取配置，直接从缓存中取出
     * 数据验证采用默认提供的验证方法</pre>
     * @param configurationPath excel配置文件路径
     * @param configurationId excel配置id
     * @return configurationId excel配置id
     * @author Jamin Zou
     */
    public static String laodConfiguration(String configurationPath,String configurationId){
        //判断是否已经读取过配置
        if(configurationMap.get(configurationId)==null){
          //没有--new context，put
          Configuration configuration =  strategy.getConfiguration(configurationPath, configurationId);
          //加载默认验证方法信息
          ValidateConfiguration defaultValidateConfiguration = validateConfigurationMap.get(
              ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath());
          if(defaultValidateConfiguration==null){
              defaultValidateConfiguration = strategy.getValidateConfiguration(
              ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath()
              );
              validateConfigurationMap.put(
                ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath()
                , defaultValidateConfiguration);
          }
          Map<String, Validate> vs = defaultValidateConfiguration.getValidates();
          //configuration.setValidateConfiguration(validateConfiguration);
          validate.put(configurationId, vs);
          configurationMap.put(configurationId, configuration);
        }
       
        return configurationId;
    }
    /**
     * 加载配置文件，如果已经存在，则不重新读取配置，直接从缓存中取出
     * @param configurationPath excel配置文件路径
     * @param validateconfigurationPath 数据验证方法配置文件路径
     * @param configurationId excel配置id
     * @return configurationId excel配置id
     * @author Jamin Zou
     */
    public static String laodConfiguration(String configurationPath,String validateconfigurationPath,String configurationId){
          //判断是否已经读取过配置
            if(configurationMap.get(configurationId)==null){
              //没有--new context，put
              Configuration configuration =  strategy.getConfiguration(configurationPath, configurationId);
              if(validate.get(configurationId)==null){
                  //加载默认验证方法信息
                  ValidateConfiguration defaultValidateConfiguration = validateConfigurationMap.get(
                      ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath());
                  if(defaultValidateConfiguration==null){
                      defaultValidateConfiguration = strategy.getValidateConfiguration(
                      ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath()
                      );
                      validateConfigurationMap.put(
                        ExcelImportUtil.class.getResource("excelValidate_default.xml").getPath()
                        , defaultValidateConfiguration);
                  }
                  //用户自定验证方法信息
                  ValidateConfiguration customValidateConfiguration = validateConfigurationMap.get(validateconfigurationPath);
                  if(customValidateConfiguration==null){
                      customValidateConfiguration = strategy.getValidateConfiguration(validateconfigurationPath);
                      validateConfigurationMap.put(validateconfigurationPath, customValidateConfiguration);
                  }
                  Map<String, Validate> vs = defaultValidateConfiguration.getValidates();
                  vs.putAll(customValidateConfiguration.getValidates());
                  validate.put(configurationId, vs);
              }
              configurationMap.put(configurationId, configuration);
         }
        
        return configurationId;
    }
    
    /**
     * 获得配置信息
     * @param configurationId 配置id
     * @return
     * @author Jamin Zou
     */
    public static Configuration getConfiguration(String configurationId){
        return  configurationMap.get(configurationId);
    }
    /**
     * 读取数据，分配一个UUID，用于后续数据转换操作
     * @param configurationId excel配置id
     * @param excleInp excel文件流
     * @return {succes/failure,dataid} 读取成功/失败,数据id（用来取数据）
     * @author Jamin Zou
     */
    public static String[] LoadData(String configurationId,InputStream excleInp){
        //根据id从configurationMap得到configuration
        Configuration configuration = configurationMap.get(configurationId);
        //获得验证方法信息
        Map<String, Validate> valids=validate.get(configurationId);
        //根据配置文件和输入流读取数据 并且把数据存储到 datas
        Erros erros = new Erros();
        String[] result = strategy.loadData(configuration,valids, datas, erros, excleInp);
        //把错误信息存起来
        errosMap.put(result[1], erros);
        return result;
    }
    
    /**
     * 读取转换指定sheet的数据，用dataid取出数据，然后转换成指定dto实例集合
     * @param dataId  数据id 
     * @param sheetKey 配置文件中对应 sheet 名称
     * @param t 需要转换的dto
     * @param containInvalid 是否包含验证错误的数据
     * @return 封装好数据的对象集合
     * @author Jamin Zou
     */
    public static  <T> List<T> convertData(String dataId,String sheetKey,Class<T> t,Boolean containInvalid){
        //根据dataid得到数据
        Map<String, SheetList> data =  datas.get(dataId);
        //数据进行转换
        return strategy.convertDatas(t, data,sheetKey,extFields.get(dataId),containInvalid);
    }
    
    /**
     * 读取指定sheet的数据，以list-->map键值对返回
     * @param dataid 数据id 
     * @param sheetKey 配置文件中对应 sheet 名称
     * @param containInvalid 是否包含验证错误的数据
     * @return 封装好数据的对象集合
     * @author Jamin Zou
     */
    public static List<Map<String,Object>> convertData(String dataid,String sheetKey,Boolean containInvalid){
        //根据dataid得到数据
        Map<String, SheetList> data =  datas.get(dataid);
        //数据进行转换
        return strategy.convertDatas(data,sheetKey,extFields.get(dataid),containInvalid);
    }
    
    /**
     * 得到本次导入的验证错误信息
     * @param dataid 数据id 
     * @return 错误集合对象 
     * @author Jamin Zou
     */
    public static Erros getErros(String dataid){
        //根据dataid得到对应的错误信息
        return errosMap.get(dataid);
    }
    
    /**
     * 删除本次导入的数据以及对应的错误信息，数据导入操作完后必须执行此方法，否则内存开销会越来越大
     * @param dataid 数据id 
     * @author Jamin Zou
     */
    public static void removeData(String dataid){
        //根据dataid删除数据
        errosMap.remove(dataid);
        datas.remove(dataid);
        extFields.remove(dataid);
    }

    /**
     * 根据配置文件得到导入数据的模板，通过excelid获得，返回生成模板后的存放的路径
     * @param configurationId excel配置id
     * @return
     * @author Jamin Zou
     */
    public static String getExcelTemple(String configurationId) {
        //根据id从configurationMap得到configuration
        Configuration configuration = configurationMap.get(configurationId);
        return strategy.getExcelTempleAsFilePath(configuration);
    }
    
    
    /**
     *  根据配置文件得到导入数据的模板，通过excelid获得，返回HSSFWorkbook，可以用流直接写出
     * @param configurationId excel配置id
     * @return
     * @author Jamin Zou
     */
    public static HSSFWorkbook getExcelTempleASWorkbook(String configurationId){
        //根据id从configurationMap得到configuration
        Configuration configuration = configurationMap.get(configurationId);
        return strategy.getExcelTempleAsHSSFWorkbook(configuration);
    }
    
    /**
     * 设置扩展字段map--->fieldName=valve
     * @param dataid 数据id 
     * @param extFs 扩展字段map
     * @author Jamin Zou
     */
    public static void putExtFields(String dataid,Map<String, Object> extFs){
        extFields.put(dataid,extFs);
    }
    
    /**
     * 获取 处理策略
     * @return strategy
     */
    public static Strategy getStrategy() {
        return strategy;
    }

    
    /**
     * 设置 处理策略
     * @param strategy 处理策略
     */
    public static void setStrategy(Strategy strategy) {
        ExcelImportUtil.strategy = strategy;
    }

    
    
    
}
