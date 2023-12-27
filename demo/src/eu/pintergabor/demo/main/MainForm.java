package eu.pintergabor.demo.main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import eu.pintergabor.demo.demo.CloseFirefox;
import eu.pintergabor.demo.demo.GetInfo;
import eu.pintergabor.demo.demo.OpenFirefox;
import eu.pintergabor.demo.demo.OpenPage;
import eu.pintergabor.demo.demo.PaintPages;

public class MainForm {
	private JTextArea result;
	private JButton openExistingButton;
	public JPanel contentPanel;
	private JTextField address;
	private JButton openNewButton;
	private JButton getInfoButton;
	private JButton paintButton;
	private JButton goButton;
	private JButton closeButton;

	private boolean existing;

	/**
	 * Called after opening a connection
	 * @param success True if opening the connection was successful
	 */
	private void enableButtons(boolean success) {
		openExistingButton.setEnabled(!success);
		openNewButton.setEnabled(!success);
		getInfoButton.setEnabled(success);
		paintButton.setEnabled(success);
		goButton.setEnabled(success);
		closeButton.setEnabled(success);
		closeButton.setText(existing ? "Close connection" : "Close Firefox and connection");
	}

	/**
	 * Called before doing any time-consuming operation
	 */
	private void disableButtons() {
		openExistingButton.setEnabled(false);
		openNewButton.setEnabled(false);
		getInfoButton.setEnabled(false);
		paintButton.setEnabled(false);
		goButton.setEnabled(false);
		closeButton.setEnabled(false);
	}

	public MainForm() {
		openExistingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				existing = true;
				disableButtons();
				result.setText("Connecting to a running Firefox ...\r\n");
				final OpenFirefox task = new OpenFirefox(true);
				task.setWhenDone(() -> {
					final boolean success = task.safeGet();
					result.append(success ? "Success\r\n" : "Failed\r\n");
					enableButtons(success);
				}).execute();
			}
		});
		openNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				existing = false;
				disableButtons();
				final OpenFirefox task = new OpenFirefox(false);
				task.setWhenDone(() -> {
					final boolean success = task.safeGet();
					result.append(success ? "Success\r\n" : "Failed\r\n");
					enableButtons(success);
				}).execute();
			}
		});
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				disableButtons();
				final CloseFirefox task = new CloseFirefox();
				task.setWhenDone(() -> {
					enableButtons(false);
				}).execute();
			}
		});
		goButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				disableButtons();
				final OpenPage task = new OpenPage(address.getText());
				task.setWhenDone(() -> {
					if (!task.safeGet()){
						result.append("???\r\n");
					}
					enableButtons(true);
				}).execute();
			}
		});
		getInfoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				disableButtons();
				final GetInfo task = new GetInfo();
				task.setWhenDoing(s -> {
					result.append(s + "\r\n");
				});
				task.setWhenDone(() -> {
					if (!task.safeGet()){
						result.append("???\r\n");
					}
					enableButtons(true);
				}).execute();
			}
		});
		paintButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				disableButtons();
				final PaintPages task = new PaintPages();
				task.setWhenDone(() -> {
					enableButtons(true);
				}).execute();
			}
		});
	}
}
