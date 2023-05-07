package com.plugin.loader;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.plugin.publisher.IPublisher;

public class PluginLoader {

	public static Iterator<IPublisher> extendClasspath(File pluginsDir) throws IOException {
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
		
		// It extended the current current class loader context
		// URLClassLoader ucl = new URLClassLoader(urls, getContextClassLoader());
		
		ServiceLoader<IPublisher> sl = ServiceLoader.load(IPublisher.class, ucl);
		System.out.println(sl.findFirst().isPresent() ? sl.findFirst().get() : null);
		return sl.iterator();
	}
	
	/**
         * Wraps <code>Thread.currentThread().getContextClassLoader()</code> into a doPrivileged block if security manager is present
         */
        private static ClassLoader getContextClassLoader() {
            if (System.getSecurityManager() == null) {
                return Thread.currentThread().getContextClassLoader();
            } else {
                return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
                    public ClassLoader run() {
                        return Thread.currentThread().getContextClassLoader();
                    }
                });
            }
        }

	public static void main(String[] args) {
		try {
			extendClasspath(new File("plugins"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
