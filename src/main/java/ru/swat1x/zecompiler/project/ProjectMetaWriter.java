package ru.swat1x.zecompiler.project;

import java.util.List;

public interface ProjectMetaWriter {

  void initializeProject(String projectName);

  void initializeDependencies(List<Dependency> dependencies);

}
