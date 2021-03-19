package us.ascendtech.client.services;

import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.gwt.simplerest.client.CompletableCallback;
import us.ascendtech.gwt.simplerest.client.ErrorCallback;
import us.ascendtech.gwt.simplerest.client.MultipleCallback;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwt;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@SimpleRestGwt
@Path("/service/todo")
public interface ToDoServiceClient {

	@GET
	@Path("/list")
	void getCurrentToDos(MultipleCallback<ToDoDTO> callback, ErrorCallback errorCallback);

	@PUT
	@Path("/add")
	void addToDo(ToDoDTO toDo, SingleCallback<ToDoDTO> callback, ErrorCallback errorCallback);

	@DELETE
	@Path("/delete/{id}")
	void deleteToDo(@PathParam("id") Integer id, CompletableCallback callback, ErrorCallback errorCallback);

	@POST
	@Path("/search/{query}")
	void searchToDos(@PathParam("query") String query, MultipleCallback<ToDoDTO> callback, ErrorCallback errorCallback);

}
