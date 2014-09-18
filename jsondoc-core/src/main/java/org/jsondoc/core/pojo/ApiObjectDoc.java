package org.jsondoc.core.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.jsondoc.core.annotation.ApiVersion;

public class ApiObjectDoc implements Comparable<ApiObjectDoc> {
	public String jsondocId = UUID.randomUUID().toString();
	private String name;
	private String description;
	private List<ApiObjectFieldDoc> fields;
	private ApiVersionDoc apiVersion;

	@SuppressWarnings("rawtypes")
	public static ApiObjectDoc buildFromAnnotation(ApiObject annotation, Class clazz) {
		List<ApiObjectFieldDoc> fieldDocs = new ArrayList<ApiObjectFieldDoc>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getAnnotation(ApiObjectField.class) != null) {
				ApiObjectFieldDoc fieldDoc = ApiObjectFieldDoc.buildFromAnnotation(field.getAnnotation(ApiObjectField.class), field);
				
				if(field.isAnnotationPresent(ApiVersion.class)) {
					fieldDoc.setApiVersion(ApiVersionDoc.buildFromAnnotation(field.getAnnotation(ApiVersion.class)));
				}
				
				fieldDocs.add(fieldDoc);
			}
		}

		Class<?> c = clazz.getSuperclass();
		if (c != null) {
			if (c.isAnnotationPresent(ApiObject.class)) {
				ApiObjectDoc objDoc = ApiObjectDoc.buildFromAnnotation(c.getAnnotation(ApiObject.class), c);
				fieldDocs.addAll(objDoc.getFields());
			}
		}

		return new ApiObjectDoc(annotation.name(), annotation.description(), fieldDocs);
	}

	public ApiObjectDoc(String name, String description, List<ApiObjectFieldDoc> fields) {
		super();
		this.name = name;
		this.description = description;
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public List<ApiObjectFieldDoc> getFields() {
		return fields;
	}

	@Override
	public int compareTo(ApiObjectDoc o) {
		return name.compareTo(o.getName());
	}

	public ApiVersionDoc getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(ApiVersionDoc apiVersion) {
		this.apiVersion = apiVersion;
	}

}
