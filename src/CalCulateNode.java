

public class CalCulateNode {

	private PageNode pageNode;
	private int mass;

	public PageNode getPageNode() {
		return pageNode;
	}

	public void setPageNode(PageNode pageNode) {
		this.pageNode = pageNode;
	}

	public int getMass() {
		return mass;
	}

	public void setMass(int mass) {
		this.mass = mass;
	}
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + mass;
        result = prime * result + ((pageNode == null) ? 0 : pageNode.hashCode());
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
        CalCulateNode other = (CalCulateNode) obj;
        if (mass != other.mass)
            return false;
        if (pageNode == null) {
            if (other.pageNode != null)
                return false;
        } else if (!pageNode.equals(other.pageNode))
            return false;
        return true;
    }
	
	
}
