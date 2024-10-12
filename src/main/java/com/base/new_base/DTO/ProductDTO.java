package com.base.new_base.DTO;

public class ProductDTO {
    private int id;
    private String nameProd;
    private Double priceProd;
    private int quantityProd;

    public ProductDTO() {
    }

    public ProductDTO(int id, String nameProd, Double priceProd, int quantityProd) {
        this.id = id;
        this.nameProd = nameProd;
        this.priceProd = priceProd;
        this.quantityProd = quantityProd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProd() {
        return nameProd;
    }

    public void setNameProd(String nameProd) {
        this.nameProd = nameProd;
    }

    public Double getPriceProd() {
        return priceProd;
    }

    public void setPriceProd(Double priceProd) {
        this.priceProd = priceProd;
    }

    public int getQuantityProd() {
        return quantityProd;
    }

    public void setQuantityProd(int quantityProd) {
        this.quantityProd = quantityProd;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", nameProd='" + nameProd + '\'' +
                ", priceProd=" + priceProd +
                ", quantityProd=" + quantityProd +
                '}';
    }
}
