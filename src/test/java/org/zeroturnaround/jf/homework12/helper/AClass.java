package org.zeroturnaround.jf.homework12.helper;

import java.util.List;

public class AClass extends ParentClass {
  private final String value;

  public AClass(String value) {
    this.value = value;
  }

  public static String staticMethod(String input) {
    return "static"+input;
  }

  public String statefulMethod(String input) {
    return value+input;
  }

  public String existingMethod() {
    return "existingMethod";
  }

  public String existingMethod(String input) {
    return "existingMethod"+input;
  }

  public String anotherMethod(String input) {
    return "anotherMethod"+input;
  }

  public String nonExistingMethod(String input) {
    return "nonExistingMethod"+input;
  }

  public String overriddenMethod(String input) {
    return "childOverride"+input;
  }

  public int hashCode() {
    return 1234;
  }

  public void voidReturnMethod(List<String> strings) {
    strings.add("added string");
  }
}