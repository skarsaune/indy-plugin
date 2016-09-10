package no.kantega.jvm.indy.compiler.plugin;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.tree.JCTree.JCIdent;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;


public class IndyMethodInvocationReplacer extends TreeScanner<Object, Tree> {
	
	@Override
	public Object visitMethodInvocation(MethodInvocationTree node, Tree p) {
		
		if(node instanceof JCMethodInvocation) {
			JCMethodInvocation impl=(JCMethodInvocation) node;
			if(impl.meth instanceof JCIdent) {
				JCIdent identifier = (JCIdent) impl.meth;
				if(identifier.sym instanceof MethodSymbol) {
					MethodSymbol replacementMethod = IndyMethodMappings.getInstance().mappingFor(identifier.sym);
					if(replacementMethod!= null) {
						System.out.println("Found method invocation to replace " + node);
					}
						
				}
				
			}
		}
		return super.visitMethodInvocation(node, p);
	}

}
