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
    void itemUserSearchTest(String searchQuery) {
        $("#search").setValue(searchQuery).pressEnter();
        $$(".digi-product").shouldBe(sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "lipstick , категория Макияж , nude",
            "Mascara , категория Макияж , stellary "
    })
    @ParameterizedTest(name = "Для поиска товара {0} найдено в категории {1} уточнить по {2} выдается не пустой поиск")
    @Tag("BLOCKER")
    void SearchResultsForCategoryAreNotZeroTest(String searchQuery, String CategoryFoundItem, String digiTip) {
        $("#search").setValue(searchQuery).pressEnter();
        $("digi-category-found-item__image").setValue(CategoryFoundItem).click();
        $("digi-tip__cloud").setValue(digiTip).click();
        $$("digi-products").shouldBe(sizeGreaterThan(0));

    }
}

