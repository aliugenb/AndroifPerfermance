package com.fanli.android.handleData;

import com.fanli.android.Switch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fps extends GetData {

    @Override
    public String handleCmd(String data) {
        return data;
    }

    @Override
    public List<String> handleData() throws IOException, InterruptedException {
        String command = "adb shell \"dumpsys gfxinfo com.fanli.android.apps reset | grep frames\"";
        if (osName.equals("Mac OS X")){
            command = "adb shell dumpsys gfxinfo com.fanli.android.apps reset | grep frames";
        }else if(osName.indexOf("Windows")!= -1){
            command = "adb shell \"dumpsys gfxinfo com.fanli.android.apps reset | grep frames\"";
        }
        System.out.println("FPS收集数据开始...");
        List<String> data = new ArrayList<String>();

        while (!Switch.fpsEnd){
            System.out.println("FPS收集数据中...");
            String fps=execCommand(command);
            System.out.println(fps);
            if(fps!=null){
                String total = fps.substring(fps.indexOf("rendered:")+10, fps.indexOf("Janky")-1);
                String janky = fps.substring(fps.indexOf("Janky frames:")+14, fps.indexOf("(")-1);
                String percent = fps.substring(fps.indexOf("(")+1, fps.indexOf(")"));

                String result = total+","+janky+","+percent+",";
                data.add(result);
            }
            Thread.sleep(4000);
        }
        System.out.println("FPS收集数据完成...");
        return data;
    }

    @Override
    public void toExcel(List<String> dataMaps, String dataType) {
        int size = dataMaps.size();
        String path;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        path = resultPath+"\\"+dataType+"-"+dateFormat.format(now)+".xls";
        if (osName.equals("Mac OS X")){
            path = resultPath+"/"+dataType+"-"+dateFormat.format(now)+".xls";
        }
        System.out.println(path);
        File file = new File(path);
        FileOutputStream fOut = null;

        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(dataType);

            // 行标
            int rowNum;
            // 列标
//            int colNum;

            HSSFRow row = sheet.createRow(0);
            // 单元格
            HSSFCell cell = null;
            String[] title = {"Total frames rendered", "Janky frames", "percent"};
            for(int i=0;i<title.length;i++){
                cell = row.createCell(i);
                cell.setCellValue(title[i]);
            }

            for (rowNum=0; rowNum<size; rowNum++){
                row = sheet.createRow((short) rowNum+1);
                String cellData = dataMaps.get(rowNum);
                String[] cellDatas = cellData.split(",");
                cell = row.createCell(0);
                cell.setCellValue(cellDatas[0]);
                cell = row.createCell(1);
                cell.setCellValue(cellDatas[1]);
                cell = row.createCell(2);
                cell.setCellValue(cellDatas[2]);
            }

            // 新建一输出文件流
            fOut = new FileOutputStream(file);
            // 把相应的Excel 工作簿存盘
            workbook.write(fOut);
            fOut.flush();
            // 操作结束，关闭文件
            fOut.close();

            System.out.println("Excel文件生成成功！Excel文件名：" + file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Excel文件" + file.getAbsolutePath() + "生成失败：" + e);
        } finally {
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
