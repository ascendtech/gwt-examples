package us.ascendtech.rest.dto;

public class DropzoneResponse {

	private String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "DropzoneResponse{" + "response='" + response + '\'' + '}';
	}
}
