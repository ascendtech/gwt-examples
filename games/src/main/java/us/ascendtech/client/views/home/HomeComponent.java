package us.ascendtech.client.views.home;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.GameKeyDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import java.util.Arrays;

@Component
public class HomeComponent implements IsVueComponent, HasCreated {
	@Data
	String error;
	@Data
	boolean showError = false;
	@Data
	String gameKey = "";
	@Data
	boolean joinGameInput = false;
	@Data
	String gameKeyInput = "";

	@JsMethod
	void newGame() {
		ServiceProvider.get().getSessionServiceClient().newSession(new SingleCallback<GameKeyDTO>() {
			@Override
			public void onData(GameKeyDTO data) {
				DomGlobal.console.debug("New Session Key:", data.getGameKey());
				DomGlobal.document.cookie = "gameKey=" + data.getGameKey();
				DomGlobal.console.debug("Cookie:", DomGlobal.document.cookie);
				gameKey = data.getGameKey();
			}

			@Override
			public void onError(int statusCode, String status, String errorBody) {
				error = errorBody;
				showError = true;
			}
		});
	}

	@JsMethod
	void joinGame() {
		ServiceProvider.get().getSessionServiceClient().exists(gameKeyInput, new SingleCallback<Boolean>() {
			@Override
			public void onData(Boolean gameExists) {
				if (gameExists) {
					gameKey = gameKeyInput;
					joinGameInput = false;
				}
				else {
					error = "Game does not exist";
					showError = true;
				}
			}

			@Override
			public void onError(int statusCode, String status, String errorBody) {
				error = "Game does not exist";
				showError = true;
			}
		});
	}

	@Override
	public void created() {
		gameKey = Arrays.stream(DomGlobal.document.cookie.split("; ")).filter(cookie -> cookie.startsWith("gameKey=")).map(gameKey -> gameKey.substring(8))
				.findFirst().orElse("");
	}
}
