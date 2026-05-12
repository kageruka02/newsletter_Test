package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Manages WebDriver lifecycle with ThreadLocal for parallel-safe execution.
 */
public class DriverManager {

    /**
     * ThreadLocal ensures each thread gets its own independent WebDriver instance.
     */
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Returns the WebDriver instance for the current thread.
     *
     * @return WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Initialises a ChromeDriver instance for the current thread.
     *
     * @param headless When true, Chrome runs without a visible window (CI/CD mode).
     */
    public static void initDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            // Required for running on CI/CD servers with no display
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    /**
     * Quits the WebDriver instance for the current thread and removes it
     * from ThreadLocal to prevent memory leaks.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
