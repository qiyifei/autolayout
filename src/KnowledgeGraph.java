

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;



public class KnowledgeGraph {

	private static List<PageNode> oldNodes;
	private static List<PageNode> queryNodes;
	private static List<PageNode> newNodes;
	private static List<PageNode> moveNodes;
	private static List<PageNode> allNodes;
	private static List<PageEdge> relations;
	
	private static final int scale_1000 = 1000;
	
	private final int normalLength_peacock_tesion = 100;
	private final int normalLength_peacock_repulsion= 800;
	private final int normalLength_peacock_hasNoRelation = 800;
	private final double step_weight = 0.003;
	private final double k_peacock = 1;
	private final double g_peacock = 0.0005;
	private final int limitDL_mutiply_loop_count = 100;
	private final int max_loop_count = 1000;
	private final double stable_nodes_percentage = 0.8;
	
//	private final int normalLength = 800;
//	private final int normalLength_peacock = 800;
//	private final int normalLength_peacock_hasNoRelation = 800;
//	private final int normalForce = 50000;
//	private final int step = 300;
//	private final double k = 1;
//	private final double k_peacock = 1;
//	private final int g = 2000;
//	private final int g_peacock = 1000;
//	private final int limitDL_mutiply_loop_count = 100;
//	private final int max_loop_count = 1000;
//	private final double stable_nodes_percentage = 0.8;
	
	
	static {
		int oldNodeNum = 0;
		int newNodeNum = 1000;
		int queryNodeNum = 3;
		
		oldNodes = new ArrayList<>();
		queryNodes = new ArrayList<>();
		newNodes = new ArrayList<>();
		relations = new ArrayList<>();
		
		//初始化节点
		int num = 0;
		for (int i = 0; i < num+oldNodeNum; i++) {
			PageNode node = new PageNode();
			node.setId(i+"");
			oldNodes.add(node);
		}
		
		num += oldNodeNum;
		for (int i = num; i < num+queryNodeNum; i++) {
			PageNode node = new PageNode();
			node.setId(i+"");
			queryNodes.add(node);
		}
		
		num += queryNodeNum;
		for (int i = num; i < num+newNodeNum; i++) {
			PageNode node = new PageNode();
			node.setId(i+"");
			newNodes.add(node);
		}
		
		//初始化边
		num = 0;
		for (int i = 0; i < num+newNodeNum/3; i++) {
			PageEdge edge = new PageEdge();
			edge.setId(i+"");
			edge.setSource(queryNodes.get(0).getId());
			edge.setTarget(newNodes.get(i).getId());
			relations.add(edge);
		}
		
		
		num += newNodeNum/3;
		for (int i = num-1; i < num+newNodeNum/3; i++) {
			PageEdge edge = new PageEdge();
			edge.setId(i+"");
			edge.setSource(queryNodes.get(1).getId());
			edge.setTarget(newNodes.get(i).getId());
			relations.add(edge);
		}
		
		num += newNodeNum/3;
        for (int i = num-1; i < num+newNodeNum/3; i++) {
            PageEdge edge = new PageEdge();
            edge.setId(i+"");
            edge.setSource(queryNodes.get(2).getId());
            edge.setTarget(newNodes.get(i).getId());
            relations.add(edge);
        }
		
//		num += newNodeNum/2;
//		for (int i = num; i < num+newNodeNum; i++) {
//			PageEdge edge = new PageEdge();
//			edge.setId(i+"");
//			edge.setSource(queryNodes.get(1).getId());
//			edge.setTarget(newNodes.get(i-newNodeNum/2).getId());
//			relations.add(edge);
//		}
		
		//初始化坐标
		Iterator<PageNode> it;
		it = oldNodes.iterator();
		while (it.hasNext()) {
			PageNode node = it.next();
			node.setX(Math.random() * scale_1000);
			node.setY(Math.random() * scale_1000);
		}
		
		it = queryNodes.iterator();
		while (it.hasNext()) {
			PageNode node = it.next();
			node.setX(Math.random() * scale_1000);
			node.setY(Math.random() * scale_1000);
		}
		
		it = newNodes.iterator();
		while (it.hasNext()) {
			PageNode node = it.next();
			node.setX(Math.random() * scale_1000);
			node.setY(Math.random() * scale_1000);
		}
		
		moveNodes = new ArrayList<>();
//		moveNodes.addAll(queryNodes);
		moveNodes.addAll(newNodes);
		
		allNodes = new ArrayList<>();
		allNodes.addAll(moveNodes);
		allNodes.addAll(queryNodes);
		allNodes.addAll(oldNodes);
		
//		moveNodes = allNodes;
//		relations = null;
		
//		System.out.println(LayoutParameter.auto_g);
//		System.out.println(LayoutParameter.auto_k);
	}
	
	public LayoutEntity peacockLayout() {
		
//		List<PageNode> allNodes = new ArrayList<>();
//		allNodes.addAll(newNodes);
//		allNodes.addAll(oldNodes);
	    
	    int times = 0;
	    double limitDl = 1;
	    
	    long start = System.currentTimeMillis();
	    List<CalCulateNode> calCulateMoveNodes = new ArrayList<>();
        for (PageNode node : moveNodes) {
            calCulateMoveNodes.add(calculateMass(node, relations));
        }
        
        List<CalCulateNode> calCulateAllNodes = new ArrayList<>();
        for (PageNode node : allNodes) {
            calCulateAllNodes.add(calculateMass(node, relations));
        }
	    
	    long startTime = System.currentTimeMillis();
	    while (true) {
	    	times++;
            
            if (times%limitDL_mutiply_loop_count == 0) {
				limitDl *= 2;
			}
            System.out.println(times);
            System.out.println(limitDl);
            
            
            
            List<List<Double>> totalForce = calculateForce(calCulateMoveNodes, calCulateAllNodes);
//            System.out.println(totalForce.size());
//            List<Double> totalForceX = totalForce.get(0);
//            List<Double> totalForceY = totalForce.get(1);
//            for (int i = 0; i < totalForceX.size(); i++) {
//            	System.out.println(moveNodes.get(i).getId()+": forceX:"+totalForceX.get(i)+" forceY:"+totalForceY.get(i));
//			}
//            System.out.println("移动前的位置：");
//            for (PageNode moveNode : moveNodes) {
//				System.out.println(moveNode.getId()+": X:"+moveNode.getX()+" Y:"+moveNode.getY());
//			}
            List<Double> moveDistances = move(calCulateMoveNodes, totalForce);
//            System.out.println("移动后的位置：");
//            for (PageNode moveNode : moveNodes) {
//				System.out.println(moveNode.getId()+": X:"+moveNode.getX()+" Y:"+moveNode.getY());
//			}
            if (isConvergent(moveDistances, limitDl) || times > max_loop_count) {
				break;
			}
		}
	    long endTime = System.currentTimeMillis();
	    System.out.println("Calculate mass cost:" + (startTime - start));
	    System.out.println("Time cost:" + (endTime - startTime));
//            
        return changeDataFormat(allNodes, relations);
	}
	
	private List<List<Double>> calculateForce(List<CalCulateNode> moveNodes, List<CalCulateNode> allNodes) {
		List<Double> totalForceX = new ArrayList<>();
        List<Double> totalForceY = new ArrayList<>();

        // 计算所有节点重力
        

        // 计算没个要移动的节点的受力情况
        for (CalCulateNode moveNode : moveNodes) {
            double sumForceX = 0;
            double sumForceY = 0;
            for (CalCulateNode node : allNodes) {
                if (node.equals(moveNode)) {
                    continue;
                }
                
//                long before = System.currentTimeMillis();
//                boolean has_relation = hasRelation(moveNode.getPageNode(), node.getPageNode(), relations);
//                long after = System.currentTimeMillis();
//                System.out.println("hasRelation cost:" + (after - before));
//                System.out.println("node"+moveNode.getPageNode().getId()+" and node"+node.getPageNode().getId()+" isHasRelation:"+has_relation);

                List<Double> force;
                // 计算拉力
//                force = tesion(moveNode, node);
//                if (has_relation) {
//                force = tesion(moveNode, node);
//                if (has_relation) {
                    force = tesion_peacock(moveNode, node);
                    sumForceX += force.get(0);
                    sumForceY += force.get(1);
//                }
            	
                
//                force = repulsion(moveNode, node);
                force = repulsion_peacock(moveNode, node);
                sumForceX += force.get(0);
                sumForceY += force.get(1);
//				} else {
//					force = repulsion_peacock_hasNoRelation(moveNode, node);
//                    sumForceX += force.get(0);
//                    sumForceY += force.get(1);
//				}

                // 计算斥力
//                force = repulsion(moveNode, node);
                

            }
            totalForceX.add(sumForceX);
            totalForceY.add(sumForceY);

        }

        List<List<Double>> totalForce = new ArrayList<>();
        totalForce.add(totalForceX);
        totalForce.add(totalForceY);
        return totalForce;
	}
	
	
	
	private List<Double> repulsion_peacock(CalCulateNode moveNode, CalCulateNode forceNode) {
		List<Double> force = new ArrayList<>();
		double dl = calculateDL(moveNode.getPageNode(), forceNode.getPageNode());
		if (dl > normalLength_peacock_repulsion) {
			force.add(0.0);
			force.add(0.0);
		} else {
			double forceX = k_peacock * (normalLength_peacock_repulsion - dl) * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
			double forceY = k_peacock * (normalLength_peacock_repulsion - dl) * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
			force.add(forceX);
			force.add(forceY);
		}
		return force;
	}
	
	private List<Double> repulsion_peacock_hasNoRelation(CalCulateNode moveNode, CalCulateNode forceNode) {
		List<Double> force = new ArrayList<>();
		double dl = calculateDL(moveNode.getPageNode(), forceNode.getPageNode());
		if (dl > normalLength_peacock_hasNoRelation) {
			force.add(0.0);
			force.add(0.0);
		} else {
			double forceX = k_peacock * (normalLength_peacock_hasNoRelation - dl) * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
			double forceY = k_peacock * (normalLength_peacock_hasNoRelation - dl) * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
			force.add(forceX);
			force.add(forceY);
		}
		return force;
	}
	
	private List<Double> tesion_peacock(CalCulateNode moveNode, CalCulateNode forceNode) {
		List<Double> force = new ArrayList<>();
		double forceX;
		double forceY;
		double dl = calculateDL(moveNode.getPageNode(), forceNode.getPageNode());
//		System.out.println(dl);
		if (dl < normalLength_peacock_tesion) {
//			forceX = normalForce * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
//			forceY = normalForce * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
		    forceX = 0;
		    forceY = 0;
		} else {
			double f = -g_peacock * moveNode.getMass() * forceNode.getMass() * dl;
			forceX = f * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
			forceY = f * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
		}
		force.add(forceX);
		force.add(forceY);
		
		return force;
	}
	
	
	private CalCulateNode calculateMass(PageNode node, List<PageEdge> relations) {
	    int m = 1;
        if (null != relations) {
            for (int i = 0; i < relations.size(); i++) {
                PageEdge relation = relations.get(i);
                if (relation.getSource().equals(node.getId()) || relation.getTarget().equals(node.getId())) {
                    m++;
                }
            }
        }
        
        CalCulateNode calCulateNode = new CalCulateNode();
        calCulateNode.setPageNode(node);
        calCulateNode.setMass(m);
        return calCulateNode;
    }
	
	private boolean hasRelation(PageNode node1, PageNode node2, List<PageEdge> relations) {
		if (relations == null || relations.size() == 0) {
			return false;
		}
		
		for (PageEdge relation : relations) {
			if (relation.getSource().equals(node1.getId()) && relation.getTarget().equals(node2.getId())
					|| relation.getSource().equals(node2.getId()) && relation.getTarget().equals(node1.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	private double calculateDL(PageNode moveNode, PageNode forceNode) {
		double dx = moveNode.getX() - forceNode.getX();
		double dy = moveNode.getY() - forceNode.getY();
		return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
	}
	
	private List<Double> move(List<CalCulateNode> moveNodes, List<List<Double>> force) {
		
		List<Double> totalForceX = force.get(0);
		List<Double> totalForceY = force.get(1);
		List<Double> moveDistances = new ArrayList<>();
		
		for (int i = 0; i < moveNodes.size(); i++) {
			PageNode moveNode = moveNodes.get(i).getPageNode();
			double forceX = totalForceX.get(i);
			double forceY = totalForceY.get(i);
			double dX = forceX * step_weight / moveNodes.get(i).getMass();
			double dY = forceY * step_weight / moveNodes.get(i).getMass();
			moveNode.setX(moveNode.getX() + dX);
			moveNode.setY(moveNode.getY() + dY);
			
			moveDistances.add(Math.sqrt(dX*dX+dY*dY));
		}
		
		return moveDistances;
	}
	
	private boolean isConvergent(List<Double> moveDistances, double limitDl) {
		int count = 0;
		for (int i = 0; i < moveDistances.size(); i++) {
			double dl = moveDistances.get(i);
			if (dl < limitDl) {
				count++;
			}
		}
		if (count > moveDistances.size() * stable_nodes_percentage) {
			return true;
		}
		return false;
	}
	
	private LayoutEntity changeDataFormat(List<PageNode> allNodes, List<PageEdge> relations) {
		List<PageNode> newAllNodes = new ArrayList<>();
    	for (PageNode node : allNodes) {
    		PageNode newNode = new PageNode();
    		newNode.setId(node.getId());
    		newNode.setX(node.getX() / scale_1000);
    		newNode.setY(node.getY() / scale_1000);
    		newAllNodes.add(newNode);
		}
    	LayoutEntity result = new LayoutEntity();
    	result.setNodes(newAllNodes);
    	result.setEdges(relations);
    	return result;
	}
	
	public static void main(String[] args) {
		KnowledgeGraph kGraph = new KnowledgeGraph();
//		oldNodes.forEach(node -> System.out.println(node.getId()+":"+node.getX()+" "+node.getY()));
//		queryNodes.forEach(node -> System.out.println(node.getId()+":"+node.getX()+" "+node.getY()));
//		newNodes.forEach(node -> System.out.println(node.getId()+":"+node.getX()+" "+node.getY()));
//		for (PageNode node : oldNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		for (PageNode node : queryNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		for (PageNode node : newNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		List<PageNode> allNodes = new ArrayList<>();
//		allNodes.addAll(newNodes);
//		allNodes.addAll(oldNodes);
//		kGraph.calculateForce(newNodes, allNodes);
//		kGraph.calculateMass(queryNodes.get(1), edges);
//		System.out.println(queryNodes.get(1).getData().getQuantity());
		kGraph.peacockLayout();
//		for (PageNode node : oldNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		for (PageNode node : queryNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		for (PageNode node : newNodes) {
//			System.out.println(node.getId()+":"+node.getX()+" "+node.getY());
//		}
//		
//		for (PageNode node : allNodes) {
//			kGraph.calculateMass(node, edges);
//		}
//		
//		for (PageNode node : allNodes) {
//			System.out.println(node.getData().getQuantity());
//		}
	}
}
 