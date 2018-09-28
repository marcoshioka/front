package StepDefinitions;

import Commom.Hooks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class BotaoNovaConsultaSteps {

    private WebDriver driver = new ChromeDriver();
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);


    @After("@botaonovaconsulta")
    public void afterScenario(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot.png"));
        }
    }

    @After("@botaonovaconsulta")
    public void closeBrowser() {
        if(driver!=null) {
            driver.quit();
        }
    }

    @Before ("@titulodorelatorio")
    public void userLogin(){
        Hooks login = new Hooks();
        login.linhaLogin(driver);
    }


    @Dado("^que eu realize a consulta do documento \"([^\"]*)\"$")
    public void que_eu_realize_a_consulta_do_documento(String arg1) throws Throwable {

        WebDriverWait wait = new WebDriverWait(driver, 90);
        wait.until(ExpectedConditions.urlToBe("https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/report-types"));
        ProductAreaPage.cpfCnpj.sendKeys(arg1);
        ProductAreaPage.searchIcon.click();
    }

    @Dado("^gere um relatório \"([^\"]*)\"$")
    public void gere_um_relatório(String arg1) throws Throwable {

        driver.findElement(By.xpath("//div[@class='report-type-card-container__title'][contains(text(),arg1)]")).click();
        WebElement free = driver.findElement(By.xpath("//div[@class='report-type-card-container__title'][contains(text(),arg1)]"));
        free.findElement(By.xpath("//*[contains(text(),'Escolher')]")).click();
        WebElement freeReport = driver.findElement(By.xpath("//*[contains(text(),'Escolher')]"));
        freeReport.findElement(By.xpath("//div[@class='report-type-card-container__title'][contains(text(),arg1)]")).click();
        driver.findElement(By.cssSelector("button[class = 'report-type-card-container__button'][contains(text(),arg1)]")).click();

    }

    @Quando("^eu observo o cabeçalho do relatório$")
    public void eu_observo_o_cabeçalho_do_relatório() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Então("^eu devo ver que o título \"([^\"]*)\" é apresentado$")
    public void eu_devo_ver_que_o_título_é_apresentado(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }


}

