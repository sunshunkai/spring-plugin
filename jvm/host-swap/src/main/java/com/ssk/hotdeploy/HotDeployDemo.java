package com.ssk.hotdeploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;

public class HotDeployDemo {

	//path
	public static final File CURRENT = new File("data");
	public static final File SOURCES_DIR = new File(CURRENT, "sources");
	public static final File BYTECODES_DIR = new File(CURRENT, "bytecodes");
	
	public static FileClassLoader classloader;
	public static EnhanceClass enhanceClass;
	
	public static String testFile = "fruit/Apple.java";

	private static String fileNameToClassName(String fileName) {
		return testFile.replace(".java", "").replace("/", ".");
	}

	static void load() {
		String javaCode = null;
		try {
			javaCode = IOUtils.toString(new FileInputStream(new File(SOURCES_DIR, testFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		enhanceClass.sourceCode = javaCode;
//		JdtCompiler.compile(testFile);
		String compiledClass = BYTECODES_DIR.getAbsolutePath() + File.separatorChar
				+ testFile.replace(".java", ".class");
		try {
			enhanceClass.byteCode = IOUtils.toByteArray(new FileInputStream(new File(compiledClass)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void runHello() {
		if (enhanceClass.hasChange()) {
			System.out.println("Class has been changed and reloaded ...");
			load();
		}

		String classDataRootPath = BYTECODES_DIR.getAbsolutePath();
		
		//载入方式一
//		FileClassLoader loader = new FileClassLoader(classDataRootPath);
		
		//载入方式二
		FileClassLoader loader = new FileClassLoader(enhanceClass);
		String className = fileNameToClassName(testFile);
		
		try {
			Class<?> class1 = loader.loadClass(className);
			Object obj1 = class1.newInstance();
			Method helloMethod = class1.getMethod("hello");
			helloMethod.invoke(obj1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		classloader = new FileClassLoader(SOURCES_DIR.getAbsolutePath());
		enhanceClass = new EnhanceClass();
		File javaFile = new File(SOURCES_DIR, testFile);
		enhanceClass.setFile(javaFile);
		//先部署
		load();
		for(int i = 0; i < 10; i++){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			runHello();
		}
	}
}
