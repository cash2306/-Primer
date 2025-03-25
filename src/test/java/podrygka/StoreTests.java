package podrygka;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.Arrays;
import java.util.List;

class StoreTests {

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
 void checkSearchFunctionality() {
  List<String> queries = Arrays.asList("пудра", "помада", "туш для объема");

  for (String query : queries) {
   // Поиск товара
   $("#search").setValue(query).pressEnter();

   // Проверка наличия результатов поиска
   $(".title").shouldHave(text(query));
  }
 }

 @Test
 void checkNavigationMenu() {
  List<String> menuItems = Arrays.asList(
          "Бренды",
          "Акции",
          "Новинки",
          "Магазины",
          "Подарочные карты",
          "Скидки месяца",
          "Ликвидация"
  );

  for (String menuItem : menuItems) {
   $(By.linkText(menuItem)).click();
   $(".title").shouldHave(text(menuItem));
  }
 }
}
