package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class RechargePage {

    @FindBy(how = How.XPATH,using = "//div[@class='credits__header']")
    public WebElement creditsTitle;

    @FindBy(how = How.ID,using = "customValue")
    public WebElement customValue;

    @FindBy(how = How.ID,using = "creditCardNumber")
    public WebElement creditCardNumber;

    @FindBy(how = How.ID,using = "expiration")
    public WebElement expiration;

    @FindBy(how = How.ID,using = "cvv")
    public WebElement cvv;

    @FindBy(how = How.ID,using = "cardholder")
    public WebElement cardHolder;

    @FindBy(how = How.ID,using = "saveCard")
    public WebElement saveCard;

    @FindBy(how = How.XPATH,using = "//button[@type='submit']")
    public WebElement submitButton;

    @FindBy(how = How.CSS,using = "body>app-root>app-layout>div>app-credits>section>div:nth-child(3)>app-regiter-success>section>div>div.register-container__section__button>button")
    public  WebElement newSearch;

}
