package no.kantega.jvm.indy.compiler.plugin.test;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class InvokeDynamicReplacementTest {

	@Test
	public void testIndyReplacement() throws IOException, InterruptedException {
		ProcessHelper mainClass = new ProcessHelper(
				Arrays.asList("no/kantega/jvm/indy/example/SomeClass", "no/kantega/jvm/indy/example/SomeService",
						"no/kantega/jvm/indy/example/SomeProvider"),
				System.getProperty("java.specification.version"), "IndyPlugin");
		mainClass.compile().assertSuccess();
		ProcessResult runResult = mainClass.run();
		runResult.assertSuccess();
		runResult.assertOutputText("Bootstrap method called");
		runResult.assertOutputText("Actual method called");

	}

}
