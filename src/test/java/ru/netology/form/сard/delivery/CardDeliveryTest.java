package ru.netology.form.сard.delivery;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

        private String getFutureDate (int daysToAdd){
            LocalDate currentDate = LocalDate.now();
            LocalDate futureDate = currentDate.plusDays(daysToAdd);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = futureDate.format(formatter);
            return formattedDate;
        }

        @Test
        public void shouldSuccessfulRegistrationsCardDelivery () {
            open("http://localhost:9999/");
            $("[data-test-id=city] input").setValue("Калуга");
            $(".calendar-input__custom-control input").doubleClick().sendKeys(getFutureDate(3));
            $("[data-test-id=name] input").setValue("Иванов Иван");
            $("[data-test-id=phone] input").setValue("+79990001122");
            $("[data-test-id=agreement]").click();
            $("button").click();
            WebElement title = $("[data-test-id=notification] .notification__title").shouldBe(visible, Duration.ofSeconds(20));
            Assertions.assertEquals("Успешно!", title.getText());
            WebElement content = $("[data-test-id=notification] .notification__content"); //.shouldBe(visible, Duration.ofSeconds(15));
            Assertions.assertEquals("Встреча успешно забронирована на " + getFutureDate(3), content.getText());
        }
    }
