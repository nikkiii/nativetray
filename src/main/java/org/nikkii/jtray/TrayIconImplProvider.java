package org.nikkii.jtray;

import org.nikkii.jtray.java.JavaTrayIconImpl;
import org.nikkii.jtray.linux.Gtk;
import org.nikkii.jtray.linux.NativeTrayIconImpl;

public class TrayIconImplProvider {

	private static boolean gtkInit = false;

	private static void initializeGtk() {
		gtkInit = true;

		Gtk.XInitThreads();
		Gtk.gtk_init(0, null);

		new Thread(Gtk::gtk_main).start();
	}

	public static TrayIconImpl getTrayPeer(TrayIcon icon) {
		if (Gtk.isGtkPresent()) {
			if (!gtkInit) {
				initializeGtk();
				gtkInit = true;
			}
			return new NativeTrayIconImpl(icon);
		}
		return new JavaTrayIconImpl(icon);
	}

}
