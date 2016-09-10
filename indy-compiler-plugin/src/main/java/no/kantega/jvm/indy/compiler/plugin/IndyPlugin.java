package no.kantega.jvm.indy.compiler.plugin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;

public class IndyPlugin implements Plugin {

	public String getName() {
		return "IndyPlugin";
	}

	public void init(JavacTask paramJavacTask, String... paramArrayOfString) {
		paramJavacTask.addTaskListener(new GenerateInvokeDynamicHandler());
		
	}

}
