package com.zorbit.utils.eImporter.interfaces.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import com.zorbit.utils.eImporter.interfaces.IReadExcel;
import com.zorbit.utils.eImporter.vo.Column;
import com.zorbit.utils.eImporter.vo.Configuration;
import com.zorbit.utils.eImporter.vo.Erros;
import com.zorbit.utils.eImporter.vo.Excel;
import com.zorbit.utils.eImporter.vo.RowMap;
import com.zorbit.utils.eImporter.vo.Sheet;
import com.zorbit.utils.eImporter.vo.SheetList;
import com.zorbit.utils.eImporter.vo.Validate;
import com.zorbit.utils.eImporter.vo.ValidateConfiguration;
import com.zorbit.utils.eImporter.vo.Value;

/**
 * 读取excle文件
 * @since 2013-6-25 下午5:20:22
 * @author biller zou
 */
public class ReadExcelImpl implements IReadExcel {
    
    static Logger logger = Logger.getLogger(ReadExcelImpl.class.getName());
    
//////////////////////////////////////////////
    
    @Override
    public HSSFWorkbook getExcelTempleASWorkbook(Configuration configuration) {
        if (configuration == null) {
            logger.error("没有找到excel配置信息...............");
            return null;
        }
        
        HSSFWorkbook workbook = null;
        Excel excel = configuration.getExcel();
        if (excel != null && excel.getSheets().size() > 0) {
            workbook = new HSSFWorkbook();
            // 创建表头style
            HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
            cellStyleTitle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
            //表头字体
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 12); // 字体大小
            // font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_NORMAL); // 粗体
            font.setColor(HSSFColor.WHITE.index); // 绿字
            cellStyleTitle.setFont(font);
            //普通列，设置为text
            HSSFCellStyle columnStyle = workbook.createCellStyle();
            HSSFDataFormat format = workbook.createDataFormat();
            columnStyle.setDataFormat(format.getFormat("@"));
            
            Map<String, Sheet> sheets = excel.getSheets();
            for (Entry<String, Sheet> entry : sheets.entrySet()) {
                Sheet sheet = entry.getValue();
                HSSFSheet sheetpoi = workbook.createSheet();
                workbook.setSheetName(sheet.getIndex(), sheet.getName());
                Map<String, Column> columnMap = sheet.getColumns();
                HSSFRow hssfRow = sheetpoi.createRow(0);
                hssfRow.setHeight(Short.parseShort("800"));
                HSSFCell hssfCell = null;
                hssfCell = hssfRow.createCell(0, HSSFCell.CELL_TYPE_STRING);
                hssfCell.setCellValue(sheet.getTitle());// 设置模版标题
                List<Column> listc = sheet.getColumnAsListOrderByIndex();// 得到所有的列
                if (columnMap != null && columnMap.size() > 0) {
                    for (int i = 1; i <= sheet.getHeaderRowNum() + 200; i++) {
                        HSSFRow hssfRow2 = sheetpoi.createRow(i);
                        if (sheet.getHeaderRowNum() == i) {// 当下标为指定的表头row时写入表头
                            generateColunm(cellStyleTitle, columnStyle,sheet, sheetpoi, hssfRow2,listc,true);
                        } else {
                            generateColunm(cellStyleTitle, columnStyle,sheet, sheetpoi, hssfRow2,listc,false);
                        }
                    }
                    sheetpoi.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
                }
            }
        }
        return workbook;
    }
    
    /**
     * 生成模板
     * @param cellStyleTitle 表头单元格样式
     * @param columnStyle 数据单元格样式
     * @param sheet
     * @param sheetpoi
     * @param hssfRow2
     * @param listc
     * @param isTableTile
     * @author Jamin Zou
     */
    private void generateColunm(HSSFCellStyle cellStyleTitle,  HSSFCellStyle columnStyle,Sheet sheet, HSSFSheet sheetpoi, HSSFRow hssfRow2,  List<Column> listc,boolean isTableTile) {
        HSSFCell hssfCell2;
        hssfRow2.setHeight(Short.parseShort("400"));
        int rownum =  hssfRow2.getRowNum();
        int i=0;
        for (Column column : listc) {
           i= column.getIndex();
            hssfCell2 = hssfRow2.createCell(column.getIndex(), HSSFCell.CELL_TYPE_STRING);
            if(isTableTile){
                hssfCell2.setCellValue(column.getName());
            }else{
                if(columnStyle!=null){
                    hssfCell2.setCellStyle(columnStyle);
                    if(column.getIndex()==0){
                        hssfCell2.setCellValue("#");
                    }else{
                        hssfCell2.setCellValue("");
                    }
                }
            }
            if(cellStyleTitle!=null&&isTableTile){
                hssfCell2.setCellStyle(cellStyleTitle);
            }
            if(!isTableTile){
                if (column.getValueRange() != null && !"".equals(column.getValueRange())) {// 下拉框值
                    String[] list = column.getValueRange().split(",");
                    CellRangeAddressList regions = new CellRangeAddressList(rownum,rownum, i, i);
                    // 生成下拉框内容
                    DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
                    // 绑定下拉框和作用区域
                    HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
                    // 对 sheet页生效
                    sheetpoi.addValidationData(data_validation);
                }
            }
        }
    }
    @Override
    public File getExcelTempDirectory() {
        URL url = getClass().getResource("/");
        File file = new File(url.getFile());
        File parent = file.getParentFile();
        File temp = new File(parent.getPath() + "/file/ExcelTemple");
        if (!temp.exists()) {
            temp.mkdirs();
        }
        return temp;
    }

    @Override
    public String[] loadData(Configuration configuration, Map< String, Validate> validsmap, Map<String, Map<String, SheetList>> datas, Erros erros, InputStream excleInp) {
        String result = "failure";
        String dataid=UUID.randomUUID().toString();
        try {
            // 打开HSSFWorkbook
            POIFSFileSystem fs = new POIFSFileSystem(excleInp);
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            Map<String, Sheet> sheets = configuration.getExcel().getSheets();
            for (String index : sheets.keySet()) {
                Sheet sheet = sheets.get(index);
                HSSFSheet hssfSheet = wb.getSheetAt(Integer.parseInt(index));
                int lastRowNum = hssfSheet.getLastRowNum();
                SheetList sheetList = new SheetList();
                for (int i = sheet.getHeaderRowNum() + 1; i <= lastRowNum; i++) {
                    Map<String, Column> columns = sheet.getColumns();
                    HSSFRow hssfRow = hssfSheet.getRow(i);
                    RowMap rowMap = new RowMap();
                    HSSFCell hssfCell0 = hssfRow.getCell(0);
                    // 某行的第一列值为“#”时为不读取的数据行
                    if (!"#".equals(hssfCell0.getStringCellValue() == null ? "" : hssfCell0.getStringCellValue().trim())) {
                        // boolean haserro = false;
                        for (String culindex : columns.keySet()) {
                            // 封装数据
                            Column column1 = columns.get(culindex);
                            Value value = new Value();
                            value.setDataType(column1.getDataType());
                            // 获得单元格
                            HSSFCell hssfCell = hssfRow.getCell(Integer.parseInt(culindex));
                            // 根据xml中定义的列从excel娶到对应的值
                            if (hssfCell != null) {
                                value.setValue(hssfCell.getStringCellValue());
                            } else {
                                // haserro = true;
                                logger.debug("没有找到单元格：" + i + "，" + columns.get(culindex).getName() + "》》》》》》》");
                            }
                            List<String> valus = new ArrayList<>();
                            //该单元个的值，放验证方法的第一个参数，其他参数为辅助验证参数，放后面
                            valus.add(value.getValue());
                            String methodstr = column1.getValidateMethod();
                            boolean flag1 = true;
                            if (methodstr != null && !"".equals(methodstr)) {
                                String methods[] = methodstr.split(",");
                                for (String mehtod : methods) {
                                    //获得验证辅助参数   配置格式为：range:1|2,required
                                    String[] mp = mehtod.split(":");
                                    String methodName = mp[0];
                                    if(mp.length>1){
                                        valus.addAll(Arrays.asList(mp[1].split("\\|"))) ;
                                    }
                                    // 验证数据
                                    flag1 = ValidateConfiguration.validate(methodName, valus,validsmap);
                                    if (!flag1) {
                                        // 放置错误信息
                                        erros.getErroMap().put(i + culindex, "第" + i + "行，" + column1.getName() + "：" + (column1.getValidateMsg().get(methodName)!=null?column1.getValidateMsg().get(methodName):"数据格式错误"));
                                        value.setValidateInfo(column1.getValidateMsg().get(methodName) != null ? column1.getValidateMsg().get(methodName).toString() : "数据格式错误");
                                        break;
                                    }
                                    
                                }
                                
                            }
                            value.setValid(flag1 ? "1" : "0");
                            rowMap.put(column1.getColumnName(), value);
                            
                        }
                        sheetList.add(rowMap);
                    }
                }
                sheetList.setName(sheet.getName());
                sheetList.setId(sheet.getId());
               // configuration.getExcel().getId();
                Map<String, SheetList> sheetMap = new HashMap<String, SheetList>();
                sheetMap.put(sheet.getId(), sheetList);
                datas.put(dataid,sheetMap);
                result="success";
            }
        } catch (FileNotFoundException e) {
            result = "failure";
            logger.error(e);
        } catch (IOException e) {
            result = "failure";
            logger.error(e);
        } finally {
            if (excleInp != null) {
                try {
                    excleInp.close();
                } catch (IOException e) {
                    logger.debug(e);
                    e.printStackTrace();
                }
            }
        }
        return new String[]{result,dataid};
    }
    @Override
    public String getExcelTempleAsFilePath(Configuration configuration) {
        String path = null;
        if (configuration == null) {
            logger.error("没有找到excel配置信息...............");
            return null;
        }
        HSSFWorkbook workbook = null;
        Excel excel = configuration.getExcel();
        if (excel != null && excel.getSheets().size() > 0) {
            workbook = getExcelTempleASWorkbook(configuration);
        }
        path = getExcelTempDirectory().getPath() + "/" + excel.getName() + ".xls";
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(path);
            workbook.write(fOut);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件：" + path, e);
        } catch (IOException e) {
            logger.error("io错误", e);
        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public HSSFWorkbook getExcelTempleAsHSSFWorkbook(Configuration configuration) {
        if (configuration == null) {
            logger.error("没有找到excel配置信息...............");
            return null;
        }
        
        HSSFWorkbook workbook = null;
        Excel excel = configuration.getExcel();
        if (excel != null && excel.getSheets().size() > 0) {
            workbook = new HSSFWorkbook();
            // 创建表头style
            HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
            cellStyleTitle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// //居中显示
            //表头字体
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short) 12); // 字体大小
            // font.setFontName("楷体");
            font.setBoldweight(Font.BOLDWEIGHT_NORMAL); // 粗体
            font.setColor(HSSFColor.WHITE.index); // 绿字
            cellStyleTitle.setFont(font);
            //普通列，设置为text
            HSSFCellStyle columnStyle = workbook.createCellStyle();
            HSSFDataFormat format = workbook.createDataFormat();
            columnStyle.setDataFormat(format.getFormat("@"));
            
            Map<String, Sheet> sheets = excel.getSheets();
            for (Entry<String, Sheet> entry : sheets.entrySet()) {
                Sheet sheet = entry.getValue();
                HSSFSheet sheetpoi = workbook.createSheet();
                workbook.setSheetName(sheet.getIndex(), sheet.getName());
                Map<String, Column> columnMap = sheet.getColumns();
                HSSFRow hssfRow = sheetpoi.createRow(0);
                hssfRow.setHeight(Short.parseShort("800"));
                HSSFCell hssfCell = null;
                hssfCell = hssfRow.createCell(0, HSSFCell.CELL_TYPE_STRING);
                hssfCell.setCellValue(sheet.getTitle());// 设置模版标题
                List<Column> listc = sheet.getColumnAsListOrderByIndex();// 得到所有的列
                if (columnMap != null && columnMap.size() > 0) {
                    for (int i = 1; i <= sheet.getHeaderRowNum() + 200; i++) {
                        HSSFRow hssfRow2 = sheetpoi.createRow(i);
                        if (sheet.getHeaderRowNum() == i) {// 当下标为指定的表头row时写入表头
                            generateColunm(cellStyleTitle, columnStyle,sheet, sheetpoi, hssfRow2,listc,true);
                        } else {
                            generateColunm(cellStyleTitle, columnStyle,sheet, sheetpoi, hssfRow2,listc,false);
                        }
                    }
                    sheetpoi.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
                }
            }
        }
        return workbook;
    }
 ////////////////////////////////////////////////  
}
