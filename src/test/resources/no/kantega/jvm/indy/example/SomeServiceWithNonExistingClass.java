package no.kantega.jvm.indy.example;

import no.kantega.jvm.dynamic.annotation.IndyMethod;

public class SomeServiceWithNonExistingClass {
	
	@IndyMethod(implementation="no.kantega.jvm.indy.example.NonExistingClass", method= "doesntMatter")
	public static void doStuff() {
		throw new UnsupportedOperationException("doStuff() should not be delegated by <invokedynamic> to NonExistingClass as it does not exist");
	}
}
