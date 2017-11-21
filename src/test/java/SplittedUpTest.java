import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by main on 21.11.17.
 */
public class SplittedUpTest {

    WebDriver driver;

    @Before
    public void ConfigureWebDriver(){
        System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.wiley.com/WileyCDA");
    }

    @Test
    public void action1(){

        WebElement navigationMenu = driver.findElement(By.id("links-site"));

        Assert.assertTrue(navigationMenu.findElement(By.linkText("Home")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Subjects")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("About Wiley")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Contact Us")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Help")).isDisplayed());

    }

    @Test
    public void action2(){

        WebElement resources = driver.findElement(By.id("homepage-links"));
        List<WebElement> linkList = resources.findElements(By.tagName("a"));

        Assert.assertEquals(9,linkList.size());

        List<String> linkTextList = new ArrayList<>();

        for (WebElement el : linkList) {
            linkTextList.add(el.getText());
        }

        Assert.assertTrue(linkTextList.contains("Students"));
        Assert.assertTrue(linkTextList.contains("Authors"));
        Assert.assertTrue(linkTextList.contains("Instructors"));
        Assert.assertTrue(linkTextList.contains("Librarians"));
        Assert.assertTrue(linkTextList.contains("Societies"));
        Assert.assertTrue(linkTextList.contains("Conferences"));
        Assert.assertTrue(linkTextList.contains("Booksellers"));
        Assert.assertTrue(linkTextList.contains("Corporations"));
        Assert.assertTrue(linkTextList.contains("Institutions"));

    }

    @Test
    public void actionOf3To6() {

        WebElement students = driver.findElement(By.id("homepage-links")).findElement(By.linkText("Students"));
        students.click();

        //action 3
        String needUrl = "http://www.wiley.com/WileyCDA/Section/id-404702.html";
        String realUrl = "http://eu.wiley.com/WileyCDA/Section/id-404702.html";
        Assert.assertNotEquals(needUrl, driver.getCurrentUrl());
        Assert.assertEquals(realUrl, driver.getCurrentUrl());

        WebElement header = driver.findElement(By.id("page-title")).findElement(By.tagName("h1"));

        Assert.assertEquals("Students", header.getText());
        Assert.assertTrue(header.isDisplayed());

        // 4 action
        WebElement sidebar = driver.findElement(By.id("sidebar"));
        List<WebElement> items = sidebar.findElements(By.xpath(".//ul[@class=\"autonavLevel1\"]/li/a|.//ul[@class=\"autonavLevel1\"]/li/span"));
        Assert.assertNotEquals(8,items.size());
        Assert.assertEquals(7,items.size());

        List<String> strings = new ArrayList<>();
        WebElement studentsItem = null;
        for (WebElement el : items){
            Assert.assertTrue(el.isDisplayed());
            if (el.getText().equals("Students")){
                studentsItem = el;
            }
            strings.add(el.getText());
        }

        Assert.assertTrue(strings.contains("Authors"));
        Assert.assertTrue(strings.contains("Librarians"));
        Assert.assertTrue(strings.contains("Booksellers"));
        Assert.assertTrue(strings.contains("Instructors"));
        Assert.assertTrue(strings.contains("Students"));
        Assert.assertTrue(strings.contains("Societies"));
        Assert.assertFalse(strings.contains("Goverment Employees"));
        Assert.assertTrue(strings.contains("Corporate Partners"));

        //action5
        String colorItem = "";
        String borderLeftStyle = "";
        String borderLeftWidth = "";

        if (studentsItem != null) {
            colorItem = studentsItem.getCssValue("color");
            borderLeftStyle = studentsItem.getCssValue("border-left-style");
            borderLeftWidth = studentsItem.getCssValue("border-left-width");
        }

        Assert.assertEquals("rgba(2, 95, 98, 1)", colorItem);
        Assert.assertEquals("7px", borderLeftWidth);
        Assert.assertEquals("solid", borderLeftStyle);

        Assert.assertNotEquals("a", studentsItem.getTagName());

        //action6
        WebElement home = driver.findElement(By.id("links-site")).findElement(By.linkText("Home"));
        home.click();


    }

    @Test
    public void actionOf7To8(){
        WebElement emailAddress = driver.findElement(By.id("EmailAddress"));
        WebElement button = driver.findElement(By.id("id31"));
        //action7
        button.click();
        try {
            Alert alert = driver.switchTo().alert();
            Assert.assertEquals("Please enter email address", alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.assertEquals(0,1);
        }
        //action8
        emailAddress.sendKeys("srdkr2yandex.ru");
        button.click();
        try {
            Alert alert = driver.switchTo().alert();
            Assert.assertEquals("Invalid email address.", alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.assertEquals(0,1);
        }
    }

    @Test
    public void actionOf9To12(){
        //action9
        WebElement linksSite = driver.findElement(By.id("links-site"));
        WebElement searchInput = linksSite.findElement(By.id("query"));
        WebElement searchButton = linksSite.findElement(By.className("icon"));
        searchInput.sendKeys("for dummies");
        searchButton.click();
        List<WebElement> itemList = driver.findElements(By.className("product-title"));
        Assert.assertTrue(itemList.size() > 0);

        //action10
        Random random = new Random();
        int i = random.nextInt(itemList.size());
        WebElement item = itemList.get(i);
        WebElement link = item.findElement(By.tagName("a"));
        String title = link.getText();
        link.click();
        String pageHeader = driver.findElement(By.className("productDetail-title")).getText();
        Assert.assertEquals(title,pageHeader);

        //action11
        WebElement home = driver.findElement(By.id("links-site")).findElement(By.linkText("Home"));
        home.click();

        //action12
        String currentHandle = driver.getWindowHandle();
        WebElement resources = driver.findElement(By.id("homepage-links"));
        WebElement institutes = resources.findElement(By.linkText("Institutions"));
        institutes.click();
        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles){
            if (!handle.equals(currentHandle)){
                driver.switchTo().window(handle);
            }
        }

        Assert.assertNotEquals("http://wileyedsolutions.com/", driver.getCurrentUrl());
        Assert.assertEquals("https://edservices.wiley.com/", driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(currentHandle);
    }

    @After
    public void CloseBrowser(){
        driver.close();
    }
}
