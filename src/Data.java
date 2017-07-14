

public class Data {
    private Integer quantity;
    private String quality;
    
    public Data() {
        
    }
    
    public Data(Integer quantity, String quality) {
        this.quantity = quantity;
        this.quality = quality;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getQuality() {
        return quality;
    }
    public void setQuality(String quality) {
        this.quality = quality;
    }
}
