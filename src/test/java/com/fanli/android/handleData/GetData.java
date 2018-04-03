package com.fanli.android.handleData;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class GetData implements WriteExcel {

    public static String resultPath;

    static {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "src");
        File testDir = new File(appDir, "test");
        File resultDir = new File(testDir, "result");
        resultPath = resultDir.getPath();
    }


    @Override
    public void writeExcel(String fileName) {
        try {
            toExcel(handleData(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected static String osName = System.getProperty("os.name");

    //处理cmd命令行获取的数据
    public abstract String handleCmd(String result);

    public abstract List<String> handleData() throws IOException, InterruptedException;

    public String execCommand(String command) throws IOException {
        String result = null;
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }
            String str = stringBuffer.toString().trim();
            result = handleCmd(str);

        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e1) {
                System.err.print(e1);
            }
        }
        return result;
    }

    public void toExcel(List<String> dataMaps, String dataType) {
        String path;
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        path = resultPath + "\\" + dataType + "-" + dateFormat.format(now) + ".xls";
        if (osName.equals("Mac OS X")) {
            path = resultPath + "/" + dataType + "-" + dateFormat.format(now) + ".xls";
        }
        File file = new File(path);
        FileOutputStream fOut = null;

        try {
            int size = dataMaps.size();
            System.out.println(dataType + "收集完成...");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(dataType);

            // 行标
            int rowNum;
            // 列标
            // int colNum;

            HSSFRow row = sheet.createRow(0);
            // 单元格
            HSSFCell cell = null;
            row.createCell(0).setCellValue(dataType);

            for (rowNum = 0; rowNum < size; rowNum++) {
                row = sheet.createRow((short) rowNum + 1);
                row.createCell(0).setCellValue(dataMaps.get(rowNum));
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
