package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PasswordRecoveryModalPage {

    @FindBy(how = How.XPATH,using = "//div[@class='modal-content']//app-forgot-password")
    public WebElement recoveryModal;

    @FindBy(how = How.ID,using = "forgotPassword")
    public WebElement emailField;

    @FindBy(how = How.XPATH,using = "//button[@type='submit']")
    public WebElement submitButton;
}
