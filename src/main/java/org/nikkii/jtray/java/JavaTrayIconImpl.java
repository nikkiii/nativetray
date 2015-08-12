package org.nikkii.jtray.java;

import org.nikkii.jtray.TrayIcon;
import org.nikkii.jtray.TrayIcon.MessageType;
import org.nikkii.jtray.TrayIconImpl;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.io.IOException;
import java.net.URL;

public class JavaTrayIconImpl implements TrayIconImpl {

	private java.awt.TrayIcon javaIcon;
	private TrayIcon icon;

	public JavaTrayIconImpl(TrayIcon icon) {
		this.icon = icon;
	}

	private void setAndInitialize(Image image) {
		if (javaIcon != null) {
			javaIcon.setImage(image);
		} else {
			javaIcon = new java.awt.TrayIcon(image);

			javaIcon.addActionListener(e -> icon.fireActionEvent(e));
		}
	}

	@Override
	public void setImage(Image image) {
		setAndInitialize(image);
	}

	@Override
	public void setImage(URL imageUrl) {
		try {
			setAndInitialize(ImageIO.read(imageUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setToolTip(String tooltip) {
		javaIcon.setToolTip(tooltip);
	}

	@Override
	public void setPopupMenu(PopupMenu popup) {
		javaIcon.setPopupMenu(popup);
	}

	@Override
	public void displayMessage(String caption, String text, MessageType messageType) {
		javaIcon.displayMessage(caption, text, java.awt.TrayIcon.MessageType.valueOf(messageType.name()));
	}

	@Override
	public void show() {
		try {
			SystemTray.getSystemTray().add(javaIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void hide() {
		SystemTray.getSystemTray().remove(javaIcon);
	}
}
