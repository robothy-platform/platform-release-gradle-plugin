package com.robothy.platform.release.gradle.task;

/**
 * Update the {@code version} property of gradle.properties.
 * Remove the "-SNAPSHOT" suffix.
 * <pre>
 *   1.0-SNAPSHOT -> 1.0
 *   1.0 -> 1.0
 * </pre>
 */
public class ReleaseVersionTask extends AbstractVersionUpdater {

  private static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

  @Override
  public String update(String oldVersion) {
    return oldVersion.endsWith(SNAPSHOT_SUFFIX)
        ? oldVersion.substring(0, oldVersion.length() - SNAPSHOT_SUFFIX.length())
        : oldVersion;
  }

}
