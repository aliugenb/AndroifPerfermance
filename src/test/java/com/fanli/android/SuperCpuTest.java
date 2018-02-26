package com.fanli.android;

import com.fanli.android.handleData.Cpu;
import io.appium.java_client.android.AndroidKeyCode;
import org.testng.annotations.Test;

import java.io.IOException;

public class SuperCpuTest extends Action{

    private boolean start = false;

    @Test
    public void superTest() throws InterruptedException,IOException {
        Thread.sleep(10000);
        try {
            start = true;
            for (int i=0;i<5;i++){
                driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
                driver.pressKeyCode(AndroidKeyCode.BACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {
            start = true;
            Switch.cpuEnd=true;
        }
    }

    @Test
    public void cpuMonitor() throws IOException, InterruptedException{
        while (!start){
            Thread.sleep(500);
            System.out.println("waiting");
        }
        new Cpu().writeExcel("super-Cpu");
    }

}