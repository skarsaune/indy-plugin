package no.kantega.jvm.indy.example;

import no.kantega.jvm.dynamic.annotation.IndyMethod;

public class SomeServiceWithNonPublicReference {
	
	@IndyMethod(implementation="no.kantega.jvm.indy.example.SomeProvider", method= "nonPublicProviderMethod")
	public static void doStuff() {
		throw new UnsupportedOperationException("doStuff() should not be delegated by <invokedynamic> to SomeProvider.nonPublicProviderMethod");
	}
}
