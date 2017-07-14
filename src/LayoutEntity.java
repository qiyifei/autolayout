

import java.util.List;

public class LayoutEntity {
    private List<PageNode> nodes;
    
    private List<PageEdge> edges;

    public List<PageNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PageNode> nodes) {
        this.nodes = nodes;
    }
    
    public List<PageEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<PageEdge> edges) {
        this.edges = edges;
    }
}
