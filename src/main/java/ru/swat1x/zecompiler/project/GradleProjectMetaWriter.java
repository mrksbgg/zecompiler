package ru.swat1x.zecompiler.project;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.swat1x.zecompiler.util.FileUtil;
import ru.swat1x.zecompiler.writer.ClassContextWriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GradleProjectMetaWriter implements ProjectMetaWriter {

  File rootDirectory;

  @Override
  public void initializeProject(String projectName) {
    try {
      FileUtil.writeSimpleFile(
              new File(rootDirectory, "settings.gradle"),
              "rootProject.name = '%s'".formatted(projectName)
      );
    } catch (IOException e) {
      throw new ClassContextWriteException("Can't write gradle files to project", e);
    }
  }

  @Override
  public void initializeDependencies(List<Dependency> dependencies) {

  }
}
