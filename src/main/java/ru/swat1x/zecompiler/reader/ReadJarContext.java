package ru.swat1x.zecompiler.reader;

import java.util.List;

public interface ReadJarContext {

  List<ReadClassContext> getReadClassContextList();

  List<ReadResourceContext> getReadResourceContextList();

}
