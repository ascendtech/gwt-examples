package us.ascendtech.client.views.trivia;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Ref;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.core.client.GWT;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.client.dto.TriviaCategoryDTO;
import us.ascendtech.client.dto.TriviaQuestionDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.client.views.players.PlayersComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component(components = { PlayersComponent.class })
public class TriviaComponent implements IsVueComponent, HasMounted, HasCreated {

	@Data
	String error;
	@Data
	boolean showError = false;
	private final Consumer<Throwable> err = e -> {
		GWT.log("exception: " + e, e);
		error = e.getMessage();
		showError = true;
	};
	@Data
	TriviaQuestionDTO question = new TriviaQuestionDTO();
	@Data
	JsArray<String> choices = new JsArray<>();
	@Data
	boolean awaitingAnswer = true;
	@Data
	int selection = 0;
	@Data
	int oldSelection = 0;
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

	@JsMethod
	void restart() {
	}

	@JsMethod
	void setDifficulty(String difficulty) {
		DomGlobal.console.log("Difficulty changed to", difficulty);
		ServiceProvider.get().getTriviaServiceClient().setDifficulty(difficulty).subscribe(() -> {
			next();
		}, err);
		next();
	}

	@JsMethod
	void setCategory(int category) {
		DomGlobal.console.log("Category changed to", category);
		ServiceProvider.get().getTriviaServiceClient().setCategory(category).subscribe(() -> {
			next();
		}, err);
		next();
	}

	@JsMethod
	void answer() {
		awaitingAnswer = false;
		oldSelection = selection;
		if (choices.getAt(selection).equals(question.getCorrectAnswer())) {
			DomGlobal.console.log("Good Job");
			Optional<PlayerDTO> currentPlayer = playersComponent.currentPlayer();
			if (currentPlayer.isPresent()) {
				ServiceProvider.get().getPlayersServiceClient().addScore(currentPlayer.get().getId(), 1).subscribe(result -> {
					currentPlayer.get().setScore(currentPlayer.get().getScore() + 1);
				}, err);
			}
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
		selection = 0;
		oldSelection = 0;
		awaitingAnswer = true;
		ServiceProvider.get().getTriviaServiceClient().getQuestion().subscribe(question -> {
			this.setQuestion(question);
		}, err);
		playersComponent.nextPlayer();
	}

	@Override
	public void mounted() {
	}

	@Override
	public void created() {
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
		ServiceProvider.get().getTriviaServiceClient().getCategories().subscribe(category -> {
			this.categories.push(category);
			this.categories.sort((first, second) -> first.getName().toLowerCase().compareTo(second.getName().toLowerCase()));
		}, err);
		ServiceProvider.get().getTriviaServiceClient().getState().subscribe(state -> {
			this.difficulty = this.difficulties.find((difficulty, index, arr) -> difficulty.toLowerCase() == state.getDifficulty().toLowerCase());
			this.category = state.getCategory();
			this.setQuestion(state.getQuestion());
		}, err);
	}
}
