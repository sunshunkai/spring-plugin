package com.ssk.hotdeploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * 增强类，存放待编译文件的路径、编译好的字节码
 * @author Eric - ericquan8@163.com
 *
 */
public class EnhanceClass {

	public String sourceCode;

	public long lastModifyTime;

	//编译好的字节码
	public byte[] byteCode;

	public File file;

	public void setFile(File f) {
		this.file = f;
		this.lastModifyTime = file.lastModified();
	}

	public boolean hasChange() {
		String javaCode = null;
		try {
			javaCode = IOUtils.toString(new FileInputStream(this.file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.sourceCode.hashCode() != javaCode.hashCode();
	}

}
