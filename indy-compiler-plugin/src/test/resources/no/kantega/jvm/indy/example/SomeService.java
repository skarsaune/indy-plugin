package no.kantega.jvm.indy.example;

import no.kantega.jvm.dynamic.annotation.IndyMethod;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.io.Serializable;

public class SomeService {
	
	@IndyMethod(implementation="no.kantega.jvm.indy.example.SomeProvider", method= "dummyMethod")
	public static void doStuff() {
		throw new UnsupportedOperationException("doStuff() should be delegated by <invokedynamic> to SomeProvider.dummyMethod");
	}
}
