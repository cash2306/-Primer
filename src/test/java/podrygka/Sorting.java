package podrygka;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class Sorting {
    @Tag("SMOKE")
    @DisplayName("Сортировка")
    void sorting(String filterCatalog) {
        open("https://www.podrygka.ru/catalog/");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        $("#smartFilterForm").setValue(filterCatalog).click();
        $( "#field-property_offer_price-desc").click();
        $("#sort_apply").click();
    };

}

