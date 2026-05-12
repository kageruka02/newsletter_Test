package com.automation.helpers;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base page object providing shared WebDriver utilities.
 */
public class PageHelper {

    /**
     * WebDriver instance used by page objects.
     */
    protected final WebDriver driver;

    /**
     * Explicit wait helper for element conditions.
     */
    protected final WebDriverWait wait;

    /**
     * Creates a base page with a default explicit wait.
     *
     * @param driver WebDriver instance for browser interactions.
     */
    public PageHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigates the browser to the supplied URL.
     *
     * @param url Absolute URL to open.
     */
    public void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Waits for the element to be visible.
     *
     * @param element WebElement to wait for.
     */
    protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for the element to be clickable.
     *
     * @param element WebElement to wait for.
     */
    protected void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Types text into the provided element after it becomes visible.
     *
     * @param element WebElement to receive the text.
     * @param text    Text value to type.
     */
    public void type(WebElement element, String text) {
        waitForVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Clicks the provided element after it becomes clickable.
     *
     * @param element WebElement to click.
     */
    public void click(WebElement element) {
        waitForClickable(element);
        element.click();
    }

    /**
     * Reads visible text from the provided element.
     *
     * @param element WebElement to read.
     * @return Visible text content.
     */
    public String getText(WebElement element) {
        waitForVisible(element);
        return element.getText();
    }

    /**
     * Safely checks whether the element is displayed.
     *
     * @param element WebElement to check.
     * @return True when displayed, false when not displayed or detached.
     */
    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (RuntimeException ex) {
            return false;
        }
    }

    /**
     * Retrieves the value of an attribute from the provided element.
     *
     * @param element   WebElement to inspect.
     * @param attribute Attribute name to retrieve.
     * @return Attribute value as a String.
     */
    public String getAttribute(WebElement element, String attribute) {
        waitForVisible(element);
        return element.getAttribute(attribute);
    }

    /**
     * Checks whether the element's class attribute contains the given value.
     *
     * @param element   WebElement to inspect.
     * @param className CSS class name to look for.
     * @return True when the class is present, false otherwise.
     */
    public boolean hasClass(WebElement element, String className) {
        String classes = element.getAttribute("class");
        return classes != null && classes.contains(className);
    }

    /**
     * Returns the current URL of the browser.
     *
     * @return Current page URL as a String.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Returns the current title of the browser tab.
     *
     * @return Page title as a String.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
