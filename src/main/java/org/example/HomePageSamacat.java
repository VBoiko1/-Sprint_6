package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class HomePageSamacat {

    private final WebDriver driver;

    //Кнопка "Заказать" в  хедере
    private final By headerToOrder = By.xpath(".//button[@class='Button_Button__ra12g']");

    //Кнопка"Заказать" в центре страницы
    private final By midleToOrder = By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");

    //Раздел "Вопросы о важном"
    private final By sectionImportant = By.xpath(".//div[text()='Вопросы о важном']");

    //Кнопка согласие с cookies
    private final By cookieesButton = By.xpath(".//button[@id='rcc-confirm-button']");

    //Вопросы
    private final String questions = ".//div[@id='accordion__heading-%d']";

    //Ответы
    private final String answer = ".//div[@id='accordion__panel-%d']";


    public HomePageSamacat(WebDriver driver) {
        this.driver = driver;
    }

    public By getQuestions(int questionsIndex) {
        return By.xpath(String.format(questions, questionsIndex));
    }

    public By getAnswer(int questionsIndex) {
        return By.xpath(String.format(answer, questionsIndex));
    }

    public By getSectionImportant() {
        return sectionImportant;
    }

    public void clickCookieesButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(cookieesButton)).click();
    }

    public void clickHeaderToOrder() {
        driver.findElement(headerToOrder).click();
    }
    public By clickMidleToOrder() {
        return midleToOrder;
    }


}
