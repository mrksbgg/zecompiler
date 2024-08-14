package ru.swat1x.zecompiler.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.java.decompiler.main.DecompilerContext;
import ru.swat1x.zecompiler.writer.ClassContextWriteException;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@UtilityClass
public class FileUtil {

  public void writeSimpleFile(File toFile, String content) throws IOException {
    if (!toFile.exists()) toFile.createNewFile();

    try (var writeStream = new BufferedOutputStream(new FileOutputStream(toFile))) {
      writeStream.write(content.getBytes());
    }
  }

  public String readZipEntryContent(String zipPath, String entryPath) {
    StringBuilder contentBuilder = new StringBuilder();
    try (ZipFile srcArchive = new ZipFile(new File(zipPath))) {
      ZipEntry entry = srcArchive.getEntry(entryPath);
      if (entry != null) {
        try (var in = new BufferedReader(new InputStreamReader(srcArchive.getInputStream(entry)))) {
          String line;
          while ((line = in.readLine()) != null) contentBuilder.append(line).append("\n");
        }
      }
    } catch (IOException e) {
      throw new ClassContextWriteException("Cannot read entry " + entryPath + " from " + zipPath, e);
    }
    return contentBuilder.toString();
  }

}
