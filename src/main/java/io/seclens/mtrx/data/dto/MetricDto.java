package io.seclens.mtrx.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MetricDto {
    private String file;
    private String name;
    private final String category = "Security";
    private int value;
}
