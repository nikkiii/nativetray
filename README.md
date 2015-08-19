# nativetray

A Java TrayIcon library that allows you to use Gtk if supported on Linux to get past the [background issue in Java's TrayIcon](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6453521), which likely won't be fixed any time soon.

Installation
============
The project is temporarily hosted on my personal maven repository, which you can add to your pom.xml using this:

```
       <repositories>
           <repository>
               <id>nikkiius</id>
               <name>Nikkii.us Repository</name>
               <url>http://maven.nikkii.us/maven2</url>
           </repository>
       </repositories>
```

And include it in your dependencies:

```
			<dependency>
                <groupId>us.nikkii</groupId>
                <artifactId>nativetray</artifactId>
                <version>1.0</version>
            </dependency>
```

Usage
=====

Note: TrayIcon in this example is in package org.nikkii.jtray, while PopupMenu and MenuItem are java.awt

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