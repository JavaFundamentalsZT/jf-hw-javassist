package org.zeroturnaround.jf.homework12.helper;

public interface AnInterface extends ParentInterface {
	String firstMethod();

	String secondMethod(String input) throws Exception;

	String missingMethod();
}