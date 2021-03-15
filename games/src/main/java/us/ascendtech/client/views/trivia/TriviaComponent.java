package us.ascendtech.client.views.trivia;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Ref;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.VueRouterProvider;
import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.client.dto.TriviaCategoryDTO;
import us.ascendtech.client.dto.TriviaQuestionDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.client.views.players.PlayersComponent;
import us.ascendtech.gwt.simplerest.client.ErrorCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component(components = { PlayersComponent.class })
public class TriviaComponent implements IsVueComponent, HasCreated {

	@Data
	String error;
	@Data
	boolean showError = false;
	@Data
	TriviaQuestionDTO question = new TriviaQuestionDTO();
	@Data
	JsArray<String> choices = new JsArray<>();
	@Data
	boolean awaitingAnswer = true;
	@Data
	int selection = -1;
	@Data
	int oldSelection = -1;
	@Ref
	PlayersComponent playersComponent;

	@Data
	JsArray<String> difficulties = new JsArray<>();

	@Data
	String difficulty = "";

	@Data
	JsArray<TriviaCategoryDTO> categories = new JsArray<>();

	@Data
	int category = -1;

	@Data
	String gameKey;

	@JsMethod
	void restart() {
	}

	private ErrorCallback errorHandler;

	@JsMethod
	void setDifficulty(String difficulty) {
		DomGlobal.console.log("Difficulty changed to", difficulty);
		ServiceProvider.get().getTriviaServiceClient().setDifficulty(gameKey, difficulty, () -> next(), errorHandler);
	}

	@JsMethod
	void setCategory(int category) {
		DomGlobal.console.log("Category changed to", category);
		ServiceProvider.get().getTriviaServiceClient().setCategory(gameKey, category, () -> next(), errorHandler);
	}

	@JsMethod
	void answer() {
		awaitingAnswer = false;
		oldSelection = selection;
		if (choices.getAt(selection).equals(question.getCorrectAnswer())) {
			DomGlobal.console.log("Good Job");
			Optional<PlayerDTO> currentPlayer = playersComponent.currentPlayer();
			currentPlayer.ifPresent(playerDTO -> ServiceProvider.get().getPlayersServiceClient()
					.addScore(gameKey, playerDTO.getId(), 1, () -> playerDTO.setScore(playerDTO.getScore() + 1), errorHandler));
		}
		else {
			selection = choices.findIndex((answer, index, list) -> answer.equals(question.getCorrectAnswer()));
			DomGlobal.console.log("Wrong, correct answer:", question.getCorrectAnswer());
		}
	}

	private void setQuestion(TriviaQuestionDTO question) {
		this.question = question;
		JsArray<String> incorrectAnswers = question.getIncorrectAnswers();
		List<String> answers = new ArrayList<>(incorrectAnswers.asList());
		answers.add(question.getCorrectAnswer());
		Collections.shuffle(answers);
		choices.length = 0;
		answers.forEach(answer -> choices.push(answer));
	}

	@JsMethod
	void next() {
		selection = -1;
		oldSelection = -1;
		awaitingAnswer = true;
		ServiceProvider.get().getTriviaServiceClient().getQuestion(gameKey, question -> setQuestion(question), errorHandler);
		playersComponent.nextPlayer();
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

		DomGlobal.console.log("Cookie:", DomGlobal.document.cookie);
		String session = Arrays.stream(DomGlobal.document.cookie.split("; ")).filter(cookie -> cookie.startsWith("gameKey="))
				.map(gameKey -> gameKey.substring(8)).findFirst().orElse("");
		DomGlobal.console.log("Session Key:", session);
		if (!session.isEmpty()) {
			gameKey = session;
			initialize();
		}
		else {
			VueRouterProvider.getInstance().getRouter().push("/");
		}
	}

	private void initialize() {
		question = new TriviaQuestionDTO();
		question.setQuestion("No Question");
		question.setCategory("No Category");
		question.setDifficulty("No Difficulty");
		choices.push("No Choice");

		difficulties.push("All", "Easy", "Medium", "Hard");
		TriviaCategoryDTO all = new TriviaCategoryDTO();
		all.setName("All");
		all.setId(0);
		categories.push(all);
		ServiceProvider.get().getTriviaServiceClient().getCategories(gameKey, categoriesList -> {
			for (TriviaCategoryDTO category : categoriesList) {
				categories.push(category);
				categories.sort((first, second) -> first.getName().toLowerCase().compareTo(second.getName().toLowerCase()));
			}
		}, errorHandler);

		ServiceProvider.get().getTriviaServiceClient().getState(gameKey, state -> {
			difficulty = difficulties.find((difficulty, index, arr) -> difficulty.toLowerCase() == state.getDifficulty().toLowerCase());
			category = state.getCategory();
			setQuestion(state.getQuestion());
		}, errorHandler);
	}
}
