package org.zeroturnaround.jf.homework12.helper;

interface ParentInterface extends GrandParentInterface {
  String parentMethod(String input);
  String overriddenMethod(String input);
}