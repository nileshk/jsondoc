package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.annotation.ApiVersion;

public class ApiVersionDoc {
	public String jsondocId = UUID.randomUUID().toString();
	private String since;
	private String until;

	public static ApiVersionDoc buildFromAnnotation(ApiVersion annotation) {
		ApiVersionDoc apiVersionDoc = new ApiVersionDoc();
		apiVersionDoc.setSince(annotation.since());
		apiVersionDoc.setUntil(annotation.until());
		return apiVersionDoc;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

}
