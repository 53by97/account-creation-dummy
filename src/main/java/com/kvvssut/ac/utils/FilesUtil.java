/**
 * 
 */
package com.kvvssut.ac.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import com.kvvssut.ac.constants.ApplicationConstants;

/**
 * @author srimantas 19-Jan-2017
 */
public final class FilesUtil {

	/**
	 * @param directoryPath
	 * @return
	 */
	public static List<Path> listCSVFilesForDirectory(final String directoryPath) {
		final List<Path> files = new ArrayList<Path>();

		try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
			paths.forEach(filePath -> {
				if (isCSVFile(filePath.getFileName().toString())) {
					files.add(filePath);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

	/**
	 * @param filePath
	 * @return
	 */
	public static boolean isCSVFile(final String fileName) {
		return FilenameUtils.isExtension(fileName, ApplicationConstants.FILE_EXTENSION_CSV);
	}

}
