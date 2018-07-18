package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Single;
import org.reactivestreams.Publisher;

import java.io.File;
import java.io.IOException;

@Controller("/service/todo")
public class UploadController {

	@Post(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA)
	public Single<HttpResponse<String>> upload(StreamingFileUpload file) throws IOException {
		File tempFile = File.createTempFile(file.getFilename(), "temp");
		Publisher<Boolean> uploadPublisher = file.transferTo(tempFile);
		return Single.fromPublisher(uploadPublisher).map(success -> {
			if (success) {
				System.out.println("Uploaded this thing!");
				return HttpResponse.ok("Uploaded");
			}
			else {
				return HttpResponse.<String>status(HttpStatus.CONFLICT).body("Upload Failed");
			}
		});
	}

}
