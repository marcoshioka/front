package StepDefinitions;

import Commom.Hooks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
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
import java.util.concurrent.TimeUnit;


public class AreaLogadaSteps {


    private WebDriver driver = new ChromeDriver();
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.ProductAreaPage ProductAreaPage = PageFactory.initElements(driver, PageObjects.ProductAreaPage.class);


    @After("@arealogada")
    public void afterScenario(Scenario scenario) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy h-m-s");
        Date date = new Date();
        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot_"+dateFormat.format(date)+".png"));
            //FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Pictures\\fail.png"));
        }
    }

    @After("@arealogada")
    public void close() {
        Hooks closeBrowser = new Hooks();
        closeBrowser.closeBrowser(driver);
    }


    @Dado("^que eu realize login$")
    public void queEuRealizeLogin() throws Throwable {
        driver.manage().window().maximize();
        driver.get(Hooks.urlLandingPage);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        LandingPage.enterButton.click();
        LoginModalPage.username.sendKeys(Hooks.userID);
        LoginModalPage.password.sendKeys(Hooks.userPassword);
        LoginModalPage.submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
    }

    @Quando("^eu observo o cabeçalho da área logada$")
    public void euObservoOCabeçalhoDaÁreaLogada() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.greetingsHeader.isDisplayed();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Então("^eu devo ver a mensagem de boas vindas de acordo com o horário$")
    public void euDevoVerAMensagemDeBoasVindasDeAcordoComOHorário() throws Throwable {
        Hooks welcome = new Hooks();
        welcome.welcomeMessage(driver);
    }

    @Dado("^que eu esteja na área logada$")
    public void queEuEstejaNaÁreaLogada() throws Throwable {
        Hooks login = new Hooks();
        login.linhaLogin(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
    }

    @Quando("^eu insiro um CPF$")
    public void euInsiroUmCPF() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
    }

    @Então("^eu devo ver que a máscara de CPF é aplicada$")
    public void euDevoVerQueAMáscaraDeCPFÉAplicada() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Quando("^eu insiro um CNPJ$")
    public void euInsiroUmCNPJ() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("67780279000144");
    }

    @Então("^eu devo ver que a máscara de CNPJ é aplicada$")
    public void euDevoVerQueAMáscaraDeCNPJÉAplicada() throws Throwable {
        //Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }

    @Quando("^eu realizo uma busca de um CPF pelo ícone de lupa$")
    public void euRealizoUmaBuscaDeUmCPFPeloÍconeDeLupa() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
        ProductAreaPage.searchIcon.click();
    }

    @Então("^eu devo ver que o CPF é validado pela quantidade de caracteres$")
    public void euDevoVerQueOCPFÉValidadoPelaQuantidadeDeCaracteres() throws Throwable {
        ProductAreaPage.cpfCnpjHeader.isDisplayed();
        String text = ProductAreaPage.cpfCnpjHeader.getText();
        Assert.assertEquals("Informações sobre a pessoa física", text);
    }

    @E("^se é um CPF válido$")
    public void seÉUmCPFVálido() throws Throwable {
        ProductAreaPage.cpfName.isDisplayed();
    }

    @Quando("^eu realizo uma busca de um CNPJ pelo ícone de lupa$")
    public void euRealizoUmaBuscaDeUmCNPJPeloÍconeDeLupa() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("67780279000144");
        ProductAreaPage.searchIcon.click();
    }

    @Então("^eu devo ver que o CNPJ é validado pela quantidade de caracteres$")
    public void euDevoVerQueOCNPJÉValidadoPelaQuantidadeDeCaracteres() throws Throwable {
        ProductAreaPage.cpfCnpjHeader.isDisplayed();
        String text = ProductAreaPage.cpfCnpjHeader.getText();
        Assert.assertEquals("Informações sobre a empresa", text);
    }

    @E("^se é um CNPJ válido$")
    public void seÉUmCNPJVálido() throws Throwable {
        ProductAreaPage.cnpjName.isDisplayed();
        ProductAreaPage.cnpjCompany.isDisplayed();
    }

    @Quando("^eu realizo uma busca com um CPF inválido$")
    public void euRealizoUmaBuscaComUmCPFInválido() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("1111111111");
        ProductAreaPage.searchIcon.click();
    }

    @Então("^eu devo ver uma mensagem de documento incorreto$")
    public void euDevoVerUmaMensagemDeDocumentoIncorreto() throws Throwable {
        String errorMessage = ProductAreaPage.cpfCnpjErrorMessage.getText();
        Assert.assertEquals("CPF/CNPJ inválido.", errorMessage);
    }

    @Quando("^eu realizo uma busca com um CNPJ inválido$")
    public void euRealizoUmaBuscaComUmCNPJInválido() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("1111111111111");
        ProductAreaPage.searchIcon.click();
    }

    @Quando("^eu realizo uma busca com um CPF válido$")
    public void euRealizoUmaBuscaComUmCPFVálido() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
        ProductAreaPage.searchIcon.click();
    }

    @Então("^eu devo ver o Nome Completo$")
    public void euDevoVerONomeCompleto() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        ProductAreaPage.cpfName.isDisplayed();
    }

    @Quando("^eu realizo uma busca com um CNPJ válido$")
    public void euRealizoUmaBuscaComUmCNPJVálido() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("67780279000144");
        ProductAreaPage.searchIcon.click();
    }

    @Então("^eu devo ver o Nome Fantasia$")
    public void euDevoVerONomeFantasia() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        ProductAreaPage.cnpjName.isDisplayed();
    }

    @E("^a Razão Social$")
    public void aRazãoSocial() throws Throwable {
        ProductAreaPage.cnpjCompany.isDisplayed();
    }

    @Então("^eu devo ver a listagem dos relatórios disponíveis$")
    public void euDevoVerAListagemDosRelatóriosDisponíveis() throws Throwable {
        ProductAreaPage.freeReport.isDisplayed();
        ProductAreaPage.basicReport.isDisplayed();
        ProductAreaPage.intermediateReport.isDisplayed();
        ProductAreaPage.completeReport.isDisplayed();
    }

    @Quando("^eu seleciono o relatório básico$")
    public void euSelecionoORelatórioBásico() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
        ProductAreaPage.searchIcon.click();
        ProductAreaPage.basicReportButton.click();
    }

    @Então("^eu devo ver uma sugestão para mudar para o relatório intermediário$")
    public void euDevoVerUmaSugestãoParaMudarParaORelatórioIntermediário() throws Throwable {
        ProductAreaPage.advisorReport.isDisplayed();
        String advisor = ProductAreaPage.advisorReport.getText();
        Assert.assertTrue(advisor.contains("90% das empresas como a sua, preferem relatórios que contenham protestos."));
    }

    @Quando("^eu seleciono o relatório intermediário$")
    public void euSelecionoORelatórioIntermediário() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
        ProductAreaPage.searchIcon.click();
        ProductAreaPage.intermediateReportButton.click();
    }

    @Então("^eu devo ver uma sugestão para mudar para o relatório completo$")
    public void euDevoVerUmaSugestãoParaMudarParaORelatórioCompleto() throws Throwable {
        ProductAreaPage.advisorReport.isDisplayed();
        String advisor = ProductAreaPage.advisorReport.getText();
    }


    @Quando("^eu confirmo a escolha de relatório$")
    public void euConfirmoAEscolhaDeRelatório() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("48285790010");
        ProductAreaPage.searchIcon.click();
        ProductAreaPage.basicReportButton.click();
        ProductAreaPage.confirmReportButton.click();
        ProductAreaPage.statesComboBox.click();
        ProductAreaPage.spState.click();
    }

    @Então("^eu devo ver a exibição do \"([^\"]*)\"$")
    public void euDevoVerAExibiçãoDo(String arg0) throws Throwable {
        ProductAreaPage.actualBalanceLabel.isDisplayed();
        ProductAreaPage.actualBalanceValue.isDisplayed();
        ProductAreaPage.reportNameLabel.isDisplayed();
        ProductAreaPage.negativeBalanceValue.isDisplayed();
        ProductAreaPage.finalBalanceLabel.isDisplayed();
        ProductAreaPage.finalBalanceValue.isDisplayed();
        ProductAreaPage.reportGenerateButton.isDisplayed();

    }

    @Quando("^eu confirmo a seleção do tipo de relatório$")
    public void euConfirmoASeleçãoDoTipoDeRelatório() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe(Hooks.urlProductArea));
        ProductAreaPage.cpfCnpj.sendKeys("67780279000144");
        ProductAreaPage.searchIcon.click();
        ProductAreaPage.chooseFreeReportButton.click();

    }

    @E("^aciono o botão Gerar Relatório$")
    public void acionoOBotãoGerarRelatório() throws Throwable {
        ProductAreaPage.reportGenerateButton.click();
    }

    @Então("^eu devo ser direcionado a página do relatório do documento consultado$")
    public void euDevoSerDirecionadoAPáginaDoRelatórioDoDocumentoConsultado() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.urlToBe("https://sme-credit-reports-frontend-sme-staging.apps.appcanvas.net/report-types/free"));
        //throw new PendingException();
    }
}
