package ru.swat1x.zecompiler.reader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.swat1x.zecompiler.util.FileUtil;
import ru.swat1x.zecompiler.util.FormatUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Manifest;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BaseClassContextResultSaver implements ClassContextResultSaver {

  ClassContextReader reader;
  @Getter
  List<ReadClassContext> readClassContextList = new ArrayList<>();
  @Getter
  List<ReadResourceContext> readResourceContextList = new ArrayList<>();

  @Override
  public void saveFolder(String path) {

  }

  @Override
  public void copyFile(String source, String path, String entryName) {

  }

  @Override
  public void saveClassFile(String path, String qualifiedName, String entryName, String content, int[] mapping) {

  }

  @Override
  public void createArchive(String path, String archiveName, Manifest manifest) {

  }

  @Override
  public void saveDirEntry(String path, String archiveName, String entryName) {

  }

  @Override
  public void copyEntry(String source, String path, String archiveName, String entryName) {
    var content = FileUtil.readZipEntryContent(source, entryName);
    var resourcePath = FormatUtil.readPackageAndName(entryName);

    readResourceContextList.add(new BaseReadResourceContext(resourcePath[0], resourcePath[1], content));
  }

  @Override
  public void saveClassEntry(String path, String archiveName, String qualifiedName, String entryName, String content) {
    readClassContextList.add(reader.readClassContext(
            entryName,
            content
    ));
  }

  @Override
  public void closeArchive(String path, String archiveName) {

  }
}
