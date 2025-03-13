package org.example.objectApi;

import org.example.objectApi.CampaignsApi.CreateCampaignApi;
import org.example.objectApi.CampaignsApi.DeleteCampaignApi;
import org.example.objectApi.CampaignsApi.ListCampaignApi;
import org.example.objectApi.CampaignsApi.UpdateCampaignApi;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.model.Campaign;
import org.openapitools.client.model.CampaignResponse;
import org.openapitools.client.model.CreateCampaign;
import org.openapitools.client.model.UpdateCampaign;

import java.io.IOException;
import java.util.Map;

public class CampaignsApiService {
    private final CampaignsApi campaignsApi;
    private final Map<String, String> authMap;

    public CampaignsApiService(CampaignsApi campaignsApi, Map<String, String> authMap) {
        this.campaignsApi = campaignsApi;
        this.authMap = authMap;
    }

    public Long createCampaign(final CreateCampaign createRequestContent) throws IOException, InterruptedException, ApiException {
        return CreateCampaignApi.createCampaign(campaignsApi, createRequestContent, authMap);
    }

    public CampaignResponse updateCampaign(final UpdateCampaign updateRequestContent) throws IOException, InterruptedException, ApiException {
        return UpdateCampaignApi.updateCampaign(campaignsApi, updateRequestContent, authMap);
    }

    public CampaignResponse deleteCampaign(final long deleteId) throws IOException, InterruptedException, ApiException {
        return DeleteCampaignApi.deleteCampaign(campaignsApi, deleteId, authMap);
    }

    public Campaign listCampaign(final long listId) throws IOException, InterruptedException, ApiException {
        return ListCampaignApi.listCampaign(campaignsApi, listId, authMap);
    }
}
