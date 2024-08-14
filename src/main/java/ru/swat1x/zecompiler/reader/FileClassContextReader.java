package ru.swat1x.zecompiler.reader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.java.decompiler.main.decompiler.BaseDecompiler;
import org.jetbrains.java.decompiler.main.decompiler.PrintStreamLogger;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import ru.swat1x.zecompiler.util.ClassKeys;
import ru.swat1x.zecompiler.util.FormatUtil;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;

import static ru.swat1x.zecompiler.util.ClassKeys.DIR_DELIMITER;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileClassContextReader implements ClassContextReader {

  File targetJarFile;

  @Override
  public ReadJarContext readTargetContext() {
    var resultSaver = new BaseClassContextResultSaver(this);
    var decompiler = new BaseDecompiler(
            resultSaver,
            new HashMap<>(),
            IFernflowerLogger.NO_OP
    );
    decompiler.addSource(targetJarFile);
    decompiler.decompileContext();

    return new BaseJarReadContext(
            resultSaver.getReadClassContextList(),
            resultSaver.getReadResourceContextList()
    );
  }

  @Override
  public ReadClassContext readClassContext(String classPathLabel, String classContent) {
    var lines = classContent.split(ClassKeys.BR);

    var context = new BaseReadClassContext();
    var classPath = FormatUtil.readPackageAndName(classPathLabel);

    context.setClassName(classPath[1].substring(0, classPath[1].length() - ClassKeys.CLASS_SUFFIX.length()));
    context.setPackageName(classPath[0].replace(DIR_DELIMITER, "."));

    boolean isContentStarted = false;
    StringBuilder contentBuilder = new StringBuilder();
    for (String line : lines) {
      if (!isContentStarted) {
        if (line.startsWith(ClassKeys.PACKAGE) || handleImportLine(context, line)) {
          continue;
        } else isContentStarted = isClassLine(line);
      }

      if (isContentStarted) {
        contentBuilder.append(line).append(ClassKeys.BR);
      }
    }

    context.setClassContent(contentBuilder.toString());

    return context;
  }

  private boolean handleImportLine(ReadClassContext context, String line) {
    if (line.startsWith(ClassKeys.IMPORT_STATIC)) {
      addImport(context, line, ClassKeys.IMPORT_STATIC, true);
    } else if (line.startsWith(ClassKeys.IMPORT)) {
      addImport(context, line, ClassKeys.IMPORT, false);
    } else return false;
    return true;
  }

  private boolean isClassLine(String line) {
    return line.startsWith(ClassKeys.ANNOTATION_PREFIX) ||
            line.contains(ClassKeys.PUBLIC) ||
            line.contains(ClassKeys.STATIC) ||
            line.contains(ClassKeys.CLASS);
  }

  private void addImport(ReadClassContext context, String line, String splitter, boolean isStatic) {
    context.getImportList()
            .add(new BaseReadClassContext.Import()
                    .setStatic(isStatic)
                    .setLine(line.split(splitter)[1])
            );
  }

}
