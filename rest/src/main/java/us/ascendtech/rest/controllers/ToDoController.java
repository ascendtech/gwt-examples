package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import us.ascendtech.rest.model.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.util.List;

@Controller("/todo")
public class ToDoController {

	private ToDoService todoService;

	public ToDoController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Get("/list")
	public HttpResponse<List<ToDo>> list() throws Exception {
		return HttpResponse.created(todoService.getCurrentTODOs());
	}

}
