package io.seclens.mtrx.data.repository;

import io.seclens.mtrx.data.domain.VulnerableLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VulnerableLibraryRepository extends JpaRepository<VulnerableLibrary, UUID> {
}
