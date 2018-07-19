package us.ascendtech.client.dto;

import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class ToDoDTO {

	public String todo;

	public Integer id;
}
