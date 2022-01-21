package lib;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class TrCardActions extends TrCardCoreMethods
{

    // Присвоение значений константам,
    protected static String
            // связанным с вводом логина
            EMAIL_FIELD,
            EMAIL_FIELD_WITH_WARNING,
            INCORRECT_EMAIL_TEXT,
            EMAIL_PATTERN,
            // связанным с вводом пароля
            PASSWORD_FIELD,
            PASSWORD_FIELD_WITH_WARNING,
            INCORRECT_PASSWORD_TEXT_v1,
            INCORRECT_PASSWORD_TEXT_v2,
            // связанным с вводом номера карты
            CARD_NUMBER_FIELD,
            CARD_NUMBER_FIELD_WITH_WARNING,
            INCORRECT_CARD_NUMBER_TEXT,
            // связанным с вводом имени карты
            CARD_NAME_FIELD,
            CARD_NAME_FIELD_WITH_WARNING,
            INCORRECT_CARD_NAME_TEXT,
            // связанным с вводом номера банковской карты
            CREDIT_CARD_NUMBER_FIELD,
            CREDIT_CARD_NUMBER_FIELD_WITH_WARNING,
            INCORRECT_CREDIT_CARD_NUMBER_TEXT,
            // связанным с предоставлением приложению разрешений
            PERMISSION_ALLOW_BUTTON,
            // связанным с шаблонами для поиска элементов
            TEXT_TEMPLATE,
            SCREEN_TITLE_TEMPLATE,
            INPUT_FIELD_TEMPLATE,
            BUTTON_TEMPLATE,
            BUTTON_WITH_PIC_TEMPLATE,
            BIG_BUTTON_TEMPLATE,
            RADIO_BUTTON_TEMPLATE,
            SWITCH_TEMPLATE,
            TOOLTIP_BUTTON_TEMPLATE,
            ANY_ELEMENT_TEMPLATE;

    // Присвоение централизованных значений таймаута для ожиданий
    private static final long
            TIMEOUT_IN_SECONDS = 10,
            TIMEOUT_IN_MILLISECONDS = 1000;

    // Присвоение централизованных параметров для свайпов
    private static final int
            TIME_OF_SWIPE = 400,
            MAX_SWIPES = 200;

    public TrCardActions(AppiumDriver driver)
    {
        super(driver);
    }

    /* Редактирование универсальных шаблонов для поиска элементов */
    private static String replaceTextAndGetLocator(String text)
    {
        return TEXT_TEMPLATE.replace("{TEXT}", text);
    }

    private static String replaceScreenTitleAndGetLocator(String screen_title)
    {
        return SCREEN_TITLE_TEMPLATE.replace("{SCREEN_TITLE}", screen_title);
    }

    private static String replaceInputFieldTextAndGetLocator(String input_field_text)
    {
        return INPUT_FIELD_TEMPLATE.replace("{INPUT_FIELD_TEXT}", input_field_text);
    }

    private static String replaceButtonTextAndGetLocator(String button_name)
    {
        return BUTTON_TEMPLATE.replace("{BUTTON_NAME}", button_name);
    }

    private static String replaceButtonWithPicTextAndGetLocator(String button_text)
    {
        return BUTTON_WITH_PIC_TEMPLATE.replace("{BUTTON_TEXT}", button_text);
    }

    private static String replaceBigButtonTextAndGetLocator(String big_button_text)
    {
        return BIG_BUTTON_TEMPLATE.replace("{BIG_BUTTON_TEXT}", big_button_text);
    }

    private static String replaceRadioButtonTextAndGetLocator(String radio_button_text)
    {
        return RADIO_BUTTON_TEMPLATE.replace("{RADIO_BUTTON_TEXT}", radio_button_text);
    }

    private static String replaceSwitchTextAndGetLocator(String switch_text)
    {
        return SWITCH_TEMPLATE.replace("{SWITCH_TEXT}", switch_text);
    }

    private static String replaceTooltipButtonTextAndGetLocator(String tooltip_button_text)
    {
        return TOOLTIP_BUTTON_TEMPLATE.replace("{TOOLTIP_BUTTON_TEXT}", tooltip_button_text);
    }

    private static String replaceAnyElementTextAndGetLocator(String any_element_text)
    {
        return ANY_ELEMENT_TEMPLATE.replace("{ANY_ELEMENT_TEXT}", any_element_text);
    }
    /* Редактирование универсальных шаблонов для поиска элементов */


    /* Поиск текстовых элементов на экране и проверка их отсутствия на экране */
    public void waitForTextToAppear(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForScreenTitleToAppear(String screen_title)
    {
        String locator = replaceScreenTitleAndGetLocator(screen_title);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + screen_title + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForInputFieldToAppear(String input_field_text)
    {
        String locator = replaceInputFieldTextAndGetLocator(input_field_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + input_field_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTextToDisappear(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Текст '" + text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForInputFieldToDisappear(String input_field_text)
    {
        String locator = replaceInputFieldTextAndGetLocator(input_field_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Текст '" + input_field_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }
    /* Поиск текстовых элементов на экране и проверка их отсутствия на экране */


    /* Поиск кнопок на экране и проверка их отсутствия на экране */
    public void waitForButtonToAppear(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonWithPicToAppear(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBigButtonToAppear(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForRadioButtonToAppear(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSwitchToAppear(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTooltipButtonToAppear(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + tooltip_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForAnyElementToAppear(String any_element_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(any_element_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + any_element_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonToDisappear(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + button_name + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonWithPicToDisappear(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBigButtonToDisappear(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + big_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForRadioButtonToDisappear(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + radio_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSwitchToDisappear(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + switch_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTooltipButtonToDisappear(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + tooltip_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForAnyElementToDisappear(String any_element_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(any_element_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Кнопка с текстом '" + any_element_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }
    /* Поиск кнопок на экране и проверка их отсутствия на экране */


    /* Поиск элементов и получение значений их атрибутов */
    public String waitForTextToAppearAndGetAttribute(String attribute, String text)
    {
        String locator = replaceTextAndGetLocator(text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForButtonWithPicToAppearAndGetAttribute(String attribute, String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForBigButtonToAppearAndGetAttribute(String attribute, String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForSwitchToAppearAndGetAttribute(String attribute, String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }
    /* Поиск элементов и получение значений их атрибутов */


    /* Поиск элементов на экране и их активация */
    public void clickTheButton(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку '" + button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheButtonWithPic(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheBigButton(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheRadioButton(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheSwitch(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheTooltipButton(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + tooltip_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheElement(String any_element_text)
    {
        String locator = replaceAnyElementTextAndGetLocator(any_element_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить кнопку с текстом '" + any_element_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }
    /* Поиск элементов на экране и их активация */


    /* Свайпы вверх для поиска элементов */
    public void swipeUpToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindRadioButtonByText(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + radio_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindSwitchByText(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + switch_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeUpToFindTooltipButtonByText(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + tooltip_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }
    /* Свайпы вверх для поиска элементов */


    /* Свайпы вниз для поиска элементов */
    public void swipeDownToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindRadioButtonByText(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + radio_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindSwitchByText(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + switch_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeDownToFindTooltipButtonByText(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + tooltip_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }
    /* Свайпы вниз для поиска элементов */


    /* Свайпы влево для поиска элементов */
    public void swipeLeftToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeLeftToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeLeftToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeLeftToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }
    /* Свайпы влево для поиска элементов */


    /* Свайпы вправо для поиска элементов */
    public void swipeRightToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeRightToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeRightToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }

    public void swipeRightToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
    }
    /* Свайпы вправо для поиска элементов */


    /* Вспомогательные свайпы */
    public void swipeDownToRefresh()
    {
        this.swipeDownToRefreshData(TIME_OF_SWIPE);
    }
    /* Вспомогательные свайпы */


    /* Нажатия на экран */
    public void waitAndTapTheUpperEdgeOfTheScreen()
    {
        Dimension size = driver.manage().window().getSize();

        this.sleepFor(2000);

        int tap_x = (int) (size.width * 0.5);
        int tap_y = (int) (size.height * 0.1);

        this.tapByCoordinates(tap_x, tap_y);
    }

    public void tapTheUpperEdgeOfTheScreen()
    {
        Dimension size = driver.manage().window().getSize();

        int tap_x = (int) (size.width * 0.5);
        int tap_y = (int) (size.height * 0.1);

        this.tapByCoordinates(tap_x, tap_y);
    }

    public void tapTheLowerEdgeOfTheScreen()
    {
        Dimension size = driver.manage().window().getSize();

        int tap_x = (int) (size.width * 0.5);
        int tap_y = (int) (size.height * 0.9);

        this.tapByCoordinates(tap_x, tap_y);
    }

    public void clickToTheLeftOfTheButton(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
        int
                width = element.getSize().getWidth(),
                height = element.getSize().getHeight(),
                left_x = element.getLocation().getX(),
                upper_y = element.getLocation().getY(),
                right_x = left_x + width,
                lower_y = upper_y + height,
                middle_x = (left_x + right_x) / 2,
                middle_y = (upper_y + lower_y) / 2,

                point_to_click_x = left_x - (width / 2),
                point_to_click_y = middle_y;

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }

    public void clickTheLeftQuarterOfTheBigButton(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        int
                width = element.getSize().getWidth(),
                height = element.getSize().getHeight(),
                left_x = element.getLocation().getX(),
                upper_y = element.getLocation().getY(),
                right_x = left_x + width,
                lower_y = upper_y + height,
                middle_x = (left_x + right_x) / 2,
                middle_y = (upper_y + lower_y) / 2,

                point_to_click_x = middle_x / 2,
                point_to_click_y = middle_y;

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }
    /* Нажатия на экран */


    /* Ввод e-mail и проверка отображения предупреждений */
    public void enterEmailAndCheckText(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                EMAIL_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода e-mail.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (!checkEmail(input)) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о некорректности введённого e-mail.",
                    text.contains(INCORRECT_EMAIL_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного e-mail отображено предупреждение о его некорректности.",
                    text.contains(INCORRECT_EMAIL_TEXT)
            );
        }
    }

    public void enterEmailAndCheckTextAgain(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                EMAIL_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода e-mail.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (!checkEmail(input)) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о некорректности введённого e-mail.",
                    text.contains(INCORRECT_EMAIL_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного e-mail отображено предупреждение о его некорректности.",
                    text.contains(INCORRECT_EMAIL_TEXT)
            );
        }
    }
    /* Ввод e-mail и проверка отображения предупреждений */


    /* Ввод пароля и проверка отображения предупреждений */
    public void enterPasswordAndCheckText(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                PASSWORD_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода пароля.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 8) {
            if (!(text.contains(INCORRECT_PASSWORD_TEXT_v1) || (text.contains(INCORRECT_PASSWORD_TEXT_v2)))) {
            throw new AssertionError("Ошибка! Не отображено предупреждение о недостаточной длине введённого пароля.");
            }
        } else {
            if (text.contains(INCORRECT_PASSWORD_TEXT_v1) || (text.contains(INCORRECT_PASSWORD_TEXT_v2))) {
            throw new AssertionError("Ошибка! При вводе корректного пароля отображено предупреждение о его недостаточной длине.");
            }
        }
    }

    public void enterPasswordAndCheckTextAgain(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                PASSWORD_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода пароля.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 8) {
            if (!(text.contains(INCORRECT_PASSWORD_TEXT_v1) || (text.contains(INCORRECT_PASSWORD_TEXT_v2)))) {
                throw new AssertionError("Ошибка! Не отображено предупреждение о недостаточной длине введённого пароля.");
            }
        } else {
            if (text.contains(INCORRECT_PASSWORD_TEXT_v1) || (text.contains(INCORRECT_PASSWORD_TEXT_v2))) {
                throw new AssertionError("Ошибка! При вводе корректного пароля отображено предупреждение о его недостаточной длине.");
            }
        }
    }
    /* Ввод пароля и проверка отображения предупреждений */


    /* Ввод имени карты и проверка отображения предупреждений */
    public void enterCardNameAndCheckText(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NAME_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода имени карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() == 0) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого имени.",
                    text.contains(INCORRECT_CARD_NAME_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного имени отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CARD_NAME_TEXT)
            );
        }
    }

    public void enterCardNameAndCheckTextAgain(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NAME_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода имени карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() == 0) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого имени.",
                    text.contains(INCORRECT_CARD_NAME_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного имени отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CARD_NAME_TEXT)
            );
        }
    }
    /* Ввод имени карты и проверка отображения предупреждений */


    /* Ввод номера карты и проверка отображения предупреждений */
    public void enterCardNumberAndCheckText(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NUMBER_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 19) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого номера.",
                    text.contains(INCORRECT_CARD_NUMBER_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного номера отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CARD_NUMBER_TEXT)
            );
        }
    }

    public void enterCardNumberAndCheckTextAgain(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NUMBER_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 19) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого номера.",
                    text.contains(INCORRECT_CARD_NUMBER_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного номера отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CARD_NUMBER_TEXT)
            );
        }
    }
    /* Ввод номера карты и проверка отображения предупреждений */


    /* Ввод номера банковской карты и проверка отображения предупреждений */
    public void enterCreditCardNumberAndCheckText(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CREDIT_CARD_NUMBER_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера банковской карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 16) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого номера.",
                    text.contains(INCORRECT_CREDIT_CARD_NUMBER_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного номера отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CREDIT_CARD_NUMBER_TEXT)
            );
        }
    }

    public void enterCreditCardNumberAndCheckTextAgain(String input)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CREDIT_CARD_NUMBER_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера банковской карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text = "";
        if (TrCardPlatform.getInstance().isIOS()) {
            text = element.getAttribute("name");
        } else {
            text = element.getAttribute("text");
        }

        if (input.length() < 16) {
            Assert.assertTrue(
                    "Ошибка! Не отображено предупреждение о недостаточной длине введённого номера.",
                    text.contains(INCORRECT_CREDIT_CARD_NUMBER_TEXT)
            );
        } else {
            Assert.assertFalse(
                    "Ошибка! При вводе корректного номера отображено предупреждение о его недостаточной длине.",
                    text.contains(INCORRECT_CREDIT_CARD_NUMBER_TEXT)
            );
        }
    }
    /* Ввод номера банковской карты и проверка отображения предупреждений */


    /* Поиск поля для ввода данных и ввод текста */
    public void enterText(String field_name, String input)
    {
        String locator = replaceInputFieldTextAndGetLocator(field_name);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить поле для ввода '" + field_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE
        );
        this.waitForElementClickClearAndSendKeys(
                locator,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода '" + field_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }
    /* Поиск поля для ввода данных и ввод текста */


    /* Поиск поля для выбора данных и выбор значения */
    public void chooseTheCity(String city)
    {
        waitForInputFieldToAppear("Выбор города");
        swipeUpToFindBigButtonByText(city);
        clickTheBigButton(city);
    }
    /* Поиск поля для выбора данных и выбор значения */


    /* Установка кода доступа для приложения */
    public void enterPassCode(String code_b_1, String code_b_2, String code_b_3, String code_b_4)
    {
        String[] code_buttons = {code_b_1, code_b_2, code_b_3, code_b_4};

        for (int i = 0; i < 4; i++) {
            String locator = replaceBigButtonTextAndGetLocator(code_buttons[i]);
            this.waitForElementAndClick(
                    locator,
                    "Ошибка! Не удалось обнаружить кнопку '" + code_buttons[i] + "'.",
                    TIMEOUT_IN_SECONDS
            );
        }
    }
    /* Установка кода доступа для приложения */


    /* Проверка строки на соответствие шаблону e-mail */
    private boolean checkEmail(String email)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    /* Проверка строки на соответствие шаблону e-mail */


    /* Маскировка почтового адреса в формат "aut***@test.test" */
    public String hideEmail(String email)
    {
        String[] email_parts = email.split("@");
        String hidden_email = "***@" + email_parts[1];
        if (email_parts[0].length() >= 4) {
            hidden_email = email_parts[0].substring(0,3) + hidden_email;
        }
        return hidden_email;
    }
    /* Маскировка почтового адреса в формат "aut***@test.test" */


    /* Определение ограничений при пополнении Транспортной карты и их проверка */
    public void testReplenishmentLimits(String replenishment_text)
    {
        String[] limits = findReplenishmentLimits(replenishment_text);
        String[] button_clickability = {"false", "true", "true", "true", "true", "false"};
        String[] warning = {"Минимальная сумма", "", "", "", "", "Максимальная сумма"};

        for (int i = 0; i < limits.length; i++) {
            enterText("Сумма пополнения", limits[i]);
            swipeUpToFindBigButtonByText("ОПЛАТИТЬ");
            if (warning[i].equals("Минимальная сумма")) {
                waitForTextToAppear("Минимальная сумма");
                waitForTextToDisappear("Максимальная сумма");
            } else if (warning[i].equals("Максимальная сумма")) {
                waitForTextToDisappear("Минимальная сумма");
                waitForTextToAppear("Максимальная сумма");
            } else {
                waitForTextToDisappear("Минимальная сумма");
                waitForTextToDisappear("Максимальная сумма");
            }
            Assert.assertEquals(
                    "Ошибка! Некорректный статус кнопки 'Оплатить'.",
                    button_clickability[i],
                    waitForBigButtonToAppearAndGetAttribute("clickable", "ОПЛАТИТЬ")
            );
        }
    }

    public String[] findReplenishmentLimits(String text)
    {
        String[] text_parts = text.split(" ");
        String minimum = text_parts[3].split(",")[0];
        String maximum = text_parts[6].split(",")[0];

        int
                minimum_int = Integer.parseInt(minimum),
                minimum_minus = minimum_int - 1,
                minimum_plus = minimum_int + 1,
                maximum_int = Integer.parseInt(maximum),
                maximum_minus = maximum_int - 1,
                maximum_plus = maximum_int + 1;

        String[] limits = {
                minimum_minus + "", minimum_int + "", minimum_plus + "",
                maximum_minus + "", maximum_int + "", maximum_plus + ""
        };

        return limits;
    }
    /* Определение ограничений при пополнении Транспортной карты и их проверка */


    /* Создание строки из случайных букв (регистрация по e-mail) */
    public String makeRandomString() {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder email = new StringBuilder();
        Random rnd = new Random();

        while (email.length() < 3) {
            int index = (int) (rnd.nextFloat() * letters.length());
            email.append(letters.charAt(index));
        }
        return email.toString();
    }
    /* Создание строки из случайных букв (регистрация по e-mail) */


    /* Предоставление приложению необходимых разрешений */
    public void checkForPermissionNotification()
    {
        int notifications = this.getAmountOfElements(PERMISSION_ALLOW_BUTTON);
        if (notifications > 0) {
            this.waitForElementAndClick(
                    PERMISSION_ALLOW_BUTTON,
                    "Ошибка! Не удалось обнаружить кнопку для предоставления разрешения.",
                    TIMEOUT_IN_SECONDS
            );
        }
    }
    /* Предоставление приложению необходимых разрешений */


    /* Перезапуск приложения */
    public void restartApp()
    {
        String app_id = "ru.ftc.tc";
        if (TrCardPlatform.getInstance().isIOS()) {
            app_id = "ru.ftc.zk.tc.inhouse";
        }
        driver.terminateApp(app_id);
        driver.activateApp(app_id);
    }
    /* Перезапуск приложения */


    /* Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения */
    public void checkAndSkipFingerprintsOrFaceID()
    {
        String text_locator = replaceTextAndGetLocator("Нажмите ОК для подключения");
        String skip_button_locator = replaceButtonTextAndGetLocator("ПРОПУСТИТЬ");
        int notifications = this.getAmountOfElements(text_locator);
        if (notifications > 0) {
            this.waitForElementAndClick(
                    skip_button_locator,
                    "Ошибка! Не удалось обнаружить кнопку для пропуска установки входа по отпечатку или по FaceID.",
                    TIMEOUT_IN_SECONDS
            );
        }
    }
    /* Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения */


    /* Активация входа по отпечатку пальца или по FaceID при появлении соответствующего предложения */
    public String checkAndAcceptFingerprintsOrFaceID()
    {
        String text_locator = replaceTextAndGetLocator("Нажмите ОК для подключения");
        String accept_button_locator = replaceButtonTextAndGetLocator("ОК");
        String auth_type = "";
        int notifications = this.getAmountOfElements(text_locator);
        if (notifications > 0) {
            String auth_text = "";
            if (TrCardPlatform.getInstance().isIOS()) {
                auth_text = waitForTextToAppearAndGetAttribute("name", "Нажмите ОК для подключения");
            } else {
                auth_text = waitForTextToAppearAndGetAttribute("content-desc", "Нажмите ОК для подключения");
            }

            if (auth_text.contains("отпечатку")) {
                auth_type = "Fingerprint";
            } else if (auth_text.contains("Face Id")) {
                auth_type = "FaceID";
            }
            waitForElementAndClick(
                    accept_button_locator,
                    "Ошибка! Не удалось обнаружить кнопку для активации входа по отпечатку или по FaceID.",
                    TIMEOUT_IN_SECONDS
            );
        }
        return auth_type;
    }
    /* Активация входа по отпечатку пальца или по FaceID при появлении соответствующего предложения */


    /* Включение и выключение режима полёта */
    public void toggleAirplaneMode()
    {
        if (TrCardPlatform.getInstance().isIOS()) {
            toggleAirplaneModeOnIOS();
        } else {
            toggleAirplaneModeOnAndroid();
        }
    }

    public void toggleAirplaneModeOnAndroid()
    {
        String  airplane_mode_text =
                "(contains(@content-desc, 'Airplane')" +
                " or contains(@content-desc, 'Flight')" +
                " or contains(@content-desc, 'Авиа')" +
                " or contains(@content-desc, 'полет'))" +
                " and (@clickable = 'true')";

        this.swipeDownToOpenTheAndroidControlCenter(TIME_OF_SWIPE);
        this.swipeDownToOpenTheAndroidControlCenter(TIME_OF_SWIPE);

        clickTheElement(airplane_mode_text);

        this.swipeUpToCloseTheAndroidControlCenter(TIME_OF_SWIPE);
        this.tapTheLowerEdgeOfTheScreen();

        this.sleepFor(3000);
    }

    public void toggleAirplaneModeOnIOS()
    {
        driver.activateApp("com.apple.Preferences");

        this.sleepFor(3000);
        driver.activateApp("ru.ftc.zk.tc.inhouse");
    }
    /* Включение и выключение режима полёта */


    /* Ожидание */
    public void waitForMilliseconds(long time_in_milliseconds)
    {
        this.sleepFor(time_in_milliseconds);
    }
    /* Ожидание */

}
