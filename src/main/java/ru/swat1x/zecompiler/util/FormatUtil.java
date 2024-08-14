package ru.swat1x.zecompiler.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;

import static ru.swat1x.zecompiler.util.ClassKeys.DIR_DELIMITER;

@Log4j2
@UtilityClass
public class FormatUtil {

  public String[] readPackageAndName(String fileAbsolutePath) {
    var absolutePathArray = fileAbsolutePath.split(DIR_DELIMITER);
    var packageArray = absolutePathArray.length == 1 ? new String[]{} : Arrays.copyOfRange(absolutePathArray, 0, absolutePathArray.length - 1);

    var filePath = packageArray.length == 0 ? "" : String.join(DIR_DELIMITER, packageArray);
    var fileName = absolutePathArray[absolutePathArray.length - 1];

    return new String[]{
            filePath,
            fileName
    };
  }

}
