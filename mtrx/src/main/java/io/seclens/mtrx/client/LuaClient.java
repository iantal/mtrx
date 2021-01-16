package io.seclens.mtrx.client;

import com.google.protobuf.ProtocolStringList;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.seclens.mtrx.ResultProviderGrpc;
import io.seclens.mtrx.ResultRequest;
import io.seclens.mtrx.ResultResponse;
import io.seclens.mtrx.data.domain.VulnerableLibrary;
import io.seclens.mtrx.data.dto.VulnerabilityDto;
import io.seclens.mtrx.data.dto.VulnerableLibraryDto;
import io.seclens.mtrx.service.VulnerableLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LuaClient {

    @Autowired
    private VulnerableLibraryService vulnerableLibraryService;

    private final ManagedChannel channel;

    public LuaClient() {
        this.channel = ManagedChannelBuilder.forAddress("lua-service", 8008)
                .usePlaintext()
                .build();
    }

    @Async
    public void getVulnerableLibraries(List<VulnerabilityDto> vulnerabilities) {
        if(vulnerabilities.isEmpty()) {
            log.info("Empty vulnerability list provided");
            return;
        }
        String projectId = vulnerabilities.get(0).getProjectId();
        String commit = vulnerabilities.get(0).getCommit();
        List<String> libs = new ArrayList<>();
        log.info("Calling lua for project {} and commit {}", projectId, commit);

        for (VulnerabilityDto vulnerability : vulnerabilities) {
            libs.add(vulnerability.getLibraryName());
        }

        ResultProviderGrpc.ResultProviderBlockingStub luaClient = ResultProviderGrpc.newBlockingStub(channel);

        ResultRequest resultRequest = ResultRequest.newBuilder()
                .setProjectID(projectId)
                .setCommitHash(commit)
                .addAllLibraries(libs)
                .build();

        ResultResponse response = luaClient.provideVulnerableLibrariesData(resultRequest);
        persist(response);
        // TODO: notify bff that analysis is done
    }

    private void persist(ResultResponse response) {
        response.getVulnerableLibrariesList()
                .forEach( v -> {
                    ProtocolStringList affectedProjectFilesList = v.getAffectedProjectFilesList();
                    List<String> affectedFiles = new ArrayList<>(affectedProjectFilesList);
                    VulnerableLibraryDto vl = VulnerableLibraryDto.builder()
                            .projectId(v.getProjectID())
                            .commit(v.getCommitHash())
                            .libraryName(v.getName())
                            .affectedFiles(affectedFiles)
                            .build();
                    vulnerableLibraryService.save(vl);
                });
    }

}
