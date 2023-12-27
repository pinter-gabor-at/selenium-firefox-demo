package eu.pintergabor.demo.demo;

import eu.pintergabor.demo.web.Browser;
import eu.pintergabor.demo.web.WebWorker;

/**
 * Close Firefox
 */
public class CloseFirefox extends WebWorker {

	@Override
	protected boolean exec() {
		Browser.close();
		return true;
	}
}
