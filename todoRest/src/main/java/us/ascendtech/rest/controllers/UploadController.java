package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.micronaut.scheduling.TaskExecutors;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import us.ascendtech.rest.dto.DropzoneResponse;
import us.ascendtech.rest.dto.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

@Controller("/service")
public class UploadController {

	private ToDoService todoService;

	@Inject
	@Named(TaskExecutors.IO)
	ExecutorService ioExecutor;

	public UploadController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Post(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA)
	public Publisher<HttpResponse<DropzoneResponse>> upload(StreamingFileUpload file) throws IOException {

		File tempFile = File.createTempFile(file.getFilename(), "temp");

		//work around for
		//https://github.com/micronaut-projects/micronaut-core/issues/6084
		//not needed here because we have a file but in other cases were an outputstream is needed
		Publisher<Boolean> uploadPublisher = TransferToStream.transferToStream(ioExecutor, file, new FileOutputStream(tempFile));

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
