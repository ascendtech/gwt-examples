package us.ascendtech.client.services;

import us.ascendtech.client.dto.PlayerDTO;
import us.ascendtech.gwt.simplerest.client.CompletableCallback;
import us.ascendtech.gwt.simplerest.client.MultipleCallback;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwt;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@SimpleRestGwt
@Path("/service/player")
public interface PlayersServiceClient {
	@GET
	@Path("/list")
	void getPlayers(MultipleCallback<PlayerDTO> callback);

	@GET
	@Path("/get/{id}")
	void getPlayer(@PathParam("id") int id, SingleCallback<PlayerDTO> callback);

	@POST
	@Path("/rename/{id}")
	void rename(@PathParam("id") int id, String name, CompletableCallback callback);

	@PUT
	@Path("/add")
	void add(String name, SingleCallback<PlayerDTO> callback);

	@PUT
	@Path("/remove/{id}")
	void remove(@PathParam("id") int id, CompletableCallback callback);

	@POST
	@Path("addScore/{id}/{amount}")
	void addScore(@PathParam("id") int id, @PathParam("amount") int amount, CompletableCallback callback);
}
