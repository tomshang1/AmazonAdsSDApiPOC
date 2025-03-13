package org.example.objectApi.CampaignsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.model.Campaign;

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

public class ListCampaignApi {
    public static Campaign listCampaign(
            final CampaignsApi campaignsApi,
            final long campaignId,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<Campaign> listResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            listResponseContent = campaignsApi.listCampaigns(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), null, null,
                    null, null, String.valueOf(campaignId), null);
        } catch (final ApiException e) {
            System.out.println("Exception while listing campaign: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("List CampaignId: " + campaignId + ", response: " + listResponseContent);

        assertNotNull(listResponseContent);
        assertEquals(1, listResponseContent.size());
        return listResponseContent.get(0);
    }
}
