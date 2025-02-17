package podrygka;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.files.DownloadActions.click;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class UserSearch {
    @BeforeEach
    void setUp() {
        open("https://www.podrygka.ru/");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
    }

    @ValueSource(strings = {
            "lipstick", "Face powder", "Mascara"
    })
    @ParameterizedTest(name = "Для поискового запроса {0} должен отдавать поле уточнить")
    @Tag("BLOCKER")
    @DisplayName("Поиск товара на сайте")
    void itemUserSearch(String searchQuery) {
        $("#search").setValue(searchQuery).pressEnter();
        $$("li.digi-tip").shouldBe(sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "lipstick , 1",
            "Face powder , 2"
    })
    @ParameterizedTest(name = "Для добавление {0} в корзине должна быть кнопка {1}")
    @Tag("BLOCKER")
    void searchResultsShouldContainExpectedUrl(String searchQuery, String expectedLink) {
        $("#search").setValue(searchQuery).pressEnter();
        $("li.digi-product__button").setValue(searchQuery).pressEnter();
        $$("li.btn-icon__amount").shouldBe(sizeGreaterThan(0));

    }
}

