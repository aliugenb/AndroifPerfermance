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
 * Date: 2018/3/30
 * Time: 17:58
 * 首页反复进入超级返检查cpu和memory
 */

public class CpuHomeToSuperTest extends Action {

    private boolean start = false;

    @Test
    public void enterSuper() throws InterruptedException, IOException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            start = true;
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            pressKey(KEY.BACK);
            Thread.sleep(2000);
            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(10)) {
                pressKey(KEY.SUPER);
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
        new Cpu().writeExcel("首页进入超级返Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页进入超级返Memory");
    }
}
