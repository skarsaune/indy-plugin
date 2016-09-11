package no.kantega.jvm.indy.compiler.plugin;

import java.util.HashMap;
import java.util.Map;

import com.sun.tools.javac.code.Symbol.MethodSymbol;

public class IndyMethodMappings {
	
	

	private static IndyMethodMappings instance;
	public static synchronized IndyMethodMappings getInstance() {
		if(instance == null) {
			instance = new IndyMethodMappings();
		}
		return instance;
	}
	private Map<String, MethodSymbol> mappings=new HashMap<String, MethodSymbol>();
	private IndyMethodMappings() {}
	public void mapMethod(MethodSymbol fromMethod, MethodSymbol toMethod) {
		this.mappings.put(describeFromMethod(fromMethod), toMethod);	
	}
	private String describeFromMethod(MethodSymbol fromMethod) {
		return fromMethod.owner + "." +  fromMethod + " : " + fromMethod.getReturnType();
	}
	public MethodSymbol mappingFor(MethodSymbol sym) {
		return this.mappings.get(describeFromMethod(sym));
		
	}
}
