package uz.pdp.task2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProgLangDto {
    @NotNull
    private String name;

    private String description;
}
