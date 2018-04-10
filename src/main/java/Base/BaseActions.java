package Base;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BaseActions {

    private WebDriver driver;

    public BaseActions(WebDriver driver) {
        this.driver = driver;
    }

    public void visit(String url) {
        driver.get(url);
    }

    public void clearCookies() {
        driver.manage().deleteAllCookies();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void navigateBackPage() {
        driver.navigate().back();
    }

    public void navigateForwardPage() {
        driver.navigate().forward();
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

    public void selectValueFromDropdown(By locator, String inputValue) {
        Select value = new Select(find(locator));
        value.selectByVisibleText(inputValue);
    }

    public void selectInputFromDropdown(By locator, int inputValue) {
        Select value = new Select(find(locator));
        value.selectByIndex(inputValue);
    }

    public void selectFromRadioButtonWithValue(By locator, String inputValue) {
        List<WebElement> checkboxValue = driver.findElements(locator);

        int checkboxSize = checkboxValue.size();

        for(int i=0; i < checkboxSize ; i++ ){
            String stringValue = checkboxValue.get(i).getAttribute(inputValue);
            if (stringValue.equalsIgnoreCase(inputValue)){
                checkboxValue.get(i).click();
                break;
            }
        }
    }

    public void selectFromRadioButton(By locator, int inputValue) {
        List<WebElement> radioButton = driver.findElements(locator);

        boolean radioStatus = false;

        radioStatus = radioButton.get(0).isSelected();

        if(radioStatus = true){
            radioButton.get(inputValue).click();
        }else{
            radioButton.get(0).click();

        }
    }
    /**
     * Assertion methods
     */
    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }



}
