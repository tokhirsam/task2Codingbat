package uz.pdp.task2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerDto {

    @NotNull
    String body;

    @NotNull
    Integer task_id;

    @NotNull
    Integer user_id;

    @NotNull
    boolean isTrue;
}
