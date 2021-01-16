package io.seclens.mtrx.data.mapper;

import io.seclens.mtrx.data.domain.VulnerableLibrary;
import io.seclens.mtrx.data.dto.VulnerableLibraryDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class VulnerableLibraryMapper {

    public VulnerableLibrary toVulnerableLibrary(VulnerableLibraryDto vulnerableLibraryDto) {
        return new VulnerableLibrary(
                vulnerableLibraryDto.getProjectId(),
                vulnerableLibraryDto.getCommit(),
                vulnerableLibraryDto.getLibraryName(),
                String.join(",", vulnerableLibraryDto.getAffectedFiles())
        );
    }

    public VulnerableLibraryDto fromVulnerableLibrary(VulnerableLibrary vulnerableLibrary) {
        return VulnerableLibraryDto.builder()
                .projectId(vulnerableLibrary.getProjectId())
                .commit(vulnerableLibrary.getCommit())
                .libraryName(vulnerableLibrary.getLibraryName())
                .affectedFiles(Arrays.asList(vulnerableLibrary.getAffectedFiles().split(",")))
                .build();
    }
}
