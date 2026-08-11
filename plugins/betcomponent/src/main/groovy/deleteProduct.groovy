def deleteProduct() {
    String productId = parameters.productId

    def product = delegator.findOne("Product", [productId: productId], false)

    if (!product) {
        return [error: "Product not found with ID: " + productId]
    }

    product.set("isEnabled", "N")
    product.store()

    return [successMessage: "Product " + productId + " has been soft deleted successfully"]
}