package com.fanli.android.handleData;

import com.fanli.android.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cpu extends GetData {

    @Override
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
            while ((line = in.readLine())!=null) {
                if((line.indexOf("com.fanli.android.apps")!=-1)&&(line.indexOf("com.fanli.android.apps:")==-1)){
                    stringBuffer.append(line+" ");
                }
            }
            String str = stringBuffer.toString().trim();
            result = handleCmd(str);

        } catch (InterruptedException e) {
            System.err.println(e);
        }finally{
            try {
                proc.destroy();
            } catch (Exception e1) {
                System.err.print(e1);
            }
        }
        return result;
    }

    @Override
    public String handleCmd(String result) {
        System.out.println(result);
        String reg="\\s+[0-9]+%\\s+";
        String top = null;
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(result);
        if(m.find()){
            top = m.group().trim();
        }
        System.out.println(top);
        return top;
    }

    @Override
    public List<String> handleData() throws IOException, InterruptedException {
        String command = "adb shell \"top -m 8 -n 1 -d 1\"";
        if (osName.equals("Mac OS X")){
            command = "adb shell top -m 8 -n 1 -d 1";
        }else if(osName.indexOf("Windows")!=-1){
            command = "adb shell \"top -m 8 -n 1 -d 1\"";
        }
        System.out.println("Cpu收集数据开始...");
        List<String> data = new ArrayList<String>();

        while (!Switch.cpuEnd){
            System.out.println("Cpu收集数据中...");
            String cpu=execCommand(command);
            if(cpu!=null){
                data.add(cpu);
            }
            Thread.sleep(5000);
        }
        System.out.println("Cpu收集数据完成...");
        return data;
    }
}
