import org.apache.ofbiz.entity.condition.EntityCondition
import org.apache.ofbiz.entity.condition.EntityOperator
import org.apache.ofbiz.entity.util.EntityQuery

def getAllProducts() {
    int viewSize = parameters.viewSize ? parameters.viewSize as int : 10
    int viewIndex = parameters.viewIndex ? parameters.viewIndex as int : 0
    String keyword = parameters.keyword ?: ""

    List conditions = []
    conditions.add(EntityCondition.makeCondition("isEnabled", EntityOperator.NOT_EQUAL, "N"))

    if (keyword) {
        conditions.add(EntityCondition.makeCondition("productName", EntityOperator.LIKE, "%" + keyword + "%"))
    }

    def finalCondition = EntityCondition.makeCondition(conditions, EntityOperator.AND)

    def allProducts = EntityQuery.use(delegator)
        .from("Product")
        .where(finalCondition)
        .queryList()

    int total = allProducts.size()
    int start = viewIndex * viewSize
    int end = Math.min(start + viewSize, total)
    def pagedProducts = (start < total) ? allProducts.subList(start, end) : []

    return [products: pagedProducts, totalProducts: total]
}