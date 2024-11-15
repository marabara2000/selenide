import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static java.awt.SystemColor.control;

public class RegistrationTest {

    public String generationDate (int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    void shouldRegisterByAccountNumber(){
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").setValue("Москва");
        String planningDate = generationDate(5, "dd.MM.yyyy");

        $("[data-test-id='date'] input").sendKeys((Keys.chord(Keys.SHIFT, Keys.HOME)), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Коля");
        $("[data-test-id='phone'] input").setValue("+79001110011");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")) .click();
        $(".notification__title")
                .should (Condition.visible,Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно!"));
        $(".notification__content")
                .should(Condition.visible,Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));

    }
}
