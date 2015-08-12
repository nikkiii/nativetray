package org.nikkii.jtray;

import org.nikkii.jtray.TrayIcon.MessageType;

import java.awt.Image;
import java.awt.PopupMenu;
import java.net.URL;

/**
 * A base TrayIcon implementation.
 */
public interface TrayIconImpl {

	void setImage(Image image);

	void setToolTip(String tooltip);

	void setPopupMenu(PopupMenu popup);

	void displayMessage(String caption, String text, MessageType messageType);

	void show();

	void hide();

	void setImage(URL imageUrl);

}
