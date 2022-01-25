package lib.android;

import io.appium.java_client.AppiumDriver;
import lib.TrCardActions;

public class AndroidActions extends TrCardActions
{

    // Присвоение значений константам,
    static {
        // связанным с вводом логина
        EMAIL_FIELD = "xpath://android.widget.EditText[contains(@text, 'Введите e-mail')]";
        EMAIL_FIELD_WITH_WARNING = "xpath://android.widget.EditText[contains(@text, 'Некорректный e-mail')]";
        INCORRECT_EMAIL_TEXT = "Некорректный e-mail";
        EMAIL_PATTERN = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,10}";
        // связанным с вводом пароля
        PASSWORD_FIELD = "xpath://android.widget.EditText[contains(@text, 'Введите пароль')]";
        PASSWORD_FIELD_WITH_WARNING = "xpath://android.widget.EditText[contains(@text, 'Длина пароля минимум') or contains(@text, 'Пароль должен содержать')]";
        INCORRECT_PASSWORD_TEXT_v1 = "Длина пароля минимум 8 символов";
        INCORRECT_PASSWORD_TEXT_v2 = "Пароль должен содержать не менее 8 символов";
        // связанным с вводом номера карты
        CARD_NUMBER_FIELD = "xpath://android.widget.EditText[@text='Номер карты']";
        CARD_NUMBER_FIELD_WITH_WARNING = "xpath://android.widget.EditText[contains(@text, 'Укажите 19-значный номер карты')]";
        INCORRECT_CARD_NUMBER_TEXT = "Укажите 19-значный номер карты";
        // связанным с вводом имени карты
        CARD_NAME_FIELD = "xpath://android.widget.EditText[contains(@text, 'Имя карты')]";
        CARD_NAME_FIELD_WITH_WARNING = "xpath://android.widget.EditText[contains(@text, 'Укажите имя карты')]";
        INCORRECT_CARD_NAME_TEXT = "Укажите имя карты длиной";
        // связанным с вводом номера банковской карты
        CREDIT_CARD_NUMBER_FIELD = "xpath://android.widget.EditText[@text='Номер карты']";
        CREDIT_CARD_NUMBER_FIELD_WITH_WARNING = "xpath://android.widget.EditText[contains(@text, 'Укажите 16-значный номер карты')]";
        INCORRECT_CREDIT_CARD_NUMBER_TEXT = "Укажите 16-значный номер карты";
        // связанным с предоставлением приложению разрешений
        PERMISSION_ALLOW_BUTTON = "xpath://android.widget.Button[contains(@resource-id, 'permission_allow')]";
        // связанным с шаблонами для поиска элементов
        TEXT_TEMPLATE = "xpath://android.view.View[contains(@content-desc, '{TEXT}')]";
        SCREEN_TITLE_TEMPLATE = "xpath://android.view.View[contains(@content-desc, '{SCREEN_TITLE}')]";
        BOTTOM_BANNER_TEMPLATE = "xpath://android.view.View[contains(@content-desc, '{BOTTOM_BANNER_TEXT}')]";
        INPUT_FIELD_TEMPLATE = "xpath://android.widget.EditText[contains(@text, '{INPUT_FIELD_TEXT}')]";
        BUTTON_TEMPLATE = "xpath://android.widget.Button[contains(@content-desc, '{BUTTON_NAME}')]";
        BUTTON_WITH_PIC_TEMPLATE = "xpath://android.widget.ImageView[contains(@content-desc, '{BUTTON_TEXT}')]";
        BIG_BUTTON_TEMPLATE = "xpath://android.view.View[contains(@content-desc, '{BIG_BUTTON_TEXT}')]";
        RADIO_BUTTON_TEMPLATE = "xpath://android.widget.RadioButton[contains(@content-desc, '{RADIO_BUTTON_TEXT}')]";
        SWITCH_TEMPLATE = "xpath://android.widget.Switch[contains(@content-desc, '{SWITCH_TEXT}')]";
        TOOLTIP_BUTTON_TEMPLATE = "xpath://android.view.View[contains(@content-desc, '{TOOLTIP_BUTTON_TEXT}')]/android.widget.ImageView[2]";
        ANY_ELEMENT_TEMPLATE = "xpath://*[{ANY_ELEMENT_TEXT}]";
    }

    public AndroidActions(AppiumDriver driver)
    {
        super(driver);
    }

}
