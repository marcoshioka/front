package StepDefinitions;

import Commom.Hooks;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OneClickBuyAssinanteSteps {

    private WebDriver driver = new ChromeDriver();
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);
    private PageObjects.RechargePage RechargePage = PageFactory.initElements(driver, PageObjects.RechargePage.class);
    private PageObjects.ReportPage ReportPage = PageFactory.initElements(driver, PageObjects.ReportPage.class);
    private WebDriverWait wait = new WebDriverWait(driver, 60);

    @After("@oneclickbuyassinante")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_" + dateFormat.format(date) + ".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After("@oneclickbuyassinante")
    public void close() {
        Commom.Hooks closeBrowser = new Commom.Hooks();
        closeBrowser.closeBrowser(driver);
    }

        @Dado("^que eu esteja logado como usuário assinante$")
        public void que_eu_esteja_logado_como_usuário_assinante() throws Throwable {
            driver.manage().window().maximize();
            driver.get(Commom.Hooks.v2UrlLandingPage);
            wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
            LandingPage.enterButton.click();
            LoginModalPage.username.sendKeys(Hooks.v2SubscriberUser);
            LoginModalPage.password.sendKeys(Hooks.v2SubscribePassword);
            LoginModalPage.submitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
        }

        @Dado("^que esteja com a franquia zerada$")
        public void que_esteja_com_a_franquia_zerada() throws Throwable {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
            String text = ProductAreaPage.balanceHeader.getText();

            try {
                Assert.assertEquals("Franquia R$ 0,00", text);
                System.out.println("Franquia zerada");
            } catch (AssertionError e) {

                System.out.println("Franquia não está zerada. Iniciando método para zerar.");
                String value = text.replaceAll("\\D+", "");
                double amount = Double.parseDouble(value);
                DecimalFormat formatter = new DecimalFormat("##,##");
                String balance = formatter.format(amount);
                System.out.println("A franquia atual é " + balance);


                if (amount < 700) {
                    System.out.println("Valor é menor que R$ 7,00.");
                    ProductAreaPage.rechargeLink.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                    double difference = 700 - amount;
                    String rest = formatter.format(difference);
                    RechargePage.customValue.sendKeys(rest);
                    System.out.println("O valor a ser recarregado é " + rest);

                    RechargePage.creditCardNumber.sendKeys("4539672659584401");
                    RechargePage.expiration.sendKeys("102020");
                    RechargePage.cvv.sendKeys("842");
                    RechargePage.cardHolder.sendKeys("Visa Teste");
                    RechargePage.saveCard.click();
                    RechargePage.submitButton.click();
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                    RechargePage.newSearch.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 7,00"));
                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    ProductAreaPage.basicReportButton.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                    ProductAreaPage.buttonNoThanks.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");


                }

                if (amount == 700) {
                    System.out.println("Valor é igual R$ 7,00");
                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    ProductAreaPage.basicReportButton.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                    ProductAreaPage.buttonNoThanks.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");

                }

                if (amount > 700 && amount < 1400) {
                    System.out.println("Valor é maior que R$ 7,00 e menor que R$ 14,00");
                    ProductAreaPage.rechargeLink.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                    double difference = 1400 - amount;
                    String rest = formatter.format(difference);
                    RechargePage.customValue.sendKeys(rest);
                    System.out.println("O valor a ser recarregado é " + rest);

                    RechargePage.creditCardNumber.sendKeys("4539672659584401");
                    RechargePage.expiration.sendKeys("102020");
                    RechargePage.cvv.sendKeys("842");
                    RechargePage.cardHolder.sendKeys("Visa Teste");
                    RechargePage.saveCard.click();
                    RechargePage.submitButton.click();
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                    RechargePage.newSearch.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 14,00"));
                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    ProductAreaPage.intermediateReportButton.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                    ProductAreaPage.buttonNoThanks.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.statesComboBox));
                    ProductAreaPage.statesComboBox.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.spState));
                    ProductAreaPage.spState.click();
                    //ProductAreaPage.spState.sendKeys(Keys.ENTER);
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");


                }

                if (amount == 1400) {
                    System.out.println("Valor é igual a R$ 14,00");

                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    ProductAreaPage.intermediateReportButton.click();
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                    ProductAreaPage.buttonNoThanks.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.statesComboBox));
                    ProductAreaPage.statesComboBox.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.spState));
                    ProductAreaPage.spState.click();
                    //ProductAreaPage.spState.sendKeys(Keys.ENTER);
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");

                }

                if (amount > 1400 && amount < 3500) {
                    System.out.println("Valor é maior que R$ 14,00 e menor que R$ 35,00");

                    ProductAreaPage.rechargeLink.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                    double difference = 3500 - amount;
                    String rest = formatter.format(difference);
                    RechargePage.customValue.sendKeys(rest);
                    System.out.println("O valor a ser recarregado é " + rest);

                    RechargePage.creditCardNumber.sendKeys("4539672659584401");
                    RechargePage.expiration.sendKeys("102020");
                    RechargePage.cvv.sendKeys("842");
                    RechargePage.cardHolder.sendKeys("Visa Teste");
                    RechargePage.saveCard.click();
                    RechargePage.submitButton.click();
                    wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                    RechargePage.newSearch.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 35,00"));
                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                    ProductAreaPage.completeReportButton.click();
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");

                }

                if (amount == 3500) {
                    System.out.println("Valor é igual a R$ 35,00");

                    ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                    ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                    ProductAreaPage.searchIcon.click();

                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                    //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                    ProductAreaPage.completeReportButton.click();
                    Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                    ProductAreaPage.confirmButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                    ReportPage.newEnquiry.click();
                    wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                    ReportPage.exitButton.click();
                    wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                    driver.navigate().refresh();
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                    System.out.println("Franquia zerada com sucesso");

                }

                if (amount>3500) {

                    do {
                        System.out.println("Valor é maior que R$ 35,00");

                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.cpfCnpj));
                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                        ProductAreaPage.completeReportButton.click();
                        Thread.sleep(2000);
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        //driver.navigate().refresh();
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.balanceHeader));
                        String newText = ProductAreaPage.balanceHeader.getText();
                        String newValue = newText.replaceAll("\\D+", "");
                        double newAmount = Double.parseDouble(newValue);
                        DecimalFormat newFormatter = new DecimalFormat("##,##");
                        String newBalance = newFormatter.format(newAmount);
                        System.out.println("A franquia agora é R$ " + newBalance);

                    } while ((Double.parseDouble(ProductAreaPage.balanceHeader.getText().replaceAll("\\D+", "")))>3500);

                    String finalText = ProductAreaPage.balanceHeader.getText();
                    String finalValue = finalText.replaceAll("\\D+", "");
                    double finalAmount = Double.parseDouble(finalValue);
                    DecimalFormat finalFormatter = new DecimalFormat("##,##");
                    String finalBalance = finalFormatter.format(finalAmount);
                    double finalDifference = 3500 - finalAmount;
                    String finalRest = formatter.format(finalDifference);


                    if (finalAmount < 700) {
                        System.out.println("Valor é menor que R$ 7,00.");
                        ProductAreaPage.rechargeLink.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                        double difference = 700 - finalAmount;
                        String rest = formatter.format(difference);
                        System.out.println("O valor a ser recarregado é " + rest);
                        RechargePage.customValue.sendKeys(rest);


                        RechargePage.creditCardNumber.sendKeys("4539672659584401");
                        RechargePage.expiration.sendKeys("102020");
                        RechargePage.cvv.sendKeys("842");
                        RechargePage.cardHolder.sendKeys("Visa Teste");
                        RechargePage.saveCard.click();
                        RechargePage.submitButton.click();
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                        RechargePage.newSearch.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 7,00"));
                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        ProductAreaPage.basicReportButton.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                        ProductAreaPage.buttonNoThanks.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");


                    }

                    if (finalAmount == 700) {
                        System.out.println("Valor é igual R$ 7,00");
                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        ProductAreaPage.basicReportButton.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                        ProductAreaPage.buttonNoThanks.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");

                    }

                    if (finalAmount > 700 && finalAmount < 1400) {
                        System.out.println("Valor é maior que R$ 7,00 e menor que R$ 14,00");
                        ProductAreaPage.rechargeLink.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                        double difference = 1400 - finalAmount;
                        String rest = formatter.format(difference);
                        System.out.println("O valor a ser recarregado é " + rest);
                        RechargePage.customValue.sendKeys(rest);

                        RechargePage.creditCardNumber.sendKeys("4539672659584401");
                        RechargePage.expiration.sendKeys("102020");
                        RechargePage.cvv.sendKeys("842");
                        RechargePage.cardHolder.sendKeys("Visa Teste");
                        RechargePage.saveCard.click();
                        RechargePage.submitButton.click();
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                        RechargePage.newSearch.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 14,00"));
                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        ProductAreaPage.intermediateReportButton.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                        ProductAreaPage.buttonNoThanks.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.statesComboBox));
                        ProductAreaPage.statesComboBox.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.spState));
                        ProductAreaPage.spState.click();
                        //ProductAreaPage.spState.sendKeys(Keys.ENTER);
                        Thread.sleep(2000);
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");


                    }

                    if (finalAmount == 1400) {
                        System.out.println("Valor é igual a R$ 14,00");

                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        ProductAreaPage.intermediateReportButton.click();
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
                        ProductAreaPage.buttonNoThanks.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.statesComboBox));
                        ProductAreaPage.statesComboBox.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.spState));
                        ProductAreaPage.spState.click();
                        //ProductAreaPage.spState.sendKeys(Keys.ENTER);
                        Thread.sleep(2000);
                        wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");

                    }

                    if (finalAmount > 1400 && finalAmount < 3500) {
                        System.out.println("Valor é maior que R$ 14,00 e menor que R$ 35,00");

                        ProductAreaPage.rechargeLink.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlRecharge));
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.creditsTitle));
                        double difference = 3500 - finalAmount;
                        String rest = formatter.format(difference);
                        System.out.println("O valor a ser recarregado é " + rest);
                        RechargePage.customValue.sendKeys(rest);

                        RechargePage.creditCardNumber.sendKeys("4539672659584401");
                        RechargePage.expiration.sendKeys("102020");
                        RechargePage.cvv.sendKeys("842");
                        RechargePage.cardHolder.sendKeys("Visa Teste");
                        RechargePage.saveCard.click();
                        RechargePage.submitButton.click();
                        wait.until(ExpectedConditions.visibilityOf(RechargePage.newSearch));
                        RechargePage.newSearch.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 35,00"));
                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                        ProductAreaPage.completeReportButton.click();
                        Thread.sleep(2000);
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");

                    }

                    if (finalAmount == 3500) {
                        System.out.println("Valor é igual a R$ 35,00");

                        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                        ProductAreaPage.searchIcon.click();

                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                        //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                        ProductAreaPage.completeReportButton.click();
                        Thread.sleep(2000);
                        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
                        ProductAreaPage.confirmButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
                        ReportPage.newEnquiry.click();
                        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
                        ReportPage.exitButton.click();
                        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

                        driver.navigate().refresh();
                        wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Franquia R$ 0,00"));
                        System.out.println("Franquia zerada com sucesso");

                    }

                }
            }
        }

        @Quando("^eu tento realizar a compra de um novo relatório$")
        public void eu_tento_realizar_a_compra_de_um_novo_relatório() throws Throwable {
            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            ProductAreaPage.completeReportButton.click();
            //Thread.sleep(2000);

        }

        @Então("^eu devo ver a opção de one click buy para assinante$")
        public void eu_devo_ver_a_opção_de_one_click_buy_para_assinante() throws Throwable {

            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.oneClickBuyButton));
            String buttonText = ProductAreaPage.oneClickBuyButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
        }

        @Quando("^eu tento realizar uma compra de um relatório via One Click buy$")
        public void eu_tento_realizar_uma_compra_de_um_relatório_via_One_Click_buy() throws Throwable {
            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            ProductAreaPage.completeReportButton.click();
        }

        @Então("^eu devo ver a informação de que as consultas serão cobradas direto no cartão cadastrado$")
        public void eu_devo_ver_a_informação_de_que_as_consultas_serão_cobradas_direto_no_cartão_cadastrado() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Quando("^eu realizo uma compra de relatório via One Click buy$")
        public void eu_realizo_uma_compra_de_relatório_via_One_Click_buy() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Então("^eu devo ver no histórico do assinante a compra com o devido formato$")
        public void eu_devo_ver_no_histórico_do_assinante_a_compra_com_o_devido_formato() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Quando("^eu pesquiso o documento \"([^\"]*)\" para compra One Click buy assinante$")
        public void eu_pesquiso_o_documento_para_compra_One_Click_buy_assinante(String arg1) throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }

        @Então("^eu devo ver que o relatório foi apresentado corretamente para o assinante$")
        public void eu_devo_ver_que_o_relatório_foi_apresentado_corretamente_para_o_assinante() throws Throwable {
            // Write code here that turns the phrase above into concrete actions
            throw new PendingException();
        }


}
