package Not.Delivered.comment.domain.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateRequestDto(
    @Size(max = 200,message = "200자 이내로 작성해주세요.")
    @NotBlank(message = "내용은 비울 수 없습니다.")
    String commentContent
) {

}
