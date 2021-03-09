package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import us.ascendtech.client.dto.PlayerDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@AutoRestGwt
@Path("/service/player")
public interface PlayersServiceClient {
	@GET
	@Path("/list")
	Observable<PlayerDTO> getPlayers();

	@GET
	@Path("/get/{id}")
	Single<PlayerDTO> getPlayer(@PathParam("id") int id);

	@POST
	@Path("/rename/{id}")
	Completable rename(@PathParam("id") int id, String name);

	@PUT
	@Path("/add")
	Single<PlayerDTO> add(String name);

	@PUT
	@Path("/remove/{id}")
	Single<Boolean> remove(@PathParam("id") int id);

	@POST
	@Path("addScore/{id}/{amount}")
	Single<Boolean> addScore(@PathParam("id") int id, @PathParam("amount") int amount);
}
