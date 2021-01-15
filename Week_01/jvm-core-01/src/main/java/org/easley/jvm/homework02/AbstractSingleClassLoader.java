package org.easley.jvm.homework02;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 只能加载一个class文件的ClassLoader。
 * ---------------------------------
 * 若需要一个ClassLoader加载多个Class文件，可做如下修改：
 * 1.{@code className}和{@code classFilePath}取消final限制，并从构造方法中移除；
 * 2.新增一个private字段，类型为{@code LoadClassBytesPostProcessor},
 *   把 {@link #postLoadClassBytes(byte[])}扔进去（类似{@link org.springframework.beans.factory.config.BeanPostProcessor}）;
 * 3.新增一个public方法{@code loadClass(String className, String classPath, LoadClassBytesPostProcessor postProcessor)}
 * 只是这样改动之后，需要做相当多的初始化和空指针检查，写的就没那么好看了（偷懒 √）。
 *
 * @author Easley
 * @date 2021/1/14
 * @since 1.0
 */
public abstract class AbstractSingleClassLoader extends ClassLoader {
    private final String className;
    private final String classFilePath;
    protected Class<?> clazz;

    protected AbstractSingleClassLoader(String className, String classFilePath) {
        this.className = className;
        this.classFilePath = classFilePath;
    }

    public Object newClassInstance() {
        try {
            if (clazz == null) {
                clazz = findClass(className);
            }
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(classFilePath + " load error.", e);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(className + " instantiation error.", e);
        }
    }

    public Object invokeClassMethod(Object target, String methodName, Object... args) {
        if (target == null) {
            target = newClassInstance();
        }
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            return method.invoke(target, args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("No such method: " + methodName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Illegal access method: " + methodName, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invocation target error.", e);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = loadAsBytes(classFilePath);
        postLoadClassBytes(classBytes);
        return defineClass(name, classBytes, 0, classBytes.length);
    }

    /**
     * 在加载了class文件字节数组后执行的操作。
     * --------------------------------
     * 字节数组不需要特殊处理时，实现一个空方法即可，
     * 在{@link HelloClassLoader}中则对字节进行了解码处理。
     *
     * @param classBytes classFilePath下类文件的字节数组
     * @author Easley
     * @date 2021/1/15
     * @since 1.0
     */
    protected abstract void postLoadClassBytes(byte[] classBytes);

    private byte[] loadAsBytes(String filePath) throws ClassNotFoundException {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (in != null) {
                return inputStream2bytes(in);
            } else {
                throw new IllegalArgumentException("File path cannot be null.");
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(filePath + " load error.");
        }
    }

    private static byte[] inputStream2bytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];

        int readLength;
        while ((readLength = in.read(buff)) > 0) {
            out.write(buff, 0, readLength);
        }
        return out.toByteArray();
    }
}
