package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class TrCardCoreMethods
{

    protected AppiumDriver driver;

    public TrCardCoreMethods(AppiumDriver driver)
    {
        this.driver = driver;
    }

    /* Поиск элементов и взаимодействие с ними */
    public WebElement waitForElementPresent(String locator, String error_message, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementAndClick(String locator, String error_message, long timeout_in_seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeout_in_seconds);
        element.click();
        return element;
    }

    public WebElement waitForElementClickAndSendKeys(String locator, String value, String error_message, long timeout_in_seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeout_in_seconds);
        element.click();
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementClickClearAndSendKeys(String locator, String value, String error_message, long timeout_in_seconds)
    {
        WebElement element = waitForElementPresent(locator, error_message, timeout_in_seconds);
        element.click();
        element.clear();
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeout_in_seconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    /* Поиск элементов и взаимодействие с ними */


    /* Разделение "полного" локатора на составные части (тип и локатор) */
    private By getLocatorByString(String locator_with_type)
    {
        String[] split_locator = locator_with_type.split(Pattern.quote(":"),2);
        String by_type = split_locator[0];
        String locator = split_locator[1];

        if (by_type.equals("xpath")) {
            return By.xpath(locator);
        } else if (by_type.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Ошибка! Не удалось определить тип локатора '" + locator_with_type + "'.");
        }
    }
    /* Разделение "полного" локатора на составные части (тип и локатор) */


    /* Нажатие на экран по заданным координатам */
    public void tapByCoordinates(int x, int y)
    {
        TouchAction point = new TouchAction(driver);
        point.tap(PointOption.point(x, y)).perform();
    }
    /* Нажатие на экран по заданным координатам */


    /* Простые свайпы */
    public void swipeUp(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int) (size.height * 0.5);
        int end_y = (int) (size.height * 0.4);

        action.press(PointOption.point(x, start_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_y = (int) (size.height * 0.1);
            action.moveTo(PointOption.point(0, -offset_y));
        } else {
            action.moveTo(PointOption.point(x, end_y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }

    public void swipeDown(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int) (size.height * 0.4);
        int end_y = (int) (size.height * 0.5);

        action.press(PointOption.point(x, start_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_y = (int) (size.height * 0.1);
            action.moveTo(PointOption.point(0, offset_y));
        } else {
            action.moveTo(PointOption.point(x, end_y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }

    public void swipeUpperLeft(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int start_x = (int) (size.width * 0.6);
        int end_x = (int) (size.width * 0.4);
        int y = size.height / 4;

        action.press(PointOption.point(start_x, y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_x = (int) (size.width * 0.2);
            action.moveTo(PointOption.point(-offset_x, 0));
        } else {
            action.moveTo(PointOption.point(end_x, y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }

    public void swipeLowerLeft(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int start_x = (int) (size.width * 0.6);
        int end_x = (int) (size.width * 0.4);
        int y = (int) (size.height * 0.6);

        action.press(PointOption.point(start_x, y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_x = (int) (size.width * 0.2);
            action.moveTo(PointOption.point(-offset_x, 0));
        } else {
            action.moveTo(PointOption.point(end_x, y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }

    public void swipeUpperRight(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int start_x = (int) (size.width * 0.4);
        int end_x = (int) (size.width * 0.6);
        int y = size.height / 4;

        action.press(PointOption.point(start_x, y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_x = (int) (size.width * 0.2);
            action.moveTo(PointOption.point(offset_x, 0));
        } else {
            action.moveTo(PointOption.point(end_x, y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }

    public void swipeLowerRight(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int start_x = (int) (size.width * 0.4);
        int end_x = (int) (size.width * 0.6);
        int y = (int) (size.height * 0.6);

        action.press(PointOption.point(start_x, y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            int offset_x = (int) (size.width * 0.2);
            action.moveTo(PointOption.point(offset_x, 0));
        } else {
            action.moveTo(PointOption.point(end_x, y));
        }
        action.release();
        action.perform();
        if (TrCardPlatform.getInstance().isIOS()) {
            sleepFor(500);
        }
    }
    /* Простые свайпы */


    /* Свайпы для открытия и закрытия панели управления Android */
    public void swipeDownToOpenTheAndroidControlCenter(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 4;
        int start_y = 0;
        int end_y = (int) (size.height * 0.1);

        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    public void swipeUpToCloseTheAndroidControlCenter(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 4;
        int start_y = (int) (size.height * 0.9);
        int end_y = 0;

        action
                .press(PointOption.point(x, start_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)))
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }
    /* Свайпы для открытия и закрытия панели управления Android */


    /* Свайп для обновления отображаемых данных */
    public void swipeDownToRefreshData(int time_of_swipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width / 2;
        int start_y = (int) (size.height * 0.5);
        int end_y = (int) (size.height * 0.8);

        action.press(PointOption.point(x, start_y));
        action.waitAction(WaitOptions.waitOptions(Duration.ofMillis(time_of_swipe)));
        if (TrCardPlatform.getInstance().isIOS()) {
            // здесь 100% должно было быть умножение на 0.3, но оно никак не хотело работать... пришлось экспериментировать!
            int offset_y = (int) (size.height * 0.9);
            action.moveTo(PointOption.point(0, offset_y));
        } else {
            action.moveTo(PointOption.point(x, end_y));
        }
        action.release();
        action.perform();
    }
    /* Свайп для обновления отображаемых данных */


    /* Свайпы для поиска элементов */
    public void swipeUpToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeUp(time_of_swipe);
            ++already_swiped;
        }
    }

    public void swipeDownToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeDown(time_of_swipe);
            ++already_swiped;
        }
    }

    public void swipeUpperLeftToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeUpperLeft(time_of_swipe);
            ++already_swiped;
        }
    }

    public void swipeLowerLeftToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeLowerLeft(time_of_swipe);
            ++already_swiped;
        }
    }

    public void swipeUpperRightToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeUpperRight(time_of_swipe);
            ++already_swiped;
        }
    }

    public void swipeLowerRightToFindElement(String locator, String error_message, int max_swipes, int time_of_swipe, long timeout_in_seconds)
    {
        By by = getLocatorByString(locator);
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(
                        locator,
                        error_message,
                        timeout_in_seconds
                );
                return;
            }

            swipeLowerRight(time_of_swipe);
            ++already_swiped;
        }
    }
    /* Свайпы для поиска элементов */


    /* Получение количества найденных элементов */
    public int getAmountOfElements(String locator)
    {
        By by = getLocatorByString(locator);
        sleepFor(500);
        return driver.findElements(by).size();
    }
    /* Получение количества найденных элементов */


    /* Ожидание */
    public void sleepFor(long time_to_sleep)
    {
        try {
            Thread.sleep(time_to_sleep);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    /* Ожидание */

}
