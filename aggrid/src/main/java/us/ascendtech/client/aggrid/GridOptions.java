package us.ascendtech.client.aggrid;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Function")
public class GridOptions<T> {

	@JsProperty
	public boolean suppressPropertyNamesCheck;

	@JsProperty
	public GridApi<T> gridApi;

}
