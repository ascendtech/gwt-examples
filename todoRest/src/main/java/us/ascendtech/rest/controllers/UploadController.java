package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import us.ascendtech.rest.dto.DropzoneResponse;
import us.ascendtech.rest.dto.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller("/service")
public class UploadController {

	private ToDoService todoService;

	public UploadController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Post(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA)
	public Publisher<HttpResponse<DropzoneResponse>> upload(StreamingFileUpload file) throws IOException {

		File tempFile = File.createTempFile(file.getFilename(), "temp");
		Publisher<Boolean> uploadPublisher = file.transferTo(tempFile);

		return Mono.from(uploadPublisher).map(success -> {
			if (success) {
				try {
					if (tempFile.getAbsolutePath().contains(".txt")) {
						Files.readAllLines(Paths.get(tempFile.getAbsolutePath())).forEach(line -> {
							if (!line.trim().isEmpty()) {
								todoService.addTodo(new ToDo(line));
							}
						});
					}

					DropzoneResponse response = new DropzoneResponse();
					response.setResponse("Uploaded " + file.getFilename());
					return HttpResponse.ok(response);
				}
				catch (Exception e) {
					DropzoneResponse response = new DropzoneResponse();
					response.setResponse("Upload failed for " + file.getFilename() + ": " + e.getMessage());
					return HttpResponse.serverError(response);
				}
			}
			else {
				DropzoneResponse response = new DropzoneResponse();
				response.setResponse("Upload failed for " + file.getFilename());
				return HttpResponse.<DropzoneResponse>status(HttpStatus.CONFLICT).body(response);
			}
		});
	}

}
