package eu.pintergabor.demo.web;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.auto.service.AutoService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverService;

/**
 * Extend {@link GeckoDriverService} to enable to connect to existing Firefox, started with the --marionette switch
 */
public class ExGeckoDriverService extends GeckoDriverService {
	public ExGeckoDriverService(
		File executable,
		int port,
		Duration timeout,
		List<String> args,
		Map<String, String> environment)
		throws IOException {
		super(
			executable,
			port,
			timeout,
			args,
			environment);
	}

	/**
	 * Builder used to configure new {@link ExGeckoDriverService} instances.
	 */
	@SuppressWarnings({"rawtypes", "RedundantSuppression"})
	@AutoService(DriverService.Builder.class)
	public static class Builder
		extends GeckoDriverService.Builder {

		protected boolean connectExisting = false;
		protected int marionettePort = 2828;

		/**
		 * Connect to a running Firefox using the default marionette port 2828
		 * <p>
		 * The running Firefox <b>must</b> be started with '--marionette' command line switch.
		 * @return A self reference
		 */
		@SuppressWarnings("unused")
		public Builder withConnectExisting() {
			connectExisting = true;
			return this;
		}

		/**
		 * Connect to a running Firefox
		 * <p>
		 * The running Firefox <b>must</b> be started with '--marionette' command line switch.
		 * @param port Marionette port
		 * @return A self reference
		 */
		@SuppressWarnings("unused")
		public Builder withMarionettePort(int port) {
			connectExisting = true;
			marionettePort = port;
			return this;
		}

		@Override
		protected List<String> createArgs() {
			ArrayList<String> args = new ArrayList<>(super.createArgs());
			if (connectExisting) {
				// Remove --websocket-port, because it is incompatible with --connect-existing
				args.removeIf(s -> s.startsWith("--websocket-port"));
				// See https://firefox-source-docs.mozilla.org/testing/geckodriver/Flags.html
				args.add("--connect-existing");
				args.add(String.format("--marionette-port=%d", marionettePort));
			}
			return args;
		}
	}
}
