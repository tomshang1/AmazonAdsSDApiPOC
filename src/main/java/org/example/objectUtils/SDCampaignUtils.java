package org.example.objectUtils;

import org.openapitools.client.model.CreateCampaign;
import org.openapitools.client.model.Tactic;
import org.openapitools.client.model.UpdateCampaign;

import static org.example.CommonUtils.generateName;

public class SDCampaignUtils {

    public static CreateCampaign buildCreateSDCampaignsRequestContent() {
        final CreateCampaign createCampaign = new CreateCampaign();
        createCampaign.setName(generateName("SB_CAMPAIGN"));
        createCampaign.setState(CreateCampaign.StateEnum.PAUSED);
        createCampaign.setBudget(100d);
        createCampaign.setBudgetType(CreateCampaign.BudgetTypeEnum.DAILY);
        createCampaign.setStartDate("20250501");
        createCampaign.setEndDate("20260101");
        createCampaign.setTactic(Tactic.T00020);

        return createCampaign;
    }

    public static UpdateCampaign buildUpdateSBCampaignsRequestContent(final long campaignId) {
        final UpdateCampaign updateCampaign = new UpdateCampaign();
        updateCampaign.setCampaignId(campaignId);
        updateCampaign.setState(UpdateCampaign.StateEnum.ENABLED);
        return updateCampaign;
    }
}
