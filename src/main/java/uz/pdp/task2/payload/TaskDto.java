package uz.pdp.task2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskDto {

    @NotNull
    private String name;

    @NotNull
    private String text;

    @NotNull
    private String examples;

    private String solution;

    @NotNull
    private Integer categoryId;

    private boolean hasStar;
}
