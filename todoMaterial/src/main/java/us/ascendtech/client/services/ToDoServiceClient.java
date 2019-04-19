package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import us.ascendtech.client.dto.ToDoDTO;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@AutoRestGwt
@Path("/service/todo")
public interface ToDoServiceClient {

	@GET
	@Path("/list")
	Observable<ToDoDTO> getCurrentToDos();

	@PUT
	@Path("/add")
	Single<ToDoDTO> addToDo(ToDoDTO toDo);

	@DELETE
	@Path("/delete/{id}")
	Completable deleteToDo(@PathParam("id") Integer id);

	@POST
	@Path("/search/{query}")
	Observable<ToDoDTO> searchToDos(@PathParam("query") String query);

}
