package Not.Delivered.review.controller;

import Not.Delivered.common.dto.ApiResponse;
import Not.Delivered.review.domain.Dto.ReviewCreateRequestDto;
import Not.Delivered.review.domain.Dto.ReviewDto;
import Not.Delivered.review.domain.Dto.ReviewListDto;
import Not.Delivered.review.domain.Dto.ReviewUpdateRequestDto;
import Not.Delivered.review.service.ReviewService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

  private final ReviewService reviewService;

  @PostMapping
  public ResponseEntity<ApiResponse<ReviewDto>> createReview(@RequestAttribute Long userId,
      @RequestBody @Valid ReviewCreateRequestDto requestDto) {
    ReviewDto review = reviewService.createReview(userId, requestDto);
    ApiResponse<ReviewDto> apiResponse = ApiResponse.success(HttpStatus.CREATED, "리뷰 생성 성공",
        review);
    return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
  }


  @DeleteMapping("/{reviewId}")
  public ResponseEntity<ApiResponse<Void>> deleteReview(@PathVariable Long reviewId,
      @RequestAttribute Long userId) {
    reviewService.deleteReview(userId, reviewId);
    ApiResponse<Void> apiResponse = ApiResponse.success(HttpStatus.OK, "리뷰 삭제 성공", null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @PatchMapping("/{reviewId}")
  public ResponseEntity<ApiResponse<ReviewDto>> updateReview(@PathVariable Long reviewId,
      @RequestBody @Valid ReviewUpdateRequestDto requestDto, @RequestAttribute Long userId) {
    ReviewDto review = reviewService.updateReview(userId, reviewId, requestDto);
    ApiResponse<ReviewDto> apiResponse = ApiResponse.success(HttpStatus.OK, "리뷰 수정 성공", review);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  @GetMapping("/shops/{shopId}")
  public ResponseEntity<ApiResponse<List<ReviewListDto>>> getShopReview(@PathVariable Long shopId,
      @RequestParam(defaultValue = "1") Long minStarPoint,
      @RequestParam(defaultValue = "5") Long maxStarPoint) {
    List<ReviewListDto> reviewDtos = reviewService.getShopReview(shopId, minStarPoint, maxStarPoint);
    ApiResponse<List<ReviewListDto>> apiResponse = ApiResponse.success(HttpStatus.OK, "가게 리뷰 조회 성공",
        reviewDtos);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
