package org.zeroturnaround.jf.homework12.helper;

import java.util.List;

public interface AnInterface extends ParentInterface {
  String existingMethod();
  String existingMethod(String input);
  String anotherMethod(String input) throws Exception;
  String missingMethod();
  String statefulMethod(String input);
  String privateMethod(String input);
  String protectedMethod(String input);
  String packageMethod(String input);
  void voidReturnMethod(List<String> strings);
  String toString();
  int hashCode();
}