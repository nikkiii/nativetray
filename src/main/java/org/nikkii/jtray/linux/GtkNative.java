package org.nikkii.jtray.linux;

import com.sun.jna.Function;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public interface GtkNative extends Library {

	GtkStatusIcon gtk_status_icon_new();

	GtkStatusIcon gtk_status_icon_new_from_file(String fileName);

	void gtk_status_icon_set_visible(GtkStatusIcon statusIcon, boolean visible);

	void gtk_status_icon_set_tooltip(GtkStatusIcon statusIcon, String tooltip);

	void gtk_status_icon_set_from_file(GtkStatusIcon statusIcon, String fileName);

	void gtk_status_icon_get_geometry(GtkStatusIcon statusIcon, Pointer screen, GdkRectangle rect, Pointer orientation);

	void gtk_init(int argc, String[] arv);

	void gtk_main();

	void XInitThreads();

	// Menu

	Pointer gtk_menu_new();

	Pointer gtk_menu_item_new_with_label(String label);

	void gtk_menu_shell_append(Pointer menu, Pointer item);

	void gtk_menu_popup(Pointer menu, Pointer widget, Pointer bla, Function func, Pointer data, int button, int time);

	void gtk_widget_show_all(Pointer menu);

	void gtk_widget_show(Pointer gtkitem);

	void g_object_unref(Pointer object);

	void gtk_status_icon_set_title(GtkStatusIcon nativeIcon, String tooltip);

	class GtkCallback implements com.sun.jna.Callback {
		public void invoke(GtkStatusIcon status, int button, int activate_time, Pointer popup) {

		}
	}

	class GtkStatusIcon extends Structure {
		public NativeLong parent_instance;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList("parent_instance");
		}
	}

	class GdkRectangle extends Structure {
		public int x;
		public int y;
		public int width;
		public int height;

		@Override
		protected List getFieldOrder() {
			return Arrays.asList("x", "y", "width", "height");
		}
	}
}