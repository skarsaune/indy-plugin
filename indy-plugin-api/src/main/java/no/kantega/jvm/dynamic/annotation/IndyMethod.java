package no.kantega.jvm.dynamic.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ METHOD})
public @interface IndyMethod {
	
	/**
	 * 
	 * @return Fully qualified name of the Java class containing the actual implementation
	 */
	String implementation();
	/**
	 * 
	 * @return Name of a method that complies with the requirements
	 */
	String method();

}
