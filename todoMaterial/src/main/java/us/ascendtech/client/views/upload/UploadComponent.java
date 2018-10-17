package us.ascendtech.client.views.upload;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import elemental2.core.Function;
import elemental2.dom.File;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;
import us.ascendtech.client.dto.DropzoneOptions;
import us.ascendtech.client.dto.DropzoneResponseDTO;

import static jsinterop.base.Js.cast;

@Component
public class UploadComponent implements IsVueComponent, HasBeforeMount {

	@Data
	DropzoneOptions dropzoneOptions;

	@Data
	boolean showMessage;

	@Data
	String message;

	@Override
	public void beforeMount() {

		dropzoneOptions = new DropzoneOptions();
		dropzoneOptions.url = "/service/fileUpload";

		dropzoneOptions.thumbnailWidth = 160.0d;
		dropzoneOptions.thumbnailHeight = 160.0d;
		dropzoneOptions.addRemoveLinks = true;
		dropzoneOptions.maxFilesize = 20.0f;
		dropzoneOptions.maxFiles = 2.0;

		dropzoneOptions.acceptedFiles = "image/*,application/pdf,.txt";
		dropzoneOptions.dictDefaultMessage = "Drop an image or pdf file here or click to upload. To add TODOs, upload a text file with a todo per line.";

	}

	@JsMethod
	public void onSuccess(File file, DropzoneResponseDTO response) {

		showMessage = true;
		message = file.name + " was successfully uploaded";

		JsPropertyMap<Function> jsComponent = cast(vue().$ref("myVueDropzone"));
		jsComponent.get("removeAllFiles").apply(jsComponent);
	}

}
