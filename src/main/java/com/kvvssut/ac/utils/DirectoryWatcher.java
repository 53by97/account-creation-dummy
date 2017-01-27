/**
 * 
 */
package com.kvvssut.ac.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;

/**
 * @author srimantas 19-Jan-2017
 */
public final class DirectoryWatcher {

	public static WatchService registerDirectory(final Path path) {
		WatchService watchService = null;

		try {
			watchService = path.getFileSystem().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return watchService;
	}

}
