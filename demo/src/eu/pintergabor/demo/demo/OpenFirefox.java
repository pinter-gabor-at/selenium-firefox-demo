package eu.pintergabor.demo.demo;

import eu.pintergabor.demo.web.Browser;
import eu.pintergabor.demo.web.WebWorker;

/**
 * Connect to Firefox
 */
public class OpenFirefox extends WebWorker {
	private final boolean existing;

	/**
	 * Connect to Firefox
	 * @param existing true = connect to existing Firefox, false = start a new Firefox
	 */
	public OpenFirefox(boolean existing) {
		this.existing = existing;
	}

	@Override
	protected boolean exec() {
		return Browser.open(existing) != null;
	}
}
