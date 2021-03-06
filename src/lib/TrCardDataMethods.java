package lib;

import io.appium.java_client.AppiumDriver;

public class TrCardDataMethods extends TrCardCoreMethods
{

    public TrCardDataMethods(AppiumDriver driver)
    {
        super(driver);
    }

    /* Хранение и выдача значений некоторых параметров карт из "боевой" базы данных */
    public String[][] getRealCardParameters()
    {
        // 0 - последние 4 цифры номера карты
        // 1 - возможность пополнения карты (Y / N) - counterReplenishment.allowed
        // 2 - возможность подключения услуг к карте (Y / N) - servicePurchase.allowed
        // 3 - является ли карта Online-картой? (Y / N)
        String[][] real_card_parameters = {
                {"2484", "N", "N", "Y"}, {"1672", "N", "N", "Y"}, {"0992", "N", "N", "Y"}, {"4636", "N", "N", "Y"},
                {"1375", "N", "N", "Y"}, {"8682", "N", "N", "Y"}, {"0133", "N", "N", "Y"}, {"3568", "Y", "N", "Y"},
                {"6090", "Y", "N", "Y"}, {"5451", "N", "N", "Y"}, {"0509", "N", "N", "Y"}, {"3568", "N", "Y", "Y"},
                {"8265", "Y", "N", "Y"}, {"8306", "Y", "Y", "Y"}, {"8314", "Y", "Y", "Y"}, {"8322", "Y", "Y", "Y"},
                {"8348", "Y", "Y", "Y"}, {"5210", "N", "N", "Y"}, {"8890", "N", "N", "N"}, {"7659", "N", "N", "N"},
                {"3637", "N", "N", "Y"}, {"5704", "Y", "N", "Y"}, {"8831", "Y", "N", "Y"}, {"4690", "N", "N", "Y"},
                {"1023", "N", "N", "Y"}, {"1243", "Y", "N", "Y"}, {"9732", "Y", "N", "Y"}, {"6784", "Y", "Y", "Y"},
                {"6842", "Y", "Y", "Y"}, {"8020", "Y", "Y", "Y"}, {"8938", "Y", "Y", "Y"}, {"9566", "N", "N", "Y"},
                {"5338", "Y", "Y", "Y"}, {"0571", "Y", "Y", "Y"}, {"8411", "Y", "Y", "Y"}, {"0040", "N", "N", "N"},
                {"6895", "N", "N", "Y"}, {"2599", "Y", "N", "Y"}, {"1090", "N", "Y", "Y"}, {"7062", "N", "Y", "Y"},
                {"0245", "N", "N", "N"}, {"2415", "Y", "N", "Y"}, {"2220", "Y", "Y", "Y"}, {"2907", "Y", "Y", "Y"},
                {"8786", "Y", "Y", "Y"}, {"0429", "Y", "Y", "Y"}, {"6319", "N", "N", "Y"}, {"2772", "N", "N", "Y"},
                {"5015", "N", "N", "Y"}, {"0030", "N", "N", "Y"}, {"5664", "N", "N", "Y"}, {"0138", "N", "N", "Y"},
                {"5869", "N", "N", "Y"}, {"7902", "N", "N", "Y"}, {"6930", "N", "N", "Y"}, {"1716", "N", "N", "Y"},
                {"7150", "N", "N", "Y"}, {"0148", "N", "N", "N"}, {"6655", "N", "N", "N"}, {"4486", "N", "N", "Y"},
                {"4635", "Y", "Y", "Y"}, {"6538", "Y", "Y", "Y"}, {"8195", "Y", "Y", "Y"}, {"3040", "Y", "Y", "Y"},
                {"6463", "Y", "Y", "Y"}, {"7151", "Y", "N", "Y"}, {"2975", "Y", "N", "Y"}, {"7396", "Y", "N", "Y"},
                {"4573", "Y", "N", "Y"}, {"6873", "Y", "N", "Y"}, {"7903", "Y", "N", "Y"}, {"2325", "N", "N", "Y"},
                {"2256", "N", "N", "Y"}, {"3591", "Y", "Y", "Y"}, {"6367", "Y", "Y", "Y"}, {"0987", "Y", "N", "Y"},
                {"1308", "Y", "N", "Y"}, {"6994", "N", "N", "Y"}, {"3906", "N", "N", "Y"}, {"0650", "N", "N", "Y"},
                {"1278", "Y", "N", "Y"}, {"1450", "N", "N", "Y"}, {"2374", "Y", "N", "Y"}, {"0039", "N", "N", "Y"},
                {"0018", "N", "N", "Y"}, {"3282", "Y", "N", "Y"}, {"8939", "Y", "N", "Y"}, {"0457", "Y", "N", "Y"},
                {"7476", "N", "N", "N"}, {"1052", "N", "N", "N"}, {"8258", "N", "N", "N"}, {"9744", "N", "N", "N"},
                {"4209", "N", "N", "N"}, {"2117", "N", "N", "N"}, {"7395", "N", "N", "Y"}, {"7452", "N", "N", "Y"},
                {"4377", "N", "N", "N"}, {"6283", "N", "N", "N"}, {"1370", "N", "N", "N"}, {"4240", "N", "N", "N"},
                {"1594", "N", "N", "N"}, {"0111", "N", "N", "Y"}, {"7552", "N", "N", "Y"}, {"6246", "N", "N", "Y"},
                {"4065", "N", "N", "Y"}, {"2744", "N", "N", "Y"}, {"8939", "N", "N", "Y"}, {"8015", "N", "N", "Y"},
                {"3994", "N", "N", "Y"}, {"1337", "N", "N", "Y"}, {"9334", "N", "N", "Y"}, {"2857", "N", "N", "Y"},
                {"6499", "N", "N", "Y"}, {"9112", "N", "N", "Y"}, {"9625", "N", "N", "Y"}, {"3188", "N", "N", "N"},
                {"3626", "N", "N", "N"}, {"0429", "N", "N", "Y"}, {"1537", "N", "N", "Y"}, {"7552", "Y", "N", "Y"},
                {"9206", "N", "N", "Y"}, {"2497", "N", "N", "Y"}, {"2505", "N", "N", "Y"}, {"1370", "Y", "N", "N"},
                {"1438", "Y", "N", "N"}, {"5567", "Y", "N", "N"}, {"1827", "Y", "N", "N"}, {"9988", "Y", "N", "N"},
                {"2066", "Y", "N", "N"}, {"5770", "N", "N", "N"}, {"0026", "N", "N", "N"}, {"9646", "N", "N", "Y"},
                {"5063", "N", "N", "N"}, {"0344", "N", "N", "Y"}, {"3585", "N", "N", "Y"}, {"2609", "N", "N", "Y"},
                {"9592", "N", "N", "Y"}, {"1253", "N", "N", "Y"}, {"1378", "N", "N", "Y"}, {"7884", "N", "N", "Y"},
                {"1846", "N", "N", "N"}, {"2380", "N", "N", "Y"}, {"5818", "N", "N", "Y"}, {"6424", "N", "N", "Y"},
                {"9410", "N", "N", "Y"}, {"1257", "N", "N", "Y"}, {"2362", "N", "N", "Y"}, {"8864", "N", "N", "Y"},
                {"2377", "N", "N", "N"}, {"0654", "N", "N", "N"}, {"3911", "N", "N", "N"}, {"9600", "N", "N", "N"},
                {"1076", "N", "N", "N"}, {"2203", "N", "N", "N"}, {"6901", "N", "N", "N"}, {"5479", "N", "N", "N"},
                {"1100", "N", "N", "N"}, {"7167", "N", "N", "N"}, {"1283", "N", "N", "N"}, {"2110", "N", "N", "N"},
                {"4803", "N", "N", "N"}, {"2210", "N", "N", "N"}, {"5684", "N", "N", "N"}, {"5850", "N", "N", "N"},
                {"8751", "N", "N", "N"}, {"4080", "N", "N", "N"}, {"6670", "N", "N", "N"}, {"7510", "N", "N", "N"}
        };

        return real_card_parameters;
    }
    /* Хранение и выдача значений некоторых параметров карт из "боевой" базы данных */


    /* Хранение и выдача информации о печатных билетах из "боевой" базы данных */
    public String[][] getRealTickets()
    {
        // 0 - дата поездки
        // 1 - время поездки
        // 2 - номер терминала
        // 3 - номер транзакции
        // 4 - тип (Транспортная карта / банковская карта / наличные)
        // 5 - город
        // 6 - статус (OK / FISCALIZATION_PROGRESS / CONTACT_CARRIER / NOT_REQUIRED)
        String[][] real_tickets = {
                {"30.10.2021", "18:17:43", "T100232", "1562841287", "TrCard", "Новосибирск", "CC"},
                {"30.10.2021", "19:31:16", "T020686", "2025147056", "TrCard", "Новосибирск", "OK"},
                {"01.01.2022", "15:53:23", "T098620", "867445444", "TrCard", "Новосибирск", "NR"},
                {"21.01.2022", "09:09:01", "T098647", "444599197", "TrCard", "Новосибирск", "NR"},
                {"21.01.2022", "12:05:14", "T100813", "1298410887", "TrCard", "Новосибирск", "NR"},
                {"21.01.2022", "14:26:54", "T100813", "714262909", "TrCard", "Новосибирск", "NR"},
                {"27.01.2022", "06:07:39", "T098680", "89024747", "TrCard", "Новосибирск", "NR"},
                {"05.02.2022", "16:51:20", "T020657", "492566014", "TrCard", "Новосибирск", "NR"},
                {"07.02.2022", "08:58:29", "T020644", "300972217", "TrCard", "Новосибирск", "NR"},
                {"08.02.2022", "10:20:20", "T020615", "1622352733", "TrCard", "Новосибирск", "NR"},
                {"20.03.2022", "15:30:02", "T020601", "931460609", "TrCard", "Новосибирск", "NR"},

                {"27.10.2021", "13:38:08", "T020186", "1894496323", "CredCard", "Новосибирск", "OK"},
                {"27.10.2021", "15:28:50", "T020350", "1408679927", "CredCard", "Новосибирск", "OK"},
                {"15.12.2021", "11:30:14", "T020615", "1643054332", "CredCard", "Новосибирск", "OK"},
                {"15.12.2021", "15:53:24", "T098647", "1435514199", "CredCard", "Новосибирск", "OK"},
                {"19.01.2022", "16:47:00", "T020700", "1809272862", "CredCard", "Новосибирск", "OK"},
                {"21.01.2022", "14:27:54", "T100813", "1503678440", "CredCard", "Новосибирск", "OK"},
                {"03.02.2022", "08:44:32", "T098723", "2013531324", "CredCard", "Новосибирск", "OK"},
                {"09.03.2022", "20:29:33", "T020654", "1388341781", "CredCard", "Новосибирск", "OK"},
                {"20.03.2022", "18:51:56", "T020598", "577868150", "CredCard", "Новосибирск", "OK"},

                {"30.10.2021", "14:27:28", "T020636", "1318624581", "Cash", "Новосибирск", "OK"},
                {"31.12.2021", "14:16:57", "T020638", "298319558", "Cash", "Новосибирск", "OK"},
                {"11.01.2022", "19:03:43", "T020686", "427331340", "Cash", "Новосибирск", "OK"},
                {"24.01.2022", "08:31:45", "T107512", "2122263199", "Cash", "Новосибирск", "OK"},
                {"05.02.2022", "17:27:41", "T020286", "93364641", "Cash", "Новосибирск", "OK"},
                {"07.02.2022", "13:47:50", "T020354", "1149953330", "Cash", "Новосибирск", "OK"},
                {"10.02.2022", "08:44:18", "T020177", "682677710", "Cash", "Новосибирск", "OK"},
                {"16.02.2022", "15:56:27", "T123779", "391109719", "Cash", "Новосибирск", "OK"},
                {"04.03.2022", "16:09:30", "T020707", "614227145", "Cash", "Новосибирск", "OK"},
                {"14.03.2022", "08:20:24", "T098680", "1081805469", "Cash", "Новосибирск", "OK"},
                {"23.05.2022", "20:08:09", "T020591", "1253567586", "Cash", "Новосибирск", "OK"}
        };

        return real_tickets;
    }
    /* Хранение и выдача информации о печатных билетах из "боевой" базы данных */


    /* Хранение и выдача информации о рулонных билетах из "боевой" базы данных */
    public String[][] getRealRollTickets()
    {
        // 0 - номер рулонного билета
        // 1 - серия рулонного билета
        // 2 - ИНН перевозчика
        // 3 - город
        // 4 - статус (OK / FISCALIZATION_PROGRESS / CONTACT_CARRIER / NOT_REQUIRED)
        String[][] real_roll_tickets = {

        };

        return real_roll_tickets;
    }
    /* Хранение и выдача информации о рулонных билетах из "боевой" базы данных */


    /* Хранение и выдача информации о печатных билетах из тестовой базы данных TK-Punk */
    public String[][] getTKPunkTickets()
    {
        // 0 - дата поездки
        // 1 - время поездки
        // 2 - номер терминала
        // 3 - номер транзакции
        // 4 - тип (Транспортная карта / банковская карта / наличные)
        // 5 - город
        // 6 - статус (OK / FISCALIZATION_PROGRESS / CONTACT_CARRIER / NOT_REQUIRED)
        String[][] tkpunk_tickets = {
                {"21.10.2021", "13:39:56", "T001646", "1254382617", "TrCard", "Новосибирск", "NR"},
                {"20.10.2021", "14:50:32", "T000838", "1127161976", "TrCard", "Новосибирск", "FP"},
                {"18.10.2021", "15:29:06", "T001063", "1869331647", "TrCard", "Новосибирск", "NR"},
                {"08.10.2021", "17:12:39", "T000827", "268", "TrCard", "Новосибирск", "FP"},
                {"04.10.2021", "15:48:41", "T001720", "466075434", "TrCard", "Новосибирск", "FP"},
                {"24.09.2021", "13:23:18", "T000827", "20", "TrCard", "Новосибирск", "FP"},
                {"16.09.2021", "17:11:19", "T000889", "1338500767", "TrCard", "Новосибирск", "CC"},
                {"09.09.2021", "18:35:06", "T000889", "2000608275", "TrCard", "Новосибирск", "CC"},
                {"06.09.2021", "18:38:40", "T001064", "1602691147", "TrCard", "Новосибирск", "NR"},
                {"02.09.2021", "11:28:17", "T000827", "247", "TrCard", "Новосибирск", "FP"},

                {"25.10.2021", "18:13:29", "T000838", "401058038", "CredCard", "Новосибирск", "FP"},
                {"19.10.2021", "11:01:58", "T001646", "1152829715", "CredCard", "Новосибирск", "FP"},
                {"15.10.2021", "18:38:39", "T001646", "1938823709", "CredCard", "Новосибирск", "NR"},
                {"12.10.2021", "16:44:33", "T001720", "1191949207", "CredCard", "Новосибирск", "FP"},
                {"24.09.2021", "16:56:15", "T001720", "1030887846", "CredCard", "Новосибирск", "FP"},
                {"07.09.2021", "10:15:57", "T000889", "186037138", "CredCard", "Новосибирск", "CC"},
                {"03.09.2021", "09:32:47", "T000889", "1828874490", "CredCard", "Новосибирск", "CC"},
                {"30.07.2021", "16:31:59", "T001646", "25089633", "CredCard", "Новосибирск", "FP"},
                {"18.06.2019", "16:57:28", "J001076", "6215058436", "CredCard", "Новосибирск", "NR"},
                {"24.01.2019", "14:01:14", "T000918", "9999", "CredCard", "Новосибирск", "NR"},

                {"15.10.2021", "18:40:31", "T001646", "579743147", "Cash", "Новосибирск", "FP"},
                {"29.09.2021", "12:41:15", "T001720", "719597071", "Cash", "Новосибирск", "FP"},
                {"10.09.2021", "18:17:10", "T001646", "1320021836", "Cash", "Новосибирск", "NR"},
                {"08.09.2021", "15:09:53", "T000889", "1487342433", "Cash", "Новосибирск", "CC"},
                {"01.09.2021", "17:26:42", "T001744", "449941755", "Cash", "Новосибирск", "CC"},
                {"26.08.2021", "08:37:40", "T000889", "902686742", "Cash", "Новосибирск", "CC"},
                {"26.08.2021", "08:01:51", "T000889", "1948278711", "Cash", "Новосибирск", "CC"},
                {"22.03.2021", "15:06:45", "T001646", "259997894", "Cash", "Новосибирск", "FP"},
                {"12.03.2021", "15:13:10", "T001712", "28167551", "Cash", "Новосибирск", "FP"},
                {"19.02.2021", "17:14:06", "T000838", "691638093", "Cash", "Новосибирск", "FP"}
        };

        return tkpunk_tickets;
    }
    /* Хранение и выдача информации о печатных билетах из тестовой базы данных TK-Punk */


    /* Хранение и выдача информации о рулонных билетах из тестовой базы данных TK-Punk */
    public String[][] getTKPunkRollTickets()
    {
        // 0 - номер рулонного билета
        // 1 - серия рулонного билета
        // 2 - ИНН перевозчика
        // 3 - город
        // 4 - статус (OK / FISCALIZATION_PROGRESS / CONTACT_CARRIER / NOT_REQUIRED)
        String[][] tkpunk_roll_tickets = {
                {"000390", "0731", "5406119655", "Новосибирск", "CC"},
                {"000444", "0731", "5406119655", "Новосибирск", "CC"},
                {"000234", "HAZ001", "07999666999", "Новосибирск", "CC"},
                {"000103", "HAZ001", "07999666999", "Новосибирск", "CC"},
                {"000123", "HAZ001", "07999666999", "Новосибирск", "CC"},
                {"000200", "HAZ001", "07999666999", "Новосибирск", "CC"},
                {"000128", "DEMO01", "5406119655", "Новосибирск", "FP"},
                {"000090", "DEMO01", "5406119655", "Новосибирск", "FP"},
                {"000070", "DEMO07", "5406119655", "Новосибирск", "FP"},
                {"000075", "DEMO07", "5406119655", "Новосибирск", "FP"}
        };

        return tkpunk_roll_tickets;
    }
    /* Хранение и выдача информации о рулонных билетах из тестовой базы данных TK-Punk */

}
