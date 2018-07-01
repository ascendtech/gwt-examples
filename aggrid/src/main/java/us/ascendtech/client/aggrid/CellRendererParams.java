package us.ascendtech.client.aggrid;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class CellRendererParams<T> {

	public T value;
	public boolean checkbox;

	@JsMethod
	public native T getValue();

}
