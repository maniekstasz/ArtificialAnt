package ant;

import java.util.Date;
import java.util.Random;

/**
 * @author Szymon Konicki
 *
 */
public class NodeFactory {
	public static Random r = new Random();
	
	
//	public static int nextInt(int limit){
//		return (int)(Math.random()*limit);
//	}
	public static Node getFunction(){
		int i = r.nextInt(3);
		return new Node(Action.values()[i]);
	}
	public static Node getTerminal(){
		int i = r.nextInt(3)+3;
		return new Node(Action.values()[i]);
	}
	
	
}
