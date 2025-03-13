package org.example.objectApi.AdGroupsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.AdGroupsApi;
import org.openapitools.client.model.AdGroupResponse;
import org.openapitools.client.model.CreateAdGroup;

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

public class CreateAdGroupApi {
    public static long createAdGroup(final AdGroupsApi adGroupsApi,
                                     final CreateAdGroup createRequestContent,
                                     final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<AdGroupResponse> createResponseContent;
        try {
            adGroupsApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            createResponseContent = adGroupsApi.createAdGroups(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), List.of(createRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while creating AdGroup: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Create AdGroup request: " + createRequestContent + ", response: " + createResponseContent);
        final Long adGroupId = Optional.ofNullable(createResponseContent)
                .map(list -> list.get(0))
                .map(AdGroupResponse::getAdGroupId)
                .orElse(null);
        assertNotNull(adGroupId);

        return adGroupId;
    }
}
