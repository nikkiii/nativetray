package org.nikkii.jtray.linux;

import com.sun.jna.Callback;
import com.sun.jna.Function;
import com.sun.jna.Pointer;
import org.nikkii.jtray.TrayIcon;
import org.nikkii.jtray.TrayIcon.MessageType;
import org.nikkii.jtray.TrayIconImpl;
import org.nikkii.jtray.linux.GtkNative.GdkRectangle;
import org.nikkii.jtray.linux.GtkNative.GtkStatusIcon;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class NativeTrayIconImpl implements TrayIconImpl {

	private static final Function gtk_status_icon_position_menu = Function.getFunction("gtk-x11-2.0", "gtk_status_icon_position_menu");

	/**
	 * Our Icon pointer.
	 */
	private GtkStatusIcon nativeIcon;

	/**
	 * The menu pointer.
	 */
	private Pointer menu;

	/**
	 * The icon instance.
	 */
	private TrayIcon icon;

	public NativeTrayIconImpl(TrayIcon icon) {
		this.icon = icon;

		nativeIcon = Gtk.gtk_status_icon_new();

		Gtk.gtk_status_icon_set_visible(nativeIcon, false);

		GObject.g_signal_connect_data(nativeIcon.getPointer(), "activate", new IconCallback(), null, null, 0);
	}

	@Override
	public void show() {
		Gtk.gtk_status_icon_set_visible(nativeIcon, true);
	}

	@Override
	public void hide() {
		Gtk.gtk_status_icon_set_visible(nativeIcon, false);
	}

	@Override
	public void setImage(Image image) {
		try {
			// Set icon from file
			setImage(getIconAsFile(image).getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setImage(URL imageUrl) {
		File file = new File(imageUrl.getFile());
		if (file.exists()) {
			setImage(file.getAbsolutePath());
			return;
		}

		try {
			file = File.createTempFile("jTrayIcon", "img");
			file.deleteOnExit();

			InputStream input = imageUrl.openStream();

			OutputStream output = new FileOutputStream(file);
			byte[] temp = new byte[1024];

			int copied = 0;
			while (copied < file.length()) {
				int read = input.read(temp, 0, 1024);
				if (read < 0) {
					throw new EOFException();
				}
				output.write(temp, 0, read);
				copied += read;
			}

			// Set it to the temporary file
			setImage(file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the image path.
	 *
	 * @param path The path to the image.
	 */
	private void setImage(String path) {
		Gtk.gtk_status_icon_set_from_file(nativeIcon, path);
	}

	@Override
	public void setToolTip(String tooltip) {
		Gtk.gtk_status_icon_set_tooltip(nativeIcon, tooltip);
		Gtk.gtk_status_icon_set_title(nativeIcon, tooltip);
	}

	@Override
	public void setPopupMenu(PopupMenu popup) {
		// Generate a menu off of the popup
		menu = Gtk.gtk_menu_new();

		for (int i = 0; i < popup.getItemCount(); i++) {
			MenuItem item = popup.getItem(i);

			Pointer p = createNativeItem(item);

			// Unused pointer, attach it to something?
		}

		GObject.g_signal_connect_data(nativeIcon.getPointer(), "popup-menu", new PopupCallback(), menu, null, 0);
	}

	private static Map<Image, File> cachedIcons = new HashMap<>();

	private static File getIconAsFile(Image image) throws IOException {
		if (cachedIcons.containsKey(image)) {
			return cachedIcons.get(image);
		}

		File file = File.createTempFile("jTrayIcon", ".png");
		file.deleteOnExit();
		// Convert image to a BufferedImage
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();
		// Save image to file
		ImageIO.write(bimage, "png", file);
		cachedIcons.put(image, file);
		return file;
	}

	/**
	 * Create a Gtk menu item, add it to the menu, and hook the 'activate' signal (Click)
	 *
	 * @param item The item  to add
	 * @return A pointer referencing the new item
	 */
	private Pointer createNativeItem(MenuItem item) {
		Pointer p = Gtk.gtk_menu_item_new_with_label(item.getLabel());

		Gtk.gtk_menu_shell_append(menu, p);

		Gtk.gtk_widget_show(p);

		GObject.g_signal_connect_data(p, "activate", new MenuItemCallback(item), null, null, 0);

		return p;
	}

	@Override
	public void displayMessage(String caption, String text, MessageType messageType) {
		GdkRectangle rect = new GdkRectangle();
		Gtk.gtk_status_icon_get_geometry(nativeIcon, null, rect, null);

		throw new UnsupportedOperationException("NativeTrayIconImpl.displayMessage is not yet implemented");
	}

	public class IconCallback implements Callback {
		public void invoke(Pointer instance, Pointer data) {
			icon.fireActionEvent(new ActionEvent(ActionEvent.ACTION_PERFORMED, 0, ""));
		}
	}

	/**
	 * A JNA Callback to invoke gtk_menu_popup when "popup-menu" is called
	 *
	 * @author Nikki
	 */
	public class PopupCallback implements Callback {
		public void invoke(Pointer instance, int button, int activate_time, Pointer data) {
			Gtk.gtk_menu_popup(data, null, null, gtk_status_icon_position_menu, instance, button, activate_time);
		}
	}

	/**
	 * A JNA Callback to invoke the action listeners attached to a menu item
	 *
	 * @author Nikki
	 */
	public class MenuItemCallback implements Callback {
		private MenuItem item;

		public MenuItemCallback(MenuItem item) {
			this.item = item;
		}

		public void invoke(Pointer gtkItem, Pointer data) {
			ActionEvent e = new ActionEvent(ActionEvent.ACTION_PERFORMED, 0, item.getActionCommand());
			for (ActionListener l : item.getActionListeners()) {
				l.actionPerformed(e);
			}
		}
	}
}