package io.seclens.mtrx.data.repository;

import io.seclens.mtrx.data.domain.VulnerableLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VulnerableLibraryRepository extends JpaRepository<VulnerableLibrary, UUID> {
    List<VulnerableLibrary> findByProjectIdAndCommit(@Param("projectId") String projectId,
                                                 @Param("commit") String commit);
}
