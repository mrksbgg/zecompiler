package ru.swat1x.zecompiler.reader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BaseReadResourceContext implements ReadResourceContext {

  String path;
  String name;
  String content;

}
