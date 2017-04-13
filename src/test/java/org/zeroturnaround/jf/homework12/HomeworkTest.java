package org.zeroturnaround.jf.homework12;

import org.junit.Assert;
import org.junit.Test;
import org.zeroturnaround.jf.homework12.helper.AClass;
import org.zeroturnaround.jf.homework12.helper.AnInterface;

public class HomeworkTest {
	private ProxyBuilder proxyBuilder = new ProxyBuilder();
	private AClass obj = new AClass();

	@Test
	public void testCreateProxyCallExistingMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("firstMethod", proxy.firstMethod());
	}

	@Test
	public void testCreateProxyCallExistingMethodWithArgs() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("foo", proxy.secondMethod("foo"));
	}

	@Test
	public void testCreateProxyCallParentMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("parentMethod", proxy.parentMethod());
	}

	@Test(expected = NoSuchMethodException.class)
	public void testCreateProxyCallNonExistingMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		proxy.missingMethod(); // Should throw the expected exception!
	}
}