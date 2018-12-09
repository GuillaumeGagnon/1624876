package ca.cours5b5.guillaumegagnon.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GDirection {

    public int incrementHorizontal;
    public int incrementVertical;

    public static List<GDirection> directions;

    static{

        directions = new ArrayList<>();

        // horizontal
        directions.add(new GDirection(1,0));

        // vertical
        directions.add(new GDirection(0,1));

        // diagonale /
        directions.add(new GDirection(1,1));

        // diagonale \
        directions.add(new GDirection(1,-1));

    }

    public GDirection(int incrementHorizontal, int incrementVertical){

        this.incrementHorizontal = incrementHorizontal;
        this.incrementVertical = incrementVertical;

    }


}
