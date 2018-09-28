package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CallCenterSearchPage {

    @FindBy(how = How.ID,using = "CnpjSearch")
    public WebElement cnpjSearch;

    @FindBy(how = How.XPATH,using = "//button[@class='btn btn-primary btn-search']")
    public WebElement searchButton;

    @FindBy(how = How.CSS,using = "div.card-body")
    public WebElement card;

    @FindBy(how = How.XPATH,using = "//ul[@class='search-result']//li[1]//div[1]//div[1]//button[1]")
    public WebElement firstCard;



}
