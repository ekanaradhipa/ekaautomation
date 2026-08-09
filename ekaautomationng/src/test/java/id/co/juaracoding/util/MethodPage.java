package id.co.juaracoding.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MethodPage {

    public MethodPage() {
    }
    private WebElement findInputField(By locator, WebDriverWait wait) {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        if (el.getTagName().equalsIgnoreCase("input") || el.getTagName().equalsIgnoreCase("textarea")) {
            return el;
        }
        try {
            return el.findElement(By.tagName("input"));
        } catch (Exception e) {
            return el;
        }
    }

    public void typeText(By locator, String value , WebDriverWait wait) {
        if (value == null) return;
        WebElement el = findInputField(locator, wait);
        wait.until(ExpectedConditions.visibilityOf(el));
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        el.sendKeys(value);
    }

}
