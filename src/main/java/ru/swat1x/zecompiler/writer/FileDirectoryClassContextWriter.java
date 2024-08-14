package ru.swat1x.zecompiler.writer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import ru.swat1x.zecompiler.options.ZecompilerOptions;
import ru.swat1x.zecompiler.project.ProjectMetaWriter;
import ru.swat1x.zecompiler.reader.ReadClassContext;
import ru.swat1x.zecompiler.reader.ReadJarContext;
import ru.swat1x.zecompiler.reader.ReadResourceContext;
import ru.swat1x.zecompiler.util.ClassKeys;
import ru.swat1x.zecompiler.util.FileUtil;

import java.io.File;
import java.io.IOException;

import static ru.swat1x.zecompiler.util.ClassKeys.DIR_DELIMITER;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileDirectoryClassContextWriter implements ClassContextWriter {

  ZecompilerOptions options;
  ProjectMetaWriter projectMetaWriter;

  File classFileDestination;
  File resourceFileDestination;

  public FileDirectoryClassContextWriter(ZecompilerOptions options, ProjectMetaWriter projectMetaWriter, File rootDirectory) {
    this.options = options;
    this.projectMetaWriter = projectMetaWriter;

    this.classFileDestination = new File(rootDirectory, "src/main/java");
    this.resourceFileDestination = new File(rootDirectory, "src/main/resources");
  }

  @Override
  public void writeJarContext(ReadJarContext jarContext) {
    projectMetaWriter.initializeProject(options.getProjectName());
    try {
      for (ReadClassContext readClassContext : jarContext.getReadClassContextList()) {
        writeClassContext(readClassContext);
      }

      for (ReadResourceContext readResourceContext : jarContext.getReadResourceContextList()) {
        writeResourceContext(readResourceContext);
      }
    } catch (Exception e) {
      log.error("Exception while writing jar", e);
    }
  }

  @Override
  public void writeClassContext(ReadClassContext classContext) {
    var classFileName = classContext.getClassName() + ".java";
    var destinationFile = getOrCreateFile(
            new File(classFileDestination, classContext.getPackageName().replace(".", DIR_DELIMITER)),
            classFileName
    );

    var formedContent = formClassContent(classContext);
    try {
      FileUtil.writeSimpleFile(
              destinationFile,
              formedContent
      );
      log.info("Formed and wrote {}", classFileName);
    } catch (Exception e) {
      log.error("Can't create write file for class " + classFileName, e);
    }
  }

  private File getOrCreateFile(File destinationDirectory, String fileName) {
    var destinationFile = new File(destinationDirectory, fileName);

    try {
      destinationDirectory.mkdirs();
      var createFile = destinationFile.createNewFile();
      if (!createFile && !options.isOverrideFiles())
        throw new ClassContextWriteException("File on path " + destinationFile.getAbsolutePath() + " is already exists!");
    } catch (IOException e) {
      throw new ClassContextWriteException("Can't create new file for class " + fileName, e);
    }

    return destinationFile;
  }

  private String formClassContent(ReadClassContext classContext) {
    StringBuilder sb = new StringBuilder();

    if (classContext.getPackageName() != null
            && !classContext.getPackageName().isEmpty()) {
      sb.append(ClassKeys.PACKAGE).append(classContext.getPackageName())
              .append(ClassKeys.BR).append(ClassKeys.BR);
    }

    for (ReadClassContext.Import anImport : classContext.getImportList()) {
      sb.append(anImport.isStatic() ? ClassKeys.IMPORT_STATIC : ClassKeys.IMPORT)
              .append(anImport.getLine())
              .append(ClassKeys.BR);
    }

    sb.append(ClassKeys.BR);
    sb.append(classContext.getClassContent());

    return sb.toString();
  }

  @Override
  public void writeResourceContext(ReadResourceContext resourceContext) {
    var destinationDirectory = resourceContext.getPath().isEmpty() ?
            resourceFileDestination :
            new File(resourceFileDestination, resourceContext.getPath());
    var destinationFile = getOrCreateFile(destinationDirectory, resourceContext.getName());

    try {
      FileUtil.writeSimpleFile(
              destinationFile,
              resourceContext.getContent()
      );
      log.info("Formed and wrote {}", resourceContext.getFullName());

    } catch (IOException e) {
      log.error("Can't create write file for resource " + resourceContext.getFullName());
    }
  }

}
