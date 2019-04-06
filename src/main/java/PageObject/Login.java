package PageObject;

import Base.BaseActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static org.junit.Assert.assertTrue;

public class Login extends BaseActions {

    @FindBy(how = How.ID, using  ="login")
    private WebElement loginFormLocator;
    private By byLoginFormLocator;

    private By usernameLocator  = By.id("username");
    private By passwordLocator  = By.id("password");
    private By submitButton     = By.cssSelector("button");
    private By successMessageLocator = By.cssSelector(".flash.success");
    private By failureMessageLocator = By.cssSelector(".flash.error");
    
    public Login(WebDriver driver) {
        super(driver);
        NavigateTo("http://the-internet.herokuapp.com/login");
        assertTrue("The login form is not present",
                isDisplayed(byLoginFormLocator));
    }
    
    public void with(String username, String password) {
        EnterText(username, byLoginFormLocator);
        EnterText(password, passwordLocator);
        Clicked(submitButton);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public Boolean successMessagePresent() {
        return isDisplayed(successMessageLocator);
    }
    
    public Boolean failureMessagePresent() {
        return isDisplayed(failureMessageLocator);
    }
}
