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
public class Main {

    WebDriver driver;

    public static void main(String... args){

        Main m = new Main();
        m.init();
        m.action1();
        m.action2();
        m.action3();
        WebElement studentsItem = m.action4();
        m.action5(studentsItem);
        m.action6();
        WebElement[] elements = m.action7();
        m.action8(elements);
        List<WebElement> elementList = m.action9();
        m.action10(elementList);
        m.action11();
        m.action12();
        m.closeDriver();

    }

    private void init(){
        System.setProperty("webdriver.gecko.driver","geckodriver.exe");
        System.setProperty("webdriver.firefox.marionette", "geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://www.wiley.com/WileyCDA");
    }

    private void closeDriver(){
        driver.close();
    }

    private void action1(){

        WebElement navigationMenu = driver.findElement(By.id("links-site"));

        actionHeader(1);

        action1Out(navigationMenu, "Home");
        action1Out(navigationMenu, "Subjects");
        action1Out(navigationMenu, "About Wiley");
        action1Out(navigationMenu, "Contact Us");
        action1Out(navigationMenu, "Help");

    }

    private void action2(){

        WebElement resources = driver.findElement(By.id("homepage-links"));
        List<WebElement> linkList = resources.findElements(By.tagName("a"));

        List<String> linkTextList = new ArrayList<>();

        for (WebElement el : linkList) {
            linkTextList.add(el.getText());
        }

        actionHeader(2);

        if (linkList.size() == 9){
            System.out.println("There are 9 items under resources sub-header");
        }
        else {
            System.out.println("ERROR: There aren't 9 items under resources sub-header");
        }
        action2Out(linkTextList, "Students");
        action2Out(linkTextList, "Authors");
        action2Out(linkTextList, "Instructors");
        action2Out(linkTextList, "Librarians");
        action2Out(linkTextList, "Societies");
        action2Out(linkTextList, "Conferences");
        action2Out(linkTextList, "Booksellers");
        action2Out(linkTextList, "Corporations");
        action2Out(linkTextList, "Institutions");

    }

    private void action3(){
        WebElement resources = driver.findElement(By.id("homepage-links"));
        WebElement students = resources.findElement(By.linkText("Students"));
        students.click();

        actionHeader(3);

        String url = "http://www.wiley.com/WileyCDA/Section/id-404702.html";

        if (url.equals(driver.getCurrentUrl())){
            System.out.println("Current page address is: " + url);
        }
        else {
            System.out.println("ERROR: Current page address is wrong: " + driver.getCurrentUrl());
        }

        WebElement header = driver.findElement(By.id("page-title")).findElement(By.tagName("h1"));
        if (header.getText().equals("Students") && header.isDisplayed()){
            System.out.println("Students header is diplayed");
        }
        else {
            System.out.println("ERROR: Students header isn't displayed");
        }


    }

    private WebElement action4(){

        actionHeader(4);

        WebElement sidebar = driver.findElement(By.id("sidebar"));
        List<WebElement> items = sidebar.findElements(By.xpath(".//ul[@class=\"autonavLevel1\"]/li/a|.//ul[@class=\"autonavLevel1\"]/li/span"));


        if (items.size() == 8){
            System.out.println("8 items are displayed in the menu");
        }
        else {
            System.out.println("ERROR: " + items.size() + " items are displayed in the menu instead 8");
        }

        List<String> strings = new ArrayList<>();
        WebElement studentsItem = null;
        for (WebElement el : items){
            if (el.getText().equals("Students") && el.isDisplayed()){
                studentsItem = el;
            }
            strings.add(el.getText());
        }

        action4Out(strings, "Authors");
        action4Out(strings, "Librarians");
        action4Out(strings, "Booksellers");
        action4Out(strings, "Instructors");
        action4Out(strings, "Students");
        action4Out(strings, "Societies");
        action4Out(strings, "Goverment Employees");
        action4Out(strings, "Corporate Partners");
        return studentsItem;

    }

    private void action5(WebElement studentsItem){
        String colorItem = "";
        String borderLeftStyle = "";
        String borderLeftWidth = "";

        actionHeader(5);

        if (studentsItem != null) {
            colorItem = studentsItem.getCssValue("color");
            borderLeftStyle = studentsItem.getCssValue("border-left-style");
            borderLeftWidth = studentsItem.getCssValue("border-left-width");
        }

        if (colorItem.equals("rgba(2, 95, 98, 1)") &&
                borderLeftWidth.equals("7px") &&
                borderLeftStyle.equals("solid")){
            System.out.println("\"Students\" item has different style");
        }
        else {
            System.out.println("ERROR: \"Students\" item has same style");
        }

        if (!studentsItem.getTagName().equals("a")){
            System.out.println("\"Students\" item is not clickable");
        }
        else {
            System.out.println("ERROR: \"Students\" item is clickable");
        }

    }

    private void action6(){
        actionHeader(6);
        goHome();
    }

    private WebElement[] action7(){
        actionHeader(7);
        WebElement emailAddress = driver.findElement(By.id("EmailAddress"));
        WebElement button = driver.findElement(By.id("id31"));
        button.click();
        try {

            Alert alert = driver.switchTo().alert();
            if (alert.getText().equals("Please enter email address")){
                System.out.println("Alert text is \"Please enter email address\"");
            }
            else {
                System.out.println("ERROR: Alert text isn't \"Please enter email address\"");
            }

            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("ERROR: Action #7. No Allert present");
        }
        WebElement[] elements = {emailAddress, button};
        return elements;
    }

    private void action8(WebElement[] elements){
        actionHeader(8);
        elements[0].sendKeys("srdkr2yandex.ru");
        elements[1].click();
        try {
            Alert alert = driver.switchTo().alert();
            if (alert.getText().equals("Invalid email address.")){
                System.out.println("Alert text is \"Invalid email address.\"");
            }
            else {
                System.out.println("ERROR: Alert text isn't \"Invalid email address.\"");
            }
            alert.accept();
        } catch (NoAlertPresentException e) {
            System.out.println("ERROR: Action #8. No Allert present");
        }
    }

    private List<WebElement> action9(){
        actionHeader(9);
        WebElement linksSite = driver.findElement(By.id("links-site"));
        WebElement searchInput = linksSite.findElement(By.id("query"));
        WebElement searchButton = linksSite.findElement(By.className("icon"));
        searchInput.sendKeys("for dummies");
        searchButton.click();

        List<WebElement> itemList = driver.findElements(By.className("product-title"));
        if (itemList.size() > 0){
            System.out.println("List of items appeared");
        }
        else {
            System.out.println("ERROR: No List of items appears");
        }
        return itemList;
    }

    private void action10(List<WebElement> itemList){
        actionHeader(10);
        Random random = new Random();
        int i = random.nextInt(itemList.size());
        WebElement item = itemList.get(i);
        WebElement link = item.findElement(By.tagName("a"));
        String title = link.getText();
        link.click();

        WebElement productTitle = driver.findElement(By.className("productDetail-title"));
        String pageHeader = productTitle.getText();
        if (productTitle.isDisplayed() && pageHeader.equals(title)){
            System.out.println("Header is displayed and equals to the link title");
        }
        else {
            System.out.println("ERROR: Header isn't displayed or isn't equals to the link title");
        }

    }

    private void action11(){
        actionHeader(11);
        goHome();
    }

    private void action12(){
        actionHeader(12);
        String currentHandle = driver.getWindowHandle();

        WebElement institutes = driver.findElement(By.id("homepage-links")).findElement(By.linkText("Institutions"));
        institutes.click();

        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles){
            if (!handle.equals(currentHandle)){
                driver.switchTo().window(handle);
            }
        }

        if (driver.getCurrentUrl().equals("http://wileyedsolutions.com/")){
            System.out.println("Address of new window (tab) is \"http://wileyedsolutions.com/\"");
        }
        else {
            System.out.println("ERROR: Address of new window (tab) is \"" + driver.getCurrentUrl() + "\". Expected \"http://wileyedsolutions.com/\"");
        }
        driver.close();
        driver.switchTo().window(currentHandle);
    }

    private void action1Out(WebElement el, String linkText) {

        if (el.findElement(By.linkText(linkText)).isDisplayed()) {
            System.out.println("Link \"" + linkText + "\" is displayed");
        } else {
            System.out.println("ERROR: Link \"" + linkText + "\" is not displayed");
        }

    }

    private void action2Out(List<String> list, String text ){

        if (list.contains(text)) {
            System.out.println("There are \"" + text + "\" under resources sub-header");
        } else {
            System.out.println("ERROR: There aren't \"" + text + "\" under resources sub-header");
        }

    }

    private void action4Out(List<String> list, String text ){

        if (list.contains(text)) {
            System.out.println("Item are \"" + text + "\" ");
        } else {
            System.out.println("ERROR: Item aren't \"" + text + "\" ");
        }

    }

    private void actionHeader(int number){
        System.out.println();
        System.out.println("Check of Action #" + number + ":");
    }

    private void goHome(){
        WebElement home = driver.findElement(By.id("links-site")).findElement(By.linkText("Home"));
        home.click();
        System.out.println("Jump to \"Home\" page");
    }
}
