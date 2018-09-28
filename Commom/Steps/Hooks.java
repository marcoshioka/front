package Commom;


import cucumber.api.Scenario;
import cucumber.api.java.Before;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Hooks {

    private WebDriver driver;
    private Scenario scenario;

    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);

    //Objetos comnuns utilizados em diversas classes do projeto
    public static String userID = "qa.consultaserasa@outlook.com";
    public static String v2User = "marcelofilipemonteiro@owlymail.com";
    public static String v2CallCenterUser = "coder.marcos@gmail.com";
    public static String v2CallCenterUserPassword = "Serasa@123";
    public static String v2SubscriberUser= "marcelofilipemonteiro@owlymail.com";
    public static String userPassword = "#Teste123";
    public static String v2SubscribePassword = "#Teste123";
    public static String v2UrlLandingPage = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net";
    public static String urlLandingPage = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net";
    public static String v2UrlProductArea = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/consulta";
    public static String urlProductArea = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/consulta";
    public static String urlReport = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/relatorio";
    public static String v2UrlReport = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/relatorio";
    public static String v2UrlRecharge = "https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/creditos";
    public static String v2CallCenter = "https://v2-frontend-reports-sme-staging.apps.appcanvas.net/call-center/login";
    public static String v2UrlCallCenterSearch = "https://v2-frontend-reports-sme-staging.apps.appcanvas.net/call-center/search";
    public static String v2UrlCallCenterHistory = "https://v2-frontend-reports-sme-staging.apps.appcanvas.net/call-center/history";

    @Before("@autenticacao, @arealogada, @botaonovaconsulta, @gratuito, @teste, @oneclickbuyavulso, @personalizarheader, @oneclickbuyassinante, @callcenterdadosusuario")
    public void beforeScenario () {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Selenium\\drivers\\chromedriver_win32\\chromedriver.exe");
    }


    //Método responsável por fechar o browser, no fim da execução do teste
    public void closeBrowser(WebDriver driver) {
        if (driver != null) {
            driver.quit();
        }
    }


    //Método responsável por realizar login no Serasa Linha
    public void linhaLogin (WebDriver driver) {
        driver.manage().window().maximize();
        driver.get(urlLandingPage);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
        LoginModalPage.username.sendKeys(userID);
        LoginModalPage.password.sendKeys(userPassword);
        LoginModalPage.submitButton.click();
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
    }

    //Método responsável por validar mensagem de boas vindas na área logada, de acordo com o horário.
    public void welcomeMessage(WebDriver driver) throws Throwable {
        WebElement TxtContent = driver.findElement(By.xpath("//div[@class='report-types__greetings']"));
        LocalTime dt = new LocalTime();  // current time
        System.out.println(dt);
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date six = parser.parse("06:00");
        Date twentyThreeFiftyEight = parser.parse("23:58");
        Date twentyThreeFiftyNine = parser.parse("23:59");
        //Date midnight = parser.parse("00:00");
        Date elevenFiftyNine = parser.parse("11:59");
        Date noon = parser.parse("12:00");
        Date seventeenFiftyNine = parser.parse("17:59");
        Date eighteen = parser.parse("18:00");
        Date fiveFiftyNine = parser.parse("05:59");

        Date userDate = parser.parse(String.valueOf(dt));

        if (userDate.after(fiveFiftyNine) && userDate.before(noon)) {
            Assert.assertEquals("Bom dia, QA", TxtContent.getText());
        }

        if (userDate.after(elevenFiftyNine) && userDate.before(eighteen)) {
            Assert.assertEquals("Boa tarde, QA", TxtContent.getText());
        }

        if (userDate.after(seventeenFiftyNine) && userDate.before(twentyThreeFiftyNine)||
                userDate.after(twentyThreeFiftyEight) && userDate.before(six)) {
            Assert.assertEquals("Boa noite, QA", TxtContent.getText());
        }


    }



}
