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

/**
 * Created with IntelliJ IDEA.
 * Author: ye.liu
 * Date: 2018/4/9
 * Time: 10:38
 * 超级返切换分类检查cpu和memory
 */

public class CpuSwitchSuperCatsTest extends Action {

    private boolean start = false;

    @Test
    public void switchSuperCats() throws InterruptedException, IOException {
        try {
            driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
            Thread.sleep(3000);
            closeInterstitial();
            Thread.sleep(2000);
            AndroidElement androidElement = driver.findElementById("com.fanli.android.apps:id/iv_arrow");
            int y = androidElement.getCenter().getY();
            int maxX = androidElement.getLocation().getX();
            long s = (new Date()).getTime();
            start = true;
            while (((new Date()).getTime() - s) < formatMin(10)) {
                execCmd("adb shell input tap " + (int) Math.round(Math.random() * (maxX - 10) + 10) + " " + y + "");
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
        new Cpu().writeExcel("超级返切换分类Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("超级返切换分类Memory");
    }
}
