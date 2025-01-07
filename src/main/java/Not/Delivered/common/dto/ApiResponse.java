package Not.Delivered.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
	private HttpStatus status;
	private String message;
	private T data;

	private ApiResponse(HttpStatus status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
		return new ApiResponse<>(status, message, data);
	}

	public static <T> ApiResponse<T> error(HttpStatus status, String message) {
		return new ApiResponse<>(status, message, null);
	}
}
