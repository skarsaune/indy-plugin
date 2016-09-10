package no.kantega.jvm.indy.compiler.plugin;

import javax.lang.model.element.Modifier;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic.Kind;

import com.sun.tools.javac.code.Symbol.MethodSymbol;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.util.Options;

import no.kantega.jvm.dynamic.annotation.IndyMethod;

/**
 *
 */
@SuppressWarnings("restriction")
@SupportedAnnotationTypes("no.kantega.jvm.dynamic.annotation.IndyMethod")
@SupportedSourceVersion(SourceVersion.RELEASE_8)//NB: *Latest* supported edition
public class IndyAnnotationChecker extends AbstractProcessor {

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);

	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		for (TypeElement annotation : annotations) {
			for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
				if(!verifyCompilerSetup(element)) {
					return false;
				}
				IndyMethod indyMethodRef = element.getAnnotation(IndyMethod.class);
				if (indyMethodRef != null) {
					TypeElement implementationClass = this.processingEnv.getElementUtils()
							.getTypeElement(indyMethodRef.implementation());
					if (implementationClass == null) {
						processingEnv.getMessager().printMessage(Kind.ERROR,
								"Can not find implementation class " + indyMethodRef.implementation(), element);
					} else {
						findAndMapMethod(element, indyMethodRef, implementationClass);
					}

				}
			}
			
		}
		return false;
	}
	
	private boolean verifyCompilerSetup(Element element) {
		if(this.processingEnv.getSourceVersion().compareTo(SourceVersion.RELEASE_7) < 0) {
			processingEnv.getMessager().printMessage(Kind.ERROR,
					"IndyMethod mappings require at least Java 1.7 (invokedynamic support)", element);
			return false;
		}
		if(this.processingEnv instanceof JavacProcessingEnvironment) {
			Options options = ((JavacProcessingEnvironment) this.processingEnv).getContext().get(Options.optionsKey);
			if(options!= null) {
				if(!"IndyPlugin".equals(options.get("-Xplugin:"))){
					processingEnv.getMessager().printMessage(Kind.ERROR,
							"You have to include -Xplugin:IndyPlugin compiler argument to use @IndyMethod annotations", element);
					return false;
				}
			}
		}
		return true;
	}
	
	private void findAndMapMethod(Element element, IndyMethod indyMethodRef, TypeElement implementationClass) {
    	for (Element innerElement : implementationClass.getEnclosedElements()) {
    		if(ElementKind.METHOD.equals(innerElement.getKind())) {
    			if(String.valueOf(innerElement.getSimpleName()).equals(indyMethodRef.method())) {
    				MethodSymbol mapToMethod = (MethodSymbol) innerElement;
					if(validateMethodSignature(mapToMethod, element)) {
    					addGlobalMapping((MethodSymbol) element, mapToMethod);
    					return;
    				}                				
    			}
    			
    		} 
			
		}
    	//have not found valid method
    	processingEnv.getMessager().printMessage(Kind.ERROR,
				"Can not find valid method " + indyMethodRef.method() + " in class " + indyMethodRef.implementation(), element);
	}

	private void addGlobalMapping(MethodSymbol fromMethod, MethodSymbol toMethod) {
		IndyMethodMappings.getInstance().mapMethod(fromMethod, toMethod);
		
	}

	private boolean validateMethodSignature(MethodSymbol method, Element element) {
		if(!method.getModifiers().contains(Modifier.STATIC)) {
			processingEnv.getMessager().printMessage(Kind.ERROR,
					"Implementation method " + method + " must be static", element);
			return false;
		}
		
		if(!method.getModifiers().contains(Modifier.PUBLIC)) {
			processingEnv.getMessager().printMessage(Kind.ERROR,
					"Implementation method " + method + " must be public", element);
			return false;
		}
		
		//TODO also validate signature
		

		return true;
	}

}
