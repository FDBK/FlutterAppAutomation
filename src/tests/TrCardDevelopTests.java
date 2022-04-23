package tests;

import lib.*;
import org.junit.Test;

public class TrCardDevelopTests extends TrCardTestCase {

    // Регистрация нового пользователя (проверка полей ввода данных и т.п.)
    @Test
    public void testRegisterNewUser()
    {
        // Инициализация библиотеки методов TrCardActions
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);

        // Переход на экран регистрации нового пользователя
        TrCardAct.clickTheButton("РЕГИСТРАЦИЯ");
        TrCardAct.waitForTextToAppear("для регистрации");

        // Ввод логина (недостаточная длина > некорректный формат > корректный формат)
        TrCardAct.enterEmailAndCheckText("tk", true);
        TrCardAct.enterEmailAndCheckTextAgain("tk.punk", true);
        TrCardAct.enterEmailAndCheckTextAgain("tk.punk@test.", true);
        TrCardAct.enterEmailAndCheckTextAgain("tk.punk@test.test", true);

        // Переход на экран ввода пароля для регистрируемой учётной записи
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.waitForTextToAppear("для приложения");

        // Ввод пароля (недостаточная длина > достаточная длина)
        TrCardAct.enterPasswordAndCheckText("1111111", true);
        TrCardAct.enterPasswordAndCheckTextAgain("11111111", true);

        // Попытка зарегистрировать уже существующего пользователя, возврат на экран ввода логина для регистрации
        TrCardAct.clickTheBigButton("ЗАРЕГИСТРИРОВАТЬСЯ");
        TrCardAct.waitForTextToAppear("уже существует");
        TrCardAct.clickTheButton("ОК");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForTextToAppear("для регистрации");

        // Ввод "свободного" логина
        String random_email = "test-" + TrCardAct.makeRandomString() + "@test.test";
        TrCardAct.enterEmailAndCheckText(random_email, true);

        // Переход на экран ввода пароля для регистрируемой учётной записи с последующей регистрацией
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.waitForTextToAppear("для приложения");
        TrCardAct.enterPasswordAndCheckText("11111111", true);
        TrCardAct.clickTheBigButton("ЗАРЕГИСТРИРОВАТЬСЯ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка отображения экрана без подключенных карт
        TrCardAct.waitForTextToAppear("У вас пока нет карт");
        TrCardAct.waitForTextToAppear("Для получения информации по карте добавьте");

        System.out.println("Тест пройден без ошибок!");
        System.out.println("Зарегистрирован новый пользователь: " + random_email + " (пароль - 11111111).");
    }


    // Проверка отображения чеков по поездкам (печатные билеты всех видов)
    @Test
    public void testCheckTripReceipt()
    {
        // Инициализация библиотеки методов TrCardActions
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);
        TrCardDataMethods TrCardData = new TrCardDataMethods(driver);

        // Инициализация массива с параметрами печатного билета
        String[][] tkpunk_tickets = TrCardData.getTKPunkTickets();

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("tk.punk@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("tk.punk@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Получение чека" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Получение чека");
        TrCardAct.checkForPermissionNotification();
        TrCardAct.clickTheBigButton("ВВЕСТИ ПАРАМЕТРЫ ВРУЧНУЮ");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // Обработка значений массива: выбор города, способа оплаты поездки, заполнение полей данными
        for (int i = 0; i < tkpunk_tickets.length; i++) {
            String
                    trip_date = tkpunk_tickets[i][0],
                    trip_time = tkpunk_tickets[i][1],
                    trip_terminal = tkpunk_tickets[i][2],
                    trip_transaction = tkpunk_tickets[i][3],
                    trip_type = tkpunk_tickets[i][4],
                    trip_city = tkpunk_tickets[i][5],
                    trip_receipt_status = tkpunk_tickets[i][6];

            // Выбор города
            TrCardAct.clickTheBigButton("Город");
            TrCardAct.chooseTheCity(trip_city);
            TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

            // Определение и выбор способа оплаты поездки
            String trip_payment_method = "Транспортная карта";
            if (trip_type.equals("CredCard")) {
                trip_payment_method = "Банковская карта";
            } else if (trip_type.equals("Cash")) {
                trip_payment_method = "Наличные";
            }
            TrCardAct.waitForRadioButtonToAppear(trip_payment_method);
            TrCardAct.clickTheRadioButton(trip_payment_method);

            // Поиск полей и ввод данных о поездке
            TrCardAct.enterText("Дата поездки", trip_date, true);
            TrCardAct.enterText("Время поездки", trip_time, true);
            TrCardAct.enterText("Идентификатор терминала", trip_terminal, true);
            TrCardAct.enterText("Номер транзакции", trip_transaction, true);

            // Получение чека
            TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");

            // Проверка корректности отображения информации о билете
            TrCardAct.waitForButtonWithPicToAppear("Проездной билет");
            TrCardAct.waitForButtonWithPicToAppear("Время поездки");
            TrCardAct.waitForButtonWithPicToAppear(trip_time.substring(0, 5));
            TrCardAct.waitForButtonWithPicToAppear(trip_date);
            TrCardAct.waitForButtonWithPicToAppear("Маршрут");
            TrCardAct.waitForButtonWithPicToAppear("Терминал");
            TrCardAct.waitForButtonWithPicToAppear(trip_terminal);
            TrCardAct.waitForButtonWithPicToAppear(trip_payment_method);

            // Проверка корректности отображения статуса кассового чека и информации о чеке (при его наличии)
            if (trip_receipt_status.equals("NR")) {
                TrCardAct.swipeUpToFindBigButtonByText("Кассовый чек не требуется!");
            } else if (trip_receipt_status.equals("CC")) {
                TrCardAct.swipeUpToFindBigButtonByText("За кассовым чеком обратитесь к перевозчику!");
            } else if (trip_receipt_status.equals("FP")) {
                TrCardAct.swipeUpToFindBigButtonByText("Проездной билет в процессе фискализации!");
                TrCardAct.swipeUpToFindBigButtonByText("Попробуйте получить кассовый чек позже!");
            } else {
                TrCardAct.swipeUpToFindButtonWithPicByText("Приход");
                TrCardAct.swipeUpToFindButtonWithPicByText("Номер чека за смену");
                TrCardAct.swipeUpToFindButtonWithPicByText("Цена");
                TrCardAct.swipeUpToFindButtonWithPicByText("РН ККТ");
                TrCardAct.swipeUpToFindButtonWithPicByText("Автомат");
                TrCardAct.swipeUpToFindButtonWithPicByText("ФН");
                TrCardAct.swipeUpToFindButtonWithPicByText("qr code");
            }

            // Проверка работоспособности кнопки "Поделиться чеком"
            if (TrCardPlatform.getInstance().isIOS()) {
                TrCardAct.clickTheButton("Поделиться");
            } else {
                TrCardAct.clickTheButtonWithPic("Поделиться");
            }
            TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

            // Возврат на экран ввода данных о поездке, прокрутка экрана (поиск пункта "Выбор города")
            TrCardAct.clickTheButton("Назад");
            TrCardAct.swipeDownToFindBigButtonByText("Город");
        }

        // Проверка поведения приложения в случае ввода данных о несуществующей поездке
        TrCardAct.enterText("Дата поездки", "29.07.2021", true);
        TrCardAct.enterText("Время поездки", "13:31:12", true);
        TrCardAct.enterText("Идентификатор терминала", "T020931", true);
        TrCardAct.enterText("Номер транзакции", "1318624554", true);
        TrCardAct.swipeDownToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForBottomBannerToAppear("Поездка не найдена, проверьте корректность введенных данных");
        TrCardAct.waitForBottomBannerToDisappear("Поездка не найдена, проверьте корректность введенных данных");
        TrCardAct.waitForBigButtonToAppear("ПОЛУЧИТЬ ЧЕК");

        // Проверка поведения приложения в случае нажатия кнопки "Получить чек" без ввода данных
        TrCardAct.enterText("Дата поездки", "", true);
        TrCardAct.enterText("Время поездки", "", true);
        TrCardAct.enterText("Идентификатор терминала", "", true);
        TrCardAct.enterText("Номер транзакции", "", true);
        TrCardAct.swipeDownToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForBigButtonToAppear("ПОЛУЧИТЬ ЧЕК");

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения чеков по поездкам (рулонные билеты)
    @Test
    public void testCheckTripReceiptRoll()
    {
        // Инициализация библиотеки методов TrCardActions
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);
        TrCardDataMethods TrCardData = new TrCardDataMethods(driver);

        // Инициализация массива с параметрами рулонного билета
        String[][] tkpunk_roll_tickets = TrCardData.getTKPunkRollTickets();

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("tk.punk@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("tk.punk@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Получение чека" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Получение чека");
        TrCardAct.checkForPermissionNotification();
        TrCardAct.clickTheBigButton("ВВЕСТИ ПАРАМЕТРЫ ВРУЧНУЮ");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // Переход на вкладку "Рулонный билет" (и ожидание, чтобы не перепутались поля "Город" на разных вкладках)
        TrCardAct.clickTheBigButton("РУЛОННЫЙ БИЛЕТ");
        TrCardAct.waitForMilliseconds(500);

        // Обработка значений массива: выбор города, способа оплаты поездки, заполнение полей данными
        for (int i = 0; i < tkpunk_roll_tickets.length; i++) {
            String
                    ticket_number = tkpunk_roll_tickets[i][0],
                    ticket_series = tkpunk_roll_tickets[i][1],
                    transport_co_inn = tkpunk_roll_tickets[i][2],
                    trip_city = tkpunk_roll_tickets[i][3],
                    trip_receipt_status = tkpunk_roll_tickets[i][4];

            // Выбор города
            TrCardAct.clickTheBigButton("Город");
            TrCardAct.chooseTheCity(trip_city);
            TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

            // Поиск полей и ввод данных о поездке
            TrCardAct.enterText("Номер билета", ticket_number, true);
            TrCardAct.enterText("Серия билета", ticket_series, true);
            TrCardAct.enterText("ИНН перевозчика", transport_co_inn, true);

            // Получение чека
            TrCardAct.swipeUpToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
            TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");

            // Проверка корректности отображения информации о билете
            TrCardAct.waitForButtonWithPicToAppear("Проездной билет");
            TrCardAct.waitForButtonWithPicToAppear("Время поездки");
            TrCardAct.waitForButtonWithPicToAppear("Маршрут");
            TrCardAct.waitForButtonWithPicToAppear("Средство оплаты");

            // Проверка корректности отображения статуса кассового чека и информации о чеке (при его наличии)
            if (trip_receipt_status.equals("NR")) {
                TrCardAct.swipeUpToFindBigButtonByText("Кассовый чек не требуется!");
            } else if (trip_receipt_status.equals("CC")) {
                TrCardAct.swipeUpToFindBigButtonByText("За кассовым чеком обратитесь к перевозчику!");
            } else if (trip_receipt_status.equals("FP")) {
                TrCardAct.swipeUpToFindBigButtonByText("Проездной билет в процессе фискализации!");
                TrCardAct.swipeUpToFindBigButtonByText("Попробуйте получить кассовый чек позже!");
            } else {
                TrCardAct.swipeUpToFindButtonWithPicByText("Приход");
                TrCardAct.swipeUpToFindButtonWithPicByText("Номер чека за смену");
                TrCardAct.swipeUpToFindButtonWithPicByText("Цена");
                TrCardAct.swipeUpToFindButtonWithPicByText("РН ККТ");
                TrCardAct.swipeUpToFindButtonWithPicByText("Автомат");
                TrCardAct.swipeUpToFindButtonWithPicByText("ФН");
                TrCardAct.swipeUpToFindButtonWithPicByText("qr code");
            }

            // Проверка работоспособности кнопки "Поделиться чеком"
            if (TrCardPlatform.getInstance().isIOS()) {
                TrCardAct.clickTheButton("Поделиться");
            } else {
                TrCardAct.clickTheButtonWithPic("Поделиться");
            }
            TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

            // Возврат на экран ввода данных о поездке, прокрутка экрана (поиск пункта "Выбор города")
            TrCardAct.clickTheButton("Назад");
            TrCardAct.swipeDownToFindBigButtonByText("Город");
        }

        // Проверка поведения приложения в случае ввода данных о несуществующей поездке
        TrCardAct.enterText("Номер билета", "000666", true);
        TrCardAct.enterText("Серия билета", "0110", true);
        TrCardAct.enterText("ИНН перевозчика", "6300551221", true);
        TrCardAct.swipeDownToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForBottomBannerToAppear("Поездка не найдена, проверьте корректность введенных данных");
        TrCardAct.waitForBottomBannerToDisappear("Поездка не найдена, проверьте корректность введенных данных");
        TrCardAct.waitForBigButtonToAppear("ПОЛУЧИТЬ ЧЕК");

        // Проверка поведения приложения в случае нажатия кнопки "Получить чек" без ввода данных
        TrCardAct.enterText("Номер билета", "", true);
        TrCardAct.enterText("Серия билета", "", true);
        TrCardAct.enterText("ИНН перевозчика", "", true);
        TrCardAct.swipeDownToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForBigButtonToAppear("ПОЛУЧИТЬ ЧЕК");

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");

    }


    // Проверка отображения чеков по поездкам (СБП)
    @Test
    public void testCheckTripReceiptSBP()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("tk.punk@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("tk.punk@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Получение чека" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Получение чека");
        TrCardAct.checkForPermissionNotification();
        TrCardAct.clickTheBigButton("ВВЕСТИ ПАРАМЕТРЫ ВРУЧНУЮ");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // ПРОВЕРКА ПОВЕДЕНИЯ ПРИЛОЖЕНИЯ ПРИ НАЛИЧИИ НЕСКОЛЬКИХ ПОЕЗДОК
        // Выбор города
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Выбор способа оплаты поездки
        TrCardAct.waitForRadioButtonToAppear("sbp label");
        TrCardAct.clickTheRadioButton("sbp label");

        // Поиск полей и ввод данных о поездке
        TrCardAct.enterText("Дата поездки", "22.04.2022", true);
        TrCardAct.enterText("Последние 4 цифры", "2222", true);
        TrCardAct.enterText("Номер маршрута", "1", true);

        // Получение чека
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");

        // Проверка корректности отображения списка поездок
        TrCardAct.waitForScreenTitleToAppear("Поиск чека");
        TrCardAct.waitForTextToAppear("Список найденных чеков");
        TrCardAct.waitForTextToAppear("Номер маршрута: 1");
        TrCardAct.waitForTextToAppear("Номер телефона: *******2222");
        TrCardAct.waitForTextToAppear("Номер транзакции: не указано");
        TrCardAct.waitForButtonWithPicToAppear("Автобус 1");
        TrCardAct.waitForButtonWithPicToAppear("31");
        TrCardAct.waitForButtonWithPicToAppear("11:32:00");
        TrCardAct.waitForButtonWithPicToAppear("11:32:10");
        TrCardAct.waitForButtonWithPicToAppear("11:32:15");
        TrCardAct.waitForButtonWithPicToAppear("11:32:17");

        // Переход к конкретной поездке
        TrCardAct.clickTheButtonWithPic("11:32:17");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // Проверка корректности отображения чека
        TrCardAct.waitForButtonWithPicToAppear("Проездной билет");
        TrCardAct.waitForButtonWithPicToAppear("Время поездки");
        TrCardAct.waitForButtonWithPicToAppear("22.04.2022");
        TrCardAct.waitForButtonWithPicToAppear("Маршрут");
        TrCardAct.waitForButtonWithPicToAppear("J999999");
        TrCardAct.waitForButtonWithPicToAppear("X1A2S3D5F6G7H8J9K0C4S5C6D7V5121");
        TrCardAct.waitForButtonWithPicToAppear("913***2222");
        TrCardAct.waitForButtonWithPicToAppear("СБП");
        TrCardAct.swipeUpToFindButtonWithPicByText("Приход");
        TrCardAct.swipeUpToFindButtonWithPicByText("Номер чека за смену");
        TrCardAct.swipeUpToFindButtonWithPicByText("Поездка по СБП");
        TrCardAct.swipeUpToFindButtonWithPicByText("РН ККТ");
        TrCardAct.swipeUpToFindButtonWithPicByText("Автомат");
        TrCardAct.swipeUpToFindButtonWithPicByText("ФН");
        TrCardAct.swipeUpToFindButtonWithPicByText("qr code");

        // Проверка работоспособности кнопки "Поделиться чеком"
        if (TrCardPlatform.getInstance().isIOS()) {
            TrCardAct.clickTheButton("Поделиться");
        } else {
            TrCardAct.clickTheButtonWithPic("Поделиться");
        }
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Возврат на экран ввода данных о поездке, прокрутка экрана (поиск пункта "Выбор города")
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.swipeDownToFindBigButtonByText("Город");

        // ПРОВЕРКА ПОВЕДЕНИЯ ПРИЛОЖЕНИЯ ПРИ НАЛИЧИИ ОДНОЙ ПОЕЗДКИ
        // Выбор города
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Выбор способа оплаты поездки
        TrCardAct.waitForRadioButtonToAppear("sbp label");
        TrCardAct.clickTheRadioButton("sbp label");

        // Поиск полей и ввод данных о поездке
        TrCardAct.enterText("Дата поездки", "22.04.2022", true);
        TrCardAct.enterText("Последние 4 цифры", "2222", true);
        TrCardAct.enterText("Номер маршрута", "1", true);
        TrCardAct.enterText("Номер транзакции", "X1A2S3D5F6G7H8J9K0C4S5C6D7V5121", true);

        // Получение чека
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");

        // Проверка корректности перехода к конкретной поездке (без отображения списка, состоящего из одной поездки)
        TrCardAct.waitForScreenTitleToDisappear("Поиск чека");
        TrCardAct.waitForTextToDisappear("Список найденных чеков");
        TrCardAct.waitForTextToDisappear("Номер маршрута: 1");
        TrCardAct.waitForTextToDisappear("Номер телефона: *******2222");
        TrCardAct.waitForTextToDisappear("Номер транзакции: не указано");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // Проверка корректности отображения чека по поездке
        TrCardAct.waitForButtonWithPicToAppear("Проездной билет");
        TrCardAct.waitForButtonWithPicToAppear("Время поездки");
        TrCardAct.waitForButtonWithPicToAppear("22.04.2022");
        TrCardAct.waitForButtonWithPicToAppear("Маршрут");
        TrCardAct.waitForButtonWithPicToAppear("J999999");
        TrCardAct.waitForButtonWithPicToAppear("X1A2S3D5F6G7H8J9K0C4S5C6D7V5121");
        TrCardAct.waitForButtonWithPicToAppear("913***2222");
        TrCardAct.waitForButtonWithPicToAppear("СБП");
        TrCardAct.swipeUpToFindButtonWithPicByText("Приход");
        TrCardAct.swipeUpToFindButtonWithPicByText("Номер чека за смену");
        TrCardAct.swipeUpToFindButtonWithPicByText("Поездка по СБП");
        TrCardAct.swipeUpToFindButtonWithPicByText("РН ККТ");
        TrCardAct.swipeUpToFindButtonWithPicByText("Автомат");
        TrCardAct.swipeUpToFindButtonWithPicByText("ФН");
        TrCardAct.swipeUpToFindButtonWithPicByText("qr code");

        // Проверка работоспособности кнопки "Поделиться чеком"
        if (TrCardPlatform.getInstance().isIOS()) {
            TrCardAct.clickTheButton("Поделиться");
        } else {
            TrCardAct.clickTheButtonWithPic("Поделиться");
        }
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Возврат на экран ввода данных о поездке, прокрутка экрана (поиск пункта "Выбор города")
        TrCardAct.clickTheButton("Назад");
        TrCardAct.swipeDownToFindBigButtonByText("Город");

        // ПРОВЕРКА ПОВЕДЕНИЯ ПРИЛОЖЕНИЯ ПРИ ОТСУТСТВИИ ПОЕЗДОК
        // Выбор города
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Выбор способа оплаты поездки
        TrCardAct.waitForRadioButtonToAppear("sbp label");
        TrCardAct.clickTheRadioButton("sbp label");

        // Поиск полей, ввод данных, попытка получить чек и проверка отображения сообщения об ошибке
        TrCardAct.enterText("Дата поездки", "29.07.2021", true);
        TrCardAct.enterText("Последние 4 цифры", "6666", true);
        TrCardAct.enterText("Номер маршрута", "72", true);
        TrCardAct.swipeDownToFindBigButtonByText("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForScreenTitleToAppear("Поиск чека");
        TrCardAct.waitForTextToAppear("Поездок не найдено");
        TrCardAct.waitForTextToAppear("Пожалуйста, проверьте корректность");

        // Возврат на экран ввода данных о поездке, прокрутка экрана (поиск пункта "Выбор города")
        TrCardAct.waitForBigButtonToAppear("НАЗАД");
        TrCardAct.clickTheBigButton("НАЗАД");
        TrCardAct.swipeDownToFindBigButtonByText("Город");

        // ПРОВЕРКА ПОВЕДЕНИЯ ПРИЛОЖЕНИЯ ПРИ НАЖАТИИ КНОПКИ "ПОЛУЧИТЬ ЧЕК" БЕЗ ВВОДА ДАННЫХ
        // Выбор города
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");
        TrCardAct.waitAndTapTheUpperEdgeOfTheScreen();

        // Выбор способа оплаты поездки
        TrCardAct.waitForRadioButtonToAppear("sbp label");
        TrCardAct.clickTheRadioButton("sbp label");

        // Поиск полей и их очистка, попытка получить чек
        TrCardAct.enterText("Дата поездки", "", true);
        TrCardAct.enterText("Последние 4 цифры", "", true);
        TrCardAct.enterText("Номер маршрута", "", true);
        TrCardAct.enterText("Номер транзакции", "", true);
        TrCardAct.clickTheBigButton("ПОЛУЧИТЬ ЧЕК");
        TrCardAct.waitForBigButtonToAppear("ПОЛУЧИТЬ ЧЕК");

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }

}
