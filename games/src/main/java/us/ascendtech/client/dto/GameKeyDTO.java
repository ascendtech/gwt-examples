package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class GameKeyDTO {
	private String gameKey;

	@JsOverlay
	public final String getGameKey() {
		return gameKey;
	}

	@JsOverlay
	public final void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}
}
