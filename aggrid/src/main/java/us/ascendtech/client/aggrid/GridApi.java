package us.ascendtech.client.aggrid;

import elemental2.core.JsArray;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Function")
public class GridApi<T> {

	@JsMethod
	public native JsArray<T> getSelectedNodes();

	@JsMethod
	public native JsArray<T> getSelectedRows();

	@JsMethod
	public native void sizeColumnsToFit();

	@JsMethod
	public native void redrawRows();
}
