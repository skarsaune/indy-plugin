package no.kantega.jvm.indy.compiler.plugin;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.jvm.ClassFile;
import com.sun.tools.javac.tree.JCTree.JCFieldAccess;
import com.sun.tools.javac.tree.JCTree.JCMethodInvocation;


public class IndyMethodInvocationReplacer extends TreeScanner<Object, Tree> {
	
	@Override
	public Object visitMethodInvocation(MethodInvocationTree node, Tree p) {
		
		if(node instanceof JCMethodInvocation) {
			JCMethodInvocation impl=(JCMethodInvocation) node;
			if(impl.meth instanceof JCFieldAccess) {
				JCFieldAccess identifier = (JCFieldAccess) impl.meth;
				if(identifier.sym instanceof MethodSymbol) {
					MethodSymbol replacementMethod = IndyMethodMappings.getInstance().mappingFor((MethodSymbol) identifier.sym);
					if(replacementMethod!= null) {//insert reference to dynamic method instead
						identifier.sym=new Symbol.DynamicMethodSymbol(identifier.sym.name, identifier.sym.owner, ClassFile.REF_invokeStatic , replacementMethod, identifier.sym.type , new Object[0]); 
					}
						
				}
				
			}
		}
		return super.visitMethodInvocation(node, p);
	}

}
