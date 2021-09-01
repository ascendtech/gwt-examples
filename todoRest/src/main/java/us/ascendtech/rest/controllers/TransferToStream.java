package us.ascendtech.rest.controllers;

import io.micronaut.http.multipart.MultipartException;
import io.micronaut.http.multipart.PartData;
import io.micronaut.http.multipart.StreamingFileUpload;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

public class TransferToStream {

	public static Publisher<Boolean> transferToStream(ExecutorService ioExecutor, StreamingFileUpload fileUpload, OutputStream outputStream) {

		return Mono.<Boolean>create(emitter ->

				Flux.from(fileUpload).subscribeOn(Schedulers.fromExecutorService(ioExecutor)).subscribe(new Subscriber<PartData>() {
					Subscription subscription;

					@Override
					public void onSubscribe(Subscription s) {
						subscription = s;
						subscription.request(1);
					}

					@Override
					public void onNext(PartData o) {
						try {
							outputStream.write(o.getBytes());
							subscription.request(1);
						}
						catch (IOException e) {
							handleError(e);
						}
					}

					@Override
					public void onError(Throwable t) {
						emitter.error(t);
						try {
							if (outputStream != null) {
								outputStream.close();
							}
						}
						catch (IOException e) {
							System.err.println("Failed to close file stream : " + fileUpload.getName());
						}
					}

					@Override
					public void onComplete() {
						try {
							outputStream.close();
							emitter.success(true);
						}
						catch (IOException e) {
							System.err.println("Failed to close file stream : " + fileUpload.getName());
							emitter.success(false);
						}
					}

					private void handleError(Throwable t) {
						subscription.cancel();
						onError(new MultipartException("Error transferring file: " + fileUpload.getName(), t));
					}
				})).flux();

	}
}
