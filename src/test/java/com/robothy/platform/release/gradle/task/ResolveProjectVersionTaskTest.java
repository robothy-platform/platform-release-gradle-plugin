package com.robothy.platform.release.gradle.task;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.Test;

class ResolveProjectVersionTaskTest {

  @Test
  public void test() throws IOException {
    String releaseVersion = "releaseVersion";

    Path projectPath = Files.createTempDirectory("gradle-project");
    File buildFile = new File(projectPath.toFile(), "build.gradle");
    File settingsFile = new File(projectPath.toFile(), "settings.gradle");
    File propFile = new File(projectPath.toFile(), "gradle.properties");
    Files.writeString(buildFile.toPath(), """
        plugins {
          id 'robothy-platform-release'
        }
        """);
    Files.writeString(settingsFile.toPath(), """
        rootProject.name = 'test-resolve-project-version'
        """);
    Files.writeString(propFile.toPath(), """
        version=1.0
        """);

    projectPath.toFile().deleteOnExit();
    buildFile.deleteOnExit();
    settingsFile.deleteOnExit();

    BuildResult result = GradleRunner.create()
        .withProjectDir(projectPath.toFile())
        .withArguments(releaseVersion)
        .withPluginClasspath()
        .build();
    assertSame(result.task(":" + releaseVersion).getOutcome(), TaskOutcome.SUCCESS);
  }

}