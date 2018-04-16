package com.fanli.android;

import com.fanli.android.action.Action;
import com.fanli.android.action.KEY;
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
 * Date: 2018/3/30
 * Time: 17:58
 * 首页反复进入超级返检查cpu和memory
 */

public class CpuHomeToSuperTest extends Action {

    private boolean start = false;

    @Test
    public void enterSuper() throws InterruptedException, IOException, MyException {
        try {
            AndroidElement superElement = driver.findElementByAndroidUIAutomator("text(\"超级返\")");
            String superCoordinates = getCenterCoordinates(superElement);
            superElement.click();
            start = true;
            sleep(3000);
            closeInterstitial();
            sleep(2000);
            pressKey(KEY.BACK);
            sleep(2000);
            long s = (new Date()).getTime();
            while (((new Date()).getTime() - s) < formatMin(testTime)) {
                checkInFanli();
                execCmd("adb shell input tap " + superCoordinates + "");
                sleep(2000);
                pressKey(KEY.BACK);
                sleep(1000);
            }
            sleep(formatMin(2));
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
            sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("首页进入超级返Cpu");
    }

    @Test
    public void memoryMonitor() throws IOException, InterruptedException {
        while (!start) {
            sleep(500);
            System.out.println("waiting");
        }
        new Memory().writeExcel("首页进入超级返Memory");
    }
}
