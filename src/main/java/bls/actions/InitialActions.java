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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InitialActions {
    private WebDriver driver;
    public InitialActions(){
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.get("https://algeria.blsspainvisa.com");
    }
    public void login(String email) {
        this.getToInitialForm();
        //TODO: Login section
        WebElement emailInput = driver.findElement(By.name("user_email"));
        emailInput.sendKeys(email);
        WebElement continueButton = driver.findElement(By.name("continue"));
        continueButton.click();
        if(driver.getCurrentUrl().startsWith("https://algeria.blsspainvisa.com/index.php"))this.login(email);
        else System.out.println("system is opened");
//        driver.quit();
    }
    private void getToInitialForm(){
        WebElement closeDialog = this.driver.findElement(By.id("IDBodyPanel"));
        if (closeDialog.isDisplayed()) {
            WebElement closeButton = this.driver.findElement(By.className("popupCloseIcon"));
            closeButton.click();
        }
        WebElement reserveLink = this.driver.findElement(By.partialLinkText("rendez-vous"));
        reserveLink.click();
    }
    public void subscribe(String email, String phoneNumber) {
        this.getToInitialForm();
        //TODO: subscribe section
        WebElement subscribeLink = this.driver.findElement(By.linkText("Click Here"));
        subscribeLink.click();
        WebElement userNameInput = this.driver.findElement(By.name("user_name"));
        userNameInput.sendKeys("first-user");
        WebElement emailInput = this.driver.findElement(By.name("user_email"));
        emailInput.sendKeys(email);
        WebElement phoneNumberInput = this.driver.findElement(By.name("user_mobile_no"));
        phoneNumberInput.sendKeys(phoneNumber);
        WebElement locationInput = this.driver.findElement(By.name("user_location"));
        locationInput.sendKeys("oran");
        WebElement captchaImage = this.driver.findElement(By.id("captchaimg"));
        File captchaImageFile = captchaImage.getScreenshotAs(OutputType.FILE);
        WebElement refreshCaptcha = this.driver.findElement(By.cssSelector("[src='images/refresh.png']"));
        String captchaText = this.readCaptcha(captchaImageFile);
        WebElement captchaError = this.driver.findElement(By.id("errorSec"));
        WebElement captchaInput = this.driver.findElement(By.id("captcha_code"));
        captchaInput.sendKeys(captchaText);
        while (captchaText.length() < 6||captchaError.isDisplayed()) {
            refreshCaptcha.click();
            captchaInput.clear();
            captchaImageFile = captchaImage.getScreenshotAs(OutputType.FILE);
            captchaText = this.readCaptcha(captchaImageFile);
        captchaInput.sendKeys(captchaText);
        }
        WebElement register = this.driver.findElement(By.name("register"));
        register.click();
        //TODO: Verify mail with OTP

//        this.driver.quit();
    }

    public String readCaptcha(File captchaFile) {
        try {
            String path = "/home/abdelwahab/Desktop/FirstBotDemo/resources/" + Math.random() + ".png";
            FileHandler.copy(captchaFile, new File(path));
            this.normaliseCaptchaImage(path);
            ITesseract tesseract = new Tesseract();
            tesseract.setDatapath("/home/abdelwahab/Desktop/FirstBotDemo/tessdata");
            String captchaText = "";
            captchaText = tesseract.doOCR(new File(path));
            captchaText = captchaText.replaceAll("[^a-zA-Z0-9]", "");
            System.out.println("captcha :" + captchaText);
            return captchaText.toLowerCase();
        } catch (TesseractException e) {
            System.out.println("exception went with do OCR ");
            System.out.println("error message: " + e.getMessage());
            return "";

        } catch (IOException e) {
            System.out.println("exception went with copying file");
            System.out.println("error message: " + e.getMessage());
            return "";
        }

    }

    private void normaliseCaptchaImage(String path) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    //get pixel value
                    int p = bufferedImage.getRGB(j, i);
                    //get alpha
                    int a = (p >> 24) & 0xff;
                    //get red
                    int r = (p >> 16) & 0xff;
                    //get green
                    int g = (p >> 8) & 0xff;
                    //get blue
                    int b = p & 0xff;
                    if (this.isBlue(r, g, b)) {
                        r = 0;
                        g = 0;
                        b = 0;
                    } else {
                        r = g = b = 255;
                    }
                    p = (a << 24) | (r << 16) | (g << 8) | b;
                    bufferedImage.setRGB(j, i, p);

                }

            }
            ImageIO.write(bufferedImage, "png", new File(path));
        } catch (IOException e) {
            System.out.println("error occurred in reading image in path " + path);
            System.out.println(" error message is: " + e.getMessage());
        }
    }

    private boolean isBlue(int red, int green, int blue) {
        return (Math.max(red * 2, blue) == blue && Math.max(green, blue) == blue && blue > 100);
    }
}
