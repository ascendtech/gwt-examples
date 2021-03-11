package us.ascendtech.client.services;

import us.ascendtech.client.dto.GameKeyDTO;
import us.ascendtech.gwt.simplerest.client.SimpleRestGwt;
import us.ascendtech.gwt.simplerest.client.SingleCallback;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@SimpleRestGwt
@Path("/service/session")
public interface SessionServiceClient {
	@GET
	@Path("/new")
	void newSession(SingleCallback<GameKeyDTO> callback);

	@GET
	@Path("/exists/{gameKey}")
	void exists(@PathParam("gameKey") String gameKey, SingleCallback<Boolean> callback);
}
