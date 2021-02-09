package io.seclens.mtrx.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "vulnerable_libraries")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VulnerableLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String projectId;

    private String commit;

    private String libraryName;

    @Column(columnDefinition = "TEXT")
    private String affectedFiles; // comma separated

    public VulnerableLibrary(String projectId, String commit, String libraryName, String affectedFiles) {
        this.projectId = projectId;
        this.commit = commit;
        this.libraryName = libraryName;
        this.affectedFiles = affectedFiles;
    }
}
