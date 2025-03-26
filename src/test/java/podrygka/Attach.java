package podrygka;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import io.qameta.allure.Allure;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.RemoteWebDriver;


public class Attach {



    // Метод для прикрепления исходного кода страницы
    public static void pageSource() {
        String pageSource = WebDriverRunner.getWebDriver().getPageSource();
        Allure.addAttachment("Page source", "text/html", pageSource, "html");
    }

    // Метод для прикрепления логов браузера
    public static void browserConsoleLogs() {
        String logs = WebDriverRunner.getWebDriver().manage().logs().get("browser").getAll().toString();
        Allure.addAttachment("Browser console logs", "text/plain", logs, "txt");
    }

    // Метод для прикрепления видео (если оно доступно)
    public static void addVideo() {
        RemoteWebDriver driver = (RemoteWebDriver) WebDriverRunner.getWebDriver();  // Приводим WebDriver к RemoteWebDriver
        String videoUrl = "https://selenoid.autotests.cloud/video/" + driver.getSessionId() + ".mp4";  // Получаем SessionId
        Allure.addAttachment("Video", "video/mp4", videoUrl);
}
}
