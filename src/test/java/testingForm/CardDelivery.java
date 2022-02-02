package testingForm;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


public class CardDelivery {

    @Test
    public void shouldChangePlanningDate() {

        open("http://localhost:9999");

        FormInfo info = DataGenerator.Form.generateInfo("ru");

        $("[data-test-id=\"city\"] input").setValue(info.getCity());
        String planningDate = DataGenerator.getOrderDate(4);
        $("[data-test-id=\"date\"] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=\"date\"] input").setValue(planningDate).click();

        $("[data-test-id=\"name\"] input").setValue(info.getName());
        $("[data-test-id=\"phone\"] input").setValue(info.getPhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();

        $("[data-test-id=\"success-notification\"]").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Успешно!"));
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));

        String newPlanningDate = DataGenerator.getOrderDate(6);
        $("[data-test-id=\"date\"] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=\"date\"] input").setValue(newPlanningDate).click();
        $(withText("Запланировать")).click();

        $("[data-test-id=\"replan-notification\"]").shouldBe(visible)
                .shouldHave(text("Необходимо подтверждение"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=\"success-notification\"]").shouldBe(visible)
                .shouldHave(text("Успешно!"));
        $("[data-test-id=\"success-notification\"]").shouldBe(visible)
                .shouldHave(text("Встреча успешно запланирована на " + newPlanningDate));

    }
}
