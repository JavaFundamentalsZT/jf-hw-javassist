package org.zeroturnaround.jf.homework12;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.zeroturnaround.jf.homework12.helper.AClass;
import org.zeroturnaround.jf.homework12.helper.AnInterface;
import org.zeroturnaround.jf.homework12.helper.BClass;
import org.zeroturnaround.jf.homework12.helper.CClass;
import org.zeroturnaround.jf.homework12.helper.DClass;

public class HomeworkTest {
	private ProxyBuilder proxyBuilder = new ProxyBuilder();
	private AClass obj = new AClass("xxx");

	@Test
	public void testCreateProxyCallExistingMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("existingMethodfoo", proxy.existingMethod("foo"));
	}

  @Test
  public void testCreateProxyCallExistingMethodWithArgs() throws Exception {
    AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
    Assert.assertEquals("existingMethod", proxy.existingMethod());
  }

	@Test
	public void testCreateProxyCallAnotherMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("anotherMethodbar", proxy.anotherMethod("bar"));
	}

	@Test
	public void testCreateProxyCallStatefulMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("xxxrrr", proxy.statefulMethod("rrr"));
	}

	@Test
	public void testCreateProxyCallParentMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("parentMethodbaz", proxy.parentMethod("baz"));
	}

	@Test
	public void testCreateProxyCallGrandParentMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		Assert.assertEquals("grandParentMethodfoo", proxy.grandParentMethod("foo"));
	}

	@Test(expected=NoSuchMethodException.class)
	public void testCreateProxyCallNonExistingMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		proxy.missingMethod(); // Should throw the expected exception!
	}

	@Test(expected=NoSuchMethodException.class)
	public void testCreateProxyCallPrivateMethod() throws Exception {
	  BClass bClass = new BClass();
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, bClass);
		proxy.privateMethod("foo"); // Should throw the expected exception!
	}

	@Test(expected=NoSuchMethodException.class)
	public void testCreateProxyCallPackageMethod() throws Exception {
	  DClass dClass = new DClass();
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, dClass);
		proxy.packageMethod("foo"); // Should throw the expected exception!
	}

	@Test(expected=NoSuchMethodException.class)
	public void testCreateProxyCallProtectedMethod() throws Exception {
	  CClass cClass = new CClass();
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, cClass);
		proxy.protectedMethod("foo"); // Should throw the expected exception!
	}

	@Test
	public void testCreateProxyCallVoidMethod() throws Exception {
		AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
		List<String> input = new ArrayList<String>();
		proxy.voidReturnMethod(input);
		Assert.assertTrue(input.contains("added string"));
	}

  @Test
  public void testCreateProxyCallDefaultJavaLangObjectMethod() throws Exception {
    AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
    Assert.assertTrue(proxy.toString().startsWith("org.zeroturnaround.jf.homework12.helper.AClass@"));
  }

  @Test
  public void testCreateProxyCallOverriddenJavaLangObjectMethod() throws Exception {
    AnInterface proxy = proxyBuilder.proxify(AnInterface.class, obj);
    Assert.assertEquals(1234, proxy.hashCode());
  }
}