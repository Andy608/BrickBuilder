package com.bountive.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.bountive.util.logger.LoggerUtil;
import com.bountive.util.resource.ResourceLocation;

public class FileUtil {

	public static String[] getAllLinesFromExternalFileAsArray(File file) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
		return lineValues.toArray(new String[lineValues.size()]);
	}
	
	public static List<String> getAllLinesFromFileAsList(File file) throws IOException {
		List<String> lineValues = null;
		lineValues = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
		return lineValues;
	}
	
	public static String[] getAllLinesFromInternalFileAsArray(ResourceLocation location) {
		try (BufferedReader r = new BufferedReader(new InputStreamReader(FileUtil.class.getClassLoader().getResourceAsStream(location.getFullPath())))) {
			List<String> result = new ArrayList<>();
            for (;;) {
                String line = r.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result.toArray(new String[result.size()]);
		} catch (Exception e) {
			LoggerUtil.logWarn(Thread.currentThread(), e, "Could not read from file or file is corrupt. File Path: " + location, false);
			return null;
		}
	}
}
