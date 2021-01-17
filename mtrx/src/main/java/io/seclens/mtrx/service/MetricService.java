package io.seclens.mtrx.service;

import io.seclens.mtrx.data.domain.Vulnerability;
import io.seclens.mtrx.data.domain.VulnerableLibrary;
import io.seclens.mtrx.data.dto.MetricDto;
import io.seclens.mtrx.data.dto.VulnerabilityDto;
import io.seclens.mtrx.data.dto.VulnerableLibraryDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@Service
public class MetricService {
    private final VulnerabilityService vulnerabilityService;
    private final VulnerableLibraryService vulnerableLibraryService;

    private static final Map<String, Integer> severityWeight;
    static {
        severityWeight = new HashMap<>();
        severityWeight.put("CRITICAL", 16);
        severityWeight.put("HIGH", 8);
        severityWeight.put("MEDIUM", 4);
        severityWeight.put("LOW", 2);
    }

    public List<MetricDto> collectMetrics(String projectId, String commit) {
        List<MetricDto> metrics = new ArrayList<>();


        //1. obtain vulnerable libraries for projectId and commit
        List<VulnerabilityDto> vulnerabilities = vulnerabilityService.getVulnerabilities(projectId, commit);
        List<VulnerableLibraryDto> libraries = vulnerableLibraryService.getVulnerableLibraries(projectId, commit);

        //2. aggregate data and map it to Metric model
        for (VulnerableLibraryDto library : libraries) {
            int countAll = 0;
            int countCritical = 0;
            int countHighs = 0;
            int countMediums = 0;
            int countLows = 0;
            int countWeights = 0;

            for (VulnerabilityDto vulnerability : vulnerabilities) {
                if (library.getLibraryName().equals(vulnerability.getLibraryName())) {
                    countAll++;
                    switch (vulnerability.getSeverity()) {
                        case "CRITICAL":
                            countCritical++;
                            countWeights += severityWeight.get("CRITICAL");
                            break;
                        case "HIGH":
                            countHighs++;
                            countWeights += severityWeight.get("HIGH");
                            break;
                        case "MEDIUM":
                            countMediums++;
                            countWeights += severityWeight.get("MEDIUM");
                            break;
                        case "LOW":
                            countLows++;
                            countWeights += severityWeight.get("LOW");
                            break;
                    }
                }
            }

            for (String file : library.getAffectedFiles()) {
                if (countAll != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("NumberOfVulnerabilities")
                            .value(countAll)
                            .build());
                }

                if (countCritical != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("CriticalVulnerabilities")
                            .value(countCritical)
                            .build());
                }

                if (countHighs != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("HighVulnerabilities")
                            .value(countHighs)
                            .build());
                }

                if (countMediums != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("MediumVulnerabilities")
                            .value(countMediums)
                            .build());
                }

                if (countLows != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("LowVulnerabilities")
                            .value(countLows)
                            .build());
                }

                if (countWeights != 0) {
                    metrics.add(MetricDto.builder()
                            .category("Security")
                            .file(file)
                            .name("WeightedSeverityCount")
                            .value(countWeights)
                            .build());
                }
            }
        }

        for (VulnerableLibraryDto library : libraries) {
            for (VulnerabilityDto vulnerability : vulnerabilities) {
                if (library.getLibraryName().equals(vulnerability.getLibraryName())) {
                    library.getAffectedFiles().forEach(file -> metrics.add(MetricDto.builder()
                            .file(file)
                            .name(vulnerability.getCve())
                            .category("Security")
                            .value(severityWeight.get(vulnerability.getSeverity()))
                            .build()));
                }
            }
        }

        //3. write result to file
        return metrics;
    }
}
