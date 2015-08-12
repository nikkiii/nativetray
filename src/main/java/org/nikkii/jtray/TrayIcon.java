package org.nikkii.jtray;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class TrayIcon {

	private Image image;
	private String tooltip;
	private PopupMenu popup;

	private TrayIconImpl peer = TrayIconImplProvider.getTrayPeer(this);

	private URL imageUrl;

	private List<ActionListener> listeners = new LinkedList<ActionListener>();

	/**
	 * Prevent initialization without params
	 */
	@SuppressWarnings("unused")
	private TrayIcon() {

	}

	public TrayIcon(Image image) {
		setImage(image);
	}

	public TrayIcon(URL imageUrl) {
		setImage(imageUrl);
	}

	public TrayIcon(Image image, String tooltip) {
		this(image);
		setToolTip(tooltip);
	}

	public TrayIcon(URL imageUrl, String tooltip) {
		this(imageUrl);
		setToolTip(tooltip);
	}

	public TrayIcon(Image image, String tooltip, PopupMenu menu) {
		this(image, tooltip);
		setPopupMenu(menu);
	}

	public TrayIcon(URL imageUrl, String tooltip, PopupMenu menu) {
		this(imageUrl, tooltip);
		setPopupMenu(menu);
	}

	public void setToolTip(String tooltip) {
		this.tooltip = tooltip;

		if (peer != null) {
			peer.setToolTip(tooltip);
		}
	}

	public String getToolTip() {
		return tooltip;
	}

	public void setImage(Image image) {
		this.image = image;
		if (peer != null) {
			peer.setImage(image);
		}
	}

	public void setImage(URL imageUrl) {
		this.imageUrl = imageUrl;
		if (peer != null) {
			peer.setImage(imageUrl);
		}
	}

	public URL getImageURL() {
		return imageUrl;
	}

	public Image getImage() {
		return image;
	}

	public void setPopupMenu(PopupMenu popup) {
		this.popup = popup;

		if (peer != null) {
			peer.setPopupMenu(popup);
		}
	}

	public PopupMenu getPopupMenu() {
		return popup;
	}

	public void show() {
		if (image == null && imageUrl == null) {
			throw new IllegalArgumentException("Image cannot be null before adding to tray!");
		}

		if (peer != null) {
			peer.show();
		}
	}

	public void hide() {
		if (peer != null) {
			peer.hide();
		}
	}

	public void displayMessage(String caption, String text, MessageType messageType) {
		if (peer != null) {
			peer.displayMessage(caption, text, messageType);
		}
	}

	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) {
		listeners.remove(listener);
	}

	public void fireActionEvent(ActionEvent e) {
		for (ActionListener listener : listeners) {
			listener.actionPerformed(e);
		}
	}

	public enum MessageType {
		/**
		 * An error message
		 */
		ERROR,
		/**
		 * A warning message
		 */
		WARNING,
		/**
		 * An information message
		 */
		INFO,
		/**
		 * Simple message
		 */
		NONE
	}
}
