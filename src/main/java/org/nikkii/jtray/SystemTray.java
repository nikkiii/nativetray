package org.nikkii.jtray;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

public class SystemTray {

	private static SystemTray systemTray = new SystemTray();

	public static SystemTray getSystemTray() {
		return systemTray;
	}

	/**
	 * A list of active tray icons.
	 */
	private List<TrayIcon> trayIcons = new LinkedList<>();

	public SystemTray() {
	}

	/**
	 * Get the tray icon size using Java's built in trayicon method.
	 *
	 * @return The required icon size.
	 */
	public Dimension getTrayIconSize() {
		return java.awt.SystemTray.getSystemTray().getTrayIconSize();
	}

	/**
	 * Add a TrayIcon to this System tray.
	 *
	 * @param trayIcon The icon to add and show.
	 */
	public void add(TrayIcon trayIcon) {
		if (trayIcon == null) {
			throw new NullPointerException("adding null TrayIcon");
		}

		if (trayIcons.contains(trayIcon)) {
			throw new IllegalArgumentException("adding TrayIcon that is already added");
		}

		trayIcons.add(trayIcon);

		trayIcon.show();
	}

	/**
	 * Remove a TrayIcon from this System tray.
	 *
	 * @param trayIcon The icon to remove and hide.
	 */
	public void remove(TrayIcon trayIcon) {
		if (trayIcon == null) {
			return;
		}

		if (!trayIcons.remove(trayIcon)) {
			return;
		}

		trayIcon.hide();
	}
}
