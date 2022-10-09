package s3.ai;

import java.util.*;

import s3.base.S3;
import s3.entities.S3PhysicalEntity;
import s3.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Node implements Comparable<Node> {

    Pair<Double, Double> pos;
    S3 current_game;
    int depth;
    int heuristic;
    Node parent;
    S3PhysicalEntity character;
    public Node(double start_x, double start_y, S3PhysicalEntity i_entity, S3 the_game){
        this.pos=new Pair(start_x, start_y);
        this.current_game=the_game;
        this.parent=null;
        this.depth=0;
        this.character=i_entity;
    }


    @Override
    public int compareTo(Node o) {
        if((this.heuristic+this.depth)>(o.heuristic+o.depth)){
            return 1;
        }
        else if((this.heuristic+this.depth)<(o.heuristic+o.depth)){
            return -1;
        }
        else{
            return 0;
        }
    }
}
