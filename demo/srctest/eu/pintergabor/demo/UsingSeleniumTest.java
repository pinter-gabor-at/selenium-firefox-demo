package eu.pintergabor.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eu.pintergabor.demo.web.Browser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UsingSeleniumTest {
	private static WebDriver driver;

	@BeforeAll
	public static void open() {
		driver = Browser.open(false);
	}

	@AfterAll
	public static void close() {
		Browser.close();
	}

	@Test
	public void eightComponents() throws InterruptedException {
		System.out.println("=== Test: open Selenium's test page and get some info ===");

		// Open the demo page
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");

		// Check its title
		String title = driver.getTitle();
		assertEquals("Web form", title);
		System.out.println("Page opened successfully.");
		WebElement textBox = driver.findElement(By.name("my-text"));
		WebElement submitButton = driver.findElement(By.cssSelector("button"));

		// Wait
		Thread.sleep(2000);

		// Write into a textbox
		final String text = "Selenium";
		textBox.sendKeys(text);
		assertEquals(text, textBox.getAttribute("value"));
		System.out.println("Text box found and modified successfully.");

		// Wait
		Thread.sleep(2000);

		// Click the submit button
		submitButton.click();

		// Check the result
		WebElement message = driver.findElement(By.id("message"));
		String value = message.getText();
		assertEquals("Received!", value);
		System.out.println("Data submitted by clicking the submit button and recived correctly.");

		// Wait
		Thread.sleep(2000);
	}
}
