package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TrCardPlatform
{

    private static final String
            APPIUM_URL = "http://127.0.0.1:4723/wd/hub",
            PLATFORM_ANDROID = "android",
            PLATFORM_IOS = "ios",
            APP_ANDROID = "C:/IntelliJ Projects/TrCardAutomation/apks/T-Card.live.release.368.apk",
            APP_IOS = "/Volumes/500/TrCardAutomation/apks/Build.app",
            PACKAGE = "ru.ftc.tc",
            ACTIVITY = "io.flutter.embedding.android.FlutterFragmentActivity";

    private static TrCardPlatform instance;

    private TrCardPlatform() {}

    public static TrCardPlatform getInstance()
    {
        if (instance == null) {
            instance = new TrCardPlatform();
        }
        return instance;
    }

    public AppiumDriver getDriver() throws Exception
    {
        URL URL = new URL(APPIUM_URL);

        if (this.isAndroid()) {
            return new AndroidDriver(URL, this.getAndroidCapabilities());
        } else if (this.isIOS()) {
            return new IOSDriver(URL, this.getIOSCapabilities());
        } else {
            throw new Exception("Ошибка! Не удалось получить сведения о платформе из переменной окружения.");
        }
    }

    public boolean isAndroid()
    {
        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS()
    {
        return isPlatform(PLATFORM_IOS);
    }

    private boolean isPlatform(String current_platform)
    {
        String platform = this.getPlatformVar();

        return current_platform.equals(platform);
    }

    private String getPlatformVar()
    {
    return System.getenv("PLATFORM");
    }

    private DesiredCapabilities getAndroidCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","11");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage", PACKAGE);
        capabilities.setCapability("appActivity", ACTIVITY);
        capabilities.setCapability("app", APP_ANDROID);

        return capabilities;
    }

    private DesiredCapabilities getIOSCapabilities()
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone SE 13.0");
        capabilities.setCapability("platformVersion","13.0");
        capabilities.setCapability("app", APP_IOS);

        return capabilities;
    }

}
