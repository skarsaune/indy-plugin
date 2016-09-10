package no.kantega.jvm.indy.compiler.plugin;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Symbol.MethodSymbol;

public class IndyMethodMappings {
	
	

	private static IndyMethodMappings instance;
	public static synchronized IndyMethodMappings getInstance() {
		if(instance == null) {
			instance = new IndyMethodMappings();
		}
		return instance;
	}
	private Map<MethodSymbol, MethodSymbol> mappings=new HashMap<MethodSymbol, MethodSymbol>();
	private IndyMethodMappings() {}
	public void mapMethod(MethodSymbol fromMethod, MethodSymbol toMethod) {
		this.mappings.put(fromMethod, toMethod);	
	}
	public MethodSymbol mappingFor(Symbol sym) {
		return this.mappings.get(sym);
		
	}
}
