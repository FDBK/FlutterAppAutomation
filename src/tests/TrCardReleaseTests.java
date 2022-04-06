package tests;

import lib.*;
import org.junit.Test;

public class TrCardReleaseTests extends TrCardTestCase
{

    // Различные сценарии следующих испытаний:
    // - Проверка полей для ввода логина и пароля > авторизация по логину > установка кода доступа;
    // - просмотр информации о карте > выход из учётной записи;
    // - проверка поля для ввода номера карты > вход по номеру карты > переименование карты и её удаление.
    @Test
    public void testLoginLogoutRenameDelete()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина (недостаточная длина > некорректный формат > корректный формат)
        TrCardAct.enterEmailAndCheckText("auto", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation@test.", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Попытка войти в приложение без ввода пароля
        TrCardAct.clickTheBigButton("ВОЙТИ");
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод пароля (недостаточная длина > неверный пароль достаточной длины)
        TrCardAct.enterPasswordAndCheckTextAgain(password.substring(0, 7), true);
        TrCardAct.enterPasswordAndCheckTextAgain(password.replace("1", "0"), true);

        // Попытка войти в приложение с неправильным паролем
        TrCardAct.clickTheBigButton("ВОЙТИ");
        TrCardAct.waitForBottomBannerToAppear("Вы неверно ввели e-mail или пароль");
        TrCardAct.waitForBottomBannerToDisappear("Вы неверно ввели e-mail или пароль");

        // Ввод правильного пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа ("неудачный" код и его изменение > неправильный код > правильный код)
        TrCardAct.enterPassCode("1", "2", "3", "4");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.waitForScreenTitleToAppear("Код доступа");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Проверка отображения параметров карты и возврат на экран "Мои карты"
        TrCardAct.waitForTextToAppear("Статус");
        TrCardAct.waitForTextToAppear("Проездной");
        TrCardAct.waitForTextToAppear("Срок действия карты");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("НЕТ");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Попытка войти с помощью номера недостаточной длины
        TrCardAct.enterCardNumberAndCheckText("9643", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Попытка войти с помощью номера достаточной длины
        TrCardAct.enterCardNumberAndCheckTextAgain(card_number_1, true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Проверка отображения параметров карты
        TrCardAct.waitForTextToAppear("Статус");
        TrCardAct.waitForTextToAppear("Проездной");
        TrCardAct.waitForTextToAppear("Срок действия карты");

        // Проверка соответствия "второго" номера карты "первому" номеру
        assertEquals(
                "Ошибка! Отображаемые номера Транспортных карт отличаются.",
                card_number_1,
                card_number_2
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");

        // Переход на экран "Настройки карты"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");

        // Переименование карты (пустое имя > корректное имя)
        TrCardAct.clickTheUpperRightCornerOfTheCardNameButton("Имя карты, отображаемое в личном кабинете");
        TrCardAct.enterCardNameAndCheckText("");
        TrCardAct.clickTheBigButton("СОХРАНИТЬ");
        TrCardAct.enterCardNameAndCheckTextAgain("Тестовая карта");
        TrCardAct.clickTheBigButton("СОХРАНИТЬ");

        // Проверка отображения нового имени карты на главном экране приложения
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");
        TrCardAct.waitForButtonWithPicToAppear("Тестовая карта");

        // Удаление карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Удалить карту");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите удалить карту Тестовая карта?");
        TrCardAct.clickTheBigButton("НЕТ");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Удалить карту");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите удалить карту Тестовая карта?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка отображения баннера с сообщением об удалении карты
        TrCardAct.waitForBottomBannerToAppear("Карта удалена");
        TrCardAct.waitForBottomBannerToDisappear("Карта удалена");

        // Проверка отображения экрана без подключенных карт
        TrCardAct.waitForTextToAppear("У вас пока нет карт");
        TrCardAct.waitForTextToAppear("Для получения информации по карте добавьте");

        System.out.println("Тест пройден без ошибок!");
    }


    // Регистрация нового пользователя (проверка полей ввода данных и т.п.)
    @Test
    public void testRegisterNewUser()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);

        // Переход на экран регистрации нового пользователя
        TrCardAct.swipeUpToFindButtonByText("РЕГИСТРАЦИЯ");
        TrCardAct.clickTheButton("РЕГИСТРАЦИЯ");
        TrCardAct.waitForTextToAppear("для регистрации");

        // Ввод логина (недостаточная длина > некорректный формат > корректный формат)
        TrCardAct.enterEmailAndCheckText("auto", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation@test.", true);
        TrCardAct.enterEmailAndCheckTextAgain("automation@test.test", true);

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


    // Проверка необходимости авторизации для добавления банковской карты > авторизация > добавление и удаление карты
    @Test
    public void testAddCreditCardThroughLogin()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран добавления новой карты
        TrCardAct.clickTheTopButton("Добавить карту");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");

        // Переход на вкладку "Банковская"
        TrCardAct.clickTheBigButton("БАНКОВСКАЯ");

        // Проверка необходимости авторизации и переход на экран входа по логину
        TrCardAct.waitForTextToAppear("необходимо войти в Личный кабинет");
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Проверка успешности перехода на экран входа по логину
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран добавления новой карты
        TrCardAct.clickTheTopButton("Добавить карту");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");

        // Переход на вкладку "Банковская"
        TrCardAct.clickTheBigButton("БАНКОВСКАЯ");
        TrCardAct.waitForTextToAppear("Введите 16-значный номер");

        // Ввод номера карты, проверка поведения поля при недостаточной длине введённого номера
        TrCardAct.enterCreditCardNumberAndCheckText("5536", true);
        TrCardAct.enterCreditCardNumberAndCheckTextAgain("5536 9140 5130 0915", true);

        // Выбор города
        TrCardAct.swipeUpToFindBigButtonByText("Город");
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");

        // Добавление карты, проверка успешности перехода на добавленную карту (экран "Мои карты")
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForTextToAppear("***0915");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("***0915");

        // Переход в раздел "Поездки" и проверка наличия поездок
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.swipeUpToFindButtonWithPicByText("Автобус 72");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Удаление карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Удалить карту");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите удалить карту ***0915?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка отображения баннера с сообщением об удалении карты
        TrCardAct.waitForBottomBannerToAppear("Карта удалена");
        TrCardAct.waitForBottomBannerToDisappear("Карта удалена");

        // Проверка перехода на предыдущую карту
        TrCardAct.waitForTextToDisappear("***0915");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка необходимости авторизации для пополнения карты > авторизация > проверка доступности пополнения
    @Test
    public void testReplenishmentAfterCardNumberLogin()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 10220 33100 83568", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Пополнение карты"
        TrCardAct.clickTheBigButton("ПОПОЛНИТЬ");

        // Проверка необходимости авторизации и переход на экран входа по логину
        TrCardAct.swipeUpToFindBigButtonByText("ЗАРЕГИСТРИРОВАТЬСЯ");
        TrCardAct.clickTheBigButton("ЗАРЕГИСТРИРОВАТЬСЯ");

        // Проверка успешности перехода на экран входа по логину
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка корректности перехода на нужную карту, проверка доступности пополнения
        TrCardAct.waitForButtonWithPicToAppear("3568");
        TrCardAct.clickTheBigButton("ПОПОЛНИТЬ");
        TrCardAct.enterText("Сумма пополнения", "100", true);
        TrCardAct.swipeUpToFindBigButtonByText("ОПЛАТИТЬ");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка необходимости авторизации для подключения услуги > авторизация > проверка доступности подключения услуги
    @Test
    public void testAddServicesAfterCardNumberLogin()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 10220 33106 18314", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Подключить услугу"
        TrCardAct.clickTheBigButton("КУПИТЬ УСЛУГУ");

        // Проверка необходимости авторизации и переход на экран входа по логину
        TrCardAct.swipeUpToFindBigButtonByText("ЗАРЕГИСТРИРОВАТЬСЯ");
        TrCardAct.clickTheBigButton("ЗАРЕГИСТРИРОВАТЬСЯ");

        // Проверка успешности перехода на экран входа по логину
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка корректности перехода на нужную карту, проверка доступности подключения услуги
        TrCardAct.waitForButtonWithPicToAppear("8314");
        TrCardAct.clickTheBigButton("КУПИТЬ УСЛУГУ");
        TrCardAct.clickTheButtonWithPic("₽");
        TrCardAct.swipeUpToFindBigButtonByText("ОПЛАТИТЬ");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    @Test
    public void testReplenishmentLimits()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("8322");

        // Переход на экран пополнения карты
        TrCardAct.waitForBigButtonToAppear("ПОПОЛНИТЬ");
        TrCardAct.clickTheBigButton("ПОПОЛНИТЬ");
        TrCardAct.waitForScreenTitleToAppear("Пополнение карты");
        TrCardAct.waitForInputFieldToAppear("Сумма пополнения");

        // Поиск строки "На сумму от..."
        TrCardAct.swipeUpToFindText("На сумму от");

        // Получение текста с указанием ограничений при пополнении карты
        String replenishment_text;
        if (TrCardPlatform.getInstance().isIOS()) {
            replenishment_text = TrCardAct.waitForTextToAppearAndGetAttribute("name", "На сумму от");
        } else {
            replenishment_text = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "На сумму от");
        }

        // Определение ограничений, создание списка граничных значений, проверка корректности работы приложения
        TrCardAct.testReplenishmentLimits(replenishment_text);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности отображения учётной записи без добавленных карт
    @Test
    public void testEmptyAccount()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("nocards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("nocards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка отображения экрана без подключенных карт
        TrCardAct.waitForTextToAppear("У вас пока нет карт");
        TrCardAct.waitForTextToAppear("Для получения информации по карте добавьте");
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ КАРТУ");

        // Переход в раздел "Поездки" и проверка корректности отображения экрана в аккаунте без карт
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.waitForTextToAppear("У вас пока нет поездок");
        TrCardAct.waitForTextToAppear("Список карт пуст");

        // Переход в раздел "Операции" и проверка корректности отображения экрана в аккаунте без карт
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");
        TrCardAct.waitForTextToAppear("У вас пока нет операций");
        TrCardAct.waitForTextToAppear("Список карт пуст");

        // Возврат на экран "Мои карты" и переход к добавлению карты
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ КАРТУ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ КАРТУ");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");
        TrCardAct.waitForTextToAppear("Введите номер карты");

        // Добавление карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности добавления карты (отображение информации о карте и т.п.)
        TrCardAct.waitForScreenTitleToAppear("Мои карты");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");
        TrCardAct.waitForTextToAppear("Статус");
        TrCardAct.waitForTextToAppear("Проездной");
        TrCardAct.waitForTextToAppear("Срок действия карты");

        // Копирование номера карты
        String card_number;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Проверка соответствия номера добавленной карты "нужному" номеру
        assertEquals(
                "Ошибка! Отображаемые номера Транспортных карт отличаются.",
                "9643 90540 33168 42210",
                card_number
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Удаление карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Удалить карту");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите удалить карту Транспортная карта?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка отображения баннера с сообщением об удалении карты
        TrCardAct.waitForBottomBannerToAppear("Карта удалена");
        TrCardAct.waitForBottomBannerToDisappear("Карта удалена");

        // Проверка отображения экрана без подключенных карт
        TrCardAct.waitForTextToAppear("У вас пока нет карт");
        TrCardAct.waitForTextToAppear("Для получения информации по карте добавьте");
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ КАРТУ");

        // Переход в раздел "Поездки" и проверка корректности отображения экрана в аккаунте без карт
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.waitForTextToAppear("У вас пока нет поездок");
        TrCardAct.waitForTextToAppear("Список карт пуст");

        // Переход в раздел "Операции" и проверка корректности отображения экрана в аккаунте без карт
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");
        TrCardAct.waitForTextToAppear("У вас пока нет операций");
        TrCardAct.waitForTextToAppear("Список карт пуст");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка механизма запоминания Транспортной карты при перезапуске приложения
    @Test
    public void testRememberTransportCardAfterRestart()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в учётную запись после перезапуска приложения
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню, проверка номера карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");
        TrCardAct.waitForTextToAppear(card_number);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка механизма запоминания банковской карты при перезапуске приложения
    @Test
    public void testRememberCreditCardAfterRestart()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("***4714");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование параметров карты
        String
                card_name,
                card_status,
                card_system,
                card_restrictions;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_name = TrCardAct.waitForTextToAppearAndGetAttribute("name", "***4714");
            card_status = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Статус");
            card_system = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Тип платежной системы");
            card_restrictions = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Ограничения");
        } else {
            card_name = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "***4714");
            card_status = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Статус");
            card_system = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Тип платежной системы");
            card_restrictions = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Ограничения");
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в учётную запись после перезапуска приложения
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню, проверка параметров карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");
        TrCardAct.waitForTextToAppear(card_name);
        TrCardAct.waitForTextToAppear(card_status);
        TrCardAct.waitForTextToAppear(card_system);
        TrCardAct.waitForTextToAppear(card_restrictions);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности поведения приложения при повторном добавлении карты
    @Test
    public void testAddDoubleCard()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход на экран добавления новой карты
        TrCardAct.clickTheTopButton("Добавить карту");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");

        // Добавление карты
        String card_number_1 = "9643 10540 33105 07452";
        TrCardAct.enterCardNumberAndCheckText(card_number_1, true);
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Ожидание всплывающего сообщения "Карта уже добавлена" и взаимодействие с ним
        TrCardAct.waitForTextToAppear("уже добавлена");
        TrCardAct.clickTheButton("Понятно");
        TrCardAct.waitForTextToAppear("Введите номер карты");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForTextToAppear("уже добавлена");
        TrCardAct.clickTheButton("Перейти");

        // Проверка успешности перехода на уже существующую карту при её повторном добавлении
        TrCardAct.waitForScreenTitleToAppear("Мои карты");
        TrCardAct.waitForButtonWithPicToAppear("7452");
        TrCardAct.waitForTextToAppear("Новосибирск");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "7452");
        } else {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "7452");
        }

        // Проверка соответствия "второго" номера карты "первому" номеру
        assertEquals(
                "Ошибка! Отображаемые номера Транспортных карт отличаются.",
                card_number_1,
                card_number_2
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Авторизация по номеру несуществующей карты и добавление несуществующей карты
    @Test
    public void testLoginByUnknownCard()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Попытка войти в приложение по номеру несуществующей карты
        TrCardAct.enterCardNumberAndCheckText("9643 10540 66613 77714", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForBottomBannerToAppear("Карта не найдена");
        TrCardAct.waitForBottomBannerToDisappear("Карта не найдена");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForBottomBannerToAppear("Карта не найдена");
        TrCardAct.waitForBottomBannerToDisappear("Карта не найдена");

        // Переход на экран входа по логину и паролю
        TrCardAct.swipeUpToFindButtonByText("ВОЙТИ ПО ЛОГИНУ");
        TrCardAct.clickTheButton("ВОЙТИ ПО ЛОГИНУ");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран добавления новой карты
        TrCardAct.clickTheTopButton("Добавить карту");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");

        // Попытка добавить в учётную запись несуществующую карту
        TrCardAct.enterCardNumberAndCheckText("9643 10540 66613 77714", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForBottomBannerToAppear("Карта не найдена");
        TrCardAct.waitForBottomBannerToDisappear("Карта не найдена");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForBottomBannerToAppear("Карта не найдена");
        TrCardAct.waitForBottomBannerToDisappear("Карта не найдена");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Изменение региона банковской карты, проверка влияния региона карты на список поездок
    @Test
    public void testChangeCreditCardRegion()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("***9794");

        // Изменение региона карты (выбор из всего списка)
        TrCardAct.clickTheLeftQuarterOfTheBigButton("Новосибирск");
        TrCardAct.chooseTheCity("Челябинская область");

        // Переход в раздел "Поездки" и проверка отсутствия поездок
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForTextToAppear("У вас пока нет поездок");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Изменение региона карты (использование поиска по списку)
        TrCardAct.clickTheLeftQuarterOfTheBigButton("Челябинская область");
        TrCardAct.enterText("Выбор города", "ово", false);
        TrCardAct.waitForTextToAppear("Новосибирск");
        TrCardAct.clickTheBigButton("Новосибирск");

        // Переход в раздел "Поездки" и проверка наличия поездок
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.swipeUpToFindButtonWithPicByText("Автобус 72");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка механизма отображения элементов функции "Транспортный роуминг"
    @Test
    public void testTransportRoaming()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (без функции "Транспортный роуминг")
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1375");

        // Проверка отсутствия баннера "Транспортный роуминг" и соответствующего пункта меню
        TrCardAct.waitForButtonWithPicToDisappear("Доступен проезд в других городах");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.waitForButtonToDisappear("роуминг");
        TrCardAct.tapTheLowerEdgeOfTheScreen();

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (с функцией "Транспортный роуминг")
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("3568");

        // Проверка наличия баннера "Транспортный роуминг"
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");

        // Просмотр информации о функции "Транспортный роуминг" через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("роуминг");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");

        // Копирование текста со списком регионов
        String roaming_text;
        if (TrCardPlatform.getInstance().isIOS()) {
            roaming_text = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Ваша карта подключена");
        } else {
            roaming_text = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Ваша карта подключена");
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о функции "Транспортный роуминг" через кнопку "Подробнее"
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");
        TrCardAct.clickTheButton("Подробнее");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");
        TrCardAct.waitForTextToAppear(roaming_text);

        // Возврат на экран "Мои карты" (с помощью кнопки "Понятно")
        TrCardAct.swipeUpToFindBigButtonByText("ПОНЯТНО");
        TrCardAct.clickTheBigButton("ПОНЯТНО");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Закрытие баннера, оповещающего о подключенной функции "Транспортный роуминг"
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");
        if (TrCardPlatform.getInstance().isIOS()) {
            TrCardAct.clickTheButton("Закрыть");
        } else {
            TrCardAct.clickTheButtonWithPic("Закрыть");
        }
        TrCardAct.waitForButtonWithPicToDisappear("Доступен проезд в других городах");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("allcards@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка отсутствия баннера, оповещающего о подключенной функции "Транспортный роуминг"
        TrCardAct.waitForButtonWithPicToDisappear("Доступен проезд в других городах");

        // Просмотр информации о функции "Транспортный роуминг" через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("роуминг");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");
        TrCardAct.waitForTextToAppear(roaming_text);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта allcards@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина для повторного входа в приложение
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (с функцией "Транспортный роуминг")
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("3568");

        // Проверка наличия баннера и просмотр информации о функции "Транспортный роуминг" через кнопку "Подробнее"
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");
        TrCardAct.clickTheButton("Подробнее");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");
        TrCardAct.waitForTextToAppear(roaming_text);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка механизма отображения информационной карусели
    @Test
    public void testCardMessages()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (с информационным сообщением)
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Проверка наличия баннера с информационным сообщением
        TrCardAct.swipeLowerLeftToFindButtonWithPicByText("Продлите срок действия");

        // Закрытие баннера с информационным сообщением
        if (TrCardPlatform.getInstance().isIOS()) {
            TrCardAct.clickTheButton("Закрыть");
        } else {
            TrCardAct.clickTheButtonWithPic("Закрыть");
        }
        TrCardAct.waitForButtonWithPicToDisappear("Продлите срок действия");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка отсутствия баннера с информационным сообщением
        TrCardAct.waitForButtonWithPicToDisappear("Продлите срок действия");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина для повторного входа в приложение
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (с информационным сообщением)
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Проверка наличия баннера с информационным сообщением
        TrCardAct.swipeLowerLeftToFindButtonWithPicByText("Продлите срок действия");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения информации об операциях типа *_TRANSFER
    @Test
    public void testFindAndCheckTransferOperations()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("otherops@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("otherops@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("7902");
        TrCardAct.waitForWarningTextToAppear("Карта заблокирована");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("6930");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Пополнение");
        TrCardAct.clickTheTooltipButton("Пополнение");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1716");
        TrCardAct.waitForWarningTextToAppear("Карта заблокирована");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("7150");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Подключение услуги");
        TrCardAct.clickTheTooltipButton("Подключение услуги");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("0987");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Пополнение");
        TrCardAct.clickTheTooltipButton("Пополнение");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1308");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Подключение услуги");
        TrCardAct.clickTheTooltipButton("Подключение услуги");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Пополнение");
        TrCardAct.clickTheTooltipButton("Пополнение");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("6994");
        TrCardAct.waitForWarningTextToAppear("Карта заблокирована");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("3906");
        TrCardAct.waitForWarningTextToAppear("Карта заблокирована");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("[-услуги]");
        TrCardAct.waitForButtonWithPicToDisappear("Льготы");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("[+услуги]");

        // Открытие списка подключенных к карте льгот и проверка отображения информации о них
        TrCardAct.waitForButtonWithPicToAppear("Льготы");
        TrCardAct.clickTheButtonWithPic("Льготы");
        TrCardAct.waitForScreenTitleToDisappear("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Подключенные льготы");
        TrCardAct.waitForButtonWithPicToAppear("Ветеран труда");
        TrCardAct.waitForTextToAppear("Во вкладке представлена информация");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Поиск операции с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Подключение услуги");
        TrCardAct.clickTheTooltipButton("Подключение услуги");
        TrCardAct.waitForButtonWithPicToAppear("Перенос операции с другой карты");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Перенос операции с другой карты");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Прокручивание истории поездок для поиска определённой даты (загрузка нескольких страниц и т.п.)
    @Test
    public void testSwipeTripsHistoryAndCheck()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Прокручивание истории поездок и поиск конкретной даты, а затем открытие поездки по времени
        TrCardAct.swipeUpToFindButtonWithPicByText("20 января");
        TrCardAct.swipeUpToFindButtonWithPicByText("18:22:09");
        TrCardAct.clickTheButtonWithPic("18:22:09");

        // Проверка отображения некоторых параметров поездки
        TrCardAct.waitForTextToAppear("Метро");
        TrCardAct.waitForTextToAppear("Дата и время поездки");
        TrCardAct.waitForTextToAppear("18:22 20.01.2022");
        TrCardAct.waitForTextToAppear("Маршрут");
        TrCardAct.waitForTextToAppear("Речной вокзал");

        // Возврат к списку поездок
        TrCardAct.clickTheButton("Назад");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Прокручивание истории операций для поиска определённой даты (загрузка нескольких страниц и т.п.)
    @Test
    public void testSwipeReplenishmentsHistoryAndCheck()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Прокручивание истории операций и поиск конкретной даты, а затем открытие операции по времени
        TrCardAct.swipeUpToFindButtonWithPicByText("24 декабря");
        TrCardAct.swipeUpToFindButtonWithPicByText("08:16:55");
        TrCardAct.clickTheButtonWithPic("08:16:55");

        // Проверка отображения некоторых параметров операции
        TrCardAct.waitForTextToAppear("Пополнение");
        TrCardAct.waitForTextToAppear("Дата и время пополнения");
        TrCardAct.waitForTextToAppear("08:16 24.12.2021");
        TrCardAct.waitForTextToAppear("Терминал");
        TrCardAct.waitForTextToAppear("J065210");

        // Возврат к списку поездок
        TrCardAct.clickTheButton("Назад");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Прокручивание карусели карт для получения информации о конкретной карте
    @Test
    public void testSwipeCards()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("name", "2210");
        } else {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "2210");
        }

        // Проверка отображения параметров карты
        TrCardAct.waitForTextToAppear("Статус");
        TrCardAct.waitForTextToAppear("Проездной");
        TrCardAct.waitForTextToAppear("Срок действия карты");

        // Проверка соответствия номера найденной карты "нужному" номеру
        assertEquals(
                "Ошибка! Номер найденной карты отличается от номера искомой карты.",
                "9643 90540 33168 42210",
                card_number
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работоспособности механизма выбора карты из списка (экраны "Поездки" и "Операции")
    @Test
    public void testCardSelector()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Нажатие на селектор
        TrCardAct.clickTheButtonWithPic("Транспортная карта");

        // Прокручивание списка карт и поиск конкретной карты, а затем открытие списка поездок
        TrCardAct.swipeUpToFindButtonWithPicByText("1370");
        TrCardAct.clickTheButtonWithPic("1370");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Прокручивание истории поездок и поиск конкретной даты, а затем открытие поездки по времени
        TrCardAct.swipeUpToFindButtonWithPicByText("20 января");
        TrCardAct.swipeUpToFindButtonWithPicByText("18:22:09");
        TrCardAct.clickTheButtonWithPic("18:22:09");

        // Проверка отображения некоторых параметров поездки
        TrCardAct.waitForTextToAppear("Метро");
        TrCardAct.waitForTextToAppear("Дата и время поездки");
        TrCardAct.waitForTextToAppear("18:22 20.01.2022");
        TrCardAct.waitForTextToAppear("Маршрут");
        TrCardAct.waitForTextToAppear("Речной вокзал");

        // Возврат к списку поездок
        TrCardAct.clickTheButton("Назад");

        // Нажатие на селектор
        TrCardAct.clickTheButtonWithPic("1370");

        // Прокручивание списка карт и поиск конкретной карты, а затем переход в раздел "Операции"
        TrCardAct.swipeUpToFindButtonWithPicByText("2210");
        TrCardAct.clickTheButtonWithPic("2210");
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Нажатие на селектор
        TrCardAct.clickTheButtonWithPic("2210");

        // Прокручивание списка карт и поиск конкретной карты, а затем открытие списка операций
        TrCardAct.swipeUpToFindButtonWithPicByText("1370");
        TrCardAct.clickTheButtonWithPic("1370");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Прокручивание истории операций и поиск конкретной даты, а затем открытие операции по времени
        TrCardAct.swipeUpToFindButtonWithPicByText("24 декабря");
        TrCardAct.swipeUpToFindButtonWithPicByText("08:16:55");
        TrCardAct.clickTheButtonWithPic("08:16:55");

        // Проверка отображения некоторых параметров операции
        TrCardAct.waitForTextToAppear("Пополнение");
        TrCardAct.waitForTextToAppear("Дата и время пополнения");
        TrCardAct.waitForTextToAppear("08:16 24.12.2021");
        TrCardAct.waitForTextToAppear("Терминал");
        TrCardAct.waitForTextToAppear("J065210");

        // Возврат к списку поездок
        TrCardAct.clickTheButton("Назад");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка закрытия списка карт при переходе между вкладками
    @Test
    public void testCloseCardSelector()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Нажатие на селектор и проверка открытия списка карт
        TrCardAct.clickTheButtonWithPic("Транспортная карта");
        TrCardAct.waitForButtonWithPicToAppear("Банковская карта");

        // Переход в раздел "Операции" (первое нажатие закрывает список карт, второе открывает вкладку "Операции")
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForButtonWithPicToDisappear("Банковская карта");
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности отображения подключенных к карте услуг и льгот
    @Test
    public void testShowServicesAndPerks()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Поиск карты с подключенными услугами, открытие списка подключенных к карте услуг
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("Подключенные услуги");
        TrCardAct.clickTheButtonWithPic("Подключенные услуги");
        TrCardAct.waitForScreenTitleToDisappear("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Подключенные услуги");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Поиск карты с подключенными льготами, открытие списка подключенных к карте льгот
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("Льготы");
        TrCardAct.clickTheButtonWithPic("Льготы");
        TrCardAct.waitForScreenTitleToDisappear("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Подключенные льготы");
        TrCardAct.waitForTextToAppear("Во вкладке представлена информация");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения кнопок "Пополнить" и "Купить услугу", а также флажка "Online" и сообщения о невозможности онлайн-пополнения карты
    @Test
    public void testCheckReplenishButtonsAndText()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);
        TrCardDataMethods TrCardData = new TrCardDataMethods(driver);

        // Инициализация массива с параметрами карт
        String[][] card_parameters = TrCardData.getRealCardParameters();

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Обработка значений массива: проверка корректности отображения кнопок "Пополнить" и "Купить услугу"
        for (int i = 0; i < card_parameters.length; i++) {
            String
                    card_number = card_parameters[i][0],
                    is_replenishable = card_parameters[i][1],
                    has_available_purchases = card_parameters[i][2],
                    is_online = card_parameters[i][3];

            // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
            TrCardAct.swipeUpperLeftToFindButtonWithPicByText(card_number);

            // Проверка наличия и работоспособности кнопки "Пополнить" (или её отсутствия)
            if (is_replenishable.equals("Y")) {
                TrCardAct.waitForBigButtonToAppear("ПОПОЛНИТЬ");
                TrCardAct.clickTheBigButton("ПОПОЛНИТЬ");
                TrCardAct.waitForScreenTitleToAppear("Пополнение карты");
                TrCardAct.waitForInputFieldToAppear("Сумма пополнения");
                TrCardAct.waitForTextToDisappear("Произошла ошибка");
                TrCardAct.waitForTextToDisappear("Не удалось получить информацию о пополнении");
                TrCardAct.clickTheButton("Назад");
            } else {
                TrCardAct.waitForBigButtonToDisappear("ПОПОЛНИТЬ");
            }

            // Проверка наличия и работоспособности кнопки "Купить услугу" (или её отсутствия)
            if (has_available_purchases.equals("Y")) {
                TrCardAct.waitForBigButtonToAppear("КУПИТЬ УСЛУГУ");
                TrCardAct.clickTheBigButton("КУПИТЬ УСЛУГУ");
                TrCardAct.waitForScreenTitleToAppear("Подключить услугу");
                TrCardAct.waitForButtonWithPicToAppear("₽");
                TrCardAct.waitForTextToDisappear("Ошибка при совершении операции");
                TrCardAct.clickTheButton("Назад");
            } else {
                TrCardAct.waitForBigButtonToDisappear("КУПИТЬ УСЛУГУ");
            }

            // Проверка наличия флажка "Online" (или отсутствия этого флажка)
            if (is_online.equals("Y")) {
                TrCardAct.waitForTextToAppear("Online");
            } else {
                TrCardAct.waitForTextToDisappear("Online");
            }

            // Проверка наличия сообщения о невозможности онлайн-пополнения карты (или отсутствия этого сообщения)
            if (is_replenishable.equals("N") & has_available_purchases.equals("N") & is_online.equals("Y")) {
                TrCardAct.waitForTextToAppear("К сожалению, в вашем регионе недоступно онлайн-пополнение транспортной карты");
            } else {
                TrCardAct.waitForTextToDisappear("К сожалению, в вашем регионе недоступно онлайн-пополнение транспортной карты");
            }
        }

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта allcards@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения чеков по поездкам (печатные билеты всех видов)
    @Test
    public void testCheckTripReceipt()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);
        TrCardDataMethods TrCardData = new TrCardDataMethods(driver);

        // Инициализация массива с параметрами печатного билета
        String[][] real_tickets = TrCardData.getRealTickets();

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

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
        for (int i = 0; i < real_tickets.length; i++) {
            String
                    trip_date = real_tickets[i][0],
                    trip_time = real_tickets[i][1],
                    trip_terminal = real_tickets[i][2],
                    trip_transaction = real_tickets[i][3],
                    trip_type = real_tickets[i][4],
                    trip_city = real_tickets[i][5],
                    trip_receipt_status = real_tickets[i][6];

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


    // Установка кода доступа при первичном входе и его последующее отключение
    @Test
    public void testPassCodeDisableAndCheck()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа (в том числе неудачные попытки)
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по коду доступа" (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Вход по коду доступа");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Вход по коду доступа");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Вход по коду доступа' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение входа по коду доступа, выход из раздела "Настройки"
        TrCardAct.clickTheSwitch("Вход по коду доступа");
        TrCardAct.clickTheButton("Назад");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Проверка отключения входа по коду доступа и проверка корректности обработки введённого пароля
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.enterPasswordAndCheckTextAgain(password.substring(0, 7), true);
        TrCardAct.enterPasswordAndCheckTextAgain(password.replace("1", "0"), true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.waitForBottomBannerToAppear("Вы неверно ввели e-mail или пароль");
        TrCardAct.waitForBottomBannerToDisappear("Вы неверно ввели e-mail или пароль");
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по коду доступа" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Вход по коду доступа");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Вход по коду доступа");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Вход по коду доступа' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Установка кода доступа через настройки после первичного входа по паролю
    @Test
    public void testPassCodeEnableAndCheck()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по коду доступа" (переключатель должен быть неактивен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Вход по коду доступа");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Вход по коду доступа");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Вход по коду доступа' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Активация входа по коду доступа и проверка корректности обработки введённого пароля
        TrCardAct.clickTheSwitch("Вход по коду доступа");
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.enterPasswordAndCheckTextAgain(password.substring(0, 7), true);
        TrCardAct.enterPasswordAndCheckTextAgain(password.replace("1", "0"), true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");
        TrCardAct.waitForBottomBannerToAppear("Вы неверно ввели e-mail или пароль");
        TrCardAct.waitForBottomBannerToDisappear("Вы неверно ввели e-mail или пароль");
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");

        // Установка кода доступа (правильный код > неправильный код > правильный код)
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.waitForScreenTitleToAppear("Код доступа");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка состояния функции "Вход по коду доступа" (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Вход по коду доступа");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Вход по коду доступа");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Вход по коду доступа' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат в главное меню
        TrCardAct.waitForScreenTitleToAppear("Настройки");
        TrCardAct.clickTheButton("Назад");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа (в том числе неправильного)
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.enterPassCode("5", "8", "3", "6");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по коду доступа" (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Вход по коду доступа");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Вход по коду доступа");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Вход по коду доступа' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Установка кода доступа при первичном входе и его последующая замена с помощью функции "Забыли?"
    @Test
    public void testPassCodeRecovery()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Сброс кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.swipeUpToFindBigButtonByText("ЗАБЫЛИ?");
        TrCardAct.clickTheBigButton("ЗАБЫЛИ?");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите сбросить код");
        TrCardAct.clickTheBigButton("НЕТ");
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.swipeUpToFindBigButtonByText("ЗАБЫЛИ?");
        TrCardAct.clickTheBigButton("ЗАБЫЛИ?");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите сбросить код");
        TrCardAct.clickTheBigButton("ДА");

        // Вход с использованием пароля
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ДАЛЕЕ");

        // Установка нового кода доступа
        TrCardAct.enterPassCode("1", "2", "3", "4");
        TrCardAct.enterPassCode("1", "2", "3", "4");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием нового кода доступа (а также проверка старого кода)
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("1", "2", "3", "4");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Активация входа по отпечатку пальца или по FaceID (при наличии такой возможности)
    @Test
    public void testToggleFingerprintsOrFaceID()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Установка входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        String auth_type = TrCardAct.checkAndAcceptFingerprintsOrFaceID();
        String switch_text;
        if (auth_type.equals("Fingerprint")) {
            switch_text = "Вход по отпечатку пальца";
        } else if (auth_type.equals("FaceID")) {
            switch_text = "Вход по Face ID";
        } else {
            return;
        }

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по отпечатку пальца / Вход по FaceID" (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", switch_text);
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", switch_text);
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя '" + switch_text + "' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение входа по отпечатку пальца или по FaceID
        TrCardAct.clickTheSwitch(switch_text);

        // Возврат в главное меню
        TrCardAct.waitForScreenTitleToAppear("Настройки");
        TrCardAct.clickTheButton("Назад");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Настройки");
        TrCardAct.waitForScreenTitleToAppear("Настройки");

        // Проверка состояния функции "Вход по отпечатку пальца / Вход по FaceID" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", switch_text);
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", switch_text);
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя '" + switch_text + "' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат в главное меню
        TrCardAct.waitForScreenTitleToAppear("Настройки");
        TrCardAct.clickTheButton("Назад");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Выход из учётной записи через меню на экране ввода пароля
    @Test
    public void testLogoutFromPasswordScreen()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Выход из учётной записи через меню на экране ввода пароля
        TrCardAct.waitForTextToAppear("Введите пароль");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работоспособности механизма поиска по списку городов (раздел "Получение чека")
    @Test
    public void testRegionSearch()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Получение чека" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Получение чека");
        TrCardAct.checkForPermissionNotification();
        TrCardAct.clickTheBigButton("ВВЕСТИ ПАРАМЕТРЫ ВРУЧНУЮ");
        TrCardAct.waitForScreenTitleToAppear("Кассовый чек");

        // Тестирование механизма поиска по списку городов (существующие в базе города)
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.enterText("Выбор города", "ово", false);
        TrCardAct.waitForTextToAppear("Новосибирск");
        TrCardAct.waitForTextToAppear("Новокузнецк");
        TrCardAct.waitForTextToDisappear("Горно-Алтайск");

        // Тестирование механизма поиска по списку городов ("Ничего не найдено")
        TrCardAct.enterText("Выбор города", "Мадрид", false);
        TrCardAct.waitForTextToAppear("Ничего не найдено");

        // Тестирование механизма поиска по списку городов (возврат к полному списку городов)
        TrCardAct.enterText("Выбор города", "", false);
        TrCardAct.waitForTextToAppear("Горно-Алтайск");

        // Возврат в главное меню и переход на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы механизма включения и отключения функции "Кассовый чек на e-mail"
    @Test
    public void testReceiptToEmail()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password_1 = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password_1, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Возврат на экран "Мои карты" и переход в раздел "Настройки карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Копирование почтового адреса
        String e_mail_step_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_1 = TrCardAct.getFirstWordFromTheString(e_mail_step_1);

        // Включение функции "Получать кассовый чек..."
        TrCardAct.clickTheSwitch("Получать кассовый чек");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password_2 = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password_2, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText(card_number_1.substring(card_number_1.length()-4));

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Проверка соответствия "второго" номера карты "первому" номеру
        assertEquals(
                "Ошибка! Отображаемые номера Транспортных карт отличаются.",
                card_number_1,
                card_number_2
        );

        // Возврат на экран "Мои карты" и переход в раздел "Настройки карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Копирование почтового адреса, проверка корректности его отображения в формате "aut***@test.test"
        String e_mail_step_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_2 = TrCardAct.getFirstWordFromTheString(e_mail_step_2);
        String hidden_email = TrCardAct.hideEmail(e_mail_step_1);
        assertEquals(
                "Ошибка! Некорректно отображается скрытый почтовый адрес ('" + e_mail_step_2 + "' вместо '" + hidden_email + "').",
                hidden_email,
                e_mail_step_2
        );

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение функции "Получать кассовый чек..."
        TrCardAct.clickTheSwitch("Получать кассовый чек");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Копирование почтового адреса, проверка корректности его отображения (должен отображаться адрес текущей учётной записи)
        String e_mail_step_3;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_3 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_3 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_3 = TrCardAct.getFirstWordFromTheString(e_mail_step_3);
        assertEquals(
                "Ошибка! Некорректно отображается почтовый адрес ('" + e_mail_step_3 + "' вместо 'allcards@test.test').",
                "allcards@test.test",
                e_mail_step_3
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы механизма включения и отключения функции "Кассовый чек на e-mail" со сменой адреса
    @Test
    public void testReceiptToEmailWithDifferentAddress()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password_1 = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password_1, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_1 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Возврат на экран "Мои карты" и переход в раздел "Настройки карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Изменение почтового адреса для получения чека
        TrCardAct.clickTheUpperRightCornerOfTheCardEmailButton("Получение кассовых чеков по данной транспортной карте");
        TrCardAct.enterEmailAndCheckText("niko", false);
        TrCardAct.enterEmailAndCheckTextAgain("nikola-ag", false);
        TrCardAct.enterEmailAndCheckTextAgain("nikola-ag@ya.", false);
        TrCardAct.enterEmailAndCheckTextAgain("nikola-ag@ya.ru", false);
        TrCardAct.clickTheBigButton("СОХРАНИТЬ");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Копирование почтового адреса, проверка корректности его отображения
        String e_mail_step_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_1 = TrCardAct.getFirstWordFromTheString(e_mail_step_1);
        assertEquals(
                "Ошибка! Некорректно отображается почтовый адрес ('" + e_mail_step_1 + "' вместо 'nikola-ag@ya.ru').",
                "nikola-ag@ya.ru",
                e_mail_step_1
        );

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password_2 = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password_2, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText(card_number_1.substring(card_number_1.length()-4));

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number_2 = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Проверка соответствия "второго" номера карты "первому" номеру
        assertEquals(
                "Ошибка! Отображаемые номера Транспортных карт отличаются.",
                card_number_1,
                card_number_2
        );

        // Возврат на экран "Мои карты" и переход в раздел "Настройки карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Копирование почтового адреса, проверка корректности его отображения в формате "aut***@test.test"
        String e_mail_step_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_2 = TrCardAct.getFirstWordFromTheString(e_mail_step_2);
        String hidden_email = TrCardAct.hideEmail(e_mail_step_1);
        assertEquals(
                "Ошибка! Некорректно отображается скрытый почтовый адрес ('" + e_mail_step_2 + "' вместо '" + hidden_email + "').",
                hidden_email,
                e_mail_step_2
        );

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение функции "Получать кассовый чек..."
        TrCardAct.clickTheSwitch("Получать кассовый чек");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Копирование почтового адреса, проверка корректности его отображения (должен отображаться адрес текущей учётной записи)
        String e_mail_step_3;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_3 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_3 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_3 = TrCardAct.getFirstWordFromTheString(e_mail_step_3);
        assertEquals(
                "Ошибка! Некорректно отображается почтовый адрес ('" + e_mail_step_3 + "' вместо 'allcards@test.test').",
                "allcards@test.test",
                e_mail_step_3
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы механизма включения и отключения функции "Push-уведомления"
    @Test
    public void testPushNotificationsSwitch()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки карты", проверка отсутствия переключателя "Push-уведомления" (без авторизации)
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToDisappear("Push-уведомления");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран авторизации через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheLowerLeftCornerOfTheButtonWithPic("Войти по логину");
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Установка кода доступа
        TrCardAct.enterPassCode("5", "8", "3", "5");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Пропуск установки входа по отпечатку пальца или по FaceID при появлении соответствующего предложения
        TrCardAct.checkAndSkipFingerprintsOrFaceID();

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Включение функции "Push-уведомления"
        TrCardAct.clickTheLeftEighthPartOfTheSwitch("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperRightToFindButtonWithPicByText("1370");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение функции "Push-уведомления"
        TrCardAct.clickTheLeftEighthPartOfTheSwitch("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Перезапуск приложения без потери пользовательских данных
        TrCardAct.restartApp();

        // Повторный вход в приложение с использованием кода доступа
        TrCardAct.waitForTextToAppear("automation@test.test");
        TrCardAct.enterPassCode("5", "8", "3", "5");

        // Проверка успешности повторного входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы механизма включения и отключения функции "Push-уведомления" при выходе из аккаунта
    @Test
    public void testPushNotificationsSwitchAfterLogout()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Включение функции "Push-уведомления"
        TrCardAct.clickTheLeftEighthPartOfTheSwitch("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть активен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности отображения и работы кнопки "i" на поездках, совершённых по услуге
    @Test
    public void testTripServiceTooltip()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("0509");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Поиск поездки с кнопкой "i", нажатие на эту кнопку и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("Автобус");
        TrCardAct.clickTheTooltipButton("Автобус");
        TrCardAct.waitForButtonWithPicToAppear("Поездка выполнена по услуге");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Поездка выполнена по услуге");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности отображения скидочных поездок (кнопка "%")
    @Test
    public void testTripDiscountTooltip()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная банковская карта
        TrCardAct.swipeUpperLeftToFindBigButtonByText("is_discount_1");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Поиск поездки со скидкой по платёжной системе, нажатие на кнопку "%" и проверка отображения всплывающего сообщения
        TrCardAct.swipeUpToFindTooltipButtonByText("19:23:42");
        TrCardAct.clickTheTooltipButton("19:23:42");
        TrCardAct.waitForButtonWithPicToAppear("Скидка по платежной системе");

        // Закрытие всплывающего сообщения и проверка его исчезновения
        TrCardAct.tapTheLowerEdgeOfTheScreen();
        TrCardAct.waitForButtonWithPicToDisappear("Скидка по платежной системе");

        // Открытие детальной информации о поездке по времени
        TrCardAct.clickTheBigButton("19:23:42");

        // Проверка отображения некоторых параметров поездки, в том числе "нажимаемых" значений стоимости поездки со скидкой и без скидки
        TrCardAct.waitForTextToAppear("Дата и время поездки");
        TrCardAct.waitForTextToAppear("Маршрут");
        if (TrCardPlatform.getInstance().isIOS()) {
            TrCardAct.waitForButtonWithPicToAppear("Стоимость поездки со скидкой");
            TrCardAct.waitForButtonWithPicToAppear("Стоимость поездки без скидки");
        } else {
            String is_clickable_discount = TrCardAct.waitForButtonWithPicToAppearAndGetAttribute("clickable", "Стоимость поездки со скидкой");
            assertEquals(
                    "Ошибка! 'Стоимость поездки со скидкой' не является кнопкой для открытия всплывающего сообщения.",
                    "true",
                    is_clickable_discount
            );
            String is_clickable_full = TrCardAct.waitForButtonWithPicToAppearAndGetAttribute("clickable", "Стоимость поездки без скидки");
            assertEquals(
                    "Ошибка! 'Стоимость поездки без скидки' не является кнопкой для открытия всплывающего сообщения.",
                    "true",
                    is_clickable_full
            );
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работоспособности функции "Вопросы / Ответы"
    @Test
    public void testQuestionsAnswers()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран "Вопрос/Ответ" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Вопрос/ответ");
        TrCardAct.waitForScreenTitleToAppear("Вопрос/ответ");

        // Проверка отображения вопросов и ответов
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если транспортная карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("разблокировать карту");
        TrCardAct.swipeUpToFindBigButtonByText("Почему моя банковская карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("образуется задолженность");
        TrCardAct.swipeUpToFindButtonLinkByText("Личный кабинет пассажира");
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если в мобильном приложении отображается неверный баланс");
        TrCardAct.swipeUpToFindButtonLinkByText("support-tk@cft.ru");
        TrCardAct.swipeUpToFindBigButtonByText("вкратце опишите ситуацию");
        TrCardAct.swipeUpToFindBigButtonByText("можно посмотреть");
        TrCardAct.swipeUpToFindButtonLinkByText("t-karta.ru");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Копирование номера карты
        String card_number;
        if (TrCardPlatform.getInstance().isIOS()) {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("name", "9643");
        } else {
            card_number = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "9643");
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Выход из приложения через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText(card_number, true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран "Вопрос/Ответ" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Вопрос/ответ");
        TrCardAct.waitForScreenTitleToAppear("Вопрос/ответ");

        // Проверка отображения вопросов и ответов
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если транспортная карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("разблокировать карту");
        TrCardAct.swipeUpToFindBigButtonByText("Почему моя банковская карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("образуется задолженность");
        TrCardAct.swipeUpToFindButtonLinkByText("Личный кабинет пассажира");
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если в мобильном приложении отображается неверный баланс");
        TrCardAct.swipeUpToFindButtonLinkByText("support-tk@cft.ru");
        TrCardAct.swipeUpToFindBigButtonByText("вкратце опишите ситуацию");
        TrCardAct.swipeUpToFindBigButtonByText("можно посмотреть");
        TrCardAct.swipeUpToFindButtonLinkByText("t-karta.ru");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы приложения при наличии в аккаунте карты с несуществующим регионом
    @Test
    public void testCardFromNonExistentRegion()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("i.judin@cft.ru", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("i.judin@cft.ru");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка полноценности отображения информации о "нормальной" карте при наличии в аккаунте "проблемной" карты
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.waitForButtonToAppear("Информация о карте");
        TrCardAct.waitForButtonToAppear("Настройки карты");
        TrCardAct.waitForButtonToAppear("Удалить карту");
        TrCardAct.tapTheLowerEdgeOfTheScreen();

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("5215");

        // Проверка появления сообщения "Ошибка загрузки..." на экране с информацией о карте
        TrCardAct.waitForTextToAppear("Ошибка загрузки деталей по карте");

        // Проверка отсутствия возможности просмотра информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.waitForButtonToDisappear("Информация о карте");
        TrCardAct.waitForButtonToDisappear("Настройки карты");
        TrCardAct.waitForButtonToAppear("Удалить карту");
        TrCardAct.tapTheLowerEdgeOfTheScreen();

        // Проверка появления сообщений "Ошибка загрузки..." на экранах с информацией о поездках и операциях
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка поездок");
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка операций");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка экрана "О программе" и отображения версии приложения на разных экранах
    @Test
    public void testAboutScreen()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Захват информации о версии приложения с экрана-заставки
        String app_version_temp;
        if (TrCardPlatform.getInstance().isIOS()) {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("name", "V");
        } else {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "V");
        }
        String app_version_start = app_version_temp.replace("V ", "");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "О программе" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("О программе");
        TrCardAct.waitForScreenTitleToAppear("О программе");

        // Проверка отображения информации о приложении и получение его версии
        TrCardAct.waitForTextToAppear("Техническая поддержка");
        TrCardAct.waitForTextToAppear("E-mail службы поддержки");
        TrCardAct.waitForTextToAppear("E-mail для обратной связи");
        TrCardAct.waitForTextToAppear("Сайт системы");
        if (TrCardPlatform.getInstance().isIOS()) {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Версия приложения");
        } else {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Версия приложения");
        }
        String app_version_login = app_version_temp.replace("Версия приложения\n", "");

        // Возврат в главное меню и выход из учётной записи
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Выйти");
        TrCardAct.waitForTextToAppear("Вы действительно хотите выйти из аккаунта automation@test.test?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка возврата на экран входа по логину и паролю
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Переход на экран входа по номеру карты
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "О программе" через главное меню и получение версии приложения
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("О программе");
        TrCardAct.waitForScreenTitleToAppear("О программе");

        // Проверка отображения информации о приложении и получение его версии
        TrCardAct.waitForTextToAppear("Техническая поддержка");
        TrCardAct.waitForTextToAppear("E-mail службы поддержки");
        TrCardAct.waitForTextToAppear("E-mail для обратной связи");
        TrCardAct.waitForTextToAppear("Сайт системы");
        if (TrCardPlatform.getInstance().isIOS()) {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Версия приложения");
        } else {
            app_version_temp = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Версия приложения");
        }
        String app_version_card = app_version_temp.replace("Версия приложения\n", "");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Проверка корректности отображения версии приложения на всех экранах
        assertEquals("Ошибка! Номер версии отличается.", app_version_start, app_version_login);
        assertEquals("Ошибка! Номер версии отличается.", app_version_start, app_version_card);

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы приложения при потере соединения с сетью (авторизация по логину и паролю)
    @Test
    public void testNoConnectionLogin()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Проверка появления всплывающего сообщения о проблемах с сетью
        TrCardAct.waitForTextToAppear("Отсутствует соединение с сетью");
        TrCardAct.clickTheButton("ОК");
        TrCardAct.clickTheBigButton("ВОЙТИ");
        TrCardAct.waitForTextToAppear("Отсутствует соединение с сетью");
        TrCardAct.clickTheButton("ОК");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Повторная попытка войти в приложение
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы приложения при потере соединения с сетью (авторизация по номеру карты)
    @Test
    public void testNoConnectionCardLogin()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Попытка войти в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка появления всплывающего сообщения о проблемах с сетью
        TrCardAct.waitForTextToAppear("Отсутствует соединение с сетью");
        TrCardAct.clickTheButton("ОК");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForTextToAppear("Отсутствует соединение с сетью");
        TrCardAct.clickTheButton("ОК");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Повторная попытка войти в приложение
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы приложения при потере соединения с сетью (информация о карте и т.п.)
    @Test
    public void testNoConnectionCardInfo()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Проверка появления сообщения "Ошибка загрузки..." на экране с информацией о карте
        TrCardAct.waitForTextToAppear("Ошибка загрузки деталей по карте");

        // Проверка отсутствия возможности просмотра информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.waitForButtonToDisappear("Информация о карте");
        TrCardAct.tapTheLowerEdgeOfTheScreen();

        // Проверка появления сообщений "Ошибка загрузки..." на экранах с информацией о поездках и операциях
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка поездок");
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка операций");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Проверка корректности отображения списка операций после восстановления соединения с сетью
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка операций");
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToDisappear("Произошла ошибка загрузки списка операций");
        TrCardAct.swipeUpToFindButtonWithPicByText("24 декабря");

        // Проверка корректности отображения списка поездок после восстановления соединения с сетью
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка поездок");
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToDisappear("Произошла ошибка загрузки списка поездок");
        TrCardAct.swipeUpToFindButtonWithPicByText("20 января");

        // Проверка корректности отображения информации о карте после восстановления соединения с сетью
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForTextToAppear("Ошибка загрузки деталей по карте");
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToDisappear("Ошибка загрузки деталей по карте");

        // Просмотр информации о карте через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Информация о карте");

        // Проверка отображения параметров карты и возврат на экран "Мои карты"
        TrCardAct.waitForTextToAppear("Статус");
        TrCardAct.waitForTextToAppear("Проездной");
        TrCardAct.waitForTextToAppear("Срок действия карты");
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка корректности работы функции "Транспортный роуминг" при потере соединения с сетью
    @Test
    public void testNoConnectionRoamingInfo()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("allcards@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("allcards@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта (с функцией "Транспортный роуминг")
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("3568");

        // Проверка наличия баннера "Транспортный роуминг"
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");

        // Просмотр информации о функции "Транспортный роуминг" через меню
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("роуминг");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");

        // Копирование текста со списком регионов
        String roaming_text;
        if (TrCardPlatform.getInstance().isIOS()) {
            roaming_text = TrCardAct.waitForTextToAppearAndGetAttribute("name", "Ваша карта подключена");
        } else {
            roaming_text = TrCardAct.waitForTextToAppearAndGetAttribute("content-desc", "Ваша карта подключена");
        }

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Просмотр информации о функции "Транспортный роуминг" через кнопку "Подробнее" (без подключения к сети)
        TrCardAct.waitForButtonWithPicToAppear("Доступен проезд в других городах");
        TrCardAct.clickTheButton("Подробнее");
        TrCardAct.waitForScreenTitleToAppear("Транспортный роуминг");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка регионов");
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка регионов");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Проверка корректности отображения списка регионов после восстановления соединения с сетью
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToDisappear("Произошла ошибка загрузки списка регионов");
        TrCardAct.waitForTextToAppear(roaming_text);

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения баннера в списке поездок при потере соединения с сетью
    @Test
    public void testNoConnectionTripsErrorBanner()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Переход в раздел "Поездки"
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");

        // Проверка отображения списка поездок
        TrCardAct.swipeUpToFindButtonWithPicByText("20 января");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Пролистывание списка поездок до момента возникновения баннера, сообщающего об ошибке загрузки данных
        TrCardAct.swipeUpToFindButtonWithPicByText("Ошибка загрузки поездок");
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.swipeUpToFindButtonWithPicByText("Ошибка загрузки поездок");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Попытка загрузить данные ещё раз, проверка исчезновения баннера
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.waitForButtonLinkToDisappear("Попробовать еще раз");

        // Пролистывание списка поездок через несколько страниц, чтобы убедиться в корректности работы приложения
        TrCardAct.swipeUpToFindButtonWithPicByText("15 декабря");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения баннера в списке операций при потере соединения с сетью
    @Test
    public void testNoConnectionReplenishmentsErrorBanner()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Переход в раздел "Операции"
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");

        // Проверка отображения списка операций
        TrCardAct.swipeUpToFindButtonWithPicByText("24 декабря");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Пролистывание списка операций до момента возникновения баннера, сообщающего об ошибке загрузки данных
        TrCardAct.swipeUpToFindButtonWithPicByText("Ошибка загрузки операций");
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.swipeUpToFindButtonWithPicByText("Ошибка загрузки операций");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Попытка загрузить данные ещё раз, проверка исчезновения баннера
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.waitForButtonLinkToDisappear("Попробовать еще раз");

        // Пролистывание списка операций через несколько страниц, чтобы убедиться в корректности работы приложения
        TrCardAct.swipeUpToFindButtonWithPicByText("25 октября");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения баннера c ошибкой обновления данных при потере соединения с сетью
    @Test
    public void testNoConnectionRefreshErrorBanner()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("1370");

        // Переход в раздел "Поездки", проверка наличия данных о поездках
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.swipeUpToFindButtonWithPicByText("Метро");

        // Переход в раздел "Операции", проверка наличия данных об операциях
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");
        TrCardAct.swipeUpToFindButtonWithPicByText("Пополнение");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Обновление списка операций с помощью свайпа, проверка появления баннера с ошибкой
        TrCardAct.swipeDownToRefresh();
        TrCardAct.waitForButtonWithPicToAppear("Ошибка обновления операций");

        // Переход в раздел "Поездки", обновление списка поездок, проверка появления баннера с ошибкой
        TrCardAct.clickTheButtonWithPic("Поездки");
        TrCardAct.waitForScreenTitleToAppear("Поездки");
        TrCardAct.swipeDownToRefresh();
        TrCardAct.waitForButtonWithPicToAppear("Ошибка обновления поездок");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Попытка загрузить данные ещё раз (кнопка "Попробовать ещё раз"), проверка исчезновения баннера
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.waitForButtonWithPicToDisappear("Ошибка загрузки поездок");
        TrCardAct.waitForButtonLinkToDisappear("Попробовать еще раз");

        // Пролистывание списка поездок через несколько страниц, чтобы убедиться в корректности работы приложения
        TrCardAct.swipeUpToFindButtonWithPicByText("15 декабря");

        // Переход в раздел "Операции", попытка загрузить данные ещё раз (свайп), проверка исчезновения баннера
        TrCardAct.clickTheButtonWithPic("Операции");
        TrCardAct.waitForScreenTitleToAppear("Операции");
        TrCardAct.swipeDownToRefresh();
        TrCardAct.waitForButtonWithPicToDisappear("Ошибка обновления операций");
        TrCardAct.waitForButtonLinkToDisappear("Попробовать еще раз");

        // Пролистывание списка операций через несколько страниц, чтобы убедиться в корректности работы приложения
        TrCardAct.swipeUpToFindButtonWithPicByText("25 октября");

        // Переход на экран "Мои карты"
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка отображения баннера c ошибкой вместо списка регионов при потере соединения с сетью
    @Test
    public void testNoConnectionRegionsErrorBanner()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран добавления новой карты
        TrCardAct.clickTheTopButton("Добавить карту");
        TrCardAct.waitForScreenTitleToAppear("Новая карта");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Переход на вкладку "Банковская", ввод номера карты
        TrCardAct.clickTheBigButton("БАНКОВСКАЯ");
        TrCardAct.waitForTextToAppear("Введите 16-значный номер");
        TrCardAct.enterCreditCardNumberAndCheckText("5536 9140 5130 0915", true);

        // Проверка возникновения баннера, сообщающего об ошибке загрузки списка регионов
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка загрузки регионов");
        TrCardAct.swipeUpToFindButtonLinkByText("Попробовать еще раз");
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка загрузки регионов");

        // Попытка добавить карту, несмотря на ошибку загрузки списка регионов
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка загрузки регионов");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Попытка загрузить список регионов ещё раз, проверка исчезновения баннера
        TrCardAct.swipeUpToFindButtonLinkByText("Попробовать еще раз");
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.waitForButtonWithPicToDisappear("Произошла ошибка загрузки регионов");
        TrCardAct.waitForButtonLinkToDisappear("Попробовать еще раз");

        // Проверка восстановления работоспособности функции выбора региона
        TrCardAct.swipeUpToFindBigButtonByText("Город");
        TrCardAct.clickTheBigButton("Город");
        TrCardAct.chooseTheCity("Новосибирск");

        // Добавление карты, проверка успешности перехода на добавленную карту (экран "Мои карты")
        TrCardAct.swipeUpToFindBigButtonByText("ДОБАВИТЬ");
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");
        TrCardAct.waitForTextToAppear("***0915");
        TrCardAct.waitForBigButtonToAppear("Новосибирск");

        // Удаление карты и проверка перехода на предыдущую карту
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Удалить карту");
        TrCardAct.waitForTextToAppear("Вы уверены, что хотите удалить карту ***0915?");
        TrCardAct.clickTheBigButton("ДА");

        // Проверка отображения баннера с сообщением об удалении карты
        TrCardAct.waitForBottomBannerToAppear("Карта удалена");
        TrCardAct.waitForBottomBannerToDisappear("Карта удалена");

        // Проверка перехода на предыдущую карту
        TrCardAct.waitForTextToDisappear("***0915");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работы приложения при попытке включения функции "Кассовый чек на e-mail" без доступа к сети
    @Test
    public void testNoConnectionReceiptToEmail()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки карты"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForCardEmailButtonToAppear("Получение кассовых чеков");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Копирование почтового адреса
        String e_mail_step_1;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_1 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_1 = TrCardAct.getFirstWordFromTheString(e_mail_step_1);

        // Включение функции "Получать кассовый чек...", проверка появления сообщения об ошибке
        TrCardAct.clickTheSwitch("Получать кассовый чек");
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть неактивен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Изменение почтового адреса для получения чека, проверка появления сообщения об ошибке
        TrCardAct.clickTheUpperRightCornerOfTheCardEmailButton("Получение кассовых чеков по данной транспортной карте");
        TrCardAct.enterEmailAndCheckText("nikola-ag@ya.ru", false);
        TrCardAct.clickTheBigButton("СОХРАНИТЬ");
        TrCardAct.waitForButtonWithPicToAppear("Произошла ошибка");

        // Копирование почтового адреса
        String e_mail_step_2;
        if (TrCardPlatform.getInstance().isIOS()) {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("name", "@");
        } else {
            e_mail_step_2 = TrCardAct.waitForCardEmailButtonToAppearAndGetAttribute("content-desc", "@");
        }
        e_mail_step_2 = TrCardAct.getFirstWordFromTheString(e_mail_step_2);

        // Проверка неизменности почтового адреса
        assertEquals(
                "Ошибка! Отображаемый почтовый адрес изменился.",
                e_mail_step_1,
                e_mail_step_2
        );

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Включение функции "Получать кассовый чек..." после восстановления доступа к сети
        TrCardAct.clickTheSwitch("Получать кассовый чек");
        TrCardAct.waitForButtonWithPicToDisappear("Произошла ошибка");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение функции "Получать кассовый чек..."
        TrCardAct.clickTheSwitch("Получать кассовый чек");

        // Проверка состояния функции "Получать кассовый чек..." (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Получать кассовый чек");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Получать кассовый чек");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Получать кассовый чек...' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работы приложения при попытке включения функции "Push-уведомления" без доступа к сети
    @Test
    public void testNoConnectionPushNotificationsSwitch()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Переход на экран входа по номеру карты
        TrCardAct.swipeUpToFindButtonByText("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");
        TrCardAct.clickTheButton("ПРОДОЛЖИТЬ БЕЗ АВТОРИЗАЦИИ");

        // Вход в приложение по номеру карты
        TrCardAct.enterCardNumberAndCheckText("9643 90540 33168 42210", true);
        TrCardAct.clickTheBigButton("ДОБАВИТЬ");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход в раздел "Настройки карты", проверка отсутствия переключателя "Push-уведомления" (без авторизации)
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToDisappear("Push-уведомления");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Переход на экран авторизации через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheLowerLeftCornerOfTheButtonWithPic("Войти по логину");
        TrCardAct.waitForTextToAppear("указанные при регистрации");

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Пролистывание списка карт до тех пор, пока не найдётся нужная карта
        TrCardAct.swipeUpperLeftToFindButtonWithPicByText("2210");

        // Переход в раздел "Настройки карты", проверка наличия переключателя "Push-уведомления"
        TrCardAct.clickTheTopButton("Показать меню");
        TrCardAct.clickTheButton("Настройки карты");
        TrCardAct.waitForScreenTitleToAppear("Настройки карты");
        TrCardAct.waitForSwitchToAppear("Push-уведомления");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Включение функции "Push-уведомления", проверка появления сообщения об ошибке
        TrCardAct.clickTheLeftEighthPartOfTheSwitch("Push-уведомления");
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка");
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.swipeUpToFindButtonWithPicByText("Произошла ошибка");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        String visible_switch_status;
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Включение функции "Push-уведомления" после восстановления доступа к сети
        TrCardAct.clickTheButtonLink("Попробовать еще раз");
        TrCardAct.waitForButtonWithPicToDisappear("Произошла ошибка");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть активен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("1", "true");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть активен).",
                "true",
                visible_switch_status
        );

        // Отключение функции "Push-уведомления"
        TrCardAct.clickTheLeftEighthPartOfTheSwitch("Push-уведомления");

        // Проверка состояния функции "Push-уведомления" (переключатель должен быть неактивен)
        if (TrCardPlatform.getInstance().isIOS()) {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("value", "Push-уведомления");
            visible_switch_status = visible_switch_status.replace("0", "false");
        } else {
            visible_switch_status = TrCardAct.waitForSwitchToAppearAndGetAttribute("checked", "Push-уведомления");
        }
        assertEquals(
                "Ошибка! Некорректное состояние переключателя 'Push-уведомления' (должен быть неактивен).",
                "false",
                visible_switch_status
        );

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }


    // Проверка работоспособности функции "Вопросы / Ответы" без доступа к сети
    @Test
    public void testNoConnectionQuestionsAnswers()
    {
        // Инициализация библиотек методов, необходимых для прохождения теста
        TrCardActions TrCardAct = TrCardActionsFactory.get(driver);
        TrCardPassMethods TrCardPass = new TrCardPassMethods(driver);

        // Ввод логина
        TrCardAct.enterEmailAndCheckText("automation@test.test", true);

        // Получение пароля для учётной записи
        String password = TrCardPass.getPasswordByLogin("automation@test.test");

        // Ввод пароля и попытка войти в приложение
        TrCardAct.enterPasswordAndCheckText(password, true);
        TrCardAct.clickTheBigButton("ВОЙТИ");

        // Отказ от установки кода доступа
        TrCardAct.swipeUpToFindButtonByText("СПАСИБО, НЕ НАДО");
        TrCardAct.clickTheButton("СПАСИБО, НЕ НАДО");

        // Проверка успешности входа в приложение (отображение экрана "Мои карты")
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        // Активация режима полёта для имитации отсутствия подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Переход на экран "Вопрос/Ответ" через главное меню
        TrCardAct.clickTheButtonWithPic("Меню");
        TrCardAct.clickTheButtonWithPic("Вопрос/ответ");
        TrCardAct.waitForScreenTitleToAppear("Вопрос/ответ");

        // Проверка появления сообщения об ошибке загрузки списка вопросов и ответов
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка вопросов");
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToAppear("Произошла ошибка загрузки списка вопросов");

        // Отключение режима полёта для восстановления подключения к сети
        TrCardAct.toggleAirplaneMode();

        // Проверка корректности отображения списка вопросов и ответов после восстановления соединения с сетью
        TrCardAct.swipeUpToFindBigButtonByText("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.clickTheBigButton("ПОПРОБОВАТЬ ЕЩЕ РАЗ");
        TrCardAct.waitForTextToDisappear("Произошла ошибка загрузки списка вопросов");
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если транспортная карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("разблокировать карту");
        TrCardAct.swipeUpToFindBigButtonByText("Почему моя банковская карта заблокирована");
        TrCardAct.swipeUpToFindBigButtonByText("образуется задолженность");
        TrCardAct.swipeUpToFindButtonLinkByText("Личный кабинет пассажира");
        TrCardAct.swipeUpToFindBigButtonByText("Что делать, если в мобильном приложении отображается неверный баланс");
        TrCardAct.swipeUpToFindButtonLinkByText("support-tk@cft.ru");
        TrCardAct.swipeUpToFindBigButtonByText("вкратце опишите ситуацию");
        TrCardAct.swipeUpToFindBigButtonByText("можно посмотреть");
        TrCardAct.swipeUpToFindButtonLinkByText("t-karta.ru");

        // Возврат на экран "Мои карты"
        TrCardAct.clickTheButton("Назад");
        TrCardAct.clickTheButtonWithPic("Мои карты");
        TrCardAct.waitForScreenTitleToAppear("Мои карты");

        System.out.println("Тест пройден без ошибок!");
    }

}
