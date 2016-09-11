package no.kantega.jvm.indy.compiler.plugin.test;

import java.util.Collections;

import org.junit.Test;

public class AnnotationErrorReportingTest {

	@Test
	public void testReferenceToNonExistingClass() {

		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonExistingClass")
				.compile();
		result.assertFailure();
		result.assertOutputText("Can not find implementation class no.kantega.jvm.indy.example.NonExistingClass");

	}

	@Test
	public void testReferenceToNonStaticMethod() {

		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonStaticReference")
				.compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonStaticProviderMethod() must be static");

	}

	@Test
	public void testReferenceToNonPublicMethod() {

		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonPublicReference")
				.compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonPublicProviderMethod() must be public");

	}

	@Test
	public void testReferenceToNonCallsiteMethod() {

		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonCallsiteReference")
				.compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonCallSiteReturnMethod() must return a CallSite");
	}

	@Test
	public void testMissingCompilerPluginArgument() {

		ProcessResult result = new ProcessHelper(
				Collections.singletonList("no/kantega/jvm/indy/example/SomeServiceWithNonCallsiteReference"), "1.8",
				null).compile();
		result.assertFailure();
		result.assertOutputText("You have to include -Xplugin:IndyPlugin compiler argument to use @IndyMethod annotations");
	}
	
	@Test
	public void testTooOldTargetFormat() {

		ProcessResult result = new ProcessHelper(
				Collections.singletonList("no/kantega/jvm/indy/example/SomeServiceWithNonCallsiteReference"), "1.6",
				"IndyPlugin").compile();
		result.assertFailure();
		result.assertOutputText("error: IndyMethod mappings require at least Java 1.7 (invokedynamic support)");
	}

	@Test
	public void testSuccessfulCompilation() {

		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeService").compile();
		result.assertSuccess();
	}

}
