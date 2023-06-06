package com.vnco.fusiontech.product.DTO;

public class ProductDTO {
        private int id;
        private String name;
        private String description;
        private String image;
        private int quantity;
        private int categoryId;
        private int brandId;

        // Constructors, getters, and setters

        // Constructors
        public ProductDTO() {
        }

        public ProductDTO(int id, String name, String description, String image, int quantity, int categoryId, int brandId) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.image = image;
            this.quantity = quantity;
            this.categoryId = categoryId;
            this.brandId = brandId;
        }

        // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
}


