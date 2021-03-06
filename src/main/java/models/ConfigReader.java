package models;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ConfigReader {
	final private static Logger log = LoggerFactory.getLogger(ConfigReader.class);

	final private static ConfigReader reader = new ConfigReader();
	Map<String, Object> yaml = null;


	private static File getSettingsFile() throws FileNotFoundException {
		final String userFilePath = "conf/capybara.yaml";
		if (Files.exists(Paths.get(userFilePath))) {
			return new File(userFilePath);
		}

		final String defaultFilePath = "src/main/java/resources/capybara.yaml";
		if (Files.exists(Paths.get(defaultFilePath))) {
			return new File(defaultFilePath);
		}

		throw new FileNotFoundException();
	}

	private ConfigReader() {
		try {
			final File SettingsFile = getSettingsFile();
			yaml = (Map<String, Object>) new Yaml().load(com.google.common.io.Files.newReader(SettingsFile, Charsets.UTF_8));
		} catch (final FileNotFoundException e) {
			log.warn("config file is not exist.", e);
			throw new RuntimeException();
		}
	}

	public static ConfigReader getInstance() {
		return reader;
	}

	public int getPort() {
		return (int) yaml.get("port");
	}

	public String getDatabaseURI() {
		return (String) yaml.get("database.uri");
	}

	public String getDatabaseId() {
		return (String) yaml.get("database.id");
	}

	public String getDatabasePassword() {
		return (String) yaml.get("database.password");
	}
}
