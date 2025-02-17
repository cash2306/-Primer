import com.codeborne.selenide.Configuration;

    public class TestBase {
        static void beforeAll() {
            Configuration.browserSize = "1920x1080";
            Configuration.pageLoadStrategy = "eager";
            Configuration.baseUrl = "https://www.podrygka.ru/";
        }

}
