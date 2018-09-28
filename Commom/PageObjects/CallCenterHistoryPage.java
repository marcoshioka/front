package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class CallCenterHistoryPage {

    @FindBy(how = How.XPATH,using = "//span[@class='name-id']")
    public WebElement nameID;

    @FindBy(how = How.ID,using = "ngb-tab-1")
    public WebElement orderTab;

    @FindBy(how = How.XPATH, using = "//tbody//tr[1]//td[1]//span[1]")
    public WebElement firstOrderID;

    @FindBy(how = How.XPATH, using = "//table[@class='history-table table table-striped']")
    public WebElement tableBody;

    @FindBy(how = How.XPATH, using = "//tbody//tr[1]//td[3]//span[1]")
    public WebElement firstOrderType;


}
