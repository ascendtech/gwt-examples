package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class TriviaStateDTO {
	private int category;
	private String difficulty;
	private TriviaQuestionDTO question;

	@JsOverlay
	public final int getCategory() {
		return category;
	}

	@JsOverlay
	public final void setCategory(int category) {
		this.category = category;
	}

	@JsOverlay
	public final String getDifficulty() {
		return difficulty;
	}

	@JsOverlay
	public final void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@JsOverlay
	public final TriviaQuestionDTO getQuestion() {
		return question;
	}

	@JsOverlay
	public final void setQuestion(TriviaQuestionDTO question) {
		this.question = question;
	}
}
