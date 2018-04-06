package CommonAction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BaseActions {
    
    private WebDriver driver;
    
    public BaseActions(WebDriver driver) {
        this.driver = driver;
    }
    
    public void visit(String url) {
        driver.get(url);
    }
    
    public WebElement find(By locator) {
        return driver.findElement(locator);
    }
    
    public void click(By locator) {
        find(locator).click();
    }
    
    public void type(String inputText, By locator) {
        find(locator).sendKeys(inputText);
    }
    
    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }
    
}
