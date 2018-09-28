package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LandingPage {

    @FindBy(how = How.XPATH,using = "//button[contains(.,'Entrar')]")
    public WebElement enterButton;

    @FindBy(how = How.XPATH,using = "//header[@class='main-header']")
    public WebElement header;
}
