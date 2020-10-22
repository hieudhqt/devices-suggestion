package hieu.dto;

public class CategoryDTO {

    public CategoryDTO(String name, String url, String hash) {
        this.name = name.trim();
        this.url = url.trim();
        this.hash = hash;
    }

    private String name, url, hash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
