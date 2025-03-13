package org.example.objectApi.CampaignsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.model.CampaignResponse;
import org.openapitools.client.model.CreateCampaign;

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

public class CreateCampaignApi {
    public static long createCampaign(final CampaignsApi campaignsApi,
                                      final CreateCampaign createRequestContent,
                                      final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final List<CampaignResponse> createResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            createResponseContent = campaignsApi.createCampaigns(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), List.of(createRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while creating campaign: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Create Campaigns request: " + createRequestContent + ", response: " + createResponseContent);
        final Long campaignId = Optional.ofNullable(createResponseContent)
                .map(list -> list.get(0))
                .map(CampaignResponse::getCampaignId)
                .orElse(null);
        assertNotNull(campaignId);

        return campaignId;
    }
}
