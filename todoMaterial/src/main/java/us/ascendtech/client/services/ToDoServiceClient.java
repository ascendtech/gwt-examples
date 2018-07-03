package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.AutoRestGwt;
import io.reactivex.Observable;
import us.ascendtech.client.dto.ToDoDTO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@AutoRestGwt
@Path("/service/todo")
public interface ToDoServiceClient {

	@GET
	@Path("/list")
	Observable<ToDoDTO> getCurrentToDos();

}
