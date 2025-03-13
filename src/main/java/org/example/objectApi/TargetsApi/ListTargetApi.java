package org.example.objectApi.TargetsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.TargetingClause;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getRefreshedToken;
import static org.example.Main.CLIENT_ID;
import static org.example.Main.CLIENT_SECRET;
import static org.example.Main.PROFILE_ID_SCOPE;
import static org.example.Main.REFRESH_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListTargetApi {
    public static TargetingClause listTarget(
            final TargetingApi targetingApi,
            final long targetId,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<TargetingClause> listResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            listResponseContent = targetingApi.listTargetingClauses(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), null, null,
                    null, String.valueOf(targetId), null, null);
        } catch (final ApiException e) {
            System.out.println("Exception while listing target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("List TargetId: " + targetId + ", response: " + listResponseContent);

        assertNotNull(listResponseContent);
        assertEquals(1, listResponseContent.size());
        return listResponseContent.get(0);
    }
}
