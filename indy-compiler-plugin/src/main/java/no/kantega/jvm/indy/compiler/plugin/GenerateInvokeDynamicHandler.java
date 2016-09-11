package no.kantega.jvm.indy.compiler.plugin;

import com.sun.source.tree.Tree;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskEvent.Kind;
import com.sun.source.util.TaskListener;

/**
 * I replace ordinary method invocation with the symbol for invokedynamic invocations
 * @author marska
 *
 */
public class GenerateInvokeDynamicHandler implements TaskListener {

	public void started(TaskEvent start) {
		if(start.getKind() == Kind.GENERATE) {
			for (Tree tree : start.getCompilationUnit().getTypeDecls()) {
				tree.accept(new IndyMethodInvocationReplacer(), tree);
			}
		}
	}

	public void finished(TaskEvent end) {
	}
}
