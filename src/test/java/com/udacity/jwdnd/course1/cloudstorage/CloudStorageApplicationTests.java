package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 9);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(
				driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!") ||
				driver.findElement(By.id("success-msg̐")).getText().contains("is already signed in")
		);
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 9);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void doLogout() {
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";
		String id_to_check = "success-upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id(id_to_check)));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testUnauthorizedUserAccess() {
		// unauthorized access leads to login page
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

		// unauthorized user can access login page
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());


		// unauthorized user can access signup page
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

	}

	@Test
	public void testFullFlow() {

		doMockSignUp("Jason","Roy","json","123");
		doLogIn("json", "123");

		// home page is accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

		doLogout();

		// home page is not accessible
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());


	}

	public void navigateToNodeTab(WebDriverWait webDriverWait, String id ) {
		// go to homepage
		driver.get("http://localhost:" + this.port + "/home");
		// go to notes tab
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		WebElement noteTab = driver.findElement(By.id(id));
		noteTab.click();
	}

	public void addNoteDetails(WebDriverWait webDriverWait, String noteTitle, String noteDescription) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitleInput = driver.findElement(By.id("note-title"));
		noteTitleInput.click();
		noteTitleInput.clear();
		noteTitleInput.sendKeys(noteTitle);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
		noteDescriptionInput.click();
		noteDescriptionInput.clear();
		noteDescriptionInput.sendKeys(noteDescription);
	}

	public void submitNote(WebDriverWait webDriverWait){
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("noteSubmitFooter")));
		WebElement noteSubmitButton = driver.findElement(By.id("noteSubmitFooter"));
		noteSubmitButton.click();
	}

	public void createNote(String noteTitle, String noteDescription) {
		// get the webdriver
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		navigateToNodeTab(webDriverWait, "nav-notes-tab");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-note")));
		WebElement addNoteButton = driver.findElement(By.id("add-new-note"));
		addNoteButton.click();
		addNoteDetails(webDriverWait, noteTitle, noteDescription);
		submitNote(webDriverWait);

	}

	@Test
	public void testNoteCreation() {
		doMockSignUp("Jason","Roy","json","123");
		doLogIn("json", "123");

		String noteTitle = "My first note";
		String noteDescription = "This is the description of my first note";

		createNote(noteTitle, noteDescription);

		Assertions.assertTrue(this.driver.getPageSource().contains(noteTitle) &&
				this.driver.getPageSource().contains(noteDescription));

	}

	@Test
	public void testNoteEditing() {

		doMockSignUp("Jason","Roy","json","123");
		doLogIn("json", "123");

		String noteTitle = "My first note";
		String noteDescription = "This is the description of my first note";

		String noteTitleUpdated = "First updates";
		String noteDescriptionUpdated = "Updated my first note, this has more details about the objectives";

		createNote(noteTitle, noteDescription);
		String edit_id = "edit-" + noteTitle.hashCode();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(edit_id)));
		WebElement noteEditButton = driver.findElement(By.id(edit_id));
		noteEditButton.click();
		addNoteDetails(webDriverWait, noteTitleUpdated, noteDescriptionUpdated);
		submitNote(webDriverWait);

		Assertions.assertTrue(this.driver.getPageSource().contains(noteTitleUpdated) &&
				this.driver.getPageSource().contains(noteDescriptionUpdated));


		Assertions.assertFalse(this.driver.getPageSource().contains(noteTitle) ||
				this.driver.getPageSource().contains(noteDescription));

	}
	@Test
	public void testNoteDeletion() {
		doMockSignUp("Jason","Roy","json","123");
		doLogIn("json", "123");

		String noteTitle = "My first note";
		String noteDescription = "This is the description of my first note";
		createNote(noteTitle, noteDescription);
		String delete_id = "delete-" + noteTitle.hashCode();
		WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(delete_id)));
		WebElement noteEditButton = driver.findElement(By.id(delete_id));
		noteEditButton.click();

		Assertions.assertFalse(this.driver.getPageSource().contains(noteTitle) ||
				this.driver.getPageSource().contains(noteDescription));

		doLogout();


	}

	@Test
	public void testCredentialCreation() {
		
	}

}
