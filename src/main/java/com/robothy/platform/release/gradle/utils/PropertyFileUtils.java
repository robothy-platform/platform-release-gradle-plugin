package com.robothy.platform.release.gradle.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.function.UnaryOperator;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class PropertyFileUtils {

  /**
   * Upsert en entry to a properties file.
   *
   * @param propFile the properties file.
   * @param key the key.
   * @param updater consumes the old value and returns a new value.
   * @return the old value; or {@code null} if the key not exist.
   */
  @SneakyThrows
  public static String upsertProperty(File propFile, String key, UnaryOperator<String> updater) {
    File dest = new File(propFile.getAbsolutePath() + ".tmp");
    boolean exists = false;
    String old = null;
    try (BufferedReader reader = new BufferedReader(new FileReader(propFile));
         BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {
      String line;
      while ((line = reader.readLine()) != null && StringUtils.isNotBlank(line)) {
        String[] entry = line.split("=");
        if (entry.length != 2) {
          throw new IllegalArgumentException("Invalid entry. " + line);
        }

        String k = entry[0].trim();
        String v = entry[1].trim();
        if (Objects.equals(k, key)) {
          old = v;
          writer.write(k + "=" + updater.apply(v));
          exists = true;
        } else {
          writer.write(line);
        }
        writer.write('\n');
      }

      if (!exists) {
        writer.write(key + "=" + updater.apply(null));
      }
      writer.flush();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    Files.move(dest.toPath(), propFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    return old;
  }

  /**
   * Upsert a value to a property file.
   *
   * @param propFile the property file.
   * @param key      the property key.
   * @param newValue new value associate with the key.
   * @return the old value of the property; {@code  null} if the key not found.
   */
  public static String upsertProperty(File propFile, String key, String newValue) {
    return upsertProperty(propFile, key, v -> newValue);
  }

}
