package org.example.objectApi.TargetsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.TargetResponse;

import java.io.IOException;
import java.util.Map;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getRefreshedToken;

public class DeleteTargetApi {
    public static TargetResponse deleteTarget(
            final TargetingApi targetApi,
            final Long deleteTargetId,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final TargetResponse deleteResponseContent;
        try {
            targetApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            deleteResponseContent = targetApi.archiveTargetingClause(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), deleteTargetId);
        } catch (final ApiException e) {
            System.out.println("Exception while deleting target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Delete TargetId: " + deleteTargetId + ", response: " + deleteResponseContent);

        return deleteResponseContent;
    }
}
