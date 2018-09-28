package StepDefinitions;

import Commom.Hooks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CallCenterDadosUsuarioSteps {

    private WebDriver driver = new ChromeDriver();
    private PageObjects.CallCenterLoginPage CallCenterLoginPage = PageFactory.initElements(driver, PageObjects.CallCenterLoginPage.class);
    private PageObjects.CallCenterSearchPage CallCenterSearchPage = PageFactory.initElements(driver, PageObjects.CallCenterSearchPage.class);
    private PageObjects.CallCenterHistoryPage CallCenterHistoryPage = PageFactory.initElements(driver, PageObjects.CallCenterHistoryPage.class);
    private WebDriverWait wait = new WebDriverWait(driver, 60);

    @After("@callcenterdadosusuario")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_" + dateFormat.format(date) + ".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After(value = "@callcenterdadosusuario", order = 1)
    public void close() {
        Hooks closeBrowser = new Hooks();
        closeBrowser.closeBrowser(driver);
    }

    @Dado("^que eu esteja logado como atendente do Call Center$")
    public void que_eu_esteja_logado_como_atendente_do_Call_Center() throws Throwable {

            driver.manage().window().maximize();
            driver.get(Hooks.v2CallCenter);
            wait.until(ExpectedConditions.urlToBe(Hooks.v2CallCenter));
            CallCenterLoginPage.username.sendKeys(Hooks.v2CallCenterUser);
            CallCenterLoginPage.password.sendKeys(Hooks.v2CallCenterUserPassword);
            CallCenterLoginPage.loginButton.click();
            wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlCallCenterSearch))    ;

    }

    @Quando("^eu realizo a consulta dos dados de um usuário$")
    public void eu_realizo_a_consulta_dos_dados_de_um_usuário() throws Throwable {

        CallCenterSearchPage.cnpjSearch.sendKeys("84603174000139");
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterSearchPage.searchButton));
        CallCenterSearchPage.cnpjSearch.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterSearchPage.card));
        CallCenterSearchPage.firstCard.click();
        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlCallCenterHistory));

    }

    @Então("^eu devo ver o customer ID$")
    public void eu_devo_ver_o_customer_ID() throws Throwable {

        wait.until(ExpectedConditions.visibilityOf(CallCenterHistoryPage.nameID));
        String nameID = CallCenterHistoryPage.nameID.getText();
        System.out.println(nameID);

    }

    @Então("^o Order ID$")
    public void o_Order_ID() throws Throwable {
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterHistoryPage.tableBody));
        CallCenterHistoryPage.orderTab.click();
        Thread.sleep(3000);
        String firstOrderID = CallCenterHistoryPage.firstOrderID.getText();
        System.out.println("\n" + firstOrderID);

    }

    @Então("^o tipo de pedido na tela de histórico$")
    public void o_tipo_de_pedido_na_tela_de_histórico() throws Throwable {
        String firstOrderType = CallCenterHistoryPage.firstOrderType.getText();
        System.out.println("\n" + firstOrderType);
    }

    @Quando("^eu realizo a consulta dos dados de um usuário no histórico$")
    public void eu_realizo_a_consulta_dos_dados_de_um_usuário_no_histórico() throws Throwable {
        CallCenterSearchPage.cnpjSearch.sendKeys("84603174000139");
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterSearchPage.searchButton));
        CallCenterSearchPage.cnpjSearch.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterSearchPage.card));
        CallCenterSearchPage.firstCard.click();
        wait.until(ExpectedConditions.urlToBe(Hooks.v2UrlCallCenterHistory));

        wait.until(ExpectedConditions.visibilityOf(CallCenterHistoryPage.nameID));
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterHistoryPage.tableBody));
        CallCenterHistoryPage.orderTab.click();
        //Thread.sleep(3000);

    }

    @Então("^eu devo ver que as informações retornadas estejam todas em português$")
    public void eu_devo_ver_que_as_informações_retornadas_estejam_todas_em_português() throws Throwable {
        //driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(CallCenterHistoryPage.tableBody));
        WebElement table = driver.findElement(By.xpath("//table[@class='history-table table table-striped']"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        List<WebElement> th=table.findElements(By.tagName("th"));
        int col_position=0;
        for(int i=0;i<th.size();i++){
            if("Método de pagamento".equalsIgnoreCase(th.get(i).getText())){
                col_position=i+1;
                //break;
            }
        }

        List<WebElement> FirstColumns = table.findElements(By.xpath("//tr/td["+col_position+"]"));
        for(WebElement e: FirstColumns){
            System.out.println(e.getText());
            String payWay = e.getText();
            System.out.println(payWay);
           // Assert.assertEquals("Cartão de crédito", payWay);
            //assertThat(payWay, anyOf(is("xerxes"), is("hopus")));

        }




    }
}
