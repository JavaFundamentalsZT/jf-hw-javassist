package org.zeroturnaround.jf.homework12;

import static javassist.Modifier.*;

import java.io.IOException;
import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

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
   *
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
   * <p>
   * For all public instance methods inside the interface hierarchy (i.e. the
   * interface itself and all interfaces it extends from and those extend from up to
   * java.lang.Object)
   * - If the object (or its hierarchy) has a method with a similar signature,
   * calling the method on the dynamic proxy should invoke that method on the object
   * itself. Basically the dynamic proxy acts as a wrapped delegating method calls to the
   * wrapped object.
   * - If the object (or its hierarchy) does not have a method with the exact same
   * signature, calling the method on the dynamic proxy should result in a
   * java.lang.NoSuchMethodException to be thrown
   *
   * @param iface The interface to conform to
   * @param obj   The object to create a proxy for
   * @param <T>   The interface type
   * @return a dynamic proxy of the object that conforms to the interface
   * @throws Exception
   */
  private <T> byte[] proxifyBytes(Class<T> iface, Object delegate) throws NotFoundException, CannotCompileException, IOException {
    ClassPool cp = new ClassPool(true);
    String delegateType = delegate.getClass().getName();

    CtClass proxyClass = cp.makeClass(getProxyClassName(iface, delegate.getClass()));
    CtClass ifaceClass = cp.get(iface.getName());
    CtClass delegateClass = cp.get(delegateType);

    proxyClass.addInterface(cp.get(
        iface.getName()
    ));
    proxyClass.addField(CtField.make(
        "private final " + delegateType + " delegate;",
        proxyClass
    ));
    proxyClass.addConstructor(CtNewConstructor.make(
        new CtClass[]{cp.get(delegateType)},
        null,
        "this.delegate = $1;",
        proxyClass
    ));

    Arrays.stream(ifaceClass.getMethods())
        .filter(this::isAbstract)
        .forEach(ifaceMethod -> addMethod(ifaceMethod, delegateClass.getMethods(), proxyClass));

    return proxyClass.toBytecode();
  }

  private void addMethod(CtMethod ifaceMethod, CtMethod[] delegateMethods, CtClass proxyClass) {
    boolean methodsMatch = Arrays.stream(delegateMethods)
        .anyMatch(delegateMethod -> hasMatchingSignature(ifaceMethod, delegateMethod));
    String body = methodsMatch ?
        "return delegate." + ifaceMethod.getName() + "($$);" :
        "throw new NoSuchMethodException();";

    try {
      proxyClass.addMethod(CtNewMethod.make(
          ifaceMethod.getReturnType(),
          ifaceMethod.getName(),
          ifaceMethod.getParameterTypes(),
          ifaceMethod.getExceptionTypes(),
          body,
          proxyClass
      ));
    }
    catch (CannotCompileException | NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean hasMatchingSignature(CtMethod ifaceMethod, CtMethod delegateMethod) {
    try {
      return ifaceMethod.getName().equals(delegateMethod.getName())
          && ifaceMethod.getReturnType().equals(delegateMethod.getReturnType())
          && Arrays.equals(ifaceMethod.getParameterTypes(), delegateMethod.getParameterTypes())
          && Arrays.equals(ifaceMethod.getExceptionTypes(), delegateMethod.getExceptionTypes())
          && isPublic(ifaceMethod)
          && isPublic(delegateMethod)
          && !isStatic(ifaceMethod)
          && !isStatic(delegateMethod);
    }
    catch (NotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean isPublic(CtMethod method) {
    return (method.getModifiers() & PUBLIC) != 0;
  }

  private boolean isStatic(CtMethod method) {
    return (method.getModifiers() & STATIC) != 0;
  }

  private boolean isAbstract(CtMethod method) {
    return (method.getModifiers() & ABSTRACT) != 0;
  }
}
