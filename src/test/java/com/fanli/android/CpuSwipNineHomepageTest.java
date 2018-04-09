package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/9
 * Time: 14:30
 * 九块九首页滑动检查cpu和memory
 */

public class CpuSwipNineHomepageTest extends Action {
    private boolean start = false;

    @Test
    public void swipNineHomepage() throws InterruptedException, IOException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"9块9\")").click();
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            start = true;
            swipUpAndDownByTime(testTime);
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
        new Cpu().writeExcel("九块九首页滑动Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("九块九首页滑动Memory");
    }
}
