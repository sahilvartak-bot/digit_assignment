package digit.academy.tutorial.repository;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.idgen.IdGenerationRequest;
import org.egov.common.contract.idgen.IdGenerationResponse;
import org.egov.common.contract.idgen.IdRequest;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class IDGenRepository {

    private final ServiceRequestRepository serviceRequestRepository;
    private final String idGenHost;
    private final String idGenPath;

    @Autowired
    public IDGenRepository(ServiceRequestRepository serviceRequestRepository,
                           @Value("${egov.idgen.host}") String idGenHost,
                           @Value("${egov.idgen.path}") String idGenPath) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.idGenHost = idGenHost;
        this.idGenPath = idGenPath;
    }

    public String generateApplicationId(RequestInfo requestInfo, String tenantId, String idName, String count) {
        StringBuilder uri = new StringBuilder(idGenHost).append(idGenPath);

        // Build the IdGenerationRequest object
        IdGenerationRequest idGenerationRequest = IdGenerationRequest.builder()
                .requestInfo(requestInfo)
                .idRequests(List.of(new IdRequest(tenantId, idName, count)))
                .build();

        // Fetch response from IDGen service
        IdGenerationResponse response = serviceRequestRepository.fetchResult(uri, idGenerationRequest, IdGenerationResponse.class);

        // Return the first generated ID
        return response != null && response.getIdResponses() != null && !response.getIdResponses().isEmpty()
                ? response.getIdResponses().get(0).toString()
                : null;
    }
}

