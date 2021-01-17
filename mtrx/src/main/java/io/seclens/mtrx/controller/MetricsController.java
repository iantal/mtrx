package io.seclens.mtrx.controller;

import io.seclens.mtrx.data.dto.MetricDto;
import io.seclens.mtrx.service.MetricService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/metrics")
@AllArgsConstructor
public class MetricsController {
    private final MetricService metricService;

    @GetMapping("/{projectId}/{commit}")
    public ResponseEntity<List<MetricDto>> getMetrics(
            @PathVariable String projectId, @PathVariable String commit) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(metricService.collectMetrics(projectId, commit));
    }
}
