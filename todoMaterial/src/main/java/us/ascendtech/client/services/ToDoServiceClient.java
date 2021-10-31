package us.ascendtech.client.services;

import us.ascendtech.client.dto.TermDTO;
import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwtSync;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@SimpleRestGwtSync
@Path("/service/todo")
public interface ToDoServiceClient {

	@GET
	@Path("/list")
	List<ToDoDTO> getCurrentToDos();

	@PUT
	@Path("/add")
	ToDoDTO addToDo(ToDoDTO toDo);

	@DELETE
	@Path("/delete/{id}")
	void deleteToDo(@PathParam("id") Integer id);

	@POST
	@Path("/search/{query}")
	List<ToDoDTO> searchToDos(@PathParam("query") String query);

	@GET
	@Path("/terms")
	List<TermDTO> getTerms();
}
