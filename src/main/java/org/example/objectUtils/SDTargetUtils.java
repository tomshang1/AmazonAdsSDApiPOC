package org.example.objectUtils;

import org.openapitools.client.model.ContentTargetingPredicate;
import org.openapitools.client.model.CreateTargetingClause;
import org.openapitools.client.model.CreateTargetingExpressionInner;
import org.openapitools.client.model.UpdateTargetingClause;

import java.util.List;

public class SDTargetUtils {
    public static CreateTargetingClause buildCreateSDTargetingClauseRequestContent(final long adGroupId) {
        final CreateTargetingClause createTargetingClause = new CreateTargetingClause();
        createTargetingClause.setState(CreateTargetingClause.StateEnum.PAUSED);
        createTargetingClause.setBid(10f);
        createTargetingClause.setAdGroupId(adGroupId);
        createTargetingClause.setExpressionType(CreateTargetingClause.ExpressionTypeEnum.MANUAL);
        createTargetingClause.setExpression(List.of(createTargetingExpressionInner()));

        return createTargetingClause;
    }

    private static CreateTargetingExpressionInner createTargetingExpressionInner() {
        final ContentTargetingPredicate predicate = new ContentTargetingPredicate();
        predicate.setType(ContentTargetingPredicate.TypeEnum.CONTENT_CATEGORY_SAME_AS);
        predicate.setValue("amzn1.iab-content.325");

        final CreateTargetingExpressionInner createTargetingExpressionInner = new CreateTargetingExpressionInner();
        createTargetingExpressionInner.setActualInstance(predicate);
        return createTargetingExpressionInner;
    }

    public static UpdateTargetingClause buildUpdateSBTargetRequestContent(final long targetId) {
        final UpdateTargetingClause updateTarget = new UpdateTargetingClause();
        updateTarget.setTargetId(targetId);
        updateTarget.setState(UpdateTargetingClause.StateEnum.ENABLED);
        return updateTarget;
    }
}
