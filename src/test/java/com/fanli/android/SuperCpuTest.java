package com.fanli.android;

import com.fanli.android.handleData.Cpu;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

public class SuperCpuTest{

    private boolean start = false;

    @BeforeTest
    public static void setUp() throws Exception {
        Action.setUp();
        Thread.sleep(3000);
        Action.skipStartScreen();
        Thread.sleep(3000);
    }

    @Test
    public void superTest() throws InterruptedException,IOException {
        try {
            start = true;

            for (int i=0;i<5;i++){
                Action.driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
                Action.driver.findElementById("com.fanli.android.apps:id/leftIcon").click();
                Thread.sleep(5000);
                Action.driver.pressKeyCode(AndroidKeyCode.BACK);
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

    @AfterTest
    public void tearDown() throws Exception {
        Action.tearDown();
    }

}