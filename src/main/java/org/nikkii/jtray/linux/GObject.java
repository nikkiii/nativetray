package org.nikkii.jtray.linux;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class GObject {
	public static GObjectNative gobj = (GObjectNative) Native.loadLibrary("gobject-2.0", GObjectNative.class);

	public static void g_signal_connect_data(Pointer instance, String detailed_signal, Callback c_handler, Pointer data, Pointer destroy_data, int connect_flags) {
		gobj.g_signal_connect_data(instance, detailed_signal, c_handler, data, destroy_data, connect_flags);
	}
}
