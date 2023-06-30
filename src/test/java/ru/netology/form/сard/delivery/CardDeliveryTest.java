package ru.netology.form.сard.delivery;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String getFutureDate(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = futureDate.format(formatter);
        return formattedDate;
    }

    @Test
    public void shouldSuccessfulRegistrationsCardDelivery() {
        open("http://localhost:9999/");
        // SelenideElement form = $("[]");
        $("[data-test-id=city] input").setValue("Калуга");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(getFutureDate(3));
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79990001122");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(14));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + getFutureDate(3)));

    }
}
