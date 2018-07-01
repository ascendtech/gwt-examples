package us.ascendtech.rest.services;

import us.ascendtech.rest.model.ToDo;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class ToDoService {

	public List<ToDo> getCurrentTODOs() {

		List<ToDo> toDoList = new ArrayList<>();
		toDoList.add(new ToDo("Need to get milk."));
		toDoList.add(new ToDo("Wash the car."));
		toDoList.add(new ToDo("Water the plants."));
		toDoList.add(new ToDo("Watch the best movie."));

		return toDoList;

	}
}
