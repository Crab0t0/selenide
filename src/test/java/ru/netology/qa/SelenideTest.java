package ru.netology.qa;


import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideTest {


    LocalDate date = LocalDate.now().plusDays(5);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
    String dateText = date.format(formatter);

    LocalDate date7 = LocalDate.now().plusDays(7);
    String dayClick = String.valueOf(date7.getDayOfMonth());



    @Test
    void shouldSubmitForm() {
        Selenide.open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item").findBy(text("Москва")).click();
        $(".icon_name_calendar").click();
        $$(".calendar__day").findBy(text(dayClick)).click();
//        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
//        $("[data-test-id='date'] input").setValue(dateText);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71234512345");
        $("[data-test-id='agreement'] span").click();
        $(".button__content").click();
        $("[data-test-id= 'notification']")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"));
    }
}
