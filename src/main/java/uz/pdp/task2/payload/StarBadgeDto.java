package uz.pdp.task2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StarBadgeDto {

    @NotNull
    private Integer score;

    @NotNull
    private Integer langId;
}
