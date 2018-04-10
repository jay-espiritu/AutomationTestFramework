package Base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
        
        for (int i = 0; i < checkboxSize; i++) {
            String stringValue = checkboxValue.get(i).getAttribute(inputValue);
            if (stringValue.equalsIgnoreCase(inputValue)) {
                checkboxValue.get(i).click();
                break;
            }
        }
    }
    
    public void selectFromRadioButton(By locator, int inputValue) {
        List<WebElement> radioButton = driver.findElements(locator);
        
        boolean radioStatus = false;
        
        radioStatus = radioButton.get(0).isSelected();
        
        if (radioStatus = true) {
            radioButton.get(inputValue).click();
        } else {
            radioButton.get(0).click();
        }
    }
    
    public void uploadFile(By UploadLocator, By SubmitLocator, String fileName, String filePath) {
        File file = new File(fileName);
        filePath = file.getAbsolutePath();
        type(filePath, UploadLocator);
        click(SubmitLocator);
    }
    
    public void downloadFile() throws IOException {
        String link = driver.findElement(By.cssSelector(".example a:nth-of-type(1)")).
                                                                                             getAttribute("href");
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHead request = new HttpHead(link);
        HttpResponse response = httpClient.execute(request);
        String contentType = response.getFirstHeader("Content-Type").getValue();
        int contentLength = Integer.parseInt(response.getFirstHeader("Content-Length").getValue
                                                                                               ());
        assertThat(contentType, is("application/octet-stream"));
        assertThat(contentLength, is(not(0)));
    }
    
    public void dragAndDrop(By locatorElement, By locatorTarget) {
        WebElement from = find(locatorElement);
        WebElement to = find(locatorTarget);
        (new Actions(driver)).dragAndDrop(from, to).perform();
    }
    
    public void implicitWaitFor(int value) {
        driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);
    }
    
    public void explicitWaitFor(By locator, int value) {
        WebDriverWait wait = new WebDriverWait(driver, value);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public void sleepFor(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
    
    public Boolean isSelected(By locator) {
        try {
            return find(locator).isSelected();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }
    
    public Boolean isEnabled(By locator) {
        try {
            return find(locator).isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }
    
    public void assertText(By locator, String expected) {
        assertEquals(expected, find(locator).getText());
    }
    
    

}
