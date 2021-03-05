package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import us.ascendtech.client.dto.TriviaCategoryDTO;
import us.ascendtech.client.dto.TriviaQuestionDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@AutoRestGwt
@Path("/service/trivia")
public interface TriviaServiceClient {

    @GET
    @Path("/next")
    Single<TriviaQuestionDTO> getQuestion();

    @POST
    @Path("/difficulty/{difficulty}")
    Completable setDifficulty(@PathParam("difficulty") String difficulty);

    @GET
    @Path("/difficulty")
    Single<String> getDifficulty();

    @POST
    @Path("/category/{category}")
    Completable setCategory(@PathParam("category") int category);

    @GET
    @Path("/category")
    Single<Integer> getCategory();

    @GET
    @Path("/categories")
    Observable<TriviaCategoryDTO> getCategories();
}
