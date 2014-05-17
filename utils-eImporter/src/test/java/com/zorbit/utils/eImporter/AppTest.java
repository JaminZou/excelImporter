package com.zorbit.utils.eImporter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zorbit.utils.eImporter.vo.Erros;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    /**
     * @param args
     * @author biller zou
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String  configurationid  =    ExcelImportUtil.laodConfiguration(AppTest.class.getResource("excelTemple_01.xml").getPath(),  
                                                                        "100000");
        com.zorbit.utils.eImporter.vo.Excel excel1 = ExcelImportUtil.getConfiguration(configurationid).getExcel();
        HSSFWorkbook hssfWorkbook2 =ExcelImportUtil.getExcelTempleASWorkbook(configurationid);
        String fileName1 = getTemplate(excel1, hssfWorkbook2);
        loadData(fileName1);
    }

    private static void loadData(String fileName1) throws FileNotFoundException {
        String dataid="";
        File inf = new File("D://inf//"+fileName1);
        String result[] = ExcelImportUtil.LoadData("100000", new FileInputStream(inf));
        if ("success".equals(result[0])) {
            // 读取是否成功
            dataid = result[1];
            Erros erro = ExcelImportUtil.getErros(dataid);
           if (erro.getErroMap().size() > 0) {// 是否有数据格式错误
                System.out.println("----"+erro.getSortErros());
            }
            System.out.println( ExcelImportUtil.convertData(dataid, "10100", true));
            
        }
    }

    private static String getTemplate(com.zorbit.utils.eImporter.vo.Excel excel1, HSSFWorkbook hssfWorkbook2) throws FileNotFoundException, IOException {
        String fileName1 = excel1.getName() + ".xls";
        BufferedOutputStream toClient = new BufferedOutputStream(new FileOutputStream(new File("D://"+fileName1)));
        hssfWorkbook2.write(toClient);
        toClient.flush();
        toClient.close();
        return fileName1;
    }
    
    

    @SuppressWarnings("unused")
    private static File getExcelTempDirectory() {
        URL url=Test.class.getResource("/");
           File file = new File(url.getFile());
           File parent=file.getParentFile();
           File temp=new File(parent.getPath()+"/file/ExcelTemple");
           if(!temp.exists()){
               temp.mkdirs();
           }
        return temp;
    }
}
