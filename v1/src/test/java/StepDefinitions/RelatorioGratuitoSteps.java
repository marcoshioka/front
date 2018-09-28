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
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelatorioGratuitoSteps {



    private WebDriver driver = new ChromeDriver();
    private WebDriverWait wait = new WebDriverWait(driver, 20);
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);
    private PageObjects.ReportPage ReportPage = PageFactory.initElements(driver, PageObjects.ReportPage.class);


    @After("@gratuito")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_" + dateFormat.format(date) + ".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After(value = "@gratuito", order = 1)
    public void close() {
        Hooks closeBrowser = new Hooks();
        closeBrowser.closeBrowser(driver);
    }

    @Dado("^que eu esteja logado$")
    public void que_eu_esteja_logado() throws Throwable {
        driver.manage().deleteAllCookies();
        Hooks login = new Hooks();
        login.linhaLogin(driver);
    }

    @Dado("^que eu solicite um relatório gratuito de pessoa física com o documento \"([^\"]*)\"$")
    public void que_eu_solicite_um_relatório_gratuito_de_pessoa_física_com_o_documento(String arg1) throws Throwable {
        ProductAreaPage.cpfCnpj.sendKeys(arg1);
        ProductAreaPage.cpfCnpj.sendKeys(Keys.TAB);
        ProductAreaPage.searchIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.pfInformation));
        ProductAreaPage.basicReportButton.click();
        Thread.sleep(2000);
        //wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.buttonNoThanks));
        ProductAreaPage.buttonNoThanks.click();
        Thread.sleep(2000);
        //wait.until(ExpectedConditions.visibilityOf(ProductAreaPage.confirmButton));
        ProductAreaPage.confirmButton.click();
        Thread.sleep(10000);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlReport));
    }

    /*@Quando("^eu observo o header do relatório com o título \"([^\"]*)\"$")
    public void euObservoOHeaderDoRelatórioComOTítulo(String arg0) throws Throwable {
        driver.getTitle().length();
        String text = ReportPage.freeReportTitle.getText();
        Assert.assertEquals(arg0, text);
        ReportPage.freeReportTitle.click();
    }*/

    @Então("^eu devo ver o CPF \"([^\"]*)\"$")
    public void euDevoVerOCPF(String arg0) throws Throwable {
        String text = ReportPage.freeReportTitle.getText();
        Assert.assertEquals(arg0, text);
        ReportPage.freeReportTitle.click();
    }

    @Quando("^eu observo o header do relatório com o título \"([^\"]*)\"$")
    public void eu_observo_o_header_do_relatório_com_o_título(String arg1) throws Throwable {
        Thread.sleep(3000);
        String text = ReportPage.freeReportTitle.getText();
        Assert.assertEquals(arg1, text);
        ReportPage.freeReportTitle.click();

        String reportTitle = ReportPage.freeReportTitle.getText();
        Assert.assertEquals("Dados Cadastrais", reportTitle);
    }


}