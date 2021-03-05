package us.ascendtech.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TriviaQuestion {
    private String category;
    private Difficulty difficulty;
    private String question;
    @SerializedName("correct_answer")
    private String correctAnswer;
    @SerializedName("incorrect_answers")
    private List<String> incorrectAnswers;

    @Override
    public String toString() {
        return "TriviaQuestion{" +
                "category='" + category + '\'' +
                ", difficulty=" + difficulty +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", incorrectAnswers=" + incorrectAnswers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriviaQuestion that = (TriviaQuestion) o;
        return Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
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

        public static Optional<Difficulty> fromString(String difficulty) {
            var lowerDifficulty = difficulty.toLowerCase();
            switch (lowerDifficulty) {
                case "all":
                    return Optional.of(ALL);
                case "easy":
                    return Optional.of(EASY);
                case "medium":
                    return Optional.of(MEDIUM);
                case "hard":
                    return Optional.of(HARD);
                default:
                    return Optional.empty();
            }
        }

        public String toString() {
            return this.name;
        }
    }
}
