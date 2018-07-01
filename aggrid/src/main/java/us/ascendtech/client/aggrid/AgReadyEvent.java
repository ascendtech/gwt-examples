package us.ascendtech.client.aggrid;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Function")
public class AgReadyEvent<T> {

	public GridApi<T> api;

}
