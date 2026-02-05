package ru.netology.qa;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideTest {

    public String generateData(int data, String pattern) {
        return LocalDate.now().plusDays(data).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSubmitForm() {
        String plamingDate = generateData(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        $("[data-test-id='date'] input").setValue(plamingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+71234512345");
        $("[data-test-id='agreement'] span").click();
        $(".button__content").click();
        $("[data-test-id= 'notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + plamingDate));
    }

    @Test
    void interactingActiveElements() {
        String plamingDate = generateData(4, "dd.MM.yyyy");
        String dayClick = String.valueOf(Integer.parseInt(plamingDate.substring(0, 2)));
        String MonthYear = generateData(4, "LLLL yyyy");
        MonthYear = MonthYear.substring(0, 1).toUpperCase() + MonthYear.substring(1);

        Selenide.open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item").findBy(text("Москва")).click();
        $(".icon_name_calendar").click();
        $(".calendar").shouldBe(visible);
        int safetyNet = 0;
        while (!$(".calendar__name").text().equals(MonthYear) && safetyNet < 12) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
            safetyNet++;
        }
        $$(".calendar__day").filter(visible).findBy(exactText(dayClick)).click();
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $(".button__content").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + plamingDate));
    }
}
