package eu.pintergabor.demo.web;

import java.util.List;

import javax.swing.*;

/**
 * A special {@link SwingWorker} that reports the success or failure of the result its main task as boolean, and may
 * send progress reports as strings.
 */
public abstract class WebWorker extends SwingWorker<Boolean, String> {
	protected Doing whenDoing = null;
	protected Done whenDone = null;

	/**
	 * Set the action do handle the strings that the {@link #exec()} sends by {@link SwingWorker#publish(Object[])}
	 * while doing its job
	 * @param whenDoing The action to handle one string (Usualy some kind of printout)
	 * @return Self reference for chaining
	 */
	@SuppressWarnings("UnusedReturnValue")
	public WebWorker setWhenDoing(Doing whenDoing) {
		this.whenDoing = whenDoing;
		return this;
	}

	/**
	 * Set the action to execute in the event-dispatching thread when the {@link #exec()} is finished
	 * @param whenDone The action to execute
	 * @return Self reference for chaining
	 */
	@SuppressWarnings("UnusedReturnValue")
	public WebWorker setWhenDone(Done whenDone) {
		this.whenDone = whenDone;
		return this;
	}

	/**
	 * The main task
	 * <p>
	 * Something time-consuming
	 * @return true on success, false on failure<br> (Exceptions are automatically translated to failure)
	 */
	protected abstract boolean exec();

	/**
	 * Do something in background
	 * @return true on success
	 */
	@Override
	protected Boolean doInBackground() {
		try {
			return exec();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Similar to {@link SwingWorker#get()}, but translates exceptions to false
	 * @return The result of {@link #exec()}
	 */
	public boolean safeGet() {
		try {
			return get();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Pass strings to {@link #whenDoing} sent by {@link #exec()} by {@link SwingWorker#publish(Object[])} while doing
	 * its job in the event-dispatching thread
	 * @param chunks progress reports
	 */
	@Override
	protected void process(List<String> chunks) {
		if (whenDoing != null) {
			for (String s : chunks) {
				if (s != null) {
					whenDoing.run(s);
				}
			}
		}
	}

	/**
	 * Execute {@link #whenDone} in the event-dispatching thread
	 */
	@Override
	protected void done() {
		if (whenDone != null) {
			whenDone.run();
		}
	}

	/**
	 * Similar to {@link Runnable}, but takes a string parameter
	 */
	@FunctionalInterface
	public interface Doing {
		void run(String s);
	}

	/**
	 * Same as {@link Runnable}
	 */
	@FunctionalInterface
	public interface Done {
		void run();
	}
}
