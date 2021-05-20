package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import us.ascendtech.rest.dto.Term;
import us.ascendtech.rest.dto.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Controller("/service/todo")
public class ToDoController {

	private ToDoService todoService;

	public ToDoController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Get("/list")
	public HttpResponse<Collection<ToDo>> list() {
		return HttpResponse.ok(todoService.getCurrentTODOs());
	}

	@Put("/add")
	public HttpResponse<ToDo> add(@Body ToDo todo) {
		System.out.println(todo);
		todoService.addTodo(todo);
		return HttpResponse.ok(todo);
	}

	@Delete("/delete/{id}")
	public HttpResponse delete(@Parameter Integer id) {
		todoService.removeTodo(id);
		return HttpResponse.ok();
	}

	@Post("/search/{query}")
	public HttpResponse<Collection<ToDo>> searchToDos(@QueryValue String query) {

		Collection<ToDo> todos = new ArrayList<>();
		for (ToDo todo : todoService.getCurrentTODOs()) {
			if (todo.getTodo().toLowerCase().contains(query.toLowerCase())) {
				todos.add(todo);
			}
		}

		return HttpResponse.ok(todos);

	}

	@Get("/terms")
	public HttpResponse<?> getTerms() {

		Collection<Term> data = new ArrayList<>();
		try (BufferedReader buffer = new BufferedReader(
				new InputStreamReader(Objects.requireNonNull(ToDoController.class.getResourceAsStream("/words.txt"))))) {
			buffer.lines().forEach(s -> {
				String[] tokens = s.split("\\s");
				data.add(new Term(tokens[1], Double.valueOf(tokens[0])));
			});
		}
		catch (IOException e) {
			return HttpResponse.serverError(e.getMessage());
		}

		return HttpResponse.ok(data);

	}

}
