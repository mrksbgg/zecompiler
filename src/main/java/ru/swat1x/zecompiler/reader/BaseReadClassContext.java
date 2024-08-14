package ru.swat1x.zecompiler.reader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseReadClassContext implements ReadClassContext {

  String packageName = "";
  String className;
  List<ReadClassContext.Import> importList = new ArrayList<>();
  String classContent;

  @Getter
  @Setter
  @Accessors(chain = true)
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Import implements ReadClassContext.Import {

    String line;
    boolean isStatic;

  }

}
