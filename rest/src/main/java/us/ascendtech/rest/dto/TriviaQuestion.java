package us.ascendtech.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Vector;

public class TriviaQuestion {
    private String category;
    private String type;
    private Difficulty difficulty;
    private String question;
    @SerializedName("correct_answer")
    private String correctAnswer;
    @SerializedName("incorrect_answers")
    private Vector<String> incorrectAnswers;

    @Override
    public String toString() {
        return "TriviaQuestion{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", incorrectAnswers=" + incorrectAnswers +
                '}';
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Vector<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(Vector<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public enum Difficulty {
        ALL("all"),
        @SerializedName("easy") EASY("easy"),
        @SerializedName("medium") MEDIUM("medium"),
        @SerializedName("hard") HARD("hard");

        private final String name;

        Difficulty(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }
}
