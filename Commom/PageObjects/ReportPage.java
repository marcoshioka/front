package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ReportPage {

    @FindBy(how = How.XPATH,using = "//h2[contains(.,'Dados Cadastrais')]]")
    public WebElement freeReportTitle;

    @FindBy(how = How.XPATH,using = "//h3[@class='report-header__section__subtitle']")
    public WebElement personType;

    @FindBy(how = How.CSS,using = "#content>app-report-header>div>div.report-header__section>div:nth-child(1)>div>button:nth-child(2)")
    public WebElement newEnquiry;

    @FindBy(how = How.XPATH,using = "//button[contains(text(),'Sair')]")
    public WebElement exitButton;



}
