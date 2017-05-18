package com.minbo.javademo.annotation.two;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes(value = { "com.minbo.javademo.annotation.two.MetdInfo" })
public class MetdInfoProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		HashMap<String, String> map = new HashMap<String, String>();
		for (TypeElement typeElement : annotations) {
			for (Element element : env.getElementsAnnotatedWith(typeElement)) {
				MetdInfo info = element.getAnnotation(MetdInfo.class);
				map.put(element.getEnclosingElement().toString(), info.author());
			}
		}
		return false;
	}
}