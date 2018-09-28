package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CallCenterLoginPage {


    @FindBy(how = How.ID,using = "username")
    public WebElement username;

    @FindBy(how = How.ID,using = "password")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    public WebElement loginButton;


}
