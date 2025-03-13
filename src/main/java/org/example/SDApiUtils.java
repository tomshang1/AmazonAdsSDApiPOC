package org.example;

import org.openapitools.client.ApiException;
import org.openapitools.client.api.AdGroupsApi;
import org.openapitools.client.api.CampaignsApi;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.AdGroup;
import org.openapitools.client.model.AdGroupResponse;
import org.openapitools.client.model.Campaign;
import org.openapitools.client.model.CampaignResponse;
import org.openapitools.client.model.CreateAdGroup;
import org.openapitools.client.model.CreateCampaign;
import org.openapitools.client.model.CreateTargetingClause;
import org.openapitools.client.model.TargetResponse;
import org.openapitools.client.model.TargetingClause;
import org.openapitools.client.model.UpdateCampaign;
import org.openapitools.client.model.UpdateTargetingClause;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.AuthUtils.getRefreshedToken;
import static org.example.Main.CLIENT_ID;
import static org.example.Main.CLIENT_SECRET;
import static org.example.Main.PROFILE_ID_SCOPE;
import static org.example.Main.REFRESH_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SDApiUtils {
    public static long createSDCampaign(final CampaignsApi campaignsApi,
                                        final CreateCampaign createRequestContent)
            throws IOException, InterruptedException, ApiException {
        final List<CampaignResponse> createResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            createResponseContent = campaignsApi.createCampaigns(CLIENT_ID, PROFILE_ID_SCOPE, List.of(createRequestContent));
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

    public static CampaignResponse updateSDCampaign(
            final CampaignsApi campaignsApi,
            final UpdateCampaign updateRequestContent)
            throws IOException, InterruptedException, ApiException {
        final List<CampaignResponse> updateResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            updateResponseContent = campaignsApi.updateCampaigns(CLIENT_ID, PROFILE_ID_SCOPE, List.of(updateRequestContent));
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

    public static CampaignResponse deleteSDCampaign(
            final CampaignsApi campaignsApi,
            final Long deleteCampaignId)
            throws IOException, InterruptedException, ApiException {
        final CampaignResponse deleteResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            deleteResponseContent = campaignsApi.archiveCampaign(CLIENT_ID, PROFILE_ID_SCOPE, deleteCampaignId);
        } catch (final ApiException e) {
            System.out.println("Exception while deleting campaign: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Delete CampaignId: " + deleteCampaignId + ", response: " + deleteResponseContent);

        return deleteResponseContent;
    }

    public static Campaign listSDCampaign(
            final CampaignsApi campaignsApi,
            final long campaignId)
            throws IOException, InterruptedException, ApiException {
        final List<Campaign> listResponseContent;
        try {
            campaignsApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            listResponseContent = campaignsApi.listCampaigns(CLIENT_ID, PROFILE_ID_SCOPE, null, null,
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

    public static long createSDTarget(final TargetingApi targetingApi,
                                      final CreateTargetingClause createRequestContent)
            throws IOException, InterruptedException, ApiException {
        final List<TargetResponse> createResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            createResponseContent = targetingApi.createTargetingClauses(CLIENT_ID, PROFILE_ID_SCOPE, List.of(createRequestContent));
        } catch (final ApiException e) {
            System.out.println("Exception while creating target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Create Target request: " + createRequestContent + ", response: " + createResponseContent);
        final Long targetId = Optional.ofNullable(createResponseContent)
                .map(list -> list.get(0))
                .map(TargetResponse::getTargetId)
                .orElse(null);
        assertNotNull(targetId);

        return targetId;
    }

    public static TargetResponse updateSDTarget(
            final TargetingApi targetingApi,
            final UpdateTargetingClause updateRequestContent)
            throws IOException, InterruptedException, ApiException {
        final List<TargetResponse> updateResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            updateResponseContent = targetingApi.updateTargetingClauses(CLIENT_ID, PROFILE_ID_SCOPE, List.of(updateRequestContent));
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

    public static TargetResponse deleteSDTarget(
            final TargetingApi targetApi,
            final Long deleteTargetId)
            throws IOException, InterruptedException, ApiException {
        final TargetResponse deleteResponseContent;
        try {
            targetApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            deleteResponseContent = targetApi.archiveTargetingClause(CLIENT_ID, PROFILE_ID_SCOPE, deleteTargetId);
        } catch (final ApiException e) {
            System.out.println("Exception while deleting target: " + e.getMessage()
                    + "\n Headers: " + e.getResponseHeaders()
                    + "\n Body: " + e.getResponseBody());
            throw e;
        }

        System.out.println("Delete TargetId: " + deleteTargetId + ", response: " + deleteResponseContent);

        return deleteResponseContent;
    }

    public static TargetingClause listSDTarget(
            final TargetingApi targetingApi,
            final long targetId)
            throws IOException, InterruptedException, ApiException {
        final List<TargetingClause> listResponseContent;
        try {
            targetingApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            listResponseContent = targetingApi.listTargetingClauses(CLIENT_ID, PROFILE_ID_SCOPE, null, null,
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

    public static long createSDAdGroup(final AdGroupsApi adGroupsApi,
                                       final CreateAdGroup createRequestContent)
            throws IOException, InterruptedException, ApiException {
        final List<AdGroupResponse> createResponseContent;
        try {
            adGroupsApi.getApiClient().addDefaultHeader("Authorization", getRefreshedToken(REFRESH_TOKEN, CLIENT_ID, CLIENT_SECRET));
            createResponseContent = adGroupsApi.createAdGroups(CLIENT_ID, PROFILE_ID_SCOPE, List.of(createRequestContent));
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
