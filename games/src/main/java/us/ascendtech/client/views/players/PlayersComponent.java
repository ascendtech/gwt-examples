package us.ascendtech.client.views.players;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.google.gwt.core.client.GWT;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.client.dto.PlayerTableHeaderDTO;
import us.ascendtech.client.services.ServiceProvider;

import java.util.Optional;

@Component
public class PlayersComponent implements IsVueComponent, HasBeforeMount {
	@Data
	boolean showError = false;
	@Data
	String error;
	private final Consumer<Throwable> err = e -> {
		GWT.log("exception: " + e, e);
		error = e.getMessage();
		showError = true;
	};
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
		ServiceProvider.get().getPlayersServiceClient().remove(item.getId()).subscribe(removed -> {
			if (removed) {
				this.players = this.players.filter((player, index, array) -> index != removeIndex);
			}
		}, err);
	}

	@JsMethod
	void save() {
		if (this.editedIndex == -1) {
			//New Player
			String name = this.editedItem.getName();
			ServiceProvider.get().getPlayersServiceClient().add(name).subscribe(player -> {
				DomGlobal.console.log("New Player id", player.getId());
				this.players.push(player);
			}, err);
		}
		else {
			//Existing Player
			int id = this.editedItem.getId();
			ServiceProvider.get().getPlayersServiceClient().rename(id, this.editedItem.getName()).subscribe(() -> {
				ServiceProvider.get().getPlayersServiceClient().getPlayer(id).subscribe(player -> {
					PlayerDTO current = this.players.find((p, index, arr) -> p.getId() == id);
					if (current != null) {
						current.setName(player.getName());
						current.setScore(player.getScore());
					}
				});
			}, err);
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
		ServiceProvider.get().getPlayersServiceClient().getPlayers().subscribe(player -> {
			this.players.push(player);
			if (this.players.length == 1) {
				this.currentPlayer = new JsArray<>(player);
			}
		}, err);
	}
}
