package s3.ai;


import java.util.*;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;
import s3.ai.Node;

public class AStar {

	Pair<Double, Double> pos;
	Pair<Double, Double> goal;
	S3 current_game;
	Queue<Node> open;
	Map<List<Double>, Node> closed;
	S3PhysicalEntity character;

	public static int pathDistance(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
		AStar a = new AStar(start_x,start_y,goal_x,goal_y,i_entity,the_game);
		List<Pair<Double, Double>> path = a.computePath();
		if (path!=null) return path.size();
		return -1;
	}

	public AStar(double start_x, double start_y, double goal_x, double goal_y,
			S3PhysicalEntity i_entity, S3 the_game) {
		pos = new Pair(start_x,start_y);
		goal = new Pair(goal_x,goal_y);
		current_game = the_game;
		character = i_entity;
	}
	public List<Pair<Double, Double>> computePath() {
		List<Pair<Double,Double>> path = new ArrayList<>();
		Node start = new Node(pos.getX().intValue(),pos.getY().intValue(),character,current_game);
		//OPEN = [Start];
		start.heuristic=computeHeuristic(start.pos,this.goal);
		open = new PriorityQueue<>();
		open.add(start);
		//CLOSED = []
		closed = new HashMap<>();
		while(!open.isEmpty()) {
			Node n = open.poll();
			if(n.pos.getX().intValue()==this.goal.getX().intValue() && n.pos.getY().intValue()==this.goal.getY().intValue()) {
				//RETURN path to N ->while loop using parent in AStar
				while(n.parent!=null){
					path.add(0, n.pos);
					n=n.parent;
				}
				return path;
			}
			//place the object using its position as the key
			List<Double> pair = new ArrayList<>();
			pair.add(n.pos.getX());
			pair.add(n.pos.getY());
			closed.put(pair, n);
			List<Pair<Double, Double>> children = getMoves(n.pos,current_game);
			//for all children M of N not in CLOSED:
			for (Pair<Double, Double> newPos : children) {
				List<Double> pair1 = new ArrayList<>();
				pair1.add(newPos.getX());
				pair1.add(newPos.getY());
				if(closed.get(pair1)==null) {
					Node child = new Node(newPos.getX().intValue(), newPos.getY().intValue(), n.character, n.current_game);
					child.parent = n;
					child.depth = n.depth + 1;
					child.heuristic = computeHeuristic(child.pos, this.goal);
					open.add(child);
				}
			}//END FOR
		}// END WHILE
		return null;
	}
	//I've realized that m_a and m_b are public, I'm going to use these getters for clarity's sake.
	public int computeHeuristic(Pair<Double,Double> current, Pair<Double,Double> goal) {
		return (Math.abs(goal.getX().intValue() - current.getX().intValue()) + (Math.abs(goal.getY().intValue() - current.getY().intValue())));

	}
	public List<Pair<Double,Double>> getMoves(Pair<Double,Double> posi, S3 the_game)
	{
		List<Pair<Double,Double>> children = new ArrayList<>();
		Pair<Double,Double> up = new Pair(posi.getX(),(posi.getY()-1));
		Pair<Double,Double> down = new Pair(posi.getX(),(posi.getY()+1));
		Pair<Double,Double> left = new Pair((posi.getX()-1),posi.getY());
		Pair<Double,Double> right = new Pair((posi.getX()+1),posi.getY());
		if( the_game.isSpaceFree(1,up.getX().intValue(),up.getY().intValue()))
		{
			children.add(up);
		}
		if(the_game.isSpaceFree(1,down.getX().intValue(),down.getY().intValue()))
		{
			children.add(down);
		}
		if(the_game.isSpaceFree(1,left.getX().intValue(),left.getY().intValue()))
		{
			children.add(left);
		}
		if(the_game.isSpaceFree(1,right.getX().intValue(),right.getY().intValue()))
		{
			children.add(right);
		}
		return children;
	}
}
