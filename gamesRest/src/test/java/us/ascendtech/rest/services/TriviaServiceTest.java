package us.ascendtech.rest.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import us.ascendtech.rest.dto.TriviaQuestion;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TriviaServiceTest {

    private static TriviaService service;

    @BeforeAll
    static void setUp() {
        service = new TriviaService();
        assertTrue(service.start());
        assertEquals(TriviaService.getNumQuestions(), service.getQuestions().size());
    }

    @Test
    void setDifficulty() {
        waitToStop429();

        assertTrue(service.setDifficulty(TriviaQuestion.Difficulty.HARD));
        assertEquals(TriviaService.getNumQuestions(), service.getQuestions().size());
        service.getQuestions().forEach(question -> assertEquals(TriviaQuestion.Difficulty.HARD, question.getDifficulty()));

        waitToStop429();
        assertTrue(service.setDifficulty(TriviaQuestion.Difficulty.EASY));
        assertEquals(TriviaService.getNumQuestions(), service.getQuestions().size());
        service.getQuestions().forEach(question -> assertEquals(TriviaQuestion.Difficulty.EASY, question.getDifficulty()));

        waitToStop429();
        // Should get more than one, but may not get all 3 out in a set of 10 questions. This will still fail with a ~1.5% probability
        assertTrue(service.setDifficulty(TriviaQuestion.Difficulty.ALL));
        assertEquals(TriviaService.getNumQuestions(), service.getQuestions().size());
        assertTrue(service.getQuestions().stream().map(TriviaQuestion::getDifficulty).distinct().count() > 1);
    }

    private static void waitToStop429() {
        try {
            Thread.sleep(10000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getCategories() {
        // The 'All' category is added by default
        assertTrue(service.getCategories().size() > 1);
    }

    @Test
    void setCategory() {
        // pick a category that isn't "all"
        assertTrue(service.getCategories().size() > 1);
        var keys = new ArrayList<>(service.getCategories().keySet());
        var generator = new Random();
        Integer key = null;
        while (key == null) {
            key = keys.get(generator.nextInt(keys.size()));
        }

        waitToStop429();

        assertTrue(service.setCategory(key));
        final var finalKey = key; // gets rid of a warning, don't ask me.
        service.getQuestions().forEach(question -> assertEquals(service.getCategories().get(finalKey), question.getCategory()));
    }

    @Test
    void nextQuestion() {
        var firstQuestion = service.nextQuestion();
        assertNotNull(firstQuestion);

        var secondService = new TriviaService();
        for (var i = 0; i < 2 * TriviaService.getNumQuestions(); i++) {
            waitToStop429();

            var question = secondService.nextQuestion();
			assertNotEquals(firstQuestion.getQuestion(), question.getQuestion());
            assertNotNull(question);
            assertNotNull(question.getCorrectAnswer());
            assertNotNull(question.getIncorrectAnswers());
            assertFalse(question.getCorrectAnswer().isEmpty());
            assertEquals(3, question.getIncorrectAnswers().size());
        }
    }
}