package org.example.objectApi.CampaignsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.model.CampaignResponse;
import org.openapitools.client.model.UpdateCampaign;

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

public class UpdateCampaignApi {
    public static CampaignResponse updateCampaign(
            final CampaignsApi campaignsApi,
            final UpdateCampaign updateRequestContent,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<CampaignResponse> updateResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            updateResponseContent = campaignsApi.updateCampaigns(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), List.of(updateRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while updating campaign: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        assertNotNull(updateResponseContent);
        assertEquals(1, updateResponseContent.size());
        System.out.println("Update Campaigns request: " + updateRequestContent + ", response: " + updateResponseContent);

        return updateResponseContent.get(0);
    }
}
