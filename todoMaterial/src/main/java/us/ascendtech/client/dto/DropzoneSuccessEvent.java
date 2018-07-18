package us.ascendtech.client.dto;

import elemental2.dom.File;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class DropzoneSuccessEvent {

	public File file;

	public String response;

}
