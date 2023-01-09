package com.zuiyu.util;

import javassist.*;

import java.io.IOException;

/**
 * @author zuiyu
 * @date 2023/1/8
 * @description
 * @link <a href="https://github.com/zuiyu-main">zuiyu GitHub</a>
 */
public class JavaClassUtils {
    public static void main(String[] args) throws NotFoundException, CannotCompileException, IOException {
        crackMethod("C:\\Users\\zcf\\Desktop\\aspose-words-18.6-jdk16.jar");
    }

    public static void crackMethod(String jarFile) throws NotFoundException, CannotCompileException, IOException {

        // 获取 Jar 包
        ClassPool.getDefault().insertClassPath(jarFile);
        // 获取 com.aspose.words.zzZLX 类
        ClassPool pool = ClassPool.getDefault();
        CtClass c2 = ClassPool.getDefault().getCtClass("com.aspose.words.zzWgI");
        // 查找并获取 private static void zzZ(Node var0, Node var1) 方法
        CtMethod aMethod = c2.getDeclaredMethod("zzZ", new CtClass[]{pool.get("org.w3c.dom.Node"), pool.get("org.w3c.dom.Node")});
        // 重置方法体
        aMethod.setBody("return;");
        c2.writeFile();

    }
}
