package ru.swat1x.zecompiler.reader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BaseJarReadContext implements ReadJarContext {

  @Setter
  String projectName;

  final List<ReadClassContext> readClassContextList;
  final List<ReadResourceContext> readResourceContextList;

}
