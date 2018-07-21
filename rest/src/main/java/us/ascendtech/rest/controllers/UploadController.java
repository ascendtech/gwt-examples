package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Single;
import org.reactivestreams.Publisher;
import us.ascendtech.rest.model.DropzoneResponse;
import us.ascendtech.rest.model.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller("/service/todo")
public class UploadController {

	private ToDoService todoService;

	public UploadController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Post(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA)
	public Single<HttpResponse<DropzoneResponse>> upload(StreamingFileUpload file) throws IOException {

		File tempFile = File.createTempFile(file.getFilename(), "temp");
		Publisher<Boolean> uploadPublisher = file.transferTo(tempFile);

		return Single.fromPublisher(uploadPublisher).map(success -> {
			if (success) {
				Files.readAllLines(Paths.get(tempFile.getAbsolutePath())).forEach(line -> {
					if (!line.trim().isEmpty()) {
						todoService.addTodo(new ToDo(line));
					}
				});

				DropzoneResponse response = new DropzoneResponse();
				response.setResponse("Uploaded that thing!");
				return HttpResponse.ok(response);
			}
			else {
				DropzoneResponse response = new DropzoneResponse();
				response.setResponse("Upload failed!");
				return HttpResponse.<DropzoneResponse>status(HttpStatus.CONFLICT).body(response);
			}
		});
	}

}
