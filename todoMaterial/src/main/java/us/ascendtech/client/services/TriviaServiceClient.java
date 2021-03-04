package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Single;
import us.ascendtech.client.dto.TriviaQuestionDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@AutoRestGwt
@Path("/service/trivia")
public interface TriviaServiceClient {

    @GET
    @Path("/next")
    Single<TriviaQuestionDTO> getQuestion();
}
