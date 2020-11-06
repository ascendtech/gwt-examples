package us.ascendtech.client.views.collections;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Computed;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Prop;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.ToDoDTO;

@Component
public class HighPriorityTodoCardComponent implements IsVueComponent {

	@Prop
	ToDoDTO item;

	@Computed
	String getTodo() {
		return item.getTodo();
	}

	@Computed
	void setTodo(String todo) {
		item.setTodo(todo);
	}

	@Data
	String highPriorityColor;

	@JsMethod
	void togglePrimary(ToDoDTO todo) {
		if (!todo.isHighPriority()) {
			todo.setHighPriority(true);
			setPreferredButtonStatus(todo);
		}
	}

	@JsMethod
	void removeToDo() {
		vue().$emit("remove-todo", item);
	}

	public void setPreferredButtonStatus(ToDoDTO todo) {
		if (todo.isHighPriority()) {
			highPriorityColor = "secondary";
		}
		else {
			highPriorityColor = "#ccc";
		}
	}

}
