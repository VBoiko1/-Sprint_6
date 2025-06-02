package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParametrazeTestOrderPadge {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePageSamacat samacat;
    private OrderPage user;
    private FinalOrderPage finalUser;


    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        driver.get("https://qa-scooter.praktikum-services.ru/");
        samacat = new HomePageSamacat(driver);
        user = new OrderPage(driver);
        finalUser = new FinalOrderPage(driver);
        samacat.clickCookieesButton();
    }

    @AfterEach
    void teardown() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ignored) {
        }
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("parameters")
    public void testOrderWithDifferentData(
            String buttonLocation,
            String name,
            String surname,
            String adrees,
            String metro,
            String number,
            int date,
            String days,
            String comment){

        if ("HEADER".equals(buttonLocation)) {
            samacat.clickHeaderToOrder();
        } else if ("MIDDLE".equals(buttonLocation)) {
            WebElement element = driver.findElement(samacat.clickMidleToOrder());
            // Прокрутить так, чтобы элемент оказался в центре экрана
            ((JavascriptExecutor)driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );
            element.click();
        }

        //Нажать кнопку "Заказать"
        samacat.clickHeaderToOrder();
        //Проверка перехода на страницу формы заполнения данных
        String title = driver.getTitle();
        assertEquals("undefined", title);
        //Заполнить форму данными
        user.fillName(name);
        user.fillSurname(surname);
        user.fillAdress(adrees);
        user.fillMetro(metro);
        user.fillTelephone(number);
        //Переход на следующую страницу, проверка перехода
        user.clickButtonNext();
        String titleOrder = driver.getTitle();
        assertEquals("undefined", titleOrder);
        //Оформление заказа
        finalUser.fillDeliveryDateWithCurrentDatePlusDays(date); //Выбор даты заказа
        finalUser.selectOption(days);
        finalUser.сhoiceColorScooter();
        finalUser.writeComment(comment);
        finalUser.clickButtonOrder();
        //Нажать "Да" для согласия и проверка сообщения об успешном заказе
        finalUser.clickButtonYes();
        assertTrue(finalUser.isOrderSuccessDisplayed());

    }


    static Stream<Arguments> parameters(){
        return Stream.of(
                Arguments.of("HEADER","Тирион","Ланистер","Вестерос,Замок Кастерли 1",
                        "Чистые пруды","+79183474621",5,"пятеро суток","Осторожно, во дворе злые драконы"),
                Arguments.of("MIDDLE","Джон","Сноу","Вестерос,Замок Винтерфел 3",
                        "Сокольники","+79182347456",2,"трое суток","Зима близко!")
                );
    }
}
