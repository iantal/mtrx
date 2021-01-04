package io.seclens.mtrx.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Result {

    @Id
    private UUID projectId;

    private String commit;

    private String libraryName;

    private String libraryType;

    private List<Vulnerability> vulnerabilities;
}
