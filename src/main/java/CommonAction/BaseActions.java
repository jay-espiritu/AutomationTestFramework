package CommonAction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

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



    public Boolean isDisplayed(By locator) {
        try {
            return find(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            return false;
        }
    }
    
}
