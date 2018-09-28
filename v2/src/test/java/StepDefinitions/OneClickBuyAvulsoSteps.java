package StepDefinitions;

import Commom.Hooks;
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


@SuppressWarnings("LoopConditionNotUpdatedInsideLoop")
public class OneClickBuyAvulsoSteps {

    private WebDriver driver = new ChromeDriver();
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);
    private PageObjects.RechargePage RechargePage = PageFactory.initElements(driver, PageObjects.RechargePage.class);
    private PageObjects.ReportPage ReportPage = PageFactory.initElements(driver, PageObjects.ReportPage.class);
    private WebDriverWait wait = new WebDriverWait(driver, 60);
    private boolean aBoolean;


    @After("@oneclickbuyavulso")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_" + dateFormat.format(date) + ".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After("@oneclickbuyavulso")
    public void close() {
        Hooks closeBrowser = new Hooks();
        closeBrowser.closeBrowser(driver);
    }

    @Dado("^que eu esteja logado como usuário avulso$")
    public void que_eu_esteja_logado_como_usuário_avulso() throws Throwable {

        driver.manage().window().maximize();
        driver.get(Hooks.v2UrlLandingPage);
        //driver.manage().deleteAllCookies();
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
        LoginModalPage.username.sendKeys(Hooks.v2User);
        LoginModalPage.password.sendKeys(Hooks.userPassword);
        LoginModalPage.submitButton.click();
        //wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

    }


    @Dado("^que esteja com o saldo zerado$")
    public void que_esteja_com_o_saldo_zerado() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
        String text = ProductAreaPage.balanceHeader.getText();

        try {
            Assert.assertEquals("Saldo R$ 0,00", text);
            System.out.println("Saldo zerado");
        } catch (AssertionError e) {

            System.out.println("Saldo não está zerado. Iniciando método para zerar.");
            String value = text.replaceAll("\\D+", "");
            double amount = Double.parseDouble(value);
            DecimalFormat formatter = new DecimalFormat("##,##");
            String balance = formatter.format(amount);
            System.out.println("O saldo atual é " + balance);


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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 7,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");


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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 14,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");


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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 35,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                    System.out.println("O saldo agora é R$ " + newBalance);

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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 7,00"));
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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");


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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");

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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 14,00"));
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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");


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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");

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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 35,00"));
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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");

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
                    wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                    System.out.println("Saldo zerado com sucesso");

                }

            }
        }
    }





    @Quando("^eu tento realizar a compra de um relatório$")
    public void eu_tento_realizar_a_compra_de_um_relatório() throws Throwable {

        ProductAreaPage.cpfCnpj.sendKeys("00000000272");
        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
        ProductAreaPage.searchIcon.click();

        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
        //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
        ProductAreaPage.completeReportButton.click();
        Thread.sleep(2000);

    }

    @Então("^eu devo ver a opção de one click buy$")
    public void eu_devo_ver_a_opção_de_one_click_buy() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
        String buttonText = ProductAreaPage.confirmButton.getText();
        Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

    }

    @Quando("^eu tento realizar uma compra via One Click buy$")
    public void euTentoRealizarUmaCompraViaOneClickBuy() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
        String text = ProductAreaPage.balanceHeader.getText();

        String value = text.replaceAll("\\D+", "");
        double amount = Double.parseDouble(value);
        DecimalFormat formatter = new DecimalFormat("##,##");
        String balance = formatter.format(amount);
        System.out.println("O saldo atual é " + balance);

        if (amount < 700) {

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            ProductAreaPage.basicReportButton.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
            ProductAreaPage.buttonNoThanks.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.oneClickBuyButton));
            String buttonText = ProductAreaPage.oneClickBuyButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);


        }

        if (amount == 700) {

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
            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.oneClickBuyButton));
            String buttonText = ProductAreaPage.oneClickBuyButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount > 700 && amount < 1400) {

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
            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.oneClickBuyButton));
            String buttonText = ProductAreaPage.oneClickBuyButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount == 1400) {
            System.out.println("Valor é igual a R$ 14,00");

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount > 1400 && amount < 3500) {
            System.out.println("Valor é maior que R$ 14,00 e menor que R$ 35,00");

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.oneClickBuyButton));
            String buttonText = ProductAreaPage.oneClickBuyButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount == 3500) {
            System.out.println("Valor é igual a R$ 35,00");


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

                driver.navigate().refresh();
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("O saldo agora é R$ " + value);

                ProductAreaPage.cpfCnpj.sendKeys("00000000272");
                ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
                ProductAreaPage.searchIcon.click();

                wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
                //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
                ProductAreaPage.completeReportButton.click();
                Thread.sleep(2000);
                wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.oneClickBuyButton));
                String buttonText = ProductAreaPage.oneClickBuyButton.getText();
                Assert.assertEquals("Finalizar e Gerar relatório", buttonText);


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
                System.out.println("O saldo agora é R$ " + newBalance);

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 7,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");


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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 14,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");


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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 35,00"));
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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

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
                wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("Saldo zerado com sucesso");

            }

        }
    }

    @Então("^eu devo ver informação de que as consultas serão cobradas direto no cartão cadastrado$")
    public void eu_devo_ver_informação_de_que_as_consultas_serão_cobradas_direto_no_cartão_cadastrado() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.oneClickBuyButton));
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.insufficientBalanceAlert));
        String alertContentText = ProductAreaPage.insufficientBalanceAlert.getText();
        System.out.println(alertContentText);
        //Assert.assertEquals("Saldo insuficiente para este relatório.", titleText);
        //String contentText = ProductAreaPage.insufficientBalanceAlert.getText();
        Assert.assertEquals(" Saldo insuficiente para este relatório."+ "\n" + "Caso queira comprá-lo, serão debitados " + ProductAreaPage.plusBalance.getText().substring(1) + " do seu cartão de crédito." + "\n" , alertContentText);

    }

    @Quando("^eu realizo uma compra via One Click buy$")
    public void euRealizoUmaCompraViaOneClickBuy() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
        String text = ProductAreaPage.balanceHeader.getText();

        String value = text.replaceAll("\\D+", "");
        double amount = Double.parseDouble(value);
        DecimalFormat formatter = new DecimalFormat("##,##");
        String balance = formatter.format(amount);
        System.out.println("O saldo atual é " + balance);

        if (amount < 700) {

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            ProductAreaPage.basicReportButton.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
            ProductAreaPage.buttonNoThanks.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));


        }

        if (amount == 700) {

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
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

        }

        if (amount > 700 && amount < 1400) {

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
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

        }

        if (amount == 1400) {
            System.out.println("Valor é igual a R$ 14,00");

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

        }

        if (amount > 1400 && amount < 3500) {
            System.out.println("Valor é maior que R$ 14,00 e menor que R$ 35,00");

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));

        }

        if (amount == 3500) {
            System.out.println("Valor é igual a R$ 35,00");

            do {
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

                driver.navigate().refresh();
                //wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("O saldo agora é R$ " + value);
            } while (amount > 3500);

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));


        }

        if (amount > 3500) {
            System.out.println("Valor é maior que R$ 35,00");

            do {
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

                driver.navigate().refresh();
                //wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("O saldo agora é R$ " + value);
            } while (amount > 3500);

            ProductAreaPage.cpfCnpj.sendKeys("00000000272");
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
            ProductAreaPage.confirmButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));

            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));
            ReportPage.newEnquiry.click();
            wait.until(ExpectedConditions.elementToBeClickable(ReportPage.exitButton));
            ReportPage.exitButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
        }
    }

    @Então("^eu devo ver no histórico a compra com o devido formato$")
    public void eu_devo_ver_no_histórico_a_compra_com_o_devido_formato() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Quando("^eu pesquiso o documento \"([^\"]*)\" para compra One Click buy$")
    public void euPesquisoODocumentoParaCompraOneClickBuy(String arg1) throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
        String text = ProductAreaPage.balanceHeader.getText();

        String value = text.replaceAll("\\D+", "");
        double amount = Double.parseDouble(value);
        DecimalFormat formatter = new DecimalFormat("##,##");
        String balance = formatter.format(amount);
        System.out.println("O saldo atual é " + balance);

        if (amount < 700) {

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            ProductAreaPage.basicReportButton.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
            wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.buttonNoThanks));
            ProductAreaPage.buttonNoThanks.click();
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);


        }

        if (amount == 700) {

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
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
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount > 700 && amount < 1400) {

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
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
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount == 1400) {
            System.out.println("Valor é igual a R$ 14,00");

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount > 1400 && amount < 3500) {
            System.out.println("Valor é maior que R$ 14,00 e menor que R$ 35,00");

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);

        }

        if (amount == 3500) {
            System.out.println("Valor é igual a R$ 35,00");

            do {
                wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.cpfCnpj));
                ProductAreaPage.cpfCnpj.sendKeys(arg1);
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
                //wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("O saldo agora é R$ " + value);
            } while (amount > 3500);

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);


        }

        if (amount > 3500) {
            System.out.println("Valor é maior que R$ 35,00");

            do {
                wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.cpfCnpj));
                ProductAreaPage.cpfCnpj.sendKeys(arg1);
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
                //wait.until(ExpectedConditions.textToBePresentInElement(ProductAreaPage.balanceHeader, "Saldo R$ 0,00"));
                System.out.println("O saldo agora é R$ " + value);
            } while (amount > 3500);

            ProductAreaPage.cpfCnpj.sendKeys(arg1);
            ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
            ProductAreaPage.searchIcon.click();

            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
            //wait.until(ExpectedConditions.elementToBeClickable(ProductAreaPage.completeReportButton));
            ProductAreaPage.completeReportButton.click();
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
            String buttonText = ProductAreaPage.confirmButton.getText();
            Assert.assertEquals("Finalizar e Gerar relatório", buttonText);
        }
    }




    @Então("^eu devo ver que o relatório foi apresentado corretamente$")
    public void euDevoVerQueORelatórioFoiApresentadoCorretamente() throws Throwable {

        ProductAreaPage.confirmButton.click();
        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlReport));
        wait.until(ExpectedConditions.elementToBeClickable(ReportPage.newEnquiry));


    }
}
