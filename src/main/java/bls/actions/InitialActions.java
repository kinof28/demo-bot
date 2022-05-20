package bls.actions;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class InitialActions {
    public static void start() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://algeria.blsspainvisa.com");
        WebElement closeDialog = driver.findElement(By.id("IDBodyPanel"));
        if (closeDialog.isDisplayed()) {
            WebElement closeButton = driver.findElement(By.className("popupCloseIcon"));
            closeButton.click();
        }
        WebElement reserveLink = driver.findElement(By.partialLinkText("rendez-vous"));
        reserveLink.click();
        //TODO: subscribe section
//        WebElement subscribeLink = driver.findElement(By.linkText("Click Here"));
//        subscribeLink.click();
        //TODO: Login section
        WebElement emailInput = driver.findElement(By.name("user_email"));
        emailInput.sendKeys("ab28fb@gmail.com");
        WebElement continueButton = driver.findElement(By.name("continue"));
        continueButton.click();
//        driver.quit();
    }

    public void subscribe(String email, String phoneNumber) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://algeria.blsspainvisa.com");
        WebElement closeDialog = driver.findElement(By.id("IDBodyPanel"));
        if (closeDialog.isDisplayed()) {
            WebElement closeButton = driver.findElement(By.className("popupCloseIcon"));
            closeButton.click();
        }
        WebElement reserveLink = driver.findElement(By.partialLinkText("rendez-vous"));
        reserveLink.click();
        //TODO: subscribe section
        WebElement subscribeLink = driver.findElement(By.linkText("Click Here"));
        subscribeLink.click();
        WebElement userNameInput = driver.findElement(By.name("user_name"));
        userNameInput.sendKeys("first-user");
        WebElement emailInput = driver.findElement(By.name("user_email"));
        emailInput.sendKeys(email);
        WebElement phoneNumberInput = driver.findElement(By.name("user_mobile_no"));
        phoneNumberInput.sendKeys(phoneNumber);
        WebElement locationInput = driver.findElement(By.name("user_location"));
        locationInput.sendKeys("oran");
        WebElement captchaImage = driver.findElement(By.id("captchaimg"));
        File captchaImageFile = captchaImage.getScreenshotAs(OutputType.FILE);
        try {
            String path="/home/abdelwahab/Desktop/FirstBotDemo/resources/newFile.BMP";
            FileHandler.copy(captchaImageFile, new File(path));
            Thread.sleep(1000);
//            File copiedImage=new File("newFile.png");
            ITesseract tesseract = new Tesseract();
            String captchaText = tesseract.doOCR(new File(path));
            System.out.println(captchaText);
            WebElement captchaInput = driver.findElement(By.id("captcha_code"));
            captchaInput.sendKeys(captchaText);
        } catch (TesseractException e) {
            System.out.println("exception went with do OCR ");
            System.out.println("error message: " + e.getMessage());
        } catch(IOException e){
            System.out.println("exception went with copying file");
            System.out.println("error message: " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        driver.quit();
    }
}
