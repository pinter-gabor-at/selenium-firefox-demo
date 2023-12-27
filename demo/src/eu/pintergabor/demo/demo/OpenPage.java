package eu.pintergabor.demo.demo;

import eu.pintergabor.demo.web.Browser;
import eu.pintergabor.demo.web.WebWorker;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WindowType;

/**
 * Open a web page
 */
public class OpenPage extends WebWorker {
	private final String url;

	/**
	 * Open a web page
	 * @param url URL
	 */
	public OpenPage(final String url) {
		this.url = url;
	}

	@Override
	protected boolean exec() {
		if (Browser.isOpen()) {
			var driver = Browser.getDriver();
			try {
				// Try to open the URL in the last used window.
				driver.get(url);
			} catch (NoSuchWindowException e) {
				// If the last used window is closed or invalid, try to open the URL in a new window.
				// Switch to a new window does not seem to work if it is not started from an existing valid window,
				// so select the first window first and then create the new one.
				driver.switchTo().window(driver.getWindowHandles().iterator().next());
				driver.switchTo().newWindow(WindowType.TAB);
				driver.get(url);
			}
			return true;
		}
		return false;
	}
}
