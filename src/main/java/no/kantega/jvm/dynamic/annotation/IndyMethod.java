package no.kantega.jvm.dynamic.annotation;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * I enable rerouting calls to method annotated by me
 * will use invoke dynamic to a bootstrap method that in turn may return an implementation
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = { METHOD })
public @interface IndyMethod {

	/**
	 * 
	 * @return Fully qualified name of the Java class containing the bootstrap method
	 */
	String implementation();

	/**
	 * 
	 * @return Name of boostrap method that complies with the requirements public
	 *         static method(MethodHandles.Lookup, String, MethodType) that in
	 *         turn provides reference to a concrete method
	 */
	String method();

}
