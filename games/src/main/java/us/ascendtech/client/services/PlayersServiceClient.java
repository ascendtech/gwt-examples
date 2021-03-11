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
	@Path("/list/{gameKey}")
	void getPlayers(@PathParam("gameKey") String gameKey, MultipleCallback<PlayerDTO> callback);

	@GET
	@Path("/get/{gameKey}/{id}")
	void getPlayer(@PathParam("gameKey") String gameKey, @PathParam("id") int id, SingleCallback<PlayerDTO> callback);

	@POST
	@Path("/rename/{gameKey}/{id}/{name}")
	void rename(@PathParam("gameKey") String gameKey, @PathParam("id") int id, @PathParam("name") String name, CompletableCallback callback);

	@PUT
	@Path("/add/{gameKey}/{name}")
	void add(@PathParam("gameKey") String gameKey, @PathParam("name") String name, SingleCallback<PlayerDTO> callback);

	@PUT
	@Path("/remove/{gameKey}/{id}")
	void remove(@PathParam("gameKey") String gameKey, @PathParam("id") int id, CompletableCallback callback);

	@POST
	@Path("addScore/{gameKey}/{id}/{amount}")
	void addScore(@PathParam("gameKey") String gameKey, @PathParam("id") int id, @PathParam("amount") int amount, CompletableCallback callback);
}
