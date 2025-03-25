import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class NavigationTests {

    private static final String BASE_URL = "https://www.podrygka.ru/";
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;



    @BeforeEach
    void setUp() {
        open("https://www.podrygka.ru/");

        // Удаление фиксированного баннера и футера с помощью JavaScript
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        js.executeScript("$('#fixedban').remove()");
        js.executeScript("$('footer').remove()");
    }

    @AfterEach
    void tearDown() {
        // Закрытие браузера после завершения теста
        getWebDriver().quit();
    }
    @Test
    public void catalogLinkLocator() {
        By catalogLinkLocator = By.linkText("Каталог");
        WebElement catalogElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(catalogLinkLocator));
        Actions actions = new Actions(driver);
        actions.moveToElement(catalogElement).build().perform();

        // Проверяем появление подменю каталога
        By dropdownMenuLocator = By.cssSelector(".catalog-menu");
        WebElement dropdownMenu = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dropdownMenuLocator));
    }

    @Test
    public void checkLoginPopup() {
        // Наводимся на кнопку "Войти"
        By loginButton = By.cssSelector("a[href='/login']");
        WebElement loginElement = driver.findElement(loginButton);
        actions.moveToElement(loginElement).perform();

        // Проверяем видимость окна входа
        By loginModal = By.cssSelector(".modal-content");
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));

        // Проверяем наличие кнопки "Войти"
        By loginSubmitButton = By.cssSelector(".btn-primary[type='submit']");
        wait.until(ExpectedConditions.elementToBeClickable(loginSubmitButton));
    }

    @Test
    public void checkEmptyCartMessage() {
        // Наводимся на иконку "Корзина"
        By cartIcon = By.cssSelector(".cart-link a");
        WebElement cartElement = driver.findElement(cartIcon);
        actions.moveToElement(cartElement).perform();

        // Проверяем видимость сообщения "в корзине пока нет товаров"
        By emptyCartMessage = By.cssSelector(".empty-cart-message");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
    }

    @Test
    public void changeSortingFromPopularityToCheaper() {
        // Найти выпадающий список сортировки
        By sortingSelectLocator = By.id("js-product-list-header-dropdown");
        Select sortingSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortingSelectLocator)));

        // Выбрать опцию "Подешевле"
        sortingSelect.selectByValue("price_asc");

        // Подождать обновления страницы
        wait.until(ExpectedConditions.urlContains("price_asc"));

        // Проверить, что товары отсортированы по возрастанию цены
        By firstProductPriceLocator = By.cssSelector(".product-card .price__current");
        WebElement firstProductPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductPriceLocator));
        System.out.println("Цена первого товара: " + firstProductPrice.getText());
    }
}
