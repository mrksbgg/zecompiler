package ru.swat1x.zecompiler.reader;

import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.util.List;

public interface ClassContextResultSaver extends IResultSaver {

  List<ReadClassContext> getReadClassContextList();

  List<ReadResourceContext> getReadResourceContextList();

}
