package io.seclens.mtrx.controller;

import io.seclens.mtrx.client.LuaClient;
import io.seclens.mtrx.data.dto.VulnerabilityDto;
import io.seclens.mtrx.service.VulnerabilityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/vulnerabilities")
@AllArgsConstructor
public class VulnerabilitiesController {
    private final VulnerabilityService vulnerabilityService;
    private final LuaClient luaClient;

    @PostMapping
    public ResponseEntity<VulnerabilityDto> addAllVulnerabilities(
            @RequestBody List<VulnerabilityDto> vulnerabilities) {

        log.info("Adding all vulnerabilities to db");
        for (VulnerabilityDto dto : vulnerabilities) {
            vulnerabilityService.save(dto);
        }
        luaClient.getVulnerableLibraries(vulnerabilities);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<List<VulnerabilityDto>> getAllVulnerabilities() {
        log.info("Reading all vulnerabilities from db");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vulnerabilityService.getAllVulnerabilities());
    }

    @GetMapping("/{projectId}/{commit}")
    public ResponseEntity<List<VulnerabilityDto>> getVulnerabilities(
            @PathVariable String projectId, @PathVariable String commit) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(vulnerabilityService.getVulnerabilities(projectId, commit));
    }
}
