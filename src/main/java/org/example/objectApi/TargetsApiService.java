package org.example.objectApi;

import org.example.objectApi.TargetsApi.CreateTargetApi;
import org.example.objectApi.TargetsApi.DeleteTargetApi;
import org.example.objectApi.TargetsApi.ListTargetApi;
import org.example.objectApi.TargetsApi.UpdateTargetApi;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.TargetingApi;
import org.openapitools.client.model.CreateTargetingClause;
import org.openapitools.client.model.TargetResponse;
import org.openapitools.client.model.TargetingClause;
import org.openapitools.client.model.UpdateTargetingClause;

import java.io.IOException;
import java.util.Map;

public class TargetsApiService {
    private final TargetingApi targetsApi;
    private final Map<String, String> authMap;

    public TargetsApiService(TargetingApi targetsApi, Map<String, String> authMap) {
        this.targetsApi = targetsApi;
        this.authMap = authMap;
    }

    public long createTarget(final CreateTargetingClause createRequestContent) throws IOException, InterruptedException, ApiException {
        return CreateTargetApi.createTarget(targetsApi, createRequestContent, authMap);
    }

    public TargetResponse updateTarget(final UpdateTargetingClause updateRequestContent) throws IOException, InterruptedException, ApiException {
        return UpdateTargetApi.updateTarget(targetsApi, updateRequestContent, authMap);
    }

    public TargetResponse deleteTarget(final Long deleteId) throws IOException, InterruptedException, ApiException {
        return DeleteTargetApi.deleteTarget(targetsApi, deleteId, authMap);
    }

    public TargetingClause listTarget(final long listId) throws IOException, InterruptedException, ApiException {
        return ListTargetApi.listTarget(targetsApi, listId, authMap);
    }
}
