package lib;

import io.appium.java_client.AppiumDriver;
import lib.android.AndroidActions;
import lib.ios.iOSActions;

public class TrCardActionsFactory {

    public static TrCardActions get(AppiumDriver driver)
    {
        if (TrCardPlatform.getInstance().isIOS()) {
            return new iOSActions(driver);
        } else {
            return new AndroidActions(driver);
        }
    }

}
