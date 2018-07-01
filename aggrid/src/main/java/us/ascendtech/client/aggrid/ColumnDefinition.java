package us.ascendtech.client.aggrid;

import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
public class ColumnDefinition<T> {

	public String headerName;
	public String field;
	public boolean checkboxSelection;
	public boolean editable;
	public boolean showRowGroup;
	public ValueGetter<T> valueGetter;
	public JsArray<ColumnDefinition> children;
	public String cellRendererFramework;
	public int width;
	public CellRendererParams<T> cellRendererParams;

	public Renderer<T> cellRenderer;

	@JsFunction
	public interface Renderer<T> {
		HTMLElement render(CellRendererParams<T> param);
	}

	@JsFunction
	public interface ValueGetter<T> {
		String render(T param);
	}
}
