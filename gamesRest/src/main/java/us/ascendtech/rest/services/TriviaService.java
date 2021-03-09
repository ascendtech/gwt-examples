package us.ascendtech.rest.services;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.TriviaQuestion;

import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

@Singleton
public class TriviaService {
    static final Logger LOG = LoggerFactory.getLogger(TriviaService.class);
    private static final String sessionUri = "https://opentdb.com/api_token.php?command=request";
    private static final String queryUri = "https://opentdb.com/api.php?token=%s&amount=%d&type=multiple";
    private static final String categoriesUri = "https://opentdb.com/api_category.php";
    private static final int numQuestions = 10;
    private final Vector<TriviaQuestion> questions;
    private final HashMap<Integer, String> categories;
    private String session;
    private TriviaQuestion.Difficulty difficulty;
    private int category;
    private int questionIndex;

    public TriviaService() {
        this.difficulty = TriviaQuestion.Difficulty.ALL;
        this.category = 0;
        this.questionIndex = 0;
        this.questions = new Vector<>();
        this.categories = new HashMap<>();
    }

    public static int getNumQuestions() {
        return numQuestions;
    }

    public HashMap<Integer, String> getCategories() {
        return categories;
    }

    public TriviaQuestion.Difficulty getDifficulty() {
        return difficulty;
    }

    public Integer getCategory() {
        return category;
    }

    private void fetchCategories() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(categoriesUri).openStream()));
        var gson = new Gson();
        var response = gson.fromJson(reader.readLine(), CategoriesResponse.class);
        this.categories.clear();
        this.categories.put(0, "All");
        Arrays.stream(response.triviaCategories).forEach(category -> this.categories.put(category.id, category.name));
        LOG.debug(String.format("Categories: %d", response.triviaCategories.length));
    }

    public Vector<TriviaQuestion> getQuestions() {
        return questions;
    }

    public TriviaQuestion getQuestion() {
        if (questions.size() == 0 || questionIndex >= questions.size()) {
            return nextQuestion();
        }
        return questions.elementAt(questionIndex);
    }

    public TriviaQuestion nextQuestion() {
        if (this.session == null || this.session.isEmpty()) {
            start();
        }
        if (questionIndex >= questions.size() - 1) {
            try {
                fetchQuestions();
                questionIndex = 0;
            }
            catch (IOException ioe) {
                LOG.debug(ioe.getMessage());
                return null;
            }
        } else {
            questionIndex = questionIndex + 1;
        }
        return questions.elementAt(questionIndex);
    }

    public boolean start() {
        try {
            this.initializeSession();
            this.fetchCategories();
            this.fetchQuestions();
        } catch (IOException ioe) {
            LOG.debug(ioe.getMessage());
            return false;
        }
        return true;
    }

    public boolean setDifficulty(TriviaQuestion.Difficulty difficulty) {
        var oldDifficulty = this.difficulty;
        try {
            this.difficulty = difficulty;
            this.fetchQuestions();
        } catch (IOException ioe) {
            this.difficulty = oldDifficulty;
            return false;
        }
        return true;
    }

    public boolean setCategory(Integer category) {
        var oldCategory = this.category;

        if (!this.categories.containsKey(category)) {
            return false;
        }

        try {
            this.category = category;
            this.fetchQuestions();
        } catch (IOException ioe) {
            this.category = oldCategory;
            return false;
        }
        return true;
    }

    /**
     * Gets a session token from the opentdb website
     *
     * @throws IOException if something goes wrong talking to the server
     */
    private void initializeSession() throws IOException {
        var reader = new BufferedReader(new InputStreamReader(new URL(sessionUri).openStream()));
        var gson = new Gson();
        var response = gson.fromJson(reader.readLine(), SessionResponse.class);
        if (response.responseCode != 0) {
            throw new IOException("Token response code: " + response.responseCode);
        }
        this.session = response.token;
        LOG.debug(String.format("Session: %s", response.toString()));
    }

    private String buildQueryString() {
        var queryString = String.format(queryUri, this.session, numQuestions);
        if (this.difficulty != TriviaQuestion.Difficulty.ALL) {
            queryString += String.format("&difficulty=%s", this.difficulty.toString());
        }
        if (this.category != 0) {
            queryString += String.format("&category=%d", this.category);
        }
        return queryString;
    }

    /**
     * Gets questions from the database. Note that this may fail if the session token is too old.
     * @throws IOException If the site returned an error
     */
    private void fetchQuestions() throws IOException {
        var queryString = this.buildQueryString();
        LOG.debug(String.format("Query: %s", queryString));
        this.questionIndex = 0;

        var reader = new BufferedReader(new InputStreamReader(new URL(queryString).openStream()));
        Gson gson = new Gson();
        var response = gson.fromJson(reader.readLine(), QuestionsResults.class);
        if (response.responseCode != 0) {
            throw new IOException("Questions response code: " + response.responseCode);
        }
        this.questions.clear();
        Collections.addAll(this.questions, response.results);
        LOG.debug(String.format("Questions: %s", response.toString()));
    }

    private static class CategoriesResponse {
        @SerializedName("trivia_categories")
        public Category[] triviaCategories;

        static class Category {
            public int id;
            public String name;
        }
    }

    private static class SessionResponse {
        @SerializedName("response_code")
        public int responseCode;
        @SerializedName("response_message")
        public String responseMessage;
        public String token;

        @Override
        public String toString() {
            return "SessionResponse{" +
                    "responseCode=" + responseCode +
                    ", responseMessage='" + responseMessage + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    private static class QuestionsResults {
        @SerializedName("response_code")
        public int responseCode;
        public TriviaQuestion[] results;

        @Override
        public String toString() {
            return "QuestionsResults{" +
                    "responseCode=" + responseCode +
                    ", results=" + Arrays.toString(results) +
                    '}';
        }
    }
}