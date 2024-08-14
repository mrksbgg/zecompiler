package ru.swat1x.zecompiler;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.swat1x.zecompiler.options.ZecompilerOptions;
import ru.swat1x.zecompiler.project.GradleProjectMetaWriter;
import ru.swat1x.zecompiler.reader.FileClassContextReader;
import ru.swat1x.zecompiler.writer.FileDirectoryClassContextWriter;

import java.io.File;

@Getter
@Log4j2
public class ZecompilerBootstrap {

  public static void main(String[] args) {
    var reader = new FileClassContextReader(new File("test", "ExChat.jar"));
    var context = reader.readTargetContext();

    var outputDirectory = new File("output");
    var writer = new FileDirectoryClassContextWriter(
            new ZecompilerOptions().setOverrideFiles(true),
            new GradleProjectMetaWriter(outputDirectory),
            outputDirectory
    );

    writer.writeJarContext(context);
  }

}
