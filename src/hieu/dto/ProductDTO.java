package hieu.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {

    private String hash, name, description, imageLink, warranty, url, categoryHash;
    private float price;

    public ProductDTO() {
    }

    public ProductDTO(String hash, String name, String warranty, String url, String categoryHash, float price) {
        this.hash = hash;
        this.name = name.trim();
        if (!warranty.isEmpty()) {
            this.warranty = warranty.trim();
        } else {
            this.warranty = "12 tháng";
        }
        this.url = url.trim();
        this.categoryHash = categoryHash;
        this.price = price;
    }

    public ProductDTO(String hash, String name, String warranty, float price) {
        this.hash = hash;
        this.name = name;
        this.warranty = warranty;
        this.price = price;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryHash() {
        return categoryHash;
    }

    public void setCategoryHash(String categoryHash) {
        this.categoryHash = categoryHash;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
