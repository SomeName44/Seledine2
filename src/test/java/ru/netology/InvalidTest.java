package ru.netology;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidTest {

    public String date(int plusDay, String pattern) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldCityNotCenterOfSubjects() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Касимов");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Смирнов Петр");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String expected = "Доставка в выбранный город недоступна";
        String actual = $("[data-test-id='city'] .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvalidDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рязань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(2, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Смирнов Петр");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String expected = "Заказ на выбранную дату невозможен";
        String actual = $("[data-test-id=date] .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvalidName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рязань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Smirnov Petr");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = $("[data-test-id=name] .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvalidPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рязань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Смирнов Петр");
        $("[data-test-id=phone] input").setValue("+7925123456");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = $("[data-test-id=phone] .input__sub").getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void shouldInvalidClick() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Рязань");
        $("[data-test-id=date] input.input__control").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateOfMeeting = date(3, "dd.MM.yyyy");
        $("[data-test-id=date] input.input__control").sendKeys(dateOfMeeting);
        $("[data-test-id=name] input").setValue("Смирнов Петр");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $$("button").find(exactText("Забронировать")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных";
        String actual = $("[data-test-id=agreement] .checkbox__text").getText().trim();
        assertEquals(expected, actual);
    }
}

