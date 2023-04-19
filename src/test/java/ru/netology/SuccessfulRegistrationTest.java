package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class SuccessfulRegistrationTest {
    public String date(int plusDay, String pattern) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldRegisterByCard() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рязань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Сидоров Петр");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateOfMeeting), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}