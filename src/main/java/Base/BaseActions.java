package Base;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    protected void NavigateTo(String url) {
        driver.get(url);
    }

    protected void ClearCookies() {
        driver.manage().deleteAllCookies();
    }

    protected void RefreshPage() {
        driver.navigate().refresh();
    }

    protected void NavigateBackPage() {
        driver.navigate().back();
    }

    protected void NavigateForwardPage() {
        driver.navigate().forward();
    }

    protected WebElement Find(By locator) {
        return driver.findElement(locator);
    }

    protected void Clicked(By locator) {
        Find(locator).click();
    }

    protected void EnterText(String inputText, By locator) {
        Find(locator).sendKeys(inputText);
    }

    protected void SelectValueFromDropdown(By locator, String inputValue) {
        Select value = new Select(Find(locator));
        value.selectByVisibleText(inputValue);
    }

    protected void SelectInputFromDropdown(By locator, int inputValue) {
        Select value = new Select(Find(locator));
        value.selectByIndex(inputValue);
    }

    protected void SelectFromRadioButtonWithValue(By locator, String inputValue) {
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

    protected void SelectFromRadioButton(By locator, int inputValue) {
        List<WebElement> radioButton = driver.findElements(locator);
        
        boolean radioStatus = false;
        
        radioStatus = radioButton.get(0).isSelected();
        
        if (radioStatus = true) {
            radioButton.get(inputValue).click();
        } else {
            radioButton.get(0).click();
        }
    }

    protected void UploadFile(By UploadLocator, By SubmitLocator, String fileName, String filePath) {
        File file = new File(fileName);
        filePath = file.getAbsolutePath();
        EnterText(filePath, UploadLocator);
        Clicked(SubmitLocator);
    }

    protected void DownloadFile() throws IOException {
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

    protected void DragAndDrop(By locatorElement, By locatorTarget) {
        WebElement from = Find(locatorElement);
        WebElement to = Find(locatorTarget);
        (new Actions(driver)).dragAndDrop(from, to).perform();
    }

    protected void ImplicitWaitFor(int value) {
        driver.manage().timeouts().implicitlyWait(value, TimeUnit.SECONDS);
    }

    
    protected void ExplicitWaitFor(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void SleepFor(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wait commands/methods
     */
    protected void WaitTillElementVisible(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
           // wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    protected void WaitTillElementIsInvisible(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

    protected void WaitTillElementIsClickable(By locator, int timeout)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }


    /**
     * Assertion methods
     */
    protected Boolean isDisplayed(By locator) {
        try {
            return Find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    protected Boolean isSelected(By locator) {
        try {
            return Find(locator).isSelected();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    protected Boolean isEnabled(By locator) {
        try {
            return Find(locator).isEnabled();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }

    protected void AssertText(By locator, String expected) {
        assertEquals(expected, Find(locator).getText());
    }

    /**
     * JavaScript commands/methods
     */
    protected void ExecuteJavaScript(String scriptToExectue)
    {
        try
        {
            JavascriptExecutor js =(JavascriptExecutor) driver;
            if(js instanceof WebDriver) {
                js.executeScript(scriptToExectue);
            }
        } catch (IllegalStateException error)
        {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }

    protected void JavaScriptClick(WebElement element)
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e)
        {
           System.out.println("An exception occurred performing JavaScript click");
        }
    }

    protected void ScrollToView(WebElement element)
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e)
        {
            System.out.println("An exception occurred performing JavaScript scroll");
        }
    }

    protected void ScrollToBottomPage()
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e)
        {
            System.out.println("An exception occurred performing JavaScript scroll to the bottom of the page");
        }
    }

    protected void ScrollToTopPage()
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, 0);");
        } catch (Exception e)
        {
            System.out.println("An exception occurred performing JavaScript scroll to the bottom of the page");
        }
    }

    protected void ScrollToBottomPageSlowly(int value)
    {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            for (int second = 0; ; second++)
            {
                if (second >= value)
                {
                    break;
                }
                js.executeScript("window.scrollBy(0,2000)");
                SleepFor(3000);
            }
        } catch (Exception e)
        {
            System.out.println("An exception occurred performing JavaScript scroll to the bottom of the page");
        }
    }
}
