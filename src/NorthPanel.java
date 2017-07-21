

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;


public class NorthPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LayoutEntity result;
	
	private static final int radius = 20;
	private static final int loop = 10;
	private static final int k = 100;
	private static final int offsetX = 500;
	private static final int offsetY = 300;
	
//	private KnowledgeGraph knowledgeGraph = new KnowledgeGraph();
//	private KnowledgeGraphV3 knowledgeGraph = new KnowledgeGraphV3();
	private KnowledgeGraphV2 knowledgeGraph = new KnowledgeGraphV2();
	
	public void getNodes() {
//		for (int i = 0; i < loop; i++) {
			result = knowledgeGraph.peacockLayout();
//		}
//		result = knowledgeGraph.autoLayout();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if (result != null) {
			
			//画节点
			List<PageNode> nodes = result.getNodes();
//			System.out.println(nodes);
			Iterator<PageNode> itNode = nodes.iterator();
			
			while (itNode.hasNext()) {
				PageNode node = itNode.next();
//				System.out.println(node);
				double x = node.getX();
				double y = node.getY();
//				System.out.println(node.getString("id")+": x:"+x+" y:"+y);
				g.setColor(Color.black);
				g.fillOval((int)(x*k+offsetX), (int)(y*k+offsetY), radius, radius);
				g.setColor(Color.red);
				g.drawString(node.getId(), (int)(x*k+offsetX), (int)(y*k+offsetY));
			}
			
			//画线
			List<PageEdge> edges = result.getEdges();
			if (edges == null) {
				return;
			}
			Iterator<PageEdge> itEdge = edges.iterator();
			
			while (itEdge.hasNext()) {
				PageEdge edge = itEdge.next();
				String sourceId = edge.getSource();
				String targetId = edge.getTarget();
				PageNode source = null;
				PageNode target = null;
				for (PageNode node : nodes) {
					if (node.getId().equals(sourceId)) {
						source = node;
						break;
					}
				}
				for (PageNode node : nodes) {
					if (node.getId().equals(targetId)) {
						target = node;
						break;
					}
				}
				
				double sourceX = source.getX();
				double sourceY = source.getY();
				double targetX = target.getX();
				double targetY = target.getY();
				g.setColor(Color.black);
				g.drawLine((int)(sourceX*k+offsetX+radius/2), (int)(sourceY*k+offsetY+radius/2), 
						(int)(targetX*k+offsetX+radius/2), (int)(targetY*k+offsetY+radius/2));
			}
		}
		
	}
}
