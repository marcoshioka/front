package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProductAreaPage {

    @FindBy(how = How.XPATH,using = "//p[@class='main-header__data-person__saldo']")
    public WebElement balanceHeader;

    @FindBy(how = How.XPATH,using = "//div[@class='report-types__greetings']")
    public WebElement greetingsHeader;

    @FindBy(how = How.ID,using = "documentType")
    public WebElement cpfCnpj;

    @FindBy(how = How.XPATH,using = "//button[@type='submit']")
    public WebElement searchIcon;

    @FindBy(how = How.XPATH,using = "//div[@class='document-type-selection-container__target-wrapper__heading']")
    public WebElement cpfCnpjHeader;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-selection-container__target-wrapper__subheading']")
    public WebElement cpfName;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-selection-container__target-wrapper__subheading'][contains(text(),'Nome Fantasia')]")
    public WebElement cnpjName;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-selection-container__target-wrapper__subheading'][contains(text(),'Razão Social')]")
    public WebElement cnpjCompany;

    @FindBy(how = How.ID,using = "documentTypeError")
    public WebElement cpfCnpjErrorMessage;

    @FindBy(how = How.XPATH,using = "//div[@class='document-type-validation-container__target-wrapper__heading']")
    public WebElement pfInformation;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-card-container__title'][contains(text(),'Grátis')]")
    public WebElement freeReport;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-card-container__title'][contains(text(),'Básico')]")
    public WebElement basicReport;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-card-container__title'][contains(text(),'Intermediário')]")
    public WebElement intermediateReport;

    @FindBy(how = How.XPATH,using = "//div[@class='report-type-card-container__title'][contains(text(),'Completo')]")
    public WebElement completeReport;

    @FindBy(how = How.CSS,using = "body>app-root>app-layout>div>app-report-types>section>div>div.report-types__step2__document-type-form>app-report-type-options>div>div:nth-child(2)>app-report-type-card>div>div:nth-child(4)>button")
    public WebElement basicReportButton;

    @FindBy(how = How.CLASS_NAME,using = "report-type-selection-container__advisory")
    public WebElement advisorReport;

    @FindBy(how = How.CSS,using = "body>app-root>app-layout>div>app-report-types>section>div>div.report-types__step2__document-type-form>app-report-type-options>div>div:nth-child(3)>app-report-type-card>div>div:nth-child(4)>button")
    public WebElement intermediateReportButton;

    @FindBy(how = How.XPATH,using = "//button[@class='report-type-advisory-container__button report-type-advisory-container__highlighted']")
    public WebElement confirmReportButton;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label'][contains(text(),'Saldo Atual')]")
    public WebElement actualBalanceLabel;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label-highlight']")
    public WebElement actualBalanceValue;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label'][contains(text(),'Intermediário')]")
    public WebElement reportNameLabel;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label-warn']")
    public WebElement negativeBalanceValue;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label-plus']")
    public WebElement finalBalanceLabel;

    @FindBy(how = How.XPATH,using = "//span[@class='report-type-confirm-container__confirm-wrapper__label-warn-plus']")
    public WebElement finalBalanceValue;

    @FindBy(how = How.XPATH,using = "//button[@class='report-type-confirm-container__confirm-wrapper__button']")
    public WebElement reportGenerateButton;

    @FindBy(how = How.XPATH,using = "/html/body/app-root/app-layout/div/app-report-types/section/div/div/app-report-type-selection/div/app-report-type-options/div/div[1]/app-report-type-card/div/div[4]/button")
    public WebElement chooseFreeReportButton;

    @FindBy(how = How.XPATH,using = "/html/body/app-root/app-layout/div/app-report-types/section/div/div[7]/app-report-type-advisory/div/div/div[2]/button[2]")
    public WebElement buttonNoThanks;

    @FindBy(how = How.XPATH,using = "//button[@class='report-type-confirm-container__confirm-wrapper__button report-type-confirm-container__confirm-wrapper__button-enabled']")
    public WebElement confirmButton;

    @FindBy(how = How.XPATH,using = "//a[@routerlink='/creditos']")
    public WebElement rechargeLink;

    @FindBy (how = How.XPATH, using = "//select[@class='report-types__states']")
    public WebElement statesComboBox;

    @FindBy (how = How.XPATH, using = "//option[contains(text(),'SP')]")
    public WebElement spState;

    @FindBy(how = How.CSS,using = "body>app-root>app-layout>div>app-report-types>section>div>div.report-types__step2__document-type-form>app-report-type-options>div>div:nth-child(4)>app-report-type-card>div>div:nth-child(4)>button")
    public WebElement completeReportButton;

    @FindBy (how= How.XPATH, using="//button[@class='report-type-confirm-container__confirm-wrapper__button report-type-confirm-container__confirm-wrapper__button-enabled']")
    public WebElement oneClickBuyButton;

    @FindBy (how= How.XPATH, using="//div[@class='report-type-confirm-container__insufficient-balance']")
    public WebElement insufficientBalanceAlert;

    @FindBy (how = How.CSS, using= "span.report-type-confirm-container__insufficient-balance-highlight")
    public WebElement balanceAlertTitle;

    @FindBy (how = How.CSS, using= "span.report-type-confirm-container__confirm-wrapper__label-warn-plus")
    public WebElement plusBalance;

}
