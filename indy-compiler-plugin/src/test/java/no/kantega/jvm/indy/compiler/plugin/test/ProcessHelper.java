package no.kantega.jvm.indy.compiler.plugin.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.sun.tools.javac.Main;

/**
 * Facade to compile and run a sub process and record the results
 * @author marska
 *
 */


class ProcessHelper {
	
	private List<String> resources;
	private final String version;
	private final String pluginName;
	
	
	
	ProcessHelper(String resource) {
		this(Collections.singletonList(resource), System.getProperty("java.specification.version"), "IndyPlugin");
	}
	
	ProcessHelper(List<String> list, String version, String pluginName) {
		super();
		this.resources = list;
		this.version = version;
		this.pluginName = pluginName;
	}
	

	public ProcessResult compile() {
		StringWriter output = new StringWriter();
		if(classFile().exists() && !classFile().delete())
			throw new RuntimeException("Unable to delete old classfile");
		System.out.println("COMPILE:");
		List<String> arguments=new LinkedList<String>();
		arguments.add("-source");
		arguments.add(this.version);
		arguments.add("-target");
		arguments.add(this.version);
		arguments.add("-Xplugin:" + this.pluginName);
		arguments.add("-d");
		arguments.add(classFolder());
		for(final String resource : this.resources) {
			arguments.add("src/test/resources/" + resource + ".java");
		}
				 
		int result = Main.compile(
				arguments.toArray(new String[arguments.size()]), 
						new PrintWriter(
						output, true));
		if(result == 0 && !classFile().exists())
			throw new RuntimeException("Compilation did not produce expected class file");
		
		System.out.println(output.toString());
		return new ProcessResult(result, output.toString());
	}

	private String classFolder() {
		return "classfiles";
	}

	private File classFile() {
		return new File(classFolder() + "/" + className() + ".class");
	}

	private String className() {
		return this.resources.get(0);
	}
	
	public ProcessResult run() throws IOException, InterruptedException {
	  System.out.println("RUN:");
		final Process process = Runtime.getRuntime().exec("java -cp " + classFolder() + " " +  className().replace('/', '.'));
		final BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
		final BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		final StringBuilder builder = new StringBuilder();
		String line=null;
		while((line = output.readLine())!=null) {
			builder.append(line);
			builder.append('\n');
			System.out.println(line);
		}
		
		while((line = error.readLine())!=null) {
			System.err.println(line);
		}
		
		
		return new ProcessResult(process.waitFor(), builder.toString());
	}
	
	

}
