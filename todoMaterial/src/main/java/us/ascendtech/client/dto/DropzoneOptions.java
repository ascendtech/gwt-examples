package us.ascendtech.client.dto;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class DropzoneOptions {

	public String url;

	public Integer thumbnailWidth;

	public Float maxFilesize;

	//public DropzoneSuccessEvent dropzoneSuccessEvent;

}
