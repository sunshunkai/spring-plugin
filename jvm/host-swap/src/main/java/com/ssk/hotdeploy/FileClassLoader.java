package com.ssk.hotdeploy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 自己实现的classloader
 * @author Eric - ericquan8@163.com
 *
 */
public class FileClassLoader extends ClassLoader {

	private String rootDir;
	
	private EnhanceClass ec;

	//从传入的路径中载入class
	public FileClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}

	//从传入的enhanceclass中载入class
	public FileClassLoader(EnhanceClass ec) {
		this.ec = ec;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = null;
		if(ec != null){
			//从增强类中获取编译完的代码
			classData = ec.byteCode; 
		}
		if(rootDir != null && rootDir.length() > 0){
			//从具体文件中获取编译完的代码
			classData = getClassData(name);
		}
		if (classData == null) {
			throw new ClassNotFoundException();
		}
		else {
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		String path = classNameToPath(className);
		try {
			InputStream ins = new FileInputStream(path);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bufferSize = 4096;
			byte[] buffer = new byte[bufferSize];
			int bytesNumRead = 0;
			while ((bytesNumRead = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesNumRead);
			}
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String classNameToPath(String className) {
		return rootDir + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
	}
}
