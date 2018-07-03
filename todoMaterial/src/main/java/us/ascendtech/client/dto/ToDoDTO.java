package us.ascendtech.client.dto;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class ToDoDTO {

	public boolean done;

	public String todo;

}
