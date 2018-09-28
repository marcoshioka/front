package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginModalPage {

    @FindBy(how = How.ID,using = "username")
    public WebElement username;

    @FindBy(how = How.ID,using = "password")
    public WebElement password;

    @FindBy(how = How.XPATH, using = "//i[@class='fa fa-lg fa-eye']")
    public WebElement passwordVisible;

    @FindBy(how = How.CSS, using = "div.modal-login__container__body__lostPassword")
    public WebElement forgotPassword;

    @FindBy(how = How.CSS, using = "body>ngb-modal-window>div>div>app-login>div>div.modal-body.modal-login__container__body>form>button")
    public WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//a[@aria-label='Close']//i[@aria-hidden='true']")
    public WebElement closeButton;

}
