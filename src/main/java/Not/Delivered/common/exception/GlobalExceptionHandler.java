package Not.Delivered.common.exception;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.purchase.service.AccessDeniedException;
import Not.Delivered.purchase.service.PurchaseNotFoundException;
import Not.Delivered.purchase.service.UnauthorizedActionException;
import Not.Delivered.user.service.TempUserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(OnlyOneDataException.class)
  public ResponseEntity<ApiResponse> handleOnlyOneDataException(OnlyOneDataException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.CONFLICT, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(OwnerDataException.class)
  public ResponseEntity<ApiResponse> handleOwnerDataException(OwnerDataException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.FORBIDDEN, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse> handleIllegalArgumentExceptionException(
      IllegalArgumentException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TempUserNotFoundException.class)
  public ResponseEntity<ApiResponse> handleTempUserNotFoundException(
      TempUserNotFoundException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse> handleResponseStatusException(
      ResponseStatusException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ApiResponse> handleAccessDeniedException(
      AccessDeniedException exception
  ) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.FORBIDDEN, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(PurchaseNotFoundException.class)
  public ResponseEntity<ApiResponse> handlePurchaseNotFoundException(
      PurchaseNotFoundException exception
  ) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UnauthorizedActionException.class)
  public ResponseEntity<ApiResponse> handleUnauthorizedActionException(
      UnauthorizedActionException exception
  ) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.FORBIDDEN, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiResponse> handleEntityNotFoundException(
      EntityNotFoundException exception
  ) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.NOT_FOUND, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiResponse> handleIllegalStateException(
      IllegalStateException exception
  ) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.BAD_REQUEST, exception.getMessage());
    log.info("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse> handleLoginException(Exception exception) {
    ApiResponse apiResponse = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR,
        exception.getMessage());
    log.error("{}, {}", exception.getStackTrace()[0], exception.getMessage());
    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
