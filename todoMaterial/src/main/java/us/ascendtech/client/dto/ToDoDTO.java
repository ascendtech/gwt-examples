package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class ToDoDTO {

	private String todo;
	private Integer id;
	private String uuid;
	private boolean highPriority;

	@JsOverlay
	public final String getTodo() {
		return todo;
	}

	@JsOverlay
	public final void setTodo(String todo) {
		this.todo = todo;
	}

	@JsOverlay
	public final Integer getId() {
		return id;
	}

	@JsOverlay
	public final void setId(Integer id) {
		this.id = id;
	}

	@JsOverlay
	public final String getUuid() {
		return uuid;
	}

	@JsOverlay
	public final void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@JsOverlay
	public final boolean isHighPriority() {
		return highPriority;
	}

	@JsOverlay
	public final void setHighPriority(boolean highPriority) {
		this.highPriority = highPriority;
	}
}
