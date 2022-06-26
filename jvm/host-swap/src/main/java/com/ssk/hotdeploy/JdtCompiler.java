//package com.ssk.hotdeploy;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Locale;
//import java.util.Map;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.IOUtils;
//import org.eclipse.jdt.core.compiler.IProblem;
//import org.eclipse.jdt.internal.compiler.ClassFile;
//import org.eclipse.jdt.internal.compiler.CompilationResult;
//import org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies;
//import org.eclipse.jdt.internal.compiler.ICompilerRequestor;
//import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
//import org.eclipse.jdt.internal.compiler.IProblemFactory;
//import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
//import org.eclipse.jdt.internal.compiler.classfmt.ClassFormatException;
//import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
//import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
//import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
//import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
//import org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory;
//
//public class JdtCompiler {
//
//	public static void compile(String fileName) {
//		INameEnvironment nameEnvironment = new INameEnvironment() {
//
//			/**
//			 * @param compoundTypeName
//			 *            {{'j','a','v','a'}, {'l','a','n','g'}}
//			 */
//			public NameEnvironmentAnswer findType(
//					final char[][] compoundTypeName) {
//				return findType(join(compoundTypeName));
//			}
//
//			public NameEnvironmentAnswer findType(final char[] typeName,
//					final char[][] packageName) {
//				return findType(join(packageName) + "." + new String(typeName));
//			}
//
//			/**
//			 * @param name
//			 *            like `aaa`,`aaa.BBB`,`java.lang`,`java.lang.String`
//			 */
//			private NameEnvironmentAnswer findType(final String name) {
////				System.out.println("### to find the type: " + name);
//
//				// check data dir first
//				File file = new File(HotDeployDemo.SOURCES_DIR, name.replace('.', '/')
//						+ ".java");
//				if (file.isFile()) {
//					return new NameEnvironmentAnswer(new CompilationUnit(file),
//							null);
//				}
//
//				// find by system
//				try {
//					InputStream input = this
//							.getClass()
//							.getClassLoader()
//							.getResourceAsStream(
//									name.replace(".", "/") + ".class");
//					if (input != null) {
//						byte[] bytes = IOUtils.toByteArray(input);
//						if (bytes != null) {
//							ClassFileReader classFileReader = new ClassFileReader(
//									bytes, name.toCharArray(), true);
//							return new NameEnvironmentAnswer(classFileReader,
//									null);
//						}
//					}
//				} catch (ClassFormatException e) {
//					// Something very very bad
//					throw new RuntimeException(e);
//				} catch (IOException e) {
//					throw new RuntimeException(e);
//				}
//
////				System.out.println("### type not found: " + name);
//				return null;
//			}
//
//			public boolean isPackage(char[][] parentPackageName,
//					char[] packageName) {
//				String name = new String(packageName);
//				if (parentPackageName != null) {
//					name = join(parentPackageName) + "." + name;
//				}
//
//				File target = new File(HotDeployDemo.SOURCES_DIR, name.replace('.', '/'));
//
//				// only return false if it's a file
//				// return true even if it doesn't exist
//				return !target.isFile();
//			}
//
//			public void cleanup() {
//			}
//		};
//
//		/**
//		 * Compilation result
//		 */
//		ICompilerRequestor compilerRequestor = new ICompilerRequestor() {
//
//			public void acceptResult(CompilationResult result) {
//				// If error
//				if (result.hasErrors()) {
//					for (IProblem problem : result.getErrors()) {
//						String className = new String(problem.getOriginatingFileName()).replace("/", ".");
//						className = className.substring(0, className.length() - 5);
//						String message = problem.getMessage();
//						if (problem.getID() == IProblem.CannotImportPackage) {
//							// Non sense !
//							message = problem.getArguments()[0] + " cannot be resolved";
//						}
//						throw new RuntimeException(className + ":" + message);
//					}
//				}
//
//				// Something has been compiled
//				ClassFile[] clazzFiles = result.getClassFiles();
//				for (int i = 0; i < clazzFiles.length; i++) {
//					String clazzName = join(clazzFiles[i].getCompoundName());
//
//					// save to disk as .class file
//					File target = new File(HotDeployDemo.BYTECODES_DIR, clazzName.replace(".", "/") + ".class");
//					try {
//						FileUtils.writeByteArrayToFile(target,
//								clazzFiles[i].getBytes());
//					} catch (IOException e) {
//						throw new RuntimeException(e);
//					}
//				}
//			}
//		};
//
//		IProblemFactory problemFactory = new DefaultProblemFactory(Locale.ENGLISH);
//		IErrorHandlingPolicy policy = DefaultErrorHandlingPolicies.exitOnFirstError();
//
//		/**
//		 * The JDT compiler
//		 */
//		org.eclipse.jdt.internal.compiler.Compiler jdtCompiler = new org.eclipse.jdt.internal.compiler.Compiler(
//				nameEnvironment, policy, getCompilerOptions(),
//				compilerRequestor, problemFactory);
//
//		// Go !
//		jdtCompiler.compile(new ICompilationUnit[] { new CompilationUnit(
//				new File(HotDeployDemo.SOURCES_DIR, fileName)) });
//	}
//
//	public static CompilerOptions getCompilerOptions() {
//		Map settings = new HashMap();
//		settings.put(CompilerOptions.OPTION_ReportMissingSerialVersion,
//				CompilerOptions.IGNORE);
//		settings.put(CompilerOptions.OPTION_LineNumberAttribute,
//				CompilerOptions.GENERATE);
//		settings.put(CompilerOptions.OPTION_SourceFileAttribute,
//				CompilerOptions.GENERATE);
//		settings.put(CompilerOptions.OPTION_ReportDeprecation,
//				CompilerOptions.IGNORE);
//		settings.put(CompilerOptions.OPTION_ReportUnusedImport,
//				CompilerOptions.IGNORE);
//		settings.put(CompilerOptions.OPTION_Encoding, "UTF-8");
//		settings.put(CompilerOptions.OPTION_LocalVariableAttribute,
//				CompilerOptions.GENERATE);
//		String javaVersion = CompilerOptions.VERSION_1_5;
//		if (System.getProperty("java.version").startsWith("1.6")) {
//			javaVersion = CompilerOptions.VERSION_1_6;
//		} else if (System.getProperty("java.version").startsWith("1.7")) {
//			javaVersion = CompilerOptions.VERSION_1_7;
//		}
//		settings.put(CompilerOptions.OPTION_Source, javaVersion);
//		settings.put(CompilerOptions.OPTION_TargetPlatform, javaVersion);
//		settings.put(CompilerOptions.OPTION_PreserveUnusedLocal,
//				CompilerOptions.PRESERVE);
//		settings.put(CompilerOptions.OPTION_Compliance, javaVersion);
//		return new CompilerOptions(settings);
//	}
//
//	private static class CompilationUnit implements ICompilationUnit {
//
//		private File file;
//
//		public CompilationUnit(File file) {
//			this.file = file;
//		}
//
//		@Override
//		public char[] getContents() {
//			try {
//				return FileUtils.readFileToString(file).toCharArray();
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//
//		@Override
//		public char[] getMainTypeName() {
//			return file.getName().replace(".java", "").toCharArray();
//		}
//
//		@Override
//		public char[][] getPackageName() {
//			String fullPkgName = this.file.getParentFile().getAbsolutePath()
//					.replace(HotDeployDemo.SOURCES_DIR.getAbsolutePath(), "");
//			fullPkgName = fullPkgName.replace("/", ".").replace("\\", ".");
//
//			if (fullPkgName.startsWith(".")){
//				fullPkgName = fullPkgName.substring(1);
//			}
//
//			String[] items = fullPkgName.split("[.]");
//			char[][] pkgName = new char[items.length][];
//			for (int i = 0; i < items.length; i++) {
//				pkgName[i] = items[i].toCharArray();
//			}
//			return pkgName;
//		}
//
//		// @Override
//		public boolean ignoreOptionalProblems() {
//			return false;
//		}
//
//		@Override
//		public char[] getFileName() {
//			return this.file.getName().toCharArray();
//		}
//	}
//
//	private static String join(char[][] chars) {
//		StringBuilder sb = new StringBuilder();
//		for (char[] item : chars) {
//			if (sb.length() > 0) {
//				sb.append(".");
//			}
//			sb.append(item);
//		}
//		return sb.toString();
//	}
//}
