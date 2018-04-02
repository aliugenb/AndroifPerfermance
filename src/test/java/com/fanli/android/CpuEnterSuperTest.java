package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/3/30
 * Time: 17:58
 * 首页反复进入超级返检查cpu和memory
 */

public class CpuEnterSuperTest extends Action {

    private boolean start = false;

    @Test
    public void enterSuperTest() throws InterruptedException,IOException {
        start = true;
        driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
        Thread.sleep(3000);
        closeInterstitial();
        execCmd("adb shell input keyevent 4");
        Thread.sleep(3000);
        try {
            long s = (new Date()).getTime();
            while (((new Date()).getTime()-s)<formatMin(5)){
                execCmd("adb shell input tap 126 558");
                Thread.sleep(3000);
                execCmd("adb shell input keyevent 4");
                Thread.sleep(3000);
            }
            Thread.sleep(formatMin(2));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            start = true;
            DataSwitch.excelNormal = false;
            DataSwitch.cpuEnd=true;
        }
    }

    @Test
    public void cpuMonitor() throws IOException, InterruptedException{
        while (!start){
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页进入超级返Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException{
        while (!start){
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页进入超级返Memory");
    }
}
