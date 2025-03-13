package org.example;
import org.apache.commons.lang3.StringUtils;
import org.example.objectApi.AdGroupsApiService;
import org.example.objectApi.CampaignsApiService;
import org.example.objectApi.TargetsApiService;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.Campaign;
import org.openapitools.client.model.TargetingClause;

import java.io.IOException;
import java.util.Map;

import static org.example.AuthUtils.CLIENT_ID_HEADER_NAME;
import static org.example.AuthUtils.CLIENT_SECRET_HEADER_NAME;
import static org.example.AuthUtils.PROFILE_ID_HEADER_NAME;
import static org.example.AuthUtils.REFRESH_TOKEN_HEADER_NAME;
import static org.example.AuthUtils.getAccessTokenMap;
import static org.example.AuthUtils.getAdGroupsApi;
import static org.example.AuthUtils.getCampaignsApi;
import static org.example.AuthUtils.getProfileId;
import static org.example.AuthUtils.getRefreshedToken;
import static org.example.AuthUtils.getTargetsApi;
import static org.example.objectUtils.SDAdGroupUtils.buildCreateSDAdGroupRequestContent;
import static org.example.objectUtils.SDCampaignUtils.buildCreateSDCampaignsRequestContent;
import static org.example.objectUtils.SDCampaignUtils.buildUpdateSBCampaignsRequestContent;
import static org.example.objectUtils.SDTargetUtils.buildCreateSDTargetingClauseRequestContent;
import static org.example.objectUtils.SDTargetUtils.buildUpdateSBTargetRequestContent;
import static org.junit.Assert.assertEquals;

public class Main {
    // TO BE SET BY USER
    private final static String AUTH_CODE = null; // Change with new auth code to fetch profileId or refresh token
    private final static String PROFILE_ID_SCOPE = null; // Set this to fetched profileId! Don't need to refresh this
    public final static String REFRESH_TOKEN = null; // Set this with fetched refresh token! Don't need to refresh this
    private final static String CLIENT_ID = null; // Get from LWA account
    private final static String CLIENT_SECRET = null; // Get from LWA account
    // END

    private static final Map<String, String> AUTH_MAP = Map.of(
            CLIENT_ID_HEADER_NAME, CLIENT_ID,
            CLIENT_SECRET_HEADER_NAME, CLIENT_SECRET,
            PROFILE_ID_HEADER_NAME, PROFILE_ID_SCOPE,
            REFRESH_TOKEN_HEADER_NAME, REFRESH_TOKEN
    );

    private static final CampaignsApiService campaignsApiService = new CampaignsApiService(getCampaignsApi(), AUTH_MAP);
    private static final AdGroupsApiService adGroupsApiService = new AdGroupsApiService(getAdGroupsApi(), AUTH_MAP);
    private static final TargetsApiService targetsApiService = new TargetsApiService(getTargetsApi(), AUTH_MAP);

    private static void testCampaignApiFunctionality() throws IOException, InterruptedException, ApiException {
        // Create Campaign with paused state
        final long campaignId = campaignsApiService.createCampaign(buildCreateSDCampaignsRequestContent());

        // Query Campaign
        final Campaign listResponseContent
                = campaignsApiService.listCampaign(campaignId);
        assertEquals(Campaign.StateEnum.PAUSED, listResponseContent.getState());

        // Update Campaign with enabled state
        campaignsApiService.updateCampaign(buildUpdateSBCampaignsRequestContent(campaignId));

        // Query Campaign
        final Campaign updateListResponseContent
                = campaignsApiService.listCampaign(campaignId);
        assertEquals(Campaign.StateEnum.ENABLED, updateListResponseContent.getState());

        // Delete Campaign with archived state
        campaignsApiService.deleteCampaign(campaignId);

        // Query Campaign
        final Campaign archiveListResponseContent
                = campaignsApiService.listCampaign(campaignId);
        assertEquals(Campaign.StateEnum.ARCHIVED, archiveListResponseContent.getState());
    }

    private static void testTargetApiFunctionality() throws IOException, InterruptedException, ApiException {
        // Create parent Campaign
        final long campaignId = campaignsApiService.createCampaign(buildCreateSDCampaignsRequestContent());

        // Create parent AdGroup
        final long adGroupId = adGroupsApiService.createAdGroup(buildCreateSDAdGroupRequestContent(campaignId));

        // Create Target with paused state
        final long targetingId = targetsApiService.createTarget(buildCreateSDTargetingClauseRequestContent(adGroupId));

        // Query Target
        final TargetingClause listResponseContent = targetsApiService.listTarget(targetingId);
        assertEquals(TargetingClause.StateEnum.PAUSED, listResponseContent.getState());

        // Update Target with enabled state
        targetsApiService.updateTarget(buildUpdateSBTargetRequestContent(targetingId));

        // Query Target
        final TargetingClause updateListResponseContent = targetsApiService.listTarget(targetingId);
        assertEquals(TargetingClause.StateEnum.ENABLED, updateListResponseContent.getState());

        // Delete Target with archived state
        targetsApiService.deleteTarget(targetingId);

        // Query Target
        final TargetingClause deleteListResponseContent = targetsApiService.listTarget(targetingId);
        assertEquals(TargetingClause.StateEnum.ARCHIVED, deleteListResponseContent.getState());

        // Delete Campaign with archived state
        campaignsApiService.deleteCampaign(campaignId);
    }


    public static void main(String[] args) throws IOException, InterruptedException, ApiException {
        if (StringUtils.isBlank(REFRESH_TOKEN)) {
            final Map<String, String> tokenMap = getAccessTokenMap(AUTH_CODE, CLIENT_ID, CLIENT_SECRET);
            System.out.println("PLEASE ADD VALUE TO REFRESH_TOKEN and try again: " + tokenMap.get(REFRESH_TOKEN_HEADER_NAME));
            return; // if refreshToken is not set, fetch it, print it to console, and return (since auth code can only be used once)
        }

        if (StringUtils.isBlank(PROFILE_ID_SCOPE)) {
            final String profileId = getProfileId(getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET), CLIENT_ID);
            System.out.println("PLEASE ADD VALUE TO PROFILE_ID_SCOPE and try again: " + profileId);
            return; // if profileId is not set, fetch it, print it to console, and return (since auth code can only be used once)
        }

        testCampaignApiFunctionality();

        testTargetApiFunctionality();
    }
}