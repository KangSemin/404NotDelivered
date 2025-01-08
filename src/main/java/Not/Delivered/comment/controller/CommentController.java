package Not.Delivered.comment.controller;

import Not.Delivered.comment.domain.Dto.CommentCreateRequestDto;
import Not.Delivered.comment.domain.Dto.CommentDto;
import Not.Delivered.comment.service.CommentService;
import Not.Delivered.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews/{reviewId}/comments")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<ApiResponse<CommentDto>> createComment(@RequestAttribute Long userId,
      @RequestBody CommentCreateRequestDto requestDto,
      @PathVariable Long reviewId) {
    CommentDto comment = commentService.createComment(userId, reviewId, requestDto);
    ApiResponse<CommentDto> apiResponse = ApiResponse.success(HttpStatus.CREATED, "코멘트 생성 성공",
        comment);
    return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
  }

  //TODO 코멘트 수정 기능

  @DeleteMapping("{commentId}")
  public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable Long commentId,
      @PathVariable Long reviewId,
      @RequestAttribute Long userId) {
    commentService.deleteComment(userId,reviewId,commentId);
    ApiResponse<String> apiResponse = ApiResponse.success(HttpStatus.OK, "코멘트 삭제 성공", null);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
