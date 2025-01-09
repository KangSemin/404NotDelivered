package Not.Delivered.common.exception;

import Not.Delivered.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OnlyOneDataException.class)
  public ResponseEntity<ApiResponse> handleOnlyOneDataException(OnlyOneDataException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.CONFLICT, exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(OwnerDataException.class)
  public ResponseEntity<ApiResponse> handleOwnerDataException(OwnerDataException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.FORBIDDEN, exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse> handleIllegalArgumentExceptionException(IllegalArgumentException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleLoginException(Exception exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
