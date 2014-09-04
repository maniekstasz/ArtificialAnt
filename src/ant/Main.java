package ant;

import java.awt.Frame;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author Szymon Konicki
 *
 */
public class Main extends JFrame {


	public static void main(String[] args) {
		ApplicationContext context = new ApplicationContext();
		GeneticService service = ApplicationContext.geneticService;
		NodeFactory factory = new NodeFactory();
		Program best = service.loop();
		service.setCurrentProgram(best);
		best.reset();
		ApplicationContext.controller.setGraphics(true);
		while(!best.isStop()){
			best.execute();
		}
	}

}
