

public class PageEdge {
    private String id;
    private String source;
    private String type;
    private String target;
    private String label;
    private Boolean isMerged;
    private LayoutEntity rawData;

    public PageEdge() {

    }

    public PageEdge(String id, String source, String type, String target, String label, LayoutEntity rawData) {
        this.id = id;
        this.source = source;
        this.type = type;
        this.target = target;
        this.label = label;
        this.isMerged = true;
        this.rawData = rawData;
    }

    public PageEdge(String id, String source, String target, String type, String label) {
        this.isMerged = false;
        this.id = id;
        this.source = source;
        this.target = target;
        this.type = type;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getIsMerged() {
        return isMerged;
    }

    public void setIsMerged(Boolean isMerged) {
        this.isMerged = isMerged;
    }

    public LayoutEntity getRawData() {
        return rawData;
    }

    public void setRawData(LayoutEntity rawData) {
        this.rawData = rawData;
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
        PageEdge other = (PageEdge) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
