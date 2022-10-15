package com.robothy.platform.release.gradle.task;

import com.robothy.platform.utils.PropertyFileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import javax.inject.Inject;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.LocalState;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

/**
 * Set the next version of current project.
 */
public class NextVersion extends DefaultTask {

  private final NextVersionResolver nextVersionResolver;

  private File gradlePropertiesFile;

  private Object nextVersion;

  /**
   * Constructor.
   *
   * @param nextVersionResolver next version resolver.
   */
  @Inject
  public NextVersion(NextVersionResolver nextVersionResolver) {
    this.nextVersionResolver = nextVersionResolver;
    super.setGroup(Constants.RELEASE_GROUP_NAME);
  }

  /**
   * Get Gradle property file.
   *
   * @return configured Gradle property file.
   */
  @Optional
  @InputFile
  public File getGradlePropertiesFile() {
    return gradlePropertiesFile;
  }

  /**
   * Set gradle properties file.
   * @param file gradle properties file. e.g. gradle.properties
   */
  public void setGradlePropertiesFile(File file) {
    this.gradlePropertiesFile = file;
  }

  /**
   * Get calculated next version in latest execution.
   *
   * @return next version.
   */
  @LocalState
  public Object getNextVersion() {
    return nextVersion;
  }

  /**
   * Task action.
   */
  @TaskAction
  public void exec() {
    this.nextVersion = nextVersionResolver.nextVersion(this);
    Path gradleProperties = gradlePropertyPath();
    if (!gradleProperties.toFile().exists()) {
      try {
        Files.writeString(gradleProperties, "version=" + nextVersion);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    PropertyFileUtils.upsertProperty(gradleProperties.toFile(), "version", nextVersion.toString());
  }

  private Path gradlePropertyPath() {
    File configuredFile = getGradlePropertiesFile();
    if (Objects.isNull(configuredFile)) {
      Path projectPath = getProject().getProjectDir().toPath().toAbsolutePath();
      return Paths.get(projectPath.toString(), "gradle.properties");
    } else {
      return configuredFile.toPath();
    }
  }

}
