package no.kantega.jvm.indy.compiler.plugin.test;

import org.junit.Test;

public class AnnotationErrorReportingTest {
	
	@Test
	public void testReferenceToNonExistingClass() {
		
		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonExistingClass").compile();
		result.assertFailure();
		result.assertOutputText("Can not find implementation class no.kantega.jvm.indy.example.NonExistingClass");
		
	}
	
	@Test
	public void testReferenceToNonStaticMethod() {
		
		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonStaticReference").compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonStaticProviderMethod() must be static");
		
	}
	
	@Test
	public void testReferenceToNonPublicMethod() {
		
		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonPublicReference").compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonPublicProviderMethod() must be public");
		
	}
	
	@Test
	public void testReferenceToNonCallsiteMethod() {
		
		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeServiceWithNonCallsiteReference").compile();
		result.assertFailure();
		result.assertOutputText("Implementation method nonCallSiteReturnMethod() must return a CallSite");
		
	}
	
	@Test
	public void testSuccessfulCompilation() {
		
		ProcessResult result = new ProcessHelper("no/kantega/jvm/indy/example/SomeService").compile();
		result.assertSuccess();		
	}
	
	


}
