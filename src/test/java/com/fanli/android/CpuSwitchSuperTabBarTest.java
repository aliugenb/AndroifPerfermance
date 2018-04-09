package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.MyException;
import com.fanli.android.handleData.Cpu;
import com.fanli.android.handleData.DataSwitch;
import com.fanli.android.handleData.Memory;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/8
 * Time: 16:05
 * 超级返切换底部bar检查cpu和memory
 */

public class CpuSwitchSuperTabBarTest extends Action {
    private boolean start = false;

    @Test
    public void switchTabBar() throws InterruptedException, IOException, MyException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"我的\")").click();
            Thread.sleep(2000);
            login();
            driver.findElementByAndroidUIAutomator("text(\"首页\")").click();
            Thread.sleep(2000);
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            Thread.sleep(2000);
            closeInterstitial();
            Thread.sleep(2000);
            List<AndroidElement> tabBars = getElementsByResourceId("com.fanli.android.apps:id/tvName");
            tabBars.get(1).click();
            start = true;
            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(testTime)) {
                for (int i = 0; i < tabBars.size(); i++) {
                    tabBars.get(i).click();
                    Thread.sleep(1000);
                }
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
        new Cpu().writeExcel("超级返切换底部barCpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("超级返切换底部barMemory");
    }
}
