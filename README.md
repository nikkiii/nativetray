# nativetray


Usage
=====

```java
TrayIcon icon = new TrayIcon(ImageIO.read(new File("icon.png")));

PopupMenu menu = new PopupMenu();

menu.add(new MenuItem("Test"));

icon.setPopupMenu(menu);

SystemTray.getSystemTray().add(icon);
```

Possible Issues
===============
XInitThreads may not be needed, and objects may leak when removing icons, at least for the native side. I'm no master with Gtk, and I did the best that I could.