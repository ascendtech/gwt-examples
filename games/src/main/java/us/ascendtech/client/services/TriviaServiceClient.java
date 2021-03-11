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
    @Path("/next/{gameKey}")
    void getQuestion(@PathParam("gameKey") String gameKey, SingleCallback<TriviaQuestionDTO> callback);

    @POST
    @Path("/difficulty/{gameKey}/{difficulty}")
    void setDifficulty(@PathParam("gameKey") String gameKey, @PathParam("difficulty") String difficulty, CompletableCallback callback);

    @POST
    @Path("/category/{gameKey}/{category}")
    void setCategory(@PathParam("gameKey") String gameKey, @PathParam("category") int category, CompletableCallback callback);

    @GET
    @Path("/state/{gameKey}")
    void getState(@PathParam("gameKey") String gameKey, SingleCallback<TriviaStateDTO> callback);

    @GET
    @Path("/categories/{gameKey}")
    void getCategories(@PathParam("gameKey") String gameKey, MultipleCallback<TriviaCategoryDTO> callback);
}
