package lib.ios;

import io.appium.java_client.AppiumDriver;
import lib.TrCardActions;

public class iOSActions extends TrCardActions
{

    // Присвоение значений константам,
    static {
        // связанным с вводом логина
        EMAIL_FIELD ="xpath://XCUIElementTypeTextField[contains(@name, 'Введите e-mail')]";
        EMAIL_FIELD_WITH_WARNING ="xpath://XCUIElementTypeTextField[contains(@name, 'Некорректный e-mail')]";
        INCORRECT_EMAIL_TEXT ="Некорректный e-mail";
        EMAIL_PATTERN ="\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,10}";
        // связанным с вводом пароля
        PASSWORD_FIELD ="xpath://XCUIElementTypeTextField[contains(@name, 'Введите пароль')]";
        PASSWORD_FIELD_WITH_WARNING ="xpath://XCUIElementTypeTextField[contains(@name, 'Длина пароля минимум') or contains(@name, 'Пароль должен содержать')]";
        INCORRECT_PASSWORD_TEXT_v1 ="Длина пароля минимум 8 символов";
        INCORRECT_PASSWORD_TEXT_v2 ="Пароль должен содержать не менее 8 символов";
        // связанным с вводом номера карты
        CARD_NUMBER_FIELD ="xpath://XCUIElementTypeTextField[@name='Номер карты']";
        CARD_NUMBER_FIELD_WITH_WARNING ="xpath://XCUIElementTypeTextField[contains(@name, 'Укажите 19-значный номер карты')]";
        INCORRECT_CARD_NUMBER_TEXT ="Укажите 19-значный номер карты";
        // связанным с вводом имени карты
        CARD_NAME_FIELD ="xpath://XCUIElementTypeTextField[contains(@name, 'Имя карты')]";
        CARD_NAME_FIELD_WITH_WARNING ="xpath://XCUIElementTypeTextField[contains(@name, 'Укажите имя карты')]";
        INCORRECT_CARD_NAME_TEXT ="Укажите имя карты длиной";
        // связанным с вводом номера банковской карты
        CREDIT_CARD_NUMBER_FIELD ="xpath://XCUIElementTypeTextField[@name='Номер карты']";
        CREDIT_CARD_NUMBER_FIELD_WITH_WARNING ="xpath://XCUIElementTypeTextField[contains(@name, 'Укажите 16-значный номер карты')]";
        INCORRECT_CREDIT_CARD_NUMBER_TEXT ="Укажите 16-значный номер карты";
        // связанным с предоставлением приложению разрешений
        PERMISSION_ALLOW_BUTTON ="xpath://XCUIElementTypeButton[contains(@name, 'Allow') or contains(@name, 'OK') or contains(@name, 'Разрешить')]";
        // связанным с шаблонами для поиска элементов
        TEXT_TEMPLATE ="xpath://XCUIElementTypeStaticText[contains(@name, '{TEXT}')]";
        SCREEN_TITLE_TEMPLATE = "xpath://XCUIElementTypeOther[contains(@name, '{SCREEN_TITLE}')]";
        INPUT_FIELD_TEMPLATE ="xpath://XCUIElementTypeTextField[contains(@name, '{INPUT_FIELD_TEXT}')]";
        BUTTON_TEMPLATE ="xpath://XCUIElementTypeButton[contains(@name, '{BUTTON_NAME}')]";
        BUTTON_WITH_PIC_TEMPLATE ="xpath://XCUIElementTypeImage[contains(@name, '{BUTTON_TEXT}')]";
        BIG_BUTTON_TEMPLATE ="xpath://XCUIElementTypeStaticText[contains(@name, '{BIG_BUTTON_TEXT}')]";
        RADIO_BUTTON_TEMPLATE ="xpath://XCUIElementTypeSwitch[contains(@name, '{RADIO_BUTTON_TEXT}')]";
        SWITCH_TEMPLATE ="xpath://XCUIElementTypeSwitch[contains(@name, '{SWITCH_TEXT}')]";
        TOOLTIP_BUTTON_TEMPLATE ="xpath://XCUIElementTypeOther[contains(@name, '{TOOLTIP_BUTTON_TEXT}')]/../XCUIElementTypeImage[2]";
        ANY_ELEMENT_TEMPLATE ="xpath://*[{ANY_ELEMENT_TEXT}]";
    }

    public iOSActions(AppiumDriver driver)
    {
        super(driver);
    }

}
