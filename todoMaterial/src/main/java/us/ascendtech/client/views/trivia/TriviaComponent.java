package us.ascendtech.client.views.trivia;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.axellience.vuegwt.core.client.component.hooks.HasMounted;
import com.google.gwt.core.client.GWT;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.TriviaCategoryDTO;
import us.ascendtech.client.dto.TriviaQuestionDTO;
import us.ascendtech.client.services.ServiceProvider;
import us.ascendtech.client.views.players.PlayersComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component(components = { PlayersComponent.class })
public class TriviaComponent implements IsVueComponent, HasBeforeMount, HasMounted {

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

	@Data
	JsArray<String> difficulties = new JsArray<>();

	@Data
	String difficulty = "All";

	@Data
	JsArray<TriviaCategoryDTO> categories = new JsArray<>();

	@Data
	int category = 0;

	@JsMethod
	void restart() {
	}

	@Watch("difficulty")
	void watchDifficulty(String newValue, String oldValue) {
		DomGlobal.console.log("Difficulty changed to", newValue);
		ServiceProvider.get().getTriviaServiceClient().setDifficulty(newValue).subscribe(() -> {
			next();
		}, err);
	}

	@Watch("category")
	void watchCategory(int newValue, int oldValue) {
		ServiceProvider.get().getTriviaServiceClient().setCategory(newValue).subscribe(() -> {
			next();
		}, err);
	}

	@JsMethod
	void answer() {
		awaitingAnswer = false;
		oldSelection = selection;
		if (choices.getAt(selection).equals(question.getCorrectAnswer())) {
			DomGlobal.console.log("Good Job");
		}
		else {
			selection = choices.findIndex((answer, index, list) -> answer.equals(question.getCorrectAnswer()));
			DomGlobal.console.log("Wrong, correct answer:", question.getCorrectAnswer());
		}
	}

	@JsMethod
	void next() {
		selection = 0;
		oldSelection = 0;
		awaitingAnswer = true;
		ServiceProvider.get().getTriviaServiceClient().getQuestion().subscribe(question -> {
			this.question = question;
			DomGlobal.console.log(question);
			JsArray<String> incorrectAnswers = question.getIncorrectAnswers();
			List<String> answers = new ArrayList<>(incorrectAnswers.asList());
			answers.add(question.getCorrectAnswer());
			Collections.shuffle(answers);
			choices.length = 0;
			answers.forEach(answer -> choices.push(answer));
		}, err);
	}

	@Override
	public void mounted() {
		next();
	}

	@Override
	public void beforeMount() {
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
	}
}
