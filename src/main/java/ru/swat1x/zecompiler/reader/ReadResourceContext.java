package ru.swat1x.zecompiler.reader;

import static ru.swat1x.zecompiler.util.ClassKeys.DIR_DELIMITER;

public interface ReadResourceContext {

  default String getFullName() {
    return (getPath().isBlank() ? "" : getPath() + DIR_DELIMITER) + getName();
  }

  String getPath();

  String getName();

  String getContent();

}
