package ru.swat1x.zecompiler.writer;

import ru.swat1x.zecompiler.reader.ReadJarContext;
import ru.swat1x.zecompiler.reader.ReadClassContext;
import ru.swat1x.zecompiler.reader.ReadResourceContext;

public interface ClassContextWriter {

  void writeJarContext(ReadJarContext jarContext);

  void writeClassContext(ReadClassContext classContext);

  void writeResourceContext(ReadResourceContext resourceContext);

}
