package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ParametrazeTestQuestions {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePageSamacat samacat;

    @BeforeEach
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        samacat = new HomePageSamacat(driver); // Инициализируем ПОСЛЕ создания драйвера
        driver.get("https://qa-scooter.praktikum-services.ru/");
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
    public void testQuestionsAnswer(int questionsIndex, String expectedAnswer){
        //Нажать на кнопку согласия кук.
        samacat.clickCookieesButton();
        // Скрол до вопросов.
        WebElement element = driver.findElement(samacat.getSectionImportant());
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        // Перебор по индексу вопросов и клик
        WebElement questions = wait.until(ExpectedConditions.elementToBeClickable(
                samacat.getQuestions(questionsIndex)));
        questions.click();
        //Получить текст ответа
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                samacat.getAnswer(questionsIndex)));
        String actualAnswer = answer.getText();
        // Сравнить результаты
        Assertions.assertEquals(expectedAnswer,actualAnswer);
    }

    static Stream<Arguments> parameters() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of(3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of(6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(7, "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }



}
