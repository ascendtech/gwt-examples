package us.ascendtech.client.views.players;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.client.dto.PlayerTableHeaderDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.gwt.simplerest.client.CompletableCallback;
import us.ascendtech.gwt.simplerest.client.MultipleCallback;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import java.util.Optional;

@Component
public class PlayersComponent implements IsVueComponent, HasBeforeMount {
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
		ServiceProvider.get().getPlayersServiceClient().remove(item.getId(), new CompletableCallback() {
			@Override
			public void onDone() {
				players = players.filter((player, index, array) -> index != removeIndex);
			}

			@Override
			public void onError(int statusCode, String status, String errorBody) {
				error = errorBody;
				showError = true;
			}
		});
	}

	@JsMethod
	void save() {
		String name = this.editedItem.getName();
		if (this.editedIndex == -1) {
			//New Player
			ServiceProvider.get().getPlayersServiceClient().add(name, new SingleCallback<PlayerDTO>() {
				@Override
				public void onData(PlayerDTO player) {
					DomGlobal.console.log("New Player id", player.getId());
					players.push(player);
				}

				@Override
				public void onError(int statusCode, String status, String errorBody) {
					error = errorBody;
					showError = true;
				}
			});
		}
		else {
			//Existing Player
			int id = this.editedItem.getId();
			ServiceProvider.get().getPlayersServiceClient().rename(id, this.editedItem.getName(), new CompletableCallback() {
				@Override
				public void onDone() {
					PlayerDTO current = players.find((p, index, arr) -> p.getId() == id);
					if (current != null) {
						current.setName(name);
					}
				}

				@Override
				public void onError(int statusCode, String status, String errorBody) {
					error = errorBody;
					showError = true;
				}
			});
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
	public void beforeMount() {
		createTableHeaders();
		ServiceProvider.get().getPlayersServiceClient().getPlayers(new MultipleCallback<PlayerDTO>() {
			@Override
			public void onData(PlayerDTO[] playersList) {
				for (PlayerDTO player : playersList) {
					players.push(player);
				}
				if (players.length > 0) {
					currentPlayer = new JsArray<>(players.getAt(0));
				}
			}

			@Override
			public void onError(int statusCode, String status, String errorBody) {
				error = errorBody;
				showError = true;
			}
		});
	}
}
