package com.riversoso.backboard.validation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyForm {

    @NotBlank(message = "내용을 입력 해주세요")
    private String content;
}
