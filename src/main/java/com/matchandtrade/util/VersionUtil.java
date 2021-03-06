package com.matchandtrade.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionUtil {
	
	private static Logger LOGGER = LoggerFactory.getLogger(VersionUtil.class);

	private Properties versionProperties = new Properties();
	
	public VersionUtil() {
		try {
			ClassLoader classLoader = VersionUtil.class.getClassLoader();
			InputStream versionInputStream = classLoader.getResource("version.properties").openStream();
			versionProperties.load(versionInputStream);
		} catch (IOException e) {
			LOGGER.error("Unable to load version.properties", e.getMessage());
		}
	}

	public String buildNumber() {
		return versionProperties.getProperty("buildNumber");
	}

	public String buildTimestamp() {
		return versionProperties.getProperty("build.date");
	}

	public String projectArtefactId() {
		return versionProperties.getProperty("project.artefactId");
	}

	public String projectGroupId() {
		return versionProperties.getProperty("project.groupId");
	}
	
	public String projectName() {
		return versionProperties.getProperty("project.name");
	}
	
	public String projectVersion() {
		return versionProperties.getProperty("project.version");
	}

}
