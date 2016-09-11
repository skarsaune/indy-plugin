package no.kantega.jvm.indy.example;

import java.lang.invoke.*;

public class SomeProvider {
	
	public static CallSite dummyMethod(MethodHandles.Lookup caller,
            String invokedName,
            MethodType invokedType) throws Exception {
		System.out.println("Bootstrap method called");
		MethodType methodType = invokedType; //MethodType.methodType(Void.class, new Class<?>[0])
	    MethodHandles.Lookup lookup = MethodHandles.lookup();
	    MethodHandle methodHandle = lookup.findStatic(SomeProvider.class, "delegate", methodType);
	    return new ConstantCallSite(methodHandle);
	}
	
	public static void delegate() {
		System.out.println("Actual method called");
	}
	
	
	private static CallSite nonPublicProviderMethod() {
		return null;
	}


	public CallSite nonStaticProviderMethod() {
		return null;
	}
	
	public static void nonCallSiteReturnMethod() {
	}

}
