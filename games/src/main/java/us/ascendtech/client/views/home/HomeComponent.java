package us.ascendtech.client.views.home;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.VueRouterProvider;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.gwt.simplerest.client.ErrorCallback;

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

	private ErrorCallback errorHandler;

	@JsMethod
	void newGame() {
		ServiceProvider.get().getSessionServiceClient().newSession(data -> {
			DomGlobal.console.debug("New Session Key:", data.getGameKey());
			DomGlobal.document.cookie = "gameKey=" + data.getGameKey();
			DomGlobal.console.debug("Cookie:", DomGlobal.document.cookie);
			gameKey = data.getGameKey();
			VueRouterProvider.getInstance().getRouter().push("/trivia");
		}, errorHandler);
	}

	@JsMethod
	void joinGame() {
		ServiceProvider.get().getSessionServiceClient().exists(gameKeyInput, gameExists -> {
			if (gameExists) {
				gameKey = gameKeyInput;
				joinGameInput = false;
			}
			else {
				error = "Game does not exist";
				showError = true;
			}
		}, errorHandler);
	}

	@Override
	public void created() {
		errorHandler = new ErrorCallback() {
			@Override
			public void onError(int statusCode, String status, String errorBody) {
				error = errorBody;
				showError = true;
			}
		};
		gameKey = Arrays.stream(DomGlobal.document.cookie.split("; ")).filter(cookie -> cookie.startsWith("gameKey=")).map(gameKey -> gameKey.substring(8))
				.findFirst().orElse("");
	}
}
