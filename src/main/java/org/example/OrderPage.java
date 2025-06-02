package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;

    //Поле Имя
    private By name = By.xpath(".//input[@placeholder='* Имя']");
    //Поле Фамилия
    private By surname = By.xpath(".//input[@placeholder='* Фамилия']");
    //Поле Адресс
    private By adress = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле Станция Метро
    private By metro = By.xpath(".//input[@placeholder='* Станция метро']");
    // Поле Телефон
    private By telephone = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка далее
    private By buttonNext = By.xpath(".//button[text()='Далее']");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillName(String nameUser) {
        WebElement fieldName = driver.findElement(name);
        fieldName.click();
        fieldName.clear();
        fieldName.sendKeys(nameUser);
    }

    public void fillSurname(String surnameUser) {
        WebElement fieldSurname = driver.findElement(surname);
        fieldSurname.click();
        fieldSurname.clear();
        fieldSurname.sendKeys(surnameUser);
    }

    public void fillAdress(String adressUser) {
        WebElement fieldAdres = driver.findElement(adress);
        fieldAdres.click();
        fieldAdres.clear();
        fieldAdres.sendKeys(adressUser);
    }

    public void fillMetro(String metroUser) {
        WebElement fieldMetro = driver.findElement(metro);
        fieldMetro.click();
        fieldMetro.clear();
        fieldMetro.sendKeys(metroUser);
        driver.findElement(By.xpath("//li[contains(@class, 'select-search__row')][1]")).click();

    }

    public void fillTelephone(String numbrUser) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement fieldTelephone = wait.until(ExpectedConditions.visibilityOfElementLocated(telephone));
        fieldTelephone.clear();
        fieldTelephone.sendKeys(numbrUser);
    }

    public void clickButtonNext(){
        driver.findElement(buttonNext).click();
    }

}