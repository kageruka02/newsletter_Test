package com.automation.pages;

import com.automation.helpers.PageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object representing the Newsletter sign-up card.
 */
public class SignupPage extends PageHelper {

    // ── Locators via Page Factory ────────────────────────────────

    /**
     * Email input field.
     */
    @FindBy(id = "email")
    private WebElement emailInput;

    /**
     * Inline error message shown for invalid email.
     */
    @FindBy(id = "emailError")
    private WebElement emailError;

    /**
     * Subscribe button that submits the form.
     */
    @FindBy(id = "subscribeBtn")
    private WebElement subscribeBtn;

    /**
     * The signup card wrapper element.
     */
    @FindBy(id = "signupCard")
    private WebElement signupCard;

    // ── Constructor ──────────────────────────────────────────────

    /**
     * Initialises the page and injects all @FindBy elements.
     *
     * @param driver WebDriver instance for browser interactions.
     */
    public SignupPage(WebDriver driver) {
        super(driver);
        // Page Factory scans @FindBy annotations and wires up the WebElements
        PageFactory.initElements(driver, this);
    }

    // ── Actions ──────────────────────────────────────────────────

    /**
     * Navigates to the newsletter app URL.
     *
     * @param url Absolute URL to open.
     * @return This SignupPage for chaining.
     */
    public SignupPage open(String url) {
        openUrl(url);
        return this;
    }

    /**
     * Types an email address into the email input field.
     *
     * @param email Email address to enter.
     * @return This SignupPage for chaining.
     */
    public SignupPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    /**
     * Clicks the subscribe button expecting a successful submission.
     * Returns SuccessPage since the browser transitions to the success state.
     *
     * @return SuccessPage representing the success card state.
     */
    public SuccessPage clickSubscribe() {
        click(subscribeBtn);
        return new SuccessPage(driver);
    }

    /**
     * Clicks the subscribe button expecting a validation error.
     * Returns this SignupPage since the browser stays on the sign-up state.
     *
     * @return This SignupPage for chaining.
     */
    public SignupPage clickSubscribeExpectingError() {
        click(subscribeBtn);
        return this;
    }

    // ── Queries ──────────────────────────────────────────────────

    /**
     * Checks whether the inline error message is visible.
     *
     * @return True when the error message is displayed.
     */
    public boolean isErrorDisplayed() {
        return isDisplayed(emailError);
    }

    /**
     * Reads the text content of the inline error message.
     *
     * @return Error message text.
     */
    public String getErrorMessage() {
        return getText(emailError);
    }

    /**
     * Checks whether the signup card is currently visible.
     * The card is hidden by adding the CSS class "hidden".
     *
     * @return True when the signup card is visible (no "hidden" class).
     */
    public boolean isVisible() {
        return !hasClass(signupCard, "hidden");
    }

    /**
     * Checks whether the email input is in the error style state.
     * The input receives the CSS class "error" on invalid submission.
     *
     * @return True when the error CSS class is present on the input.
     */
    public boolean isEmailInputInErrorState() {
        return hasClass(emailInput, "error");
    }
}
