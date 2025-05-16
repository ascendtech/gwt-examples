package us.ascendtech.client.views.players;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.client.dto.PlayerTableHeaderDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.gwt.simplerest.client.ErrorCallback;

import java.util.Arrays;
import java.util.Optional;

@Component
public class PlayersComponent implements IsVueComponent, HasCreated {
	@Data
	boolean showError = false;
	@Data
	String error;

	@Data
	boolean dialog = false;
	@Data
	boolean dialogDelete = false;
	@Data
	int editedIndex = -1;
	@Data
	PlayerDTO editedItem = new PlayerDTO();
	@Data
	JsArray<PlayerTableHeaderDTO> headers = new JsArray<>();
	@Data
	JsArray<PlayerDTO> players = new JsArray<>();
	@Data
	JsArray<PlayerDTO> currentPlayer = new JsArray<>();
	@Data
	String gameKey;

	private ErrorCallback errorHandler;

	@Watch("dialog")
	void dialogChanged(boolean newVal, boolean oldVal) {
		if (newVal == false) {
			this.editedIndex = -1;
			this.editedItem = new PlayerDTO();
			this.editedItem.setName("");
			this.editedItem.setId(-1);
			this.editedItem.setScore(0);
		}
	}

	public Optional<PlayerDTO> nextPlayer() {
		if (players.length == 0) {
			this.currentPlayer = new JsArray<>();
			return Optional.empty();
		}
		else if (currentPlayer.length == 0) {
			PlayerDTO player = players.getAt(0);
			currentPlayer = new JsArray<>(player);
			return Optional.of(player);
		}
		else {
			int currentIndex = this.players.findIndex((player, index, array) -> player.getId() == currentPlayer.getAt(0).getId());
			currentPlayer = new JsArray<>(this.players.getAt((currentIndex + 1) % this.players.length));
			return Optional.of(currentPlayer.getAt(0));
		}
	}

	public Optional<PlayerDTO> currentPlayer() {
		if (currentPlayer.length == 0) {
			return Optional.empty();
		}
		else {
			return Optional.of(currentPlayer.getAt(0));
		}
	}

	@Computed
	String formTitle() {
		return this.editedIndex == -1 ? "New Item" : "Edit Item";
	}

	@Computed
	String getEditedName() {
		return this.editedItem.getName();
	}

	@Computed
	void setEditedName(String name) {
		this.editedItem.setName(name);
	}

	@JsMethod
	void editItem(PlayerDTO item) {
		DomGlobal.console.log("Editing", item.getId());
		this.dialog = true;
		this.editedItem = new PlayerDTO();
		this.editedItem.setName(item.getName());
		this.editedItem.setScore(item.getScore());
		this.editedItem.setId(item.getId());
		this.editedIndex = this.players.indexOf(item);
	}

	@JsMethod
	void deleteItem(PlayerDTO item) {
		int removeIndex = this.players.indexOf(item);
		DomGlobal.console.log("Deleting", item.getId(), "at", removeIndex);
		if (currentPlayer.length != 0 && currentPlayer.getAt(0).getId() == item.getId()) {
			nextPlayer();
		}
		ServiceProvider.get().getPlayersServiceClient()
				.remove(gameKey, item.getId(), () -> players = players.filter((player, index) -> index != removeIndex), errorHandler);
	}

	@JsMethod
	void save() {
		String name = this.editedItem.getName();
		if (this.editedIndex == -1) {
			//New Player
			ServiceProvider.get().getPlayersServiceClient().add(gameKey, name, player -> players.push(player), errorHandler);
		}
		else {
			//Existing Player
			int id = this.editedItem.getId();
			ServiceProvider.get().getPlayersServiceClient().rename(gameKey, id, this.editedItem.getName(), () -> {
				PlayerDTO current = players.find((p, index, arr) -> p.getId() == id);
				if (current != null) {
					current.setName(name);
				}
			}, errorHandler);
		}
		this.close();
	}

	@JsMethod
	void close() {
		this.dialog = false;
		this.editedIndex = -1;
		this.editedItem = new PlayerDTO();
		this.editedItem.setName("");
		this.editedItem.setId(-1);
		this.editedItem.setScore(0);
	}

	private void createTableHeaders() {
		PlayerTableHeaderDTO name = new PlayerTableHeaderDTO();
		name.setText("Name");
		name.setAlign("start");
		name.setSortable(false);
		name.setValue("name");
		this.headers.push(name);

		PlayerTableHeaderDTO score = new PlayerTableHeaderDTO();
		score.setText("Score");
		score.setAlign("start");
		score.setSortable(false);
		score.setValue("score");
		this.headers.push(score);

		PlayerTableHeaderDTO actions = new PlayerTableHeaderDTO();
		actions.setText("Actions");
		actions.setAlign("start");
		actions.setSortable(false);
		actions.setValue("actions");
		this.headers.push(actions);
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

		createTableHeaders();
		gameKey = Arrays.stream(DomGlobal.document.cookie.split("; ")).filter(cookie -> cookie.startsWith("gameKey=")).map(gameKey -> gameKey.substring(8))
				.findFirst().orElse("");

		ServiceProvider.get().getPlayersServiceClient().getPlayers(gameKey, playersList -> {
			for (PlayerDTO player : playersList) {
				players.push(player);
			}
			if (players.length > 0) {
				currentPlayer = new JsArray<>(players.getAt(0));
			}
		}, errorHandler);
	}
}
