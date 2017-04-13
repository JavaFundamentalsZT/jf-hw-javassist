package org.zeroturnaround.jf.homework12;

public class ProxyBuilder {

	/**
	 * ProxyClassLoader for loading the proxy class
	 */
	private class ProxyClassLoader extends ClassLoader {
		byte[] bytes;

		public ProxyClassLoader(ClassLoader parent, byte[] bytes) {
			super(parent);
			this.bytes = bytes;
		}

		public Class findClass(String name) {
			return defineClass(name, bytes, 0, bytes.length);
		}
	}

	private <T, U> String getProxyClassName(Class<T> iface, Class<U> obj) {
		return obj.getName() + "_" + iface.getSimpleName() + "_Proxy";
	}

	/**
	 * Method for creating a proxy object for some other object. The proxy object is set
	 * to conform to the interface specified by iface.
	 * @param iface
	 * @param obj
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public <T> T proxify(Class<T> iface, Object obj) throws Exception {
		byte[] proxyBytes = proxifyBytes(iface, obj);
		ProxyClassLoader cl = new ProxyClassLoader(obj.getClass().getClassLoader(), proxyBytes);
		Class proxyClass = cl.loadClass(getProxyClassName(iface, obj.getClass()));
		return (T) proxyClass.getDeclaredConstructor(obj.getClass()).newInstance(obj);
	}

	/**
	 * Method for creating bytecode for a dynamic proxy class of an object that conforms
	 * to the interface specified by iface.
	 *
	 * For all public instance methods inside the interface hierarchy (i.e. the
	 * interface itself and all interfaces it extends from and those extend from up to
	 * java.lang.Object)
	 * - If the object (or its hierarchy) has a method with a similar signature,
	 *   calling the method on the dynamic proxy should invoke that method on the object
	 *   itself. Basically the dynamic proxy acts as a wrapped delegating method calls to the
	 *   wrapped object.
	 * - If the object (or its hierarchy) does not have a method with the exact same
	 *   signature, calling the method on the dynamic proxy should result in a
	 *   java.lang.NoSuchMethodException to be thrown
	 *
	 * @param iface The interface to conform to
	 * @param obj The object to create a proxy for
	 * @param <T> The interface type
	 *
	 * @return a dynamic proxy of the object that conforms to the interface
	 *
	 * @throws Exception
	 */
	public <T> byte[] proxifyBytes(Class<T> iface, Object obj) throws Exception {
		// TODO: implement me!
		return null;
	}
}
