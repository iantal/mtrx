package io.seclens.mtrx.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VulnerableLibraryDto {
    private UUID id;
    private String projectId;
    private String commit;
    private String libraryName;
    private List<String> affectedFiles;
}
