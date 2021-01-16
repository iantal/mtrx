package io.seclens.mtrx.service;

import io.seclens.mtrx.data.domain.Vulnerability;
import io.seclens.mtrx.data.domain.VulnerableLibrary;
import io.seclens.mtrx.data.dto.VulnerabilityDto;
import io.seclens.mtrx.data.dto.VulnerableLibraryDto;
import io.seclens.mtrx.data.mapper.VulnerableLibraryMapper;
import io.seclens.mtrx.data.repository.VulnerabilityRepository;
import io.seclens.mtrx.data.repository.VulnerableLibraryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class VulnerableLibraryService {
    private final VulnerableLibraryRepository vulnerableLibraryRepository;
    private final VulnerableLibraryMapper vulnerableLibraryMapper;

    @Transactional
    public VulnerableLibraryDto save(VulnerableLibraryDto vulnerableLibraryDto) {
        VulnerableLibrary vulnerableLibrary = vulnerableLibraryMapper.toVulnerableLibrary(vulnerableLibraryDto);
        log.info("AF: {}", vulnerableLibrary.getAffectedFiles());
        VulnerableLibrary save = vulnerableLibraryRepository
                .save(vulnerableLibrary);
        vulnerableLibraryDto.setId(save.getId());
        return vulnerableLibraryDto;
    }

    @Transactional
    public List<VulnerableLibraryDto> getVulnerableLibraries(String projectId, String commit) {
        return new ArrayList<>();
    }
}
