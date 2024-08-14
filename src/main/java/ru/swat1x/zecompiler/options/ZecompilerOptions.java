package ru.swat1x.zecompiler.options;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ZecompilerOptions {

  String groupId;

  String projectName;

  String version;

  boolean overrideFiles = false;

}
