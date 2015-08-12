package org.nikkii.jtray.linux;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;

public interface GObjectNative extends Library {
	void g_signal_connect_data(Pointer instance, String detailed_signal, Callback c_handler, Pointer data, Pointer destroy_data, int connect_flags);
}