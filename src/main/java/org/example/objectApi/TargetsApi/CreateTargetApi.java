package org.example.objectApi.TargetsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.CreateTargetingClause;
import org.openapitools.client.model.TargetResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getRefreshedToken;
import static org.junit.Assert.assertNotNull;

public class CreateTargetApi {
    public static long createTarget(final TargetingApi targetingApi,
                                    final CreateTargetingClause createRequestContent,
                                    final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<TargetResponse> createResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            createResponseContent = targetingApi.createTargetingClauses(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), List.of(createRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while creating target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Create Target request: " + createRequestContent + ", response: " + createResponseContent);
        final Long targetId = Optional.ofNullable(createResponseContent)
                .map(list -> list.get(0))
                .map(TargetResponse::getTargetId)
                .orElse(null);
        assertNotNull(targetId);

        return targetId;
    }
}
