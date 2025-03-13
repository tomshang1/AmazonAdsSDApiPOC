package org.example.objectApi.CampaignsApi;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.model.CampaignResponse;

import java.io.IOException;
import java.util.Map;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getRefreshedToken;

public class DeleteCampaignApi {
    public static CampaignResponse deleteCampaign(
            final CampaignsApi campaignsApi,
            final Long deleteCampaignId,
            final Map<String, String> authMap)
            throws IOException, InterruptedException, ApiException {
        final CampaignResponse deleteResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization",
                    getRefreshedToken(authMap.get(REFRESH_TOKEN_HEADER_NAME), authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(CLIENT_SECRET_HEADER_NAME)));
            deleteResponseContent = campaignsApi.archiveCampaign(authMap.get(CLIENT_ID_HEADER_NAME), authMap.get(PROFILE_ID_HEADER_NAME), deleteCampaignId);
        } catch (final ApiException e) {
            System.out.println("Exception while deleting campaign: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Delete CampaignId: " + deleteCampaignId + ", response: " + deleteResponseContent);

        return deleteResponseContent;
    }

}
