package com.base.new_base.Services;

import com.base.new_base.Converters.ProductConverters;
import com.base.new_base.DTO.ProductDTO;
import com.base.new_base.Entity.Order;
import com.base.new_base.Entity.Product;
import com.base.new_base.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    ProductConverters productConverters = new ProductConverters();

    public ArrayList<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return new ArrayList<>();
        ArrayList<ProductDTO> productsDTOS = new ArrayList<>();
        for (Product product : products) {
            productsDTOS.add(productConverters.productToProductDto(product));
        }
        return productsDTOS;
    }

    public ProductDTO findById(int id) {
        Product product = productRepository.findById(id);
        if (product == null) return null;
        return productConverters.productToProductDto(product);
    }

    public ProductDTO findByNameProd(String nameProd) {
        Product product = productRepository.findByNameProd(nameProd);
        if (product == null) return null;
        return productConverters.productToProductDto(product);
    }

    public void deleteById(int id) {
        if (findById(id) != null) productRepository.deleteById(id);
    }

    public void save(ProductDTO productDTO) {
        if (findByNameProd(productDTO.getNameProd()) == null) {
            Product product = productConverters.productDtoToProduct(productDTO);
            productRepository.save(product);
        }
    }

    public void update(ProductDTO productDTO) {
        Product product = productConverters.productDtoToProduct(productDTO);
        productRepository.save(product);
    }

    public void updateQuantityAll(List<Integer> quantityProdList){
        ArrayList<ProductDTO> productDTOS = findAll();
        for (int i = 0;i<productDTOS.size();i++) {
            productDTOS.get(i).setQuantityProd(quantityProdList.get(i));
            update(productDTOS.get(i));
        }
    }

    public void updatePriceAll(List<Double> priceProdList){
        ArrayList<ProductDTO> productDTOS = findAll();
        for (int i = 0;i<productDTOS.size();i++) {
            productDTOS.get(i).setPriceProd(priceProdList.get(i));
            update(productDTOS.get(i));
        }
    }

    public void addQuantity(int id, int quantity) {
        Product product = productRepository.findById(id);
        product.setQuantityProd(product.getQuantityProd()+quantity);
    }

    public void updateQuantity(int id, int quantity) {
        Product product = productRepository.findById(id);
        product.setQuantityProd(product.getQuantityProd()-quantity);
        productRepository.save(product);
    }
}
