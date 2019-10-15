package us.ascendtech.client.views.arrays;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.ToDoDTO;

@Component(components = HighPriorityTodoCardComponent.class)
public class HighPriorityTodoComponent implements IsVueComponent {

	@Data
	JsArray<ToDoDTO> todos = new JsArray<>();

	@JsMethod
	void unshift() {
		ToDoDTO toDoDTO = new ToDoDTO();
		toDoDTO.setUuid(generateId());
		todos.unshift(toDoDTO);
		DomGlobal.console.log("TODOs", todos);
	}

	@JsMethod
	void push() {
		ToDoDTO toDoDTO = new ToDoDTO();
		toDoDTO.setUuid(generateId());
		todos.push(toDoDTO);
		DomGlobal.console.log("TODOs", todos);
	}

	@JsMethod
	void removeToDo(ToDoDTO todo) {
		todos.splice(todos.indexOf(todo), 1);
	}

	protected static final native String generateId() /*-{
        var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        //http://www.broofa.com/Tools/Math.uuid.js
        var chars = CHARS, uuid = new Array(36), rnd = 0, r;
        for (var i = 0; i < 36; i++) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                uuid[i] = '-';
            } else if (i == 14) {
                uuid[i] = '4';
            } else {
                if (rnd <= 0x02) rnd = 0x2000000 + (Math.random() * 0x1000000) | 0;
                r = rnd & 0xf;
                rnd = rnd >> 4;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
        return uuid.join('');
    }-*/;
}
