package com.plugin.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.plugin.publisher.IPublisher;

public class PluginLoader {

	private static Iterator<IPublisher> extendClasspath(File pluginsDir) throws IOException {
		File[] jarFiles = pluginsDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.getPath().toLowerCase().endsWith(".jar");
			}
		});
		URL[] urls = new URL[jarFiles.length];
		for (int i = 0; i < jarFiles.length; i++) {
			urls[i] = jarFiles[i].toURI().toURL();
		}
		URLClassLoader ucl = new URLClassLoader(urls);
		ServiceLoader<IPublisher> sl = ServiceLoader.load(IPublisher.class, ucl);
		System.out.println(sl.findFirst().isPresent() ? sl.findFirst().get() : null);
		return sl.iterator();
	}

	public static void main(String[] args) {
		try {
			extendClasspath(new File("plugins"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
