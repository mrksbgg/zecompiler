package ru.swat1x.zecompiler.writer;

public class ClassContextWriteException extends RuntimeException {

  public ClassContextWriteException(String message) {
    super(message);
  }

  public ClassContextWriteException(String message, Throwable cause) {
    super(message, cause);
  }

}
