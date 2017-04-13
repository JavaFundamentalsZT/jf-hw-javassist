package org.zeroturnaround.jf;

import org.zeroturnaround.jf.homework12.ProxyBuilder;

public class Main {
	public static void main(String[] args) {
		// TODO:
		if (args.length < 2) {
			System.err.println("Usage: java -jar target/jf-homework12.jar interfaceClass objectClass");
			System.exit(0);
		}

		ProxyBuilder proxyBuilder = new ProxyBuilder();
	}
}
