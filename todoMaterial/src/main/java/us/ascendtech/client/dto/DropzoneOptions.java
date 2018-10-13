package us.ascendtech.client.dto;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class DropzoneOptions {

	public String url;

	public Double thumbnailWidth;

	public Double thumbnailHeight;

	public Float maxFilesize;

	public Double maxFiles;

	public String acceptedFiles;

	public String dictDefaultMessage;

	public Boolean addRemoveLinks;

	public Boolean chunking;

	public Double chunkSize;

}
