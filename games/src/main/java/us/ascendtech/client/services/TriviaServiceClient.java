package us.ascendtech.client.services;

import us.ascendtech.client.dto.TriviaCategoryDTO;
import us.ascendtech.client.dto.TriviaQuestionDTO;
import us.ascendtech.client.dto.TriviaStateDTO;
import us.ascendtech.gwt.simplerest.client.CompletableCallback;
import us.ascendtech.gwt.simplerest.client.MultipleCallback;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwt;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@SimpleRestGwt
@Path("/service/trivia")
public interface TriviaServiceClient {

    @GET
    @Path("/next")
    void getQuestion(SingleCallback<TriviaQuestionDTO> callback);

    @POST
    @Path("/difficulty/{difficulty}")
    void setDifficulty(@PathParam("difficulty") String difficulty, CompletableCallback callback);

    @POST
    @Path("/category/{category}")
    void setCategory(@PathParam("category") int category, CompletableCallback callback);

    @GET
    @Path("/state")
    void getState(SingleCallback<TriviaStateDTO> callback);

    @GET
    @Path("/categories")
    void getCategories(MultipleCallback<TriviaCategoryDTO> callback);
}
