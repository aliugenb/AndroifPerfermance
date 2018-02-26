package com.fanli.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetDeviceInfo {
    private String OsVersion;
    private String DeviceName;

//    public void setOsVersion(String osVersion) {
//        OsVersion = osVersion;
//    }

    public String getOsVersion() {
        return OsVersion;
    }

//    public void setDeviceName(String deviceName) {
//        DeviceName = deviceName;
//    }

    public String getDeviceName() {
        return DeviceName;
    }

    public  GetDeviceInfo() {
        String version = GetInfo("adb shell getprop ro.build.version.release");
        String deviceName = GetInfo("adb devices");
        Pattern r = Pattern.compile("attached(.*)device");
        Matcher m = r.matcher(deviceName);
        this.OsVersion = version.trim();
        if (m.find()){
            this.DeviceName = m.group(1).trim();
        }
    }

    public static String GetInfo(String cmd){
        Runtime run = Runtime.getRuntime();
        String info = null;
        try {
            Process process = run.exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null){
                stringBuffer.append(line+" ");
            }
            info = stringBuffer.toString();
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }
}
