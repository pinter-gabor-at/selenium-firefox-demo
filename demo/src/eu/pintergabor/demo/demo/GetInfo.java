package eu.pintergabor.demo.demo;

import eu.pintergabor.demo.web.Browser;
import eu.pintergabor.demo.web.WebWorker;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Collect and print out some info from the displayed pages
 */
public class GetInfo extends WebWorker {

	@Override
	protected boolean exec() {
		if (Browser.isOpen()) {
			final var driver = Browser.getDriver();
			final var js = (JavascriptExecutor) driver;
			final var wh = driver.getWindowHandles();
			// For all windows
			int i = 0;
			for (String w : wh) {
				driver.switchTo().window(w);
				// URL
				publish(String.format("[%d] URL: %s", i, driver.getCurrentUrl()));
				// Title
				publish(String.format("[%d] Title: %s", i, driver.getTitle()));
				// Description
				try {
					publish(String.format("[%d] Description: %s", i,
						js.executeScript(
							"return document.querySelector('meta[name=\"description\"]').content")));
				} catch (Exception ignored) {
					// If a page does not have a description, then don't display it.
					// It is not an error.
				}
				publish("");
				i++;
			}
			return true;
		}
		return false;
	}
}
