package eu.pintergabor.demo.demo;

import eu.pintergabor.demo.web.Browser;
import eu.pintergabor.demo.web.WebWorker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Do something spectacular with the displayed pages
 */
public class PaintPages extends WebWorker {

	@Override
	protected boolean exec() {
		if (Browser.isOpen()) {
			final var driver = Browser.getDriver();
			final var js = (JavascriptExecutor) driver;
			final var wh = driver.getWindowHandles();
			// For all windows
			for (String w : wh) {
				driver.switchTo().window(w);
				try {
					// Most HTML pages have a body element
					var body = driver.findElement(By.tagName("body"));
					// Make it quite colorful
					js.executeScript(
						"arguments[0].style.background=" +
							"'linear-gradient(60deg, red, magenta, blue, cyan, green, yellow, red)'", body);
				} catch (Exception ignored) {
				}
			}
			return true;
		}
		return false;
	}
}
