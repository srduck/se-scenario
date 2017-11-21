import org.junit.Assert;
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
public class ThroughTest {
    @Test
    public void scenario(){

        System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.wiley.com/WileyCDA");
        //start of action_1 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement navigationMenu = driver.findElement(By.id("links-site"));

        //start of check_1 ----------------------------------------------
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Home")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Subjects")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("About Wiley")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Contact Us")).isDisplayed());
        Assert.assertTrue(navigationMenu.findElement(By.linkText("Help")).isDisplayed());

        //end of action_1 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_2 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement resources = driver.findElement(By.id("homepage-links"));
        List<WebElement> linkList = resources.findElements(By.tagName("a"));

        List<String> linkTextList = new ArrayList<>();

        for (WebElement el : linkList) {
            linkTextList.add(el.getText());
        }

        //start of check_1 ----------------------------------------------
        Assert.assertEquals(9,linkList.size());

        //start of check_2 ----------------------------------------------
        Assert.assertTrue(linkTextList.contains("Students"));
        Assert.assertTrue(linkTextList.contains("Authors"));
        Assert.assertTrue(linkTextList.contains("Instructors"));
        Assert.assertTrue(linkTextList.contains("Librarians"));
        Assert.assertTrue(linkTextList.contains("Societies"));
        Assert.assertTrue(linkTextList.contains("Conferences"));
        Assert.assertTrue(linkTextList.contains("Booksellers"));
        Assert.assertTrue(linkTextList.contains("Corporations"));
        Assert.assertTrue(linkTextList.contains("Institutions"));

        //end of action_2 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_3 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement students = resources.findElement(By.linkText("Students"));
        students.click();

        //start of check_1 ----------------------------------------------
        String needUrl = "http://www.wiley.com/WileyCDA/Section/id-404702.html";
        String realUrl = "http://eu.wiley.com/WileyCDA/Section/id-404702.html";
        Assert.assertNotEquals(needUrl, driver.getCurrentUrl());
        Assert.assertEquals(realUrl, driver.getCurrentUrl());
        //start of check_2 ----------------------------------------------
        WebElement header = driver.findElement(By.id("page-title")).findElement(By.tagName("h1"));
        Assert.assertEquals("Students", header.getText());
        Assert.assertTrue(header.isDisplayed());

        //end of action_3 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_4 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement sidebar = driver.findElement(By.id("sidebar"));
        List<WebElement> items = sidebar.findElements(By.xpath(".//ul[@class=\"autonavLevel1\"]/li/a|.//ul[@class=\"autonavLevel1\"]/li/span"));

        //start of check_1 ----------------------------------------------
        Assert.assertNotEquals(8,items.size());
        Assert.assertEquals(7,items.size());

        //start of check_2 ----------------------------------------------
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
        Assert.assertTrue(strings.contains("Corporate Partners"));
        Assert.assertFalse(strings.contains("Goverment Employees"));

        //end of action_4 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_5 +++++++++++++++++++++++++++++++++++++++++++++
        String colorItem = "";
        String borderLeftStyle = "";
        String borderLeftWidth = "";

        if (studentsItem != null) {
            colorItem = studentsItem.getCssValue("color");
            borderLeftStyle = studentsItem.getCssValue("border-left-style");
            borderLeftWidth = studentsItem.getCssValue("border-left-width");
        }

        //start of check_1 ----------------------------------------------
        Assert.assertEquals("rgba(2, 95, 98, 1)", colorItem);
        Assert.assertEquals("7px", borderLeftWidth);
        Assert.assertEquals("solid", borderLeftStyle);

        //start of check_2 ----------------------------------------------
        Assert.assertNotEquals("a", studentsItem.getTagName());

        //end of action_5 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_6 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement home = driver.findElement(By.id("links-site")).findElement(By.linkText("Home"));
        home.click();

        //end of action_6 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_7 +++++++++++++++++++++++++++++++++++++++++++++
        WebElement emailAddress = driver.findElement(By.id("EmailAddress"));
        WebElement button = driver.findElement(By.id("id31"));
        button.click();
        try {
            //start of check_1 ----------------------------------------------
            Alert alert = driver.switchTo().alert();
            //start of check_2 ----------------------------------------------
            Assert.assertEquals("Please enter email address", alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.assertEquals(0,1);
        }

        //end of action_7 +++++++++++++++++++++++++++++++++++++++++++++++

        //start of action_8 +++++++++++++++++++++++++++++++++++++++++++++
        emailAddress.sendKeys("srdkr2yandex.ru");
        button.click();
        try {
            //start of check_1 ----------------------------------------------
            Alert alert = driver.switchTo().alert();
            //start of check_2 ----------------------------------------------
            Assert.assertEquals("Invalid email address.", alert.getText());
            alert.accept();
        } catch (NoAlertPresentException e) {
            Assert.assertEquals(0,1);
        }
        //end of action_8 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_9 +++++++++++++++++++++++++++++++++++++++++++++

        WebElement linksSite = driver.findElement(By.id("links-site"));
        WebElement searchInput = linksSite.findElement(By.id("query"));
        WebElement searchButton = linksSite.findElement(By.className("icon"));
        searchInput.sendKeys("for dummies");
        searchButton.click();

        //start of check_1 ----------------------------------------------
        List<WebElement> itemList = driver.findElements(By.className("product-title"));
        Assert.assertTrue(itemList.size() > 0);

        //end of action_9 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_10 +++++++++++++++++++++++++++++++++++++++++++++

        Random random = new Random();
        int i = random.nextInt(itemList.size());
        WebElement item = itemList.get(i);
        WebElement link = item.findElement(By.tagName("a"));
        String title = link.getText();
        link.click();

        //start of check_1 ----------------------------------------------
        String pageHeader = driver.findElement(By.className("productDetail-title")).getText();
        Assert.assertEquals(title,pageHeader);

        //end of action_10 +++++++++++++++++++++++++++++++++++++++++++++++


        //start of action_11 +++++++++++++++++++++++++++++++++++++++++++++

        driver.findElement(By.id("links-site")).findElement(By.linkText("Home")).click();

        //end of action_11 +++++++++++++++++++++++++++++++++++++++++++++++

        //start of action_12 +++++++++++++++++++++++++++++++++++++++++++++

        String currentHandle = driver.getWindowHandle();

        WebElement institutes = driver.findElement(By.id("homepage-links")).findElement(By.linkText("Institutions"));
        institutes.click();

        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles){
            if (!handle.equals(currentHandle)){
                driver.switchTo().window(handle);
            }
        }


        //start of check_1 ----------------------------------------------
        Assert.assertNotEquals("http://wileyedsolutions.com/", driver.getCurrentUrl());
        Assert.assertEquals("https://edservices.wiley.com/", driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(currentHandle);
        //end of action_12 +++++++++++++++++++++++++++++++++++++++++++++++

        driver.close();
    }
}
