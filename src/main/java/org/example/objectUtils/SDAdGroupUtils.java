package org.example.objectUtils;

import org.example.CommonUtils;
import org.openapitools.client.model.CreateAdGroup;

public class SDAdGroupUtils {
    public static CreateAdGroup buildCreateSDAdGroupRequestContent(final long campaignId) {
        final CreateAdGroup createAdGroup = new CreateAdGroup();
        createAdGroup.setName(CommonUtils.generateName("SB_AD_GROUP"));
        createAdGroup.setCampaignId(campaignId);
        createAdGroup.setState(CreateAdGroup.StateEnum.PAUSED);
        return createAdGroup;
    }
}
