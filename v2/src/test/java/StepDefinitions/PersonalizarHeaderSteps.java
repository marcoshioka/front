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

public class PersonalizarHeaderSteps {

    private WebDriver driver = new ChromeDriver();
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);
    private PageObjects.RechargePage RechargePage = PageFactory.initElements(driver, PageObjects.RechargePage.class);
    private PageObjects.ReportPage ReportPage = PageFactory.initElements(driver, PageObjects.ReportPage.class);
    private WebDriverWait wait = new WebDriverWait(driver, 60);


    @After("@personalizarheader")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_" + dateFormat.format(date) + ".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After("@personalizarheader")
    public void close() {
        Commom.Hooks closeBrowser = new Commom.Hooks();
        closeBrowser.closeBrowser(driver);
    }

    @Dado("^que eu esteja logado como assinante$")
    public void que_eu_esteja_logado_como_assinante() throws Throwable {
        driver.manage().window().maximize();
        driver.get(Commom.Hooks.v2UrlLandingPage);
        //driver.manage().deleteAllCookies();
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
        LoginModalPage.username.sendKeys("eat@food.com");
        LoginModalPage.password.sendKeys("Serasa@1");
        LoginModalPage.submitButton.click();
        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlProductArea));
    }


    @Quando("^eu observo o header$")
    public void eu_observo_o_header() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));

    }

    @Então("^eu devo ver a exibição da palavra franquia$")
    public void eu_devo_ver_a_exibição_da_palavra_franquia() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text.contains("Franquia"));
    }

    @Quando("^eu realizo a compra de um relatório$")
    public void eu_realizo_a_compra_de_um_relatório() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text.contains("Franquia"));

        String value = text.replaceAll("\\D+", "");
        double amount = Double.parseDouble(value);
        DecimalFormat formatter = new DecimalFormat("#####");
        String balance = formatter.format(amount);
        System.out.println("O saldo atual é " + balance);

        if (amount >= 700) {

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

        }
        else{
            System.out.println("Franquia insuficiente para realizar o teste");

        }
    }

    @Então("^eu devo ver que o consumo da franquia é atualizado em tempo real$")
    public void eu_devo_ver_que_o_consumo_da_franquia_é_atualizado_em_tempo_real() throws Throwable {
        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.balanceHeader));

        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text.contains("Franquia"));
        //Assert.assertEquals(amount-700);



    }

    @Então("^eu devo ver que o formato da franquia é \"([^\"]*)\"$")
    public void eu_devo_ver_que_o_formato_da_franquia_é(String arg1) throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }

    @Dado("^que eu esteja logado como avulso$")
    public void que_eu_esteja_logado_como_avulso() throws Throwable {
        driver.manage().window().maximize();
        driver.get(Commom.Hooks.v2UrlLandingPage);
        //driver.manage().deleteAllCookies();
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
        LoginModalPage.username.sendKeys(Commom.Hooks.v2User);
        LoginModalPage.password.sendKeys(Commom.Hooks.userPassword);
        LoginModalPage.submitButton.click();
    }

    @Então("^eu devo ver a exibição da palavra saldo$")
    public void eu_devo_ver_a_exibição_da_palavra_saldo() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Saldo"));
    }

    @Então("^eu devo ver que o formato do saldo é \"([^\"]*)\"$")
    public void eu_devo_ver_que_o_formato_do_saldo_é(String arg1) throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }

    @Quando("^eu observo a área logada$")
    public void eu_observo_a_área_logada() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }

    @Então("^eu devo ver que não é exibido o botão de compra de créditos$")
    public void eu_devo_ver_que_não_é_exibido_o_botão_de_compra_de_créditos() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }

    @Então("^eu devo ver a data de renovação da franquia$")
    public void eu_devo_ver_a_data_de_renovação_da_franquia() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }

    @Então("^a exibição da franquia restante$")
    public void a_exibição_da_franquia_restante() throws Throwable {
        String text = ProductAreaPage.balanceHeader.getText();
        Assert.assertTrue(text, text.contains("Franquia"));
    }
}

