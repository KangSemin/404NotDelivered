package Not.Delivered.review.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<ApiResponse<ReviewDto>> createReview(
      @RequestAttribute Long userId,
      @RequestBody ReviewCreateRequestDto requestDto
  ) {
      ReviewDto review = reviewService.createReview(userId,requestDto);
      ApiResponse<ReviewDto> apiResponse = ApiResponse.success(HttpStatus.CREATED, "리뷰 생성 성공",review);
    return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
  }


}
