package podrygka;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class StoreTests {

    @Test
    @Tag("Medium")
    @DisplayName("Проверка главной страницы")
    void checkMainPageElements() {

        step("Перейти на главную страницу", () -> {
            open("https://www.podrygka.ru"); // Открываем главную страницу
        });

        step("Проверка наличия логотипа", () -> {
            SelenideElement logo = $(".header__logo"); // Ищем элемент с логотипом
            logo.shouldBe(visible); // Проверяем, что логотип виден
        });

        step("Проверка наличия меню навигации", () -> {
            $$(".menu__item").shouldHave(sizeGreaterThan(0)); // Проверяем, что есть хотя бы один элемент в меню
        });

        step("Проверка наличия баннеров", () -> {
            SelenideElement banner = $(".main-banner"); // Ищем элемент с баннером (проверьте селектор)
            banner.shouldBe(visible); // Проверяем, что баннер виден
        });

        step("Проверка наличия раздела новинок и акций", () -> {
            SelenideElement noveltiesSection = $(".novelties-section"); // Ищем раздел новинок и акций (проверьте селектор)
            noveltiesSection.shouldBe(visible); // Проверяем, что раздел виден
        });

        step("Проверка активности ссылок и кнопок", () -> {
            $$("a").forEach(link -> { // Проверяем все ссылки на странице
                String href = link.getAttribute("href");
                // Проверяем, что href не пустой или null
                if (href == null || href.isEmpty() || href.contains("#")) {
                    throw new AssertionError("Неактивная ссылка или кнопка найдена: " + link);
                }
            });
        });

        step("Проверка наличия кнопок", () -> {
            $$("button").shouldHave(sizeGreaterThan(0)); // Проверяем, что на странице есть кнопки
        });
    }
}
