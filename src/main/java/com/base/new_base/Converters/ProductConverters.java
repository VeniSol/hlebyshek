package com.base.new_base.Converters;

import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.Entity.Product;

public class ProductConverters {
    public static Product productDtoToProduct(ProductDTO product) {
        Product productConvert = new Product();
        productConvert.setId(product.getId());
        productConvert.setNameProd(product.getNameProd());
        productConvert.setPriceProd(product.getPriceProd());
        productConvert.setQuantityProd(product.getQuantityProd());
        return productConvert;
    }

    public static ProductDTO productToProductDto(Product product) {
        ProductDTO productConvert = new ProductDTO();
        productConvert.setId(product.getId());
        productConvert.setNameProd(product.getNameProd());
        productConvert.setPriceProd(product.getPriceProd());
        productConvert.setQuantityProd(product.getQuantityProd());
        return productConvert;
    }
}
