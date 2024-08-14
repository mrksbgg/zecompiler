package ru.swat1x.zecompiler.reader;

public interface ClassContextReader {

  ReadJarContext readTargetContext();

  ReadClassContext readClassContext(String classPathLabel, String classContent);

}
