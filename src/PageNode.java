

public class PageNode {
    private double x;
    private double y;
    private String id;
    private String label;
    private String type;
    private String infoType;
    private boolean active;
    private Data data;
    
    public PageNode() {
        
    }
    
    public PageNode(String id, String label) {
        this.id = id;
        this.label = label;
    }
    
    public PageNode(double x, double y, String id, String label, String type, String infoType, boolean active, Data data) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.label = label;
        this.type = type;
        this.infoType = infoType;
        this.active = active;
        this.data = data;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }
    
    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }
    
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageNode other = (PageNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
