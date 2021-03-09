package us.ascendtech.client.dto;

import elemental2.core.JsArray;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class TriviaQuestionDTO {
    private String category;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private JsArray<String> incorrectAnswers;

    @JsOverlay
    public final String getCategory() {
        return category;
    }

    @JsOverlay
    public final void setCategory(String category) {
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
    public final String getQuestion() {
        return question;
    }

    @JsOverlay
    public final void setQuestion(String question) {
        this.question = question;
    }

    @JsOverlay
    public final String getCorrectAnswer() {
        return correctAnswer;
    }

    @JsOverlay
    public final void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @JsOverlay
    public final JsArray<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    @JsOverlay
    public final void setIncorrectAnswers(JsArray<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}
