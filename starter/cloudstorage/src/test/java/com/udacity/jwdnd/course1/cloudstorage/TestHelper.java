package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestHelper {

    public static void login(WebDriver driver, int port, String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + port + "/login");
        wait.until(ExpectedConditions.titleIs("Login"));
        WebElement loginForm = driver.findElement(By.id("loginForm"));
        loginForm.findElement(By.id("inputUsername")).sendKeys(username);
        loginForm.findElement(By.id("inputPassword")).sendKeys(password);
        wait.until(ExpectedConditions.attributeToBe(By.id("inputUsername"), "value", username));
        loginForm.submit();
    }
}
