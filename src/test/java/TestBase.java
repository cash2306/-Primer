import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import podrygka.Attach;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    public static final String REMOTE_URL = System.getProperty("remoteUrl", "selenoid.autotests.cloud");

    @BeforeEach
    public void testBase() {

        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "127.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://www.podrygka.ru/"; // Убедитесь, что baseUrl правильный
        Configuration.remote = "https://user1:1234@" + REMOTE_URL + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
        SelenideLogger.addListener("allure", new AllureSelenide());
        open(Configuration.baseUrl);
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {

        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
