package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

public class FinalOrderPage {

    private final WebDriver driver;


    //Поле даты "Когда привезти самокат"
    private final By deliveryDate = By.xpath(".//input[contains(@placeholder,'Когда привезти')]");

    //Поле "Срок аренды
    private final By termRent = By.xpath(".//div[@class='Dropdown-control']");
    //Выпадающий список
    private final By dropDownList = By.xpath(".//div[@class='Dropdown-menu']/div[@class='Dropdown-option']");

    // Цвет самоката(Чек-боксы)
    private final By blackScooter = By.xpath(".//input[@id='black']");
    private final By greyScooter = By.xpath(".//input[@id='grey']");

    //Поле "Комментарий курьеру"
    private final By comment  = By.xpath(".//input[contains(@placeholder,'Комментарий')]");

    // Кнопка согласия на заказ "Да" и "Нет"
    private final By buttonYes = By.xpath(".//button[contains(@class,'Button_Middle') and text()='Да']");
    private final By buttonNo = By.xpath(".//button[contains(@class,'Button_Middle') and text()='Нет']");

    //Кнопка заказать
    private final By buttonOrder = By.xpath(".//button[contains(@class, 'Button_Middle') and text()='Заказать']");
    // Всплывающее окно об успешном заказе
    private final By windowsSuccessfulOrder = By.xpath(".//div[contains(@class, 'Order_ModalHeader')]");


    public FinalOrderPage(WebDriver driver) {
        this.driver = driver;
    }


    // Ввод даты доставки + LocalDate
    public void fillDeliveryDate(String date) {
        WebElement fieldDeliveruDate = driver.findElement(deliveryDate);
        fieldDeliveruDate.click();
        fieldDeliveruDate.clear();
        fieldDeliveruDate.sendKeys(date);
        driver.findElement(By.xpath(".//div[contains(@class, 'datepicker__day--selected')]")).click();
    }
    public void fillDeliveryDateWithCurrentDatePlusDays(int daysToAdd) {
        LocalDate deliveryDate = LocalDate.now().plusDays(daysToAdd);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = deliveryDate.format(formatter);

        fillDeliveryDate(formattedDate);
    }

    //Заполнение срока аренды
    public void fillTernRent(){
        WebElement fieldTernRent = driver.findElement(termRent);
        fieldTernRent.click();

    }
    public void selectOption(String optionText) {
        fillTernRent(); // Сначала открываем список

        List<WebElement> options = driver.findElements(dropDownList);
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(optionText)) {
                option.click();
                return;
            }
        }
    }
    //Выбор цвета самоката
    public void сhoiceColorScooter(){
        WebElement color = driver.findElement(blackScooter);
        color.click();
    }
    //Написать комментарий курьеру
    public void writeComment(String commentText){
        WebElement fieldComment = driver.findElement(comment);
        fieldComment.click();
        fieldComment.clear();
        fieldComment.sendKeys(commentText);
    }

    //Нажать кнопку "Заказать"
    public void clickButtonOrder(){
        driver.findElement(buttonOrder).click();
    }
    //Нажать "Да"
    public void clickButtonYes(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(buttonYes)).click();
    }


        public boolean isOrderSuccessDisplayed() {
            String text = driver.findElement(windowsSuccessfulOrder).getText();
            return text.contains("Заказ оформлен");
        }
    }

