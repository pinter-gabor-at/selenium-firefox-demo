package eu.pintergabor.demo.web;

import java.awt.*;
import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Browser {
	private Browser() {
	}

	/**
	 * Location of the geckodriver
	 * <p>
	 * If set to null, Selenium Manager will locate and download the latest version, which is nice, but slow.
	 * <p>
	 * It is better to download the <a href="https://github.com/mozilla/geckodriver/releases">latest geckodriver</a>
	 * manually and place it in the same directory where Firefox is installed. The default value is set to the
	 * most common install location of Firefox under Windows.
	 */
	public static String geckoDriver = "c:\\Program Files\\Mozilla Firefox\\geckodriver.exe";

	private static WebDriver driver = null;

	 /**
	 * Connect to Firefox
	 * @param existing true = connect to existing Firefox, false = start a new Firefox
	 * @return {@link WebDriver} on success, null on failure (Exceptions are translated to failure)
	 */
	public static WebDriver open(boolean existing) {
		if (driver == null) {
			if (geckoDriver != null) {
				System.setProperty("webdriver.gecko.driver", geckoDriver);
			}
			try {
				ExGeckoDriverService.Builder builder = new ExGeckoDriverService.Builder();
				if (existing) {
					builder = builder.withConnectExisting();
				}
				driver = new FirefoxDriver(builder.build());
				init();
			} catch (SessionNotCreatedException e) {
				driver = null;
			}
		}
		return driver;
	}

	/**
	 * Set window position and wait strategy
	 */
	private static void init() {
		final java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = (int) screenSize.getWidth();
		final int height = (int) screenSize.getHeight();
		driver.manage().window().setSize(new Dimension(width / 2, height));
		driver.manage().window().setPosition(new Point(width / 2, 0));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
	}

	/**
	 * @return {@link WebDriver}, or null if it is not connected
	 */
	@SuppressWarnings("unused")
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * @return true if connected, or false if not connected
	 */
	@SuppressWarnings("unused")
	public static boolean isOpen() {
		return driver != null;
	}

	/**
	 * Always called before exit
	 */
	public static void close() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} finally {
			driver = null;
		}
	}
}
