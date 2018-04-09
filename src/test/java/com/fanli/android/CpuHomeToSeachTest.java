package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.KEY;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/4
 * Time: 14:38
 *首页反复进入主搜检查cpu和memory
 */
public class CpuHomeToSeachTest extends Action{
    private boolean start = false;

    @Test
    public void enterSeach() throws InterruptedException, IOException {
        try {
            driver.findElementById("com.fanli.android.apps:id/search_bg").click();
            start = true;
            Thread.sleep(2000);
            driver.findElementByClassName("android.widget.EditText").sendKeys("U盘");
            Thread.sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"搜索\")").click();
            Thread.sleep(3000);
            pressKey(KEY.BACK);
            Thread.sleep(1000);
            pressKey(KEY.BACK);
            Thread.sleep(1000);
            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(10)) {
                pressKey(KEY.SEARCH);
                Thread.sleep(2000);
                execCmd("adb shell input tap 100 457");
                Thread.sleep(4000);
                pressKey(KEY.BACK);
                Thread.sleep(1000);
                pressKey(KEY.BACK);
                Thread.sleep(1000);
            }
            Thread.sleep(formatMin(2));
        } catch (Exception e) {
            DataSwitch.excelNormal = false;
            throw e;
        } finally {
            start = true;
            DataSwitch.cpuEnd = true;
            DataSwitch.memoryEnd = true;
        }
    }

    @Test
    public void cpuMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页进入主搜Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页进入主搜Memory");
    }
}
