package ru.swat1x.zecompiler.reader;

import java.util.List;

public interface ReadClassContext {

  String getPackageName();

  String getClassName();

  List<Import> getImportList();

  String getClassContent();

  interface Import {

    String getLine();

    boolean isStatic();

    default Static toStatic() {
      if (!isStatic())
        throw new UnsupportedOperationException("import " + getLine() + " is not static. can't convert to static");
      return (Static) this;
    }

    interface Static {

      String getMethod();

    }


  }


}
