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
            WARNING_TEXT_TEMPLATE,
            SCREEN_TITLE_TEMPLATE,
            BOTTOM_BANNER_TEMPLATE,
            INPUT_FIELD_TEMPLATE,
            BUTTON_TEMPLATE,
            TOP_BUTTON_TEMPLATE,
            BUTTON_LINK_TEMPLATE,
            BUTTON_WITH_PIC_TEMPLATE,
            BIG_BUTTON_TEMPLATE,
            RADIO_BUTTON_TEMPLATE,
            PAYMENT_RADIO_BUTTON_TEMPLATE,
            SBP_PAYMENT_RADIO_BUTTON_TEMPLATE,
            SWITCH_TEMPLATE,
            CARD_NAME_BUTTON_TEMPLATE,
            CARD_EMAIL_BUTTON_TEMPLATE,
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

    private static String replaceWarningTextAndGetLocator(String warning_text)
    {
        return WARNING_TEXT_TEMPLATE.replace("{WARNING_TEXT}", warning_text);
    }

    private static String replaceScreenTitleAndGetLocator(String screen_title)
    {
        return SCREEN_TITLE_TEMPLATE.replace("{SCREEN_TITLE}", screen_title);
    }

    private static String replaceBottomBannerTextAndGetLocator(String bottom_banner_text)
    {
        return BOTTOM_BANNER_TEMPLATE.replace("{BOTTOM_BANNER_TEXT}", bottom_banner_text);
    }

    private static String replaceInputFieldTextAndGetLocator(String input_field_text)
    {
        return INPUT_FIELD_TEMPLATE.replace("{INPUT_FIELD_TEXT}", input_field_text);
    }

    private static String replaceButtonTextAndGetLocator(String button_name)
    {
        return BUTTON_TEMPLATE.replace("{BUTTON_NAME}", button_name);
    }

    private static String replaceTopButtonTextAndGetLocator(String top_button_name)
    {
        return TOP_BUTTON_TEMPLATE.replace("{TOP_BUTTON_NAME}", top_button_name);
    }

    private static String replaceButtonLinkTextAndGetLocator(String button_link_text)
    {
        return BUTTON_LINK_TEMPLATE.replace("{BUTTON_LINK_TEXT}", button_link_text);
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

    private static String replacePaymentRadioButtonTextAndGetLocator(String payment_radio_button_text)
    {
        return PAYMENT_RADIO_BUTTON_TEMPLATE.replace("{PAYMENT_RADIO_BUTTON_TEXT}", payment_radio_button_text);
    }

    private static String replaceSBPPaymentRadioButtonTextAndGetLocator(String sbp_payment_radio_button_text)
    {
        return SBP_PAYMENT_RADIO_BUTTON_TEMPLATE.replace("{SBP_PAYMENT_RADIO_BUTTON_TEXT}", sbp_payment_radio_button_text);
    }

    private static String replaceSwitchTextAndGetLocator(String switch_text)
    {
        return SWITCH_TEMPLATE.replace("{SWITCH_TEXT}", switch_text);
    }

    private static String replaceCardNameButtonTextAndGetLocator(String card_name_button_text)
    {
        return CARD_NAME_BUTTON_TEMPLATE.replace("{CARD_NAME_BUTTON_TEXT}", card_name_button_text);
    }

    private static String replaceCardEmailButtonTextAndGetLocator(String card_email_button_text)
    {
        return CARD_EMAIL_BUTTON_TEMPLATE.replace("{CARD_EMAIL_BUTTON_TEXT}", card_email_button_text);
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

    public void waitForWarningTextToAppear(String warning_text)
    {
        String locator = replaceWarningTextAndGetLocator(warning_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + warning_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForScreenTitleToAppear(String screen_title)
    {
        String locator = replaceScreenTitleAndGetLocator(screen_title);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить заголовок '" + screen_title + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBottomBannerToAppear(String bottom_banner_text)
    {
        String locator = replaceBottomBannerTextAndGetLocator(bottom_banner_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить баннер с текстом '" + bottom_banner_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForInputFieldToAppear(String input_field_text)
    {
        String locator = replaceInputFieldTextAndGetLocator(input_field_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить поле для ввода с текстом '" + input_field_text + "'.",
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

    public void waitForWarningTextToDisappear(String warning_text)
    {
        String locator = replaceWarningTextAndGetLocator(warning_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Текст '" + warning_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForScreenTitleToDisappear(String screen_title)
    {
        String locator = replaceScreenTitleAndGetLocator(screen_title);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Заголовок '" + screen_title + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBottomBannerToDisappear(String bottom_banner_text)
    {
        String locator = replaceBottomBannerTextAndGetLocator(bottom_banner_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Баннер с текстом '" + bottom_banner_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForInputFieldToDisappear(String input_field_text)
    {
        String locator = replaceInputFieldTextAndGetLocator(input_field_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Поле для ввода с текстом '" + input_field_text + "' всё ещё отображается на экране.",
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
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTopButtonToAppear(String top_button_name)
    {
        String locator = replaceTopButtonTextAndGetLocator(top_button_name);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент TopButton с текстом '" + top_button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonLinkToAppear(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonWithPicToAppear(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBigButtonToAppear(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForRadioButtonToAppear(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент RadioButton с текстом '" + radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForPaymentRadioButtonToAppear(String payment_radio_button_text)
    {
        String locator = replacePaymentRadioButtonTextAndGetLocator(payment_radio_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент PaymentRadioButton с текстом '" + payment_radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSBPPaymentRadioButtonToAppear(String sbp_payment_radio_button_text)
    {
        String locator = replaceSBPPaymentRadioButtonTextAndGetLocator(sbp_payment_radio_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент SBPPaymentRadioButton с текстом '" + sbp_payment_radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSwitchToAppear(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch с текстом '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForCardNameButtonToAppear(String card_name_button_text)
    {
        String locator = replaceCardNameButtonTextAndGetLocator(card_name_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardNameButton с текстом '" + card_name_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForCardEmailButtonToAppear(String card_email_button_text)
    {
        String locator = replaceCardEmailButtonTextAndGetLocator(card_email_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardEmailButton с текстом '" + card_email_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTooltipButtonToAppear(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент TooltipButton с текстом '" + tooltip_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForAnyElementToAppear(String any_element_text)
    {
        String locator = replaceAnyElementTextAndGetLocator(any_element_text);
        this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + any_element_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonToDisappear(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент Button с текстом '" + button_name + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTopButtonToDisappear(String top_button_name)
    {
        String locator = replaceTopButtonTextAndGetLocator(top_button_name);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент TopButton с текстом '" + top_button_name + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonLinkToDisappear(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент ButtonLink с текстом '" + button_link_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForButtonWithPicToDisappear(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент ButtonWithPic с текстом '" + button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForBigButtonToDisappear(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент BigButton с текстом '" + big_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForRadioButtonToDisappear(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент RadioButton с текстом '" + radio_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForPaymentRadioButtonToDisappear(String payment_radio_button_text)
    {
        String locator = replacePaymentRadioButtonTextAndGetLocator(payment_radio_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент PaymentRadioButton с текстом '" + payment_radio_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSBPPaymentRadioButtonToDisappear(String sbp_payment_radio_button_text)
    {
        String locator = replaceSBPPaymentRadioButtonTextAndGetLocator(sbp_payment_radio_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент SBPPaymentRadioButton с текстом '" + sbp_payment_radio_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForSwitchToDisappear(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент Switch с текстом '" + switch_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForCardNameButtonToDisappear(String card_name_button_text)
    {
        String locator = replaceCardNameButtonTextAndGetLocator(card_name_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент CardNameButton с текстом '" + card_name_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForCardEmailButtonToDisappear(String card_email_button_text)
    {
        String locator = replaceCardEmailButtonTextAndGetLocator(card_email_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент CardEmailButton с текстом '" + card_email_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForTooltipButtonToDisappear(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент TooltipButton с текстом '" + tooltip_button_text + "' всё ещё отображается на экране.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void waitForAnyElementToDisappear(String any_element_text)
    {
        String locator = replaceAnyElementTextAndGetLocator(any_element_text);
        this.waitForElementNotPresent(
                locator,
                "Ошибка! Элемент с текстом '" + any_element_text + "' всё ещё отображается на экране.",
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
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForBigButtonToAppearAndGetAttribute(String attribute, String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForSwitchToAppearAndGetAttribute(String attribute, String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForCardNameButtonToAppearAndGetAttribute(String attribute, String card_name_button_text)
    {
        String locator = replaceCardNameButtonTextAndGetLocator(card_name_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardNameButton '" + card_name_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        return element.getAttribute(attribute);
    }

    public String waitForCardEmailButtonToAppearAndGetAttribute(String attribute, String card_email_button_text)
    {
        String locator = replaceCardEmailButtonTextAndGetLocator(card_email_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardEmailButton '" + card_email_button_text + "'.",
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
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheTopButton(String top_button_name)
    {
        String locator = replaceTopButtonTextAndGetLocator(top_button_name);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент TopButton с текстом '" + top_button_name + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheButtonLink(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheButtonWithPic(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheBigButton(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheRadioButton(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент RadioButton с текстом '" + radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickThePaymentRadioButton(String payment_radio_button_text)
    {
        String locator = replacePaymentRadioButtonTextAndGetLocator(payment_radio_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент PaymentRadioButton с текстом '" + payment_radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheSBPPaymentRadioButton(String sbp_payment_radio_button_text)
    {
        String locator = replaceSBPPaymentRadioButtonTextAndGetLocator(sbp_payment_radio_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент SBPPaymentRadioButton с текстом '" + sbp_payment_radio_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheSwitch(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch с текстом '" + switch_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheTooltipButton(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент TooltipButton с текстом '" + tooltip_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
    }

    public void clickTheElement(String any_element_text)
    {
        String locator = replaceAnyElementTextAndGetLocator(any_element_text);
        this.waitForElementAndClick(
                locator,
                "Ошибка! Не удалось обнаружить элемент с текстом '" + any_element_text + "'.",
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
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindRadioButtonByText(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент RadioButton с текстом '" + radio_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindSwitchByText(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch с текстом '" + switch_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpToFindTooltipButtonByText(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент TooltipButton с текстом '" + tooltip_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }
    /* Свайпы вверх для поиска элементов */


    /* Свайпы вниз для поиска элементов */
    public void swipeDownToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindRadioButtonByText(String radio_button_text)
    {
        String locator = replaceRadioButtonTextAndGetLocator(radio_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент RadioButton с текстом '" + radio_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindSwitchByText(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch с текстом '" + switch_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeDownToFindTooltipButtonByText(String tooltip_button_text)
    {
        String locator = replaceTooltipButtonTextAndGetLocator(tooltip_button_text);
        this.swipeDownToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент TooltipButton с текстом '" + tooltip_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }
    /* Свайпы вниз для поиска элементов */


    /* Свайпы влево для поиска элементов */
    public void swipeUpperLeftToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeUpperLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerLeftToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeLowerLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperLeftToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeUpperLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerLeftToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeLowerLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperLeftToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeUpperLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerLeftToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeLowerLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperLeftToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeUpperLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerLeftToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeLowerLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperLeftToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeUpperLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerLeftToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeLowerLeftToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }
    /* Свайпы влево для поиска элементов */


    /* Свайпы вправо для поиска элементов */
    public void swipeUpperRightToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeUpperRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerRightToFindText(String text)
    {
        String locator = replaceTextAndGetLocator(text);
        this.swipeLowerRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить текст '" + text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperRightToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeUpperRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerRightToFindButtonByText(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        this.swipeLowerRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperRightToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeUpperRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerRightToFindButtonLinkByText(String button_link_text)
    {
        String locator = replaceButtonLinkTextAndGetLocator(button_link_text);
        this.swipeLowerRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonLink с текстом '" + button_link_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperRightToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeUpperRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerRightToFindButtonWithPicByText(String button_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_text);
        this.swipeLowerRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeUpperRightToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeUpperRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
    }

    public void swipeLowerRightToFindBigButtonByText(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        this.swipeLowerRightToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
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
    /* Нажатия на экран */


    /* Нажатия на экран по координатам, вычисляемым относительно расположения различных элементов */
    public void clickToTheLeftOfTheButton(String button_name)
    {
        String locator = replaceButtonTextAndGetLocator(button_name);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент Button с текстом '" + button_name + "'.",
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

    public void clickTheUpperRightCornerOfTheCardEmailButton(String card_email_button_text)
    {
        String locator = replaceCardEmailButtonTextAndGetLocator(card_email_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardEmailButton с текстом '" + card_email_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        int
                width = element.getSize().getWidth(),
                height = element.getSize().getHeight(),
                left_x = element.getLocation().getX(),
                upper_y = element.getLocation().getY(),
                right_x = left_x + width,
                lower_y = upper_y + height,

                point_to_click_x = right_x - (width / 10),
                point_to_click_y = upper_y + (height / 6);

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }

    public void clickTheLeftQuarterOfTheBigButton(String big_button_text)
    {
        String locator = replaceBigButtonTextAndGetLocator(big_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + big_button_text + "'.",
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

    public void clickTheUpperRightCornerOfTheCardNameButton(String card_name_button_text)
    {
        String locator = replaceCardNameButtonTextAndGetLocator(card_name_button_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент CardNameButton с текстом '" + card_name_button_text + "'.",
                TIMEOUT_IN_SECONDS
        );
        int
                width = element.getSize().getWidth(),
                height = element.getSize().getHeight(),
                left_x = element.getLocation().getX(),
                upper_y = element.getLocation().getY(),
                right_x = left_x + width,
                lower_y = upper_y + height,

                point_to_click_x = right_x - (width / 10),
                point_to_click_y = upper_y + (height / 4);

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }

    public void clickTheLowerLeftCornerOfTheButtonWithPic(String button_with_pic_text)
    {
        String locator = replaceButtonWithPicTextAndGetLocator(button_with_pic_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент ButtonWithPic с текстом '" + button_with_pic_text + "'.",
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
                point_to_click_y = middle_y + (middle_y / 2);

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }

    public void clickTheLeftEighthPartOfTheSwitch(String switch_text)
    {
        String locator = replaceSwitchTextAndGetLocator(switch_text);
        WebElement element = this.waitForElementPresent(
                locator,
                "Ошибка! Не удалось обнаружить элемент Switch с текстом '" + switch_text + "'.",
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

                point_to_click_x = middle_x / 4,
                point_to_click_y = middle_y;

        this.tapByCoordinates(point_to_click_x, point_to_click_y);
    }
    /* Нажатия на экран по координатам, вычисляемым относительно расположения различных элементов */


    /* Ввод e-mail и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */
    public void enterEmailAndCheckText(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                EMAIL_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода e-mail.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }

    public void enterEmailAndCheckTextAgain(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                EMAIL_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода e-mail.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }
    /* Ввод e-mail и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */


    /* Ввод пароля и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */
    public void enterPasswordAndCheckText(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                PASSWORD_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода пароля.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }

    public void enterPasswordAndCheckTextAgain(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                PASSWORD_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода пароля.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }
    /* Ввод пароля и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */


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
        String text;
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
        String text;
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


    /* Ввод номера карты и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */
    public void enterCardNumberAndCheckText(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NUMBER_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }

    public void enterCardNumberAndCheckTextAgain(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CARD_NUMBER_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }
    /* Ввод номера карты и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */


    /* Ввод номера банковской карты и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */
    public void enterCreditCardNumberAndCheckText(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CREDIT_CARD_NUMBER_FIELD,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера банковской карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }

    public void enterCreditCardNumberAndCheckTextAgain(String input, boolean tap)
    {
        WebElement element = this.waitForElementClickClearAndSendKeys(
                CREDIT_CARD_NUMBER_FIELD_WITH_WARNING,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода номера банковской карты.",
                TIMEOUT_IN_SECONDS
        );
        this.sleepFor(TIMEOUT_IN_MILLISECONDS);
        String text;
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

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }
    /* Ввод номера банковской карты и проверка отображения предупреждений (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */


    /* Поиск поля для ввода данных и ввод текста (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */
    public void enterText(String field_name, String input, boolean tap)
    {
        String locator = replaceInputFieldTextAndGetLocator(field_name);
        this.swipeUpToFindElement(
                locator,
                "Ошибка! Не удалось обнаружить поле для ввода с текстом '" + field_name + "'.",
                MAX_SWIPES,
                TIME_OF_SWIPE,
                TIMEOUT_IN_SECONDS
        );
        this.waitForElementClickClearAndSendKeys(
                locator,
                input,
                "Ошибка! Не удалось обнаружить поле для ввода с текстом '" + field_name + "'.",
                TIMEOUT_IN_SECONDS
        );

        if (tap) {
            tapTheUpperEdgeOfTheScreen();
        }
    }
    /* Поиск поля для ввода данных и ввод текста (+ нажатие в верхнюю часть экрана для закрытия клавиатуры) */


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
                    "Ошибка! Не удалось обнаружить элемент BigButton с текстом '" + code_buttons[i] + "'.",
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


    /* Получение первого слова из строки (добыча почтового адреса из текста) */
    public String getFirstWordFromTheString(String text)
    {
        String[] text_parts = text.split("\n", 2);
        return text_parts[0];
    }
    /* Получение первого слова из строки (добыча почтового адреса из текста) */


    /* Определение ограничений при пополнении Транспортной карты и их проверка */
    public void testReplenishmentLimits(String replenishment_text)
    {
        String[] limits = findReplenishmentLimits(replenishment_text);
        String[] button_clickability = {"false", "true", "true", "true", "true", "false"};
        String[] warning = {"Минимальная сумма", "", "", "", "", "Максимальная сумма"};

        for (int i = 0; i < limits.length; i++) {
            enterText("Сумма пополнения", limits[i], true);
            swipeUpToFindBigButtonByText("ОПЛАТИТЬ");
            if (warning[i].equals("Минимальная сумма")) {
                waitForWarningTextToAppear("Минимальная сумма");
                waitForWarningTextToDisappear("Максимальная сумма");
            } else if (warning[i].equals("Максимальная сумма")) {
                waitForWarningTextToDisappear("Минимальная сумма");
                waitForWarningTextToAppear("Максимальная сумма");
            } else {
                waitForWarningTextToDisappear("Минимальная сумма");
                waitForWarningTextToDisappear("Максимальная сумма");
            }
            if (!TrCardPlatform.getInstance().isIOS()) {
                Assert.assertEquals(
                        "Ошибка! Некорректный статус кнопки 'Оплатить'.",
                        button_clickability[i],
                        waitForBigButtonToAppearAndGetAttribute("clickable", "ОПЛАТИТЬ")
                );
            }
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


    /* Проверка появления сообщения об отсутствии SIM-карты (iOS) и нажатие на кнопку "ОК" */
    public void checkForSIMNotificationAndPressOK()
    {
        String text_locator = replaceTextAndGetLocator("Installed') or contains(@name, 'не вставлена");
        String skip_button_locator = replaceButtonTextAndGetLocator("OK') or contains(@name, 'ОК");
        int notifications = this.getAmountOfElements(text_locator);
        if (notifications > 0) {
            this.waitForElementAndClick(
                    skip_button_locator,
                    "Ошибка! Не удалось обнаружить кнопку для закрытия уведомления.",
                    TIMEOUT_IN_SECONDS
            );
        }
    }
    /* Проверка появления сообщения об отсутствии SIM-карты (iOS) и нажатие на кнопку "ОК" */


    /* Перезапуск приложения */
    public void restartApp()
    {
        String app_id = "ru.ftc.tc";
        if (TrCardPlatform.getInstance().isIOS()) {
            app_id = "ru.ftc.zk.tc.TCard-inhouse";
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
        String auth_text;
        int notifications = this.getAmountOfElements(text_locator);
        if (notifications > 0) {
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
        clickTheSwitch("Airplane') or contains(@name, 'Авиарежим");
        checkForSIMNotificationAndPressOK();
        this.sleepFor(3000);
        driver.activateApp("ru.ftc.zk.tc.TCard-inhouse");
    }
    /* Включение и выключение режима полёта */


    /* Ожидание */
    public void waitForMilliseconds(long time_in_milliseconds)
    {
        this.sleepFor(time_in_milliseconds);
    }
    /* Ожидание */

}
