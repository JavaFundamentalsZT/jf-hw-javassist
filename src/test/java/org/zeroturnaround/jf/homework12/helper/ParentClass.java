package org.zeroturnaround.jf.homework12.helper;

public class ParentClass extends GrandParent {
  public String parentMethod(String input) {
    return "parentMethod"+input;
  }

  public String parentMethod() {
    return "wrong arguments";
  }

  public String overriddenMethod(String input) {
    return "overriddenMethod"+input;
  }
}
