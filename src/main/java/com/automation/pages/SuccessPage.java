package com.automation.pages;

import com.automation.helpers.PageHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page object representing the Newsletter success card.
 */
public class SuccessPage extends PageHelper {

    // ── Locators via Page Factory ────────────────────────────────

    /**
     * The success card wrapper element.
     */
    @FindBy(id = "successCard")
    private WebElement successCard;

    /**
     * Element displaying the confirmed email address.
     */
    @FindBy(id = "confirmedEmail")
    private WebElement confirmedEmail;

    /**
     * Dismiss button that returns to the sign-up card.
     */
    @FindBy(id = "dismissBtn")
    private WebElement dismissBtn;

    // ── Constructor ──────────────────────────────────────────────

    /**
     * Initialises the page and injects all @FindBy elements.
     *
     * @param driver WebDriver instance for browser interactions.
     */
    public SuccessPage(WebDriver driver) {
        super(driver);
        // Page Factory scans @FindBy annotations and wires up the WebElements
        PageFactory.initElements(driver, this);
    }

    // ── Actions ──────────────────────────────────────────────────

    /**
     * Clicks the dismiss button to return to the sign-up card.
     *
     * @return SignupPage representing the sign-up card state.
     */
    public SignupPage clickDismiss() {
        click(dismissBtn);
        return new SignupPage(driver);
    }

    // ── Queries ──────────────────────────────────────────────────

    /**
     * Checks whether the success card is currently visible.
     * The card is shown by adding the CSS class "visible".
     *
     * @return True when the success card has the "visible" class.
     */
    public boolean isVisible() {
        return hasClass(successCard, "visible");
    }

    /**
     * Reads the confirmed email address displayed on the success card.
     *
     * @return Confirmed email address text.
     */
    public String getConfirmedEmail() {
        return getText(confirmedEmail);
    }
}
