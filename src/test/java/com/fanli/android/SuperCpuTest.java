package com.fanli.android;

import com.fanli.android.handleData.Cpu;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidKeyCode;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Date;

public class SuperCpuTest extends Action{

    private boolean start = false;
    private static  long s = (new Date()).getTime();

//    @BeforeTest
//    public static void setUp() throws Exception {
//        Action.setUp();
//        Thread.sleep(3000);
//        Action.skipStartScreen();
//        Thread.sleep(3000);
//        Action.closeInterstitial();
//    }

    @Test
    public void superTest() throws InterruptedException,IOException {
        start = true;
        Action.execCmd("adb shell input tap 126 558");
        Thread.sleep(3000);
        closeInterstitial();
        Action.execCmd("adb shell input keyevent 4");
        Thread.sleep(3000);
        try {
            while (((new Date()).getTime()-s)<Action.formatMin(5)){
//                Action.driver.findElementByAndroidUIAutomator("text(\"超级返\")").click();
                Action.execCmd("adb shell input tap 126 558");
                Thread.sleep(3000);
//                Action.driver.findElementById("com.fanli.android.apps:id/leftIcon").click();

                Action.execCmd("adb shell input keyevent 4");
                Thread.sleep(3000);
            }
            Thread.sleep(Action.formatMin(2));
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

//    @AfterTest
//    public void tearDown() throws Exception {
//        Action.tearDown();
//    }

}