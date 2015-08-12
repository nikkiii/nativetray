package org.nikkii.jtray.linux;

import com.sun.jna.Function;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.nikkii.jtray.linux.GtkNative.GdkRectangle;
import org.nikkii.jtray.linux.GtkNative.GtkStatusIcon;

public class Gtk {
	public static GtkNative gtk;

	static {
		try {
			gtk = (GtkNative) Native.loadLibrary("gtk-x11-2.0", GtkNative.class);
		} catch (UnsatisfiedLinkError e) {
			// No Gtk
		}
	}

	public static GtkStatusIcon gtk_status_icon_new() {
		return gtk.gtk_status_icon_new();
	}

	public static GtkStatusIcon gtk_status_icon_new_from_file(String fileName) {
		return gtk.gtk_status_icon_new_from_file(fileName);
	}

	public static void gtk_status_icon_set_visible(GtkStatusIcon statusIcon, boolean visible) {
		gtk.gtk_status_icon_set_visible(statusIcon, visible);
	}

	public static void gtk_status_icon_set_tooltip(GtkStatusIcon statusIcon, String tooltip) {
		gtk.gtk_status_icon_set_tooltip(statusIcon, tooltip);
	}

	public static void gtk_status_icon_set_from_file(GtkStatusIcon statusIcon, String fileName) {
		gtk.gtk_status_icon_set_from_file(statusIcon, fileName);
	}

	public static void gtk_init(int argc, String[] argv) {
		gtk.gtk_init(argc, argv);
	}

	public static void gtk_main() {
		gtk.gtk_main();
	}

	// Menu

	public static Pointer gtk_menu_new() {
		return gtk.gtk_menu_new();
	}

	public static Pointer gtk_menu_item_new_with_label(String label) {
		return gtk.gtk_menu_item_new_with_label(label);
	}

	public static void gtk_menu_shell_append(Pointer menu, Pointer item) {
		gtk.gtk_menu_shell_append(menu, item);
	}

	public static void gtk_menu_popup(Pointer menu, Pointer widget, Pointer bla, Function func, Pointer data, int button, int time) {
		gtk.gtk_menu_popup(menu, widget, bla, func, data, button, time);
	}

	public static void gtk_widget_show_all(Pointer menu) {
		gtk.gtk_widget_show_all(menu);
	}

	public static void gtk_widget_show(Pointer gtkitem) {
		gtk.gtk_widget_show(gtkitem);
	}

	public static void gtk_status_icon_get_geometry(GtkStatusIcon statusIcon, Pointer screen, GdkRectangle rect, Pointer orientation) {
		gtk.gtk_status_icon_get_geometry(statusIcon, screen, rect, orientation);
	}

	public static void g_object_unref(Pointer object) {
		gtk.g_object_unref(object);
	}

	public static void XInitThreads() {
		gtk.XInitThreads();
	}

	public static boolean isGtkPresent() {
		return gtk != null;
	}

	public static void gtk_status_icon_set_title(GtkStatusIcon nativeIcon, String tooltip) {
		gtk.gtk_status_icon_set_title(nativeIcon, tooltip);
	}
}
