package org.example.objectApi.TargetsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.TargetResponse;
import org.openapitools.client.model.UpdateTargetingClause;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getRefreshedToken;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UpdateTargetApi {
    public static TargetResponse updateTarget(
            final TargetingApi targetingApi,
            final UpdateTargetingClause updateRequestContent,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<TargetResponse> updateResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            updateResponseContent = targetingApi.updateTargetingClauses(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), List.of(updateRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while updating target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        assertNotNull(updateResponseContent);
        assertEquals(1, updateResponseContent.size());
        System.out.println("Update target request: " + updateRequestContent + ", response: " + updateResponseContent);

        return updateResponseContent.get(0);
    }
}
