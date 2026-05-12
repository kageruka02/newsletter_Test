package com.automation.tests;

import com.automation.pages.SignupPage;
import com.automation.pages.SuccessPage;
import com.automation.utils.DriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * End-to-end tests for the Newsletter Sign-Up form.
 * Covers sign-up, validation, success state, and dismiss flow.
 */
public class NewsletterTest {

    /**
     * Hosted URL of the newsletter app under test.
     */
    private static final String URL = "https://kageruka02.github.io/webFundamentals/";

    /**
     * Reads the headless flag from Maven/CI — defaults to false locally.
     * Pass -Dheadless=true when running in CI/CD.
     */
    private static final boolean HEADLESS = Boolean.parseBoolean(
            System.getProperty("headless", "false")
    );

    /** Page object for the sign-up card. */
    private SignupPage signupPage;

    // ── Setup & Teardown ─────────────────────────────────────────

    /**
     * Opens a fresh browser and navigates to the app before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // 1. start browser
        DriverManager.initDriver(HEADLESS);

        // 2. create page object AFTER driver is ready
        signupPage = new SignupPage(DriverManager.getDriver());

        // 3. navigate to app
        signupPage.open(URL);
    }

    /**
     * Quits the browser after each test to keep tests independent.
     * alwaysRun = true ensures cleanup even if the test fails.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // ── Smoke Tests ───────────────────────────────────────────

    /**
     * TC1 — Valid email should show the success card.
     */
    @Test(groups = {"smoke"}, description = "Valid email should show success card")
    public void testValidEmailShowsSuccessCard() {
        SuccessPage successPage = signupPage
                .enterEmail("test@example.com")
                .clickSubscribe();

        Assert.assertTrue(
                successPage.isVisible(),
                "Success card should be visible after valid email submission"
        );
    }

    /**
     * TC2 — Invalid email should show the inline error message.
     */
    @Test(groups = {"smoke"}, description = "Invalid email should show error message")
    public void testInvalidEmailShowsError() {
        signupPage
                .enterEmail("notanemail")
                .clickSubscribeExpectingError();

        Assert.assertTrue(
                signupPage.isErrorDisplayed(),
                "Error message should be visible for invalid email"
        );
    }

    /**
     * TC3 — Empty email should show the inline error message.
     */
    @Test(groups = {"smoke"}, description = "Empty email should show error message")
    public void testEmptyEmailShowsError() {
        signupPage
                .enterEmail("")
                .clickSubscribeExpectingError();

        Assert.assertTrue(
                signupPage.isErrorDisplayed(),
                "Error message should be visible for empty email"
        );
    }

    /**
     * TC4 — Dismiss button should return the user to the sign-up card.
     */
    @Test(groups = {"smoke"}, description = "Dismiss button should return to signup card")
    public void testDismissReturnsToSignupCard() {
        SignupPage backToSignup = signupPage
                .enterEmail("test@example.com")
                .clickSubscribe()
                .clickDismiss();

        Assert.assertTrue(
                backToSignup.isVisible(),
                "Signup card should be visible after dismissing success card"
        );
    }

    /**
     * TC5 — Success card should not be visible on initial page load.
     */
    @Test(groups = {"smoke"}, description = "Success card should be hidden on page load")
    public void testSuccessCardHiddenOnLoad() {
        SuccessPage successPage = new SuccessPage(DriverManager.getDriver());

        Assert.assertFalse(
                successPage.isVisible(),
                "Success card should not be visible on initial page load"
        );
    }

    // ── Regression Tests ─────────────────────────────────────────

    /**
     * TC6 — Success card should display the exact email that was submitted.
     */
    @Test(groups = {"regression"}, description = "Success card should display the submitted email")
    public void testSuccessCardShowsCorrectEmail() {
        String email = "john.doe@company.com";

        SuccessPage successPage = signupPage
                .enterEmail(email)
                .clickSubscribe();

        Assert.assertEquals(
                successPage.getConfirmedEmail(),
                email,
                "Confirmed email on success card should match submitted email"
        );
    }

    /**
     * TC7 — Error state should clear when a valid email is typed after an error.
     */
    @Test(groups = {"regression"}, description = "Error should clear when valid email typed after error")
    public void testErrorClearsOnValidInput() {
        // first trigger the error state
        signupPage
                .enterEmail("bademail")
                .clickSubscribeExpectingError();

        Assert.assertTrue(
                signupPage.isErrorDisplayed(),
                "Error should be shown after invalid email"
        );

        // now type a valid email — error style should clear
        signupPage.enterEmail("valid@example.com");

        Assert.assertFalse(
                signupPage.isEmailInputInErrorState(),
                "Error state should clear after typing a valid email"
        );
    }

    /**
     * TC8 — Signup card should be hidden after a successful subscription.
     */
    @Test(groups = {"regression"}, description = "Signup card should be hidden after subscribe")
    public void testSignupCardHiddenAfterSubscribe() {
        signupPage
                .enterEmail("test@example.com")
                .clickSubscribe();

        Assert.assertFalse(
                signupPage.isVisible(),
                "Signup card should be hidden after successful subscription"
        );
    }

    /**
     * TC9 — Invalid email should apply the error CSS class to the input field.
     */
    @Test(groups = {"regression"}, description = "Invalid email should put input in error style")
    public void testInvalidEmailSetsErrorStyle() {
        signupPage
                .enterEmail("invalidemail")
                .clickSubscribeExpectingError();

        Assert.assertTrue(
                signupPage.isEmailInputInErrorState(),
                "Email input should have error CSS class for invalid email"
        );
    }

    /**
     * TC10 — Error message text should match the expected copy.
     */
    @Test(groups = {"regression"}, description = "Error message text should be correct")
    public void testErrorMessageText() {
        signupPage
                .enterEmail("")
                .clickSubscribeExpectingError();

        Assert.assertEquals(
                signupPage.getErrorMessage(),
                "Valid email required",
                "Error message text should say 'Valid email required'"
        );
    }
}