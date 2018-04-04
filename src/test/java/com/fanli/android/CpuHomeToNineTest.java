package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.KEY;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/3
 * Time: 17:38
 * 首页反复进入9k9检查cpu和memory
 */

public class CpuHomeToNineTest extends Action {
    private boolean start = false;

    @Test
    public void enterNine() throws InterruptedException, IOException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            start = true;
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            pressKey(KEY.BACK);
            Thread.sleep(2000);
            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(5)) {
                pressKey(KEY.NINE);
                Thread.sleep(2000);
                pressKey(KEY.BACK);
                Thread.sleep(2000);
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
        new Cpu().writeExcel("首页进入九块九Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页进入九块九Memory");
    }
}
