
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;



public class GraphHandler extends JFrame implements MouseListener
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphHandler() {
		        setTitle("Simple Frame");		
		        addMouseListener(this);
		    }
						
		    @Override		
		    public void mouseClicked(MouseEvent e) {		
		        int x = e.getX();		
		        int y = e.getY();		
		       System.out.println("Mouse Clicked at X: " + x + " - Y: " + y);		
		    }


			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub			
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub			
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub			
			}
}
