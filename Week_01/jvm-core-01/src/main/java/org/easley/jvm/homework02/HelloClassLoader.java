package org.easley.jvm.homework02;

/**
 * 实现了抽象自定义ClassLoader
 *
 * @author Easley
 * @date 2021/1/15
 * @since 1.0
 */
public class HelloClassLoader extends AbstractSingleClassLoader {

    public HelloClassLoader(String className, String classFilePath) {
        super(className, classFilePath);
    }

    public static void main(String[] args) {
        AbstractSingleClassLoader cl = new HelloClassLoader("Hello", "Hello.xlass");
        cl.invokeClassMethod(cl.newClassInstance(), "hello");
    }

    @Override
    protected void postLoadClassBytes(byte[] classBytes) {
        decode(classBytes);
    }

    private static void decode(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
    }
}
