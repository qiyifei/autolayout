

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KnowledgeGraphV2 {

	private static List<PageNode> oldNodes;
	private static List<PageNode> queryNodes;
	private static List<PageNode> newNodes;
	private static List<PageNode> moveNodes;
	private static List<PageNode> allNodes;
	private static List<PageEdge> relations;
	
//	private static final int scale_1000 = 1;
	
	private final double normalLength_tesion= 0.1;
	private final double normalLength_repulsion = 0.1;
	private final int normalForce = 1;
	private final double step_weight = 1;
	private final double k = 0.1;
	private final double g = 0.001;
	private final int min_loop_count = 50;
	private final int max_loop_count = 130;
	private final double stable_nodes_percentage = 0.9;
	
	private static int loop = 0;
	
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
		int newNodeNum = 300;
		int queryNodeNum = 1;
		
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
		if (queryNodeNum != 0) {
	          //初始化边
	            num = 0;
	            for (int j = num; j < num+newNodeNum/queryNodeNum; j++) {
	                PageEdge edge = new PageEdge();
	                edge.setId(j+"");
	                edge.setSource(queryNodes.get(0).getId());
	                edge.setTarget(newNodes.get(j).getId());
	                relations.add(edge);
	            }
	            
	            num += newNodeNum/queryNodeNum;
	            for (int i = 1; i < queryNodeNum; i++) {
	                for (int j = num; j < num+newNodeNum/queryNodeNum; j++) {
	                    PageEdge edge = new PageEdge();
	                    edge.setId(j+"");
	                    edge.setSource(queryNodes.get(i).getId());
	                    edge.setTarget(newNodes.get(j).getId());
	                    relations.add(edge);
	                }
	                
	                num += newNodeNum/queryNodeNum;
	            }
        }
		
		
//		num += newNodeNum/2;
//		for (int i = num-1; i < num+newNodeNum/2; i++) {
//			PageEdge edge = new PageEdge();
//			edge.setId(i+"");
//			edge.setSource(queryNodes.get(1).getId());
//			edge.setTarget(newNodes.get(i).getId());
//			relations.add(edge);
//		}
		
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
			node.setX(Math.random());
			node.setY(Math.random());
		}
		
		it = queryNodes.iterator();
		while (it.hasNext()) {
			PageNode node = it.next();
			node.setX(Math.random());
			node.setY(Math.random());
		}
		
		it = newNodes.iterator();
		while (it.hasNext()) {
			PageNode node = it.next();
			node.setX(Math.random());
			node.setY(Math.random());
		}
		
		moveNodes = new ArrayList<>();
		moveNodes.addAll(queryNodes);
		moveNodes.addAll(newNodes);
		
		allNodes = new ArrayList<>();
		allNodes.addAll(moveNodes);
//		allNodes.addAll(queryNodes);
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
	    
//	    int times = 0;
	    double limitDl = 0.02;
	    
	    Map<String, PageNode> idNodeMap = buildIdNodeMap(allNodes);
        Map<String, Set<PageNode>> oneDegreeMap = buildOneDegreeMap(idNodeMap, relations);
	    
        List<CalCulateNode> calCulateMoveNodes = calculateMass(moveNodes, oneDegreeMap);
        List<CalCulateNode> calCulateAllNodes = calculateMass(allNodes, oneDegreeMap);
	    
	    long startTime = System.currentTimeMillis();
	    while (true) {
	    	loop++;
            
//            if (times%limitDL_mutiply_loop_count == 0) {
//				limitDl *= 2;
//			}
            
            
            
            
            List<List<Double>> totalForce = calculateForce(calCulateMoveNodes, calCulateAllNodes, oneDegreeMap);
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
//            for (int i = 0; i < moveDistances.size(); i++) {
//                System.out.println(moveDistances.get(i));
//            }
            System.out.println(loop);
            System.out.println(limitDl);
//            System.out.println(isConvergent(moveDistances, limitDl));
            if (loop < min_loop_count) {
                continue;
            }
            if (isConvergent(moveDistances, limitDl) || loop > max_loop_count) {
				break;
			}
		}
	    long endTime = System.currentTimeMillis();
	    System.out.println("Time cost:" + (endTime - startTime));
//            
        return changeDataFormat(allNodes, relations);
	}
	
	private List<List<Double>> calculateForce(List<CalCulateNode> moveNodes, List<CalCulateNode> allNodes, Map<String, Set<PageNode>> oneDegreeMap) {
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
                
//                boolean has_relation = hasRelation(moveNode.getPageNode(), node.getPageNode(), relations);
//                System.out.println("node"+moveNode.getPageNode().getId()+" and node"+node.getPageNode().getId()+" isHasRelation:"+has_relation);
                boolean has_relation = hasRelation(moveNode.getPageNode(), node.getPageNode(), oneDegreeMap);
                
                
                List<Double> force;
                // 计算拉力
                if (has_relation) {
                    force = tesion(moveNode, node);
                    sumForceX += force.get(0);
                    sumForceY += force.get(1);
                }
                
                force = repulsion(moveNode, node);
                sumForceX += force.get(0);
                sumForceY += force.get(1);
                

            }
            totalForceX.add(sumForceX);
            totalForceY.add(sumForceY);

        }

        List<List<Double>> totalForce = new ArrayList<>();
        totalForce.add(totalForceX);
        totalForce.add(totalForceY);
        return totalForce;
	}
	
	private List<Double> tesion(CalCulateNode moveNode, CalCulateNode forceNode) {
		List<Double> force = new ArrayList<>();
		double dl = calculateDL(moveNode.getPageNode(), forceNode.getPageNode());
//		System.out.println("点"+moveNode.getPageNode().getId()+"与点"+forceNode.getPageNode().getId()+"的距离为："+dl);
		if (dl < normalLength_tesion) {
			force.add(0.0);
			force.add(0.0);
		} else {
			double forceX = k * (dl - normalLength_tesion) * (forceNode.getPageNode().getX() - moveNode.getPageNode().getX()) / dl;
			double forceY = k * (dl - normalLength_tesion) * (forceNode.getPageNode().getY() - moveNode.getPageNode().getY()) / dl;
			force.add(forceX);
			force.add(forceY);
		}
		return force;
	}
	
	
	private List<Double> repulsion(CalCulateNode moveNode, CalCulateNode forceNode) {
		List<Double> force = new ArrayList<>();
		double forceX;
		double forceY;
		double dl = calculateDL(moveNode.getPageNode(), forceNode.getPageNode());
//		System.out.println("点"+moveNode.getPageNode().getId()+"与点"+forceNode.getPageNode().getId()+"的距离为："+dl);
		if (dl < normalLength_repulsion) {
			forceX = normalForce * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
			forceY = normalForce * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
		} else {
			double f = g * moveNode.getMass() * forceNode.getMass() / dl / dl;
			forceX = f * (moveNode.getPageNode().getX() - forceNode.getPageNode().getX()) / dl;
			forceY = f * (moveNode.getPageNode().getY() - forceNode.getPageNode().getY()) / dl;
		}
		force.add(forceX);
		force.add(forceY);
		
		return force;
	}
	
	private boolean hasRelation(PageNode node1, PageNode node2, Map<String, Set<PageNode>> oneDegreeMap) {
        if (oneDegreeMap == null || oneDegreeMap.size() == 0) {
            return false;
        }
        
        if (oneDegreeMap.get(node1.getId()) != null && oneDegreeMap.get(node1.getId()).contains(node2)) {
            return true;
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
    		newNode.setX(node.getX());
    		newNode.setY(node.getY());
    		newAllNodes.add(newNode);
		}
    	LayoutEntity result = new LayoutEntity();
    	result.setNodes(newAllNodes);
    	result.setEdges(relations);
    	return result;
	}
	
	private Map<String, PageNode> buildIdNodeMap(List<PageNode> nodes) {
        Map<String, PageNode> idNodeMap = new HashMap<String, PageNode>();
        for (PageNode node : nodes) {
            idNodeMap.put(node.getId(), node);
        }
        return idNodeMap;
    }

    private Map<String, Set<PageNode>> buildOneDegreeMap(Map<String, PageNode> idNodeMap, List<PageEdge> edges) {
        Map<String, Set<PageNode>> oneDegreeMap = new HashMap<String, Set<PageNode>>();
        Set<PageNode> oneDegreeNodes = null;
        for (PageEdge edge : edges) {
            String source = edge.getSource();
            String target = edge.getTarget();
            PageNode sourceNode = idNodeMap.get(source);
            PageNode targetNode = idNodeMap.get(target);
            if (oneDegreeMap.containsKey(source)) {
                oneDegreeMap.get(source).add(targetNode);
            } else {
                oneDegreeNodes = new HashSet<PageNode>();
                oneDegreeNodes.add(targetNode);
                oneDegreeMap.put(source, oneDegreeNodes);
            }

            if (oneDegreeMap.containsKey(target)) {
                oneDegreeMap.get(target).add(sourceNode);
            } else {
                oneDegreeNodes = new HashSet<PageNode>();
                oneDegreeNodes.add(sourceNode);
                oneDegreeMap.put(target, oneDegreeNodes);
            }
        }
        return oneDegreeMap;
    }
    
    private List<CalCulateNode> calculateMass(List<PageNode> nodes, Map<String, Set<PageNode>> oneDegreeMap) {
        List<CalCulateNode> calCulateNodes = new ArrayList<>();
        for (PageNode node : nodes) {
            CalCulateNode calCulateNode = new CalCulateNode();
            calCulateNode.setPageNode(node);
            Set<PageNode> oneDegreeNode = oneDegreeMap.get(node.getId());
            if (oneDegreeNode != null) {
                calCulateNode.setMass(oneDegreeNode.size()+1);
            } else {
                calCulateNode.setMass(1);
            }
            calCulateNodes.add(calCulateNode);
        }
        
        return calCulateNodes;
    }
	
	public static void main(String[] args) {
		KnowledgeGraphV2 kGraph = new KnowledgeGraphV2();
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
 