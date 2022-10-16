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
    String resolveProjectVersion = "resolveProjectVersion";

    Path projectPath = Files.createTempDirectory("gradle-project");
    File buildFile = new File(projectPath.toFile(), "build.gradle");
    File settingsFile = new File(projectPath.toFile(), "settings.gradle");
    Files.writeString(buildFile.toPath(), """
        plugins {
          id 'robothy-platform-release'
        }
        """);
    Files.writeString(settingsFile.toPath(), """
        rootProject.name = 'test-resolve-project-version'
        """);
    projectPath.toFile().deleteOnExit();
    buildFile.deleteOnExit();
    settingsFile.deleteOnExit();

    BuildResult result = GradleRunner.create()
        .withProjectDir(projectPath.toFile())
        .withArguments(resolveProjectVersion)
        .withPluginClasspath()
        .build();
    assertSame(result.task(":" + resolveProjectVersion).getOutcome(), TaskOutcome.SUCCESS);
  }

}