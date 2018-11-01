package AutomationTestSystem.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelHelper {
    
    private static Workbook WORKBOOK;
    
    private static final Pattern INDEX_PATTERN = Pattern.compile("^[a-zA-Z]+[0-9]+$");
    
    public static void main(String[] args) throws Exception {
        String excelPath = ConfigUtil.getProperty("testdata.path", Constants.CONFIG_COMMON);
        ExcelHelper.read(excelPath);
        List<String> values = ExcelHelper.getCellContentByIndexs(0, "a2", "E8", "E9");
        
        values = ExcelHelper.getCellContentByIndexs(0, "A1", "E8", "E9", "D1", "E8", "E9", "D1");
        
        
        for(String value : values){
            System.out.println(value);
        }
        
    }
    
    public static void read(String excelPath) throws BiffException, IOException{
        File file = new File(excelPath);  
        InputStream in = new FileInputStream(file);
        WORKBOOK = Workbook.getWorkbook(in);
    }
    
    public static void close(){
        if(WORKBOOK != null)
            WORKBOOK.close();
        WORKBOOK = null;
    }
    
    private static void check(){
        if(WORKBOOK == null)
            throw new RuntimeException("文件文空");
    }
    
    public static List<String> getCellContentByIndexs(int sheetIndex, String... indexs) throws Exception{
        
        List<String> cellList = new ArrayList<String>();
        if(indexs == null || indexs.length == 0)
            return cellList;
        check();
        Sheet sheet = WORKBOOK.getSheet(sheetIndex);
        Cell cell;
        for(String index : indexs){
            if(checkIndex(index)){
                cell = sheet.getCell(index);
                cellList.add(cell.getContents());
            }else{
                throw new Exception("错误的索引值");
            }
        }
        
        return cellList;
    }

    private static boolean checkIndex(String index){
        
        boolean isOk = false;
        if(index == null || "".equals(index))
            return isOk;
        Matcher m = INDEX_PATTERN.matcher(index.trim());
        if(m.find())
            isOk = true;
        return isOk;
    }
}
