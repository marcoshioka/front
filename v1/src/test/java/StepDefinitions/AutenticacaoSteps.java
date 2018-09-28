package StepDefinitions;

import Commom.Hooks;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.pt.*;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;


public class AutenticacaoSteps {

    private WebDriver driver = new ChromeDriver();
    private WebDriverWait wait = new WebDriverWait(driver, 25);
    private PageObjects.LandingPage LandingPage = PageFactory.initElements(driver, PageObjects.LandingPage.class);
    private PageObjects.LoginModalPage LoginModalPage = PageFactory.initElements(driver, PageObjects.LoginModalPage.class);
    private PageObjects.PasswordRecoveryModalPage PasswordRecoveryModalPage = PageFactory.initElements(driver, PageObjects.PasswordRecoveryModalPage.class);

    @After("@autenticacao")
    public void afterScenario(Scenario scenario) throws IOException {

        if (scenario.isFailed()) {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File("C:\\Users\\cwg0660\\Downloads\\screenshot.png"));
        }
    }

    @After("@autenticacao")
    public void closeBrowser() {
        if(driver!=null) {
            driver.quit();
        }
    }

    @Dado("^que eu esteja navegando no Serasa Linha$")
    public void queEuEstejaNavegandoNoSerasaLinha() throws Throwable {
        driver.manage().window().maximize();
        driver.get(Hooks.urlLandingPage);
    }

    @Quando("^eu visualizo o header do site$")
    public void euVisualizoOHeaderDoSite() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.header.isDisplayed();
    }

    @Quando("^eu aciono o botão Entrar no header do site$")
    public void euAcionoOBotãoEntrarNoHeaderDoSite() throws Throwable {
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
    }

    @Então("^eu devo ver o botão entrar$")
    public void euDevoVerOBotãoEntrar() throws Throwable {
        LandingPage.enterButton.isDisplayed();
    }

    @Então("^eu devo ver um modal com o \"([^\"]*)\"$")
    public void euDevoVerUmModalComO(String arg0) {
        LoginModalPage.username.isDisplayed();
        LoginModalPage.password.isDisplayed();
        LoginModalPage.passwordVisible.isDisplayed();
        LoginModalPage.forgotPassword.isDisplayed();
        LoginModalPage.submitButton.isDisplayed();
        LoginModalPage.closeButton.isDisplayed();
    }

    @Dado("^que eu acionei o botão Entrar no Serasa Linha$")
    public void queEuAcioneiOBotãoEntrarNoSerasaLinha() throws Throwable {
        driver.manage().window().maximize();
        driver.get(Hooks.urlLandingPage);
        wait.until(ExpectedConditions.visibilityOf(LandingPage.header));
        LandingPage.enterButton.click();
    }

    @Quando("^eu aciono o ícone de visualização de senha no modal$")
    public void euAcionoOÍconeDeVisualizaçãoDeSenhaNoModal() throws Throwable {
        LoginModalPage.password.sendKeys("Teste123");
        LoginModalPage.passwordVisible.click();
    }

    @Então("^eu devo ver os caracteres que forem digitados nesse campo$")
    public void euDevoVerOsCaracteresQueForemDigitadosNesseCampo() throws IOException {
        WebElement TxtBoxContent = driver.findElement(By.id("password"));
        //WebElement TxtBoxContent = LoginModalPage.password.isDisplayed();
        System.out.println(TxtBoxContent.getAttribute("value"));
        Assert.assertEquals("Teste123", TxtBoxContent.getAttribute("value"));
    }


    @Quando("^eu aciono o link Esqueci minha senha no modal$")
    public void euAcionoOLinkEsqueciMinhaSenhaNoModal() throws Throwable {
        LoginModalPage.forgotPassword.click();
    }

    @Então("^eu devo ver um modal onde digite o e-mail do cadastro$")
    public void euDevoVerUmModalOndeDigiteOEMailDoCadastro() throws Throwable {
        PasswordRecoveryModalPage.recoveryModal.isDisplayed();
        PasswordRecoveryModalPage.emailField.sendKeys("marcos.tester@gmail.com");
    }

    @E("^receba um e-mail contendo o link para registro de nova senha$")
    public void recebaUmEMailContendoOLinkParaRegistroDeNovaSenha() throws Throwable {
        PasswordRecoveryModalPage.submitButton.click();
    }

    @Quando("^eu utilizo um usuário \"([^\"]*)\" no modal$")
    public void euUtilizoUmUsuárioNoModal(String arg0) throws Throwable {
        //driver.findElement(By.id("username")).sendKeys(arg0);
        LoginModalPage.username.sendKeys(arg0);
    }

    @E("^uma senha \"([^\"]*)\"$")
    public void umaSenha(String arg0) throws Throwable {
        //driver.findElement(By.id("password")).sendKeys(arg0);
        LoginModalPage.password.sendKeys(arg0);
    }

    @E("^aciono o botão Acessar$")
    public void acionoOBotãoAcessar() throws Throwable {
        //driver.findElement(By.xpath("//button[@type='submit']")).click();
        LoginModalPage.submitButton.click();
    }

    @Então("^eu devo ver a mensagem \"([^\"]*)\"$")
    public void euDevoVerAMensagem(String arg0) throws Throwable {
        //Assert.assertEquals(arg 0, texto.getResult());
    }

    @Quando("^eu preencho o campo usuário corretamente no modal$")
    public void euPreenchoOCampoUsuárioCorretamenteNoModal() throws Throwable {
        LoginModalPage.username.sendKeys("marcos.tester@gmail.com");
    }

    @E("^o campo de senha também corretamente$")
    public void oCampoDeSenhaTambémCorretamente() throws Throwable {
        LoginModalPage.password.sendKeys("#Teste123");
    }

    @Então("^eu devo ser direcionado para a área logada$")
    public void euDevoSerDirecionadoParaAÁreaLogada() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Mas("^o serviço retorne algo diferente de sucesso/ insucesso$")
    public void oServiçoRetorneAlgoDiferenteDeSucessoInsucesso() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    }

    @Então("^eu devo ver a mensagem de alerta Ops! Serviço indisponível no momento. Tente novamente mais tarde.$")
    public void euDevoVerAMensagemDeAlertaOpsServiçoIndisponívelNoMomentoTenteNovamenteMaisTarde() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    }
}



