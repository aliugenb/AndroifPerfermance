package com.fanli.android.action;

import com.fanli.android.handleData.DataSwitch;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Action {

    public static AndroidDriver<AndroidElement> driver;
    //测试时长min
    public final static int testTime = 10;

    @BeforeTest
    public static void setUp() throws Exception {
        GetDeviceInfo getDeviceInfo = new GetDeviceInfo();
        String deviceName = getDeviceInfo.getDeviceName();
        String platformVersion = getDeviceInfo.getOsVersion();

        //设置apk的路径
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps");
        File app = new File(appDir, "FanliAndroid.apk");

        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", false);
        //  capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("device", "Android");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", deviceName);

        //设置安卓系统版本
        capabilities.setCapability("platformVersion", platformVersion);
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath());


        //设置新的命令等待时长（应该用不到，设置1h）
        capabilities.setCapability("newCommandTimeout", 3600);

        //使用自带输入法，输入中文
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);

//        capabilities.setCapability("automationName","uiautomator2");
//        capabilities.setCapability("noSign", true);
        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.fanli.android.apps");
        capabilities.setCapability("appActivity", "com.fanli.android.basicarc.ui.activity.SplashActivity");


        //初始化
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        //关闭开机动画和弹层
        Thread.sleep(3000);
        Action.skipStartScreen();
        Thread.sleep(2000);
        Action.closeInterstitial();
        Thread.sleep(2000);
    }

    @AfterTest
    public static void tearDown() throws Exception {
        DataSwitch.fpsEnd = false;
        DataSwitch.cpuEnd = false;
        DataSwitch.memoryEnd = false;
        DataSwitch.excelNormal = true;
        driver.quit();
    }

    //执行cmd
    public static void execCmd(String cmd) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(cmd);

        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            proc.destroy();
        }
    }

    public static void login() throws InterruptedException, MyException {
        driver.findElementById("com.fanli.android.apps:id/login_username").sendKeys("hitest");
        Thread.sleep(2000);
        driver.findElementById("com.fanli.android.apps:id/login_password").sendKeys("fanli123");
        Thread.sleep(2000);
        driver.findElementById("com.fanli.android.apps:id/btn_login").click();
        Thread.sleep(3000);
        try {
            if (driver.findElementById("com.fanli.android.apps:id/btn_login").isDisplayed()) {
                throw new MyException("登录失败");
            }
        } catch (NoSuchElementException e) {
            System.out.println("登录成功");
        } catch (MyException e) {
            System.err.println(e);
            throw e;
        }
    }

    public static void sleep(int ms) throws InterruptedException {
        Thread.sleep(ms);
    }

    public List<AndroidElement> getElementsByResourceId(String resourceId) {
        List<AndroidElement> lis = driver.findElementsById(resourceId);
        return lis;
    }

    //根据设定时长滑动页面
    public static void swipUpAndDownByTime(int time) throws InterruptedException, IOException, MyException {
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;
        long s = (new Date()).getTime();
        while ((new Date()).getTime() - s < formatMin(time)) {
            checkInFanli();
            for (int i1 = 0; i1 <= 8; i1++) {
                TouchAction action = new TouchAction(driver).press(width / 2, height * 5 / 7).waitAction().moveTo(width / 2, height * 2 / 7).release();
                action.perform();
                Thread.sleep(1000);
            }
            for (int i2 = 0; i2 <= 5; i2++) {
                TouchAction action1 = new TouchAction(driver).press(width / 2, height * 2 / 7).waitAction().moveTo(width / 2, height * 5 / 7).release();
                action1.perform();
                Thread.sleep(1000);
            }
        }
    }

    //获取中心点击坐标
    public static String getCenterCoordinates(AndroidElement androidElement) {
        String coordinates = null;
        int x = androidElement.getCenter().getX();
        int y = androidElement.getCenter().getY();
        coordinates = "" + x + " " + y + "";
        return coordinates;
    }

    public static void pressKey(KEY keyCode) throws IOException {
        if (keyCode.equals(KEY.BACK)) {
            //点击返回键
            execCmd("adb shell input keyevent 4");
        } else if (keyCode.equals(KEY.ENTER)) {
            //点击ENTER键
            execCmd("adb shell input keyevent 66");
        } else if (keyCode.equals(KEY.HOME)) {
            //点击HOME键
            execCmd("adb shell input keyevent 3");
        }
    }

    public static void skipStartScreen() {
        try {
            if (driver.findElementById("com.fanli.android.apps:id/main_image").isDisplayed()) {
                driver.findElementById("com.fanli.android.apps:id/main_image").click();
                System.out.println("跳过开机画面");
            }
        } catch (Exception e) {
            System.out.println("无开机画面或已关闭");
        }
    }

    public static void skipSplash() {
        try {
            if (driver.findElementById("com.fanli.android.apps:id/splash_img").isDisplayed()) {
                driver.findElementByAndroidUIAutomator("text(\"跳过\")").click();
                System.out.println("跳过splash");
            }
        } catch (Exception e) {
            System.out.println("无splash");
        }
    }

    public static void closeInterstitial() {
        try {
            if (driver.findElementById("com.fanli.android.apps:id/close").isDisplayed()) {
                driver.findElementById("com.fanli.android.apps:id/close").click();
                System.out.println("关闭弹层");
            }
        } catch (Exception e) {
            System.out.println("弹层不存在！");
        }
    }

    //获取非appium的输入法
    public static String inputMethod() throws IOException, MyException {
        String inputMethod = null;
        List<String> inputMethods = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("adb shell ime list -s");
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                inputMethods.add(line.toString().trim());
            }

            if (inputMethods.size() == 1 && inputMethods.indexOf("io.appium.android.ime/.UnicodeIME") == 0) {
                throw new MyException("请安装其他三方输入法");
            } else {
                if (inputMethods.indexOf("io.appium.android.ime/.UnicodeIME") > 0) {
                    inputMethod = inputMethods.get(0);
                } else if (inputMethods.indexOf("io.appium.android.ime/.UnicodeIME") == 0) {
                    inputMethod = inputMethods.get(1);
                } else {
                    throw new MyException("没有安装appium输入法");
                }
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e1) {
                System.err.print(e1);
            }
        }
        return inputMethod;
    }

    // 检查当前页面最上层的activity是否是返利
    public static void checkInFanli() throws IOException, MyException {
        String command = "adb shell \"dumpsys activity | grep \"mFocusedActivity\"\"";
        if (System.getProperty("os.name").equals("Mac OS X")) {
            command = "adb shell dumpsys activity | grep \"mFocusedActivity\"";
        }
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec(command);
        try {
            if (proc.waitFor() != 0) {
                System.err.println("exit value = " + proc.exitValue());
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line + " ");
            }
            String str = stringBuffer.toString().trim();
            if (str.indexOf("com.fanli.android.apps") == -1) {
                throw new MyException("当前不在返利app");
            }
        } catch (InterruptedException e) {
            System.err.println(e);
        } finally {
            try {
                proc.destroy();
            } catch (Exception e1) {
                System.err.print(e1);
                throw e1;
            }
        }
    }


    //分钟转换成毫秒
    public static int formatMin(int i) {
        int timeLong = 0;
        if (i > 0) {
            timeLong = i * 60 * 1000;
        } else {
            System.out.println("输入错误");
        }
        return timeLong;
    }
}
