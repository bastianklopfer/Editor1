import java.awt.EventQueue;
import java.io.IOException;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JToolBar;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Editor1 {

	private JFrame frame;
	public String selectedSUC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor1 window = new Editor1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public Editor1() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new GraphHandler();
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton btnDatei = new JButton("Datei");
		toolBar.add(btnDatei);
				
		JPanel SUCContainer = new JPanel();
		frame.getContentPane().add(SUCContainer, BorderLayout.WEST);
		SUCContainer.setBackground(Color.gray);
			GridBagLayout gbl_SUCContainer = new GridBagLayout();
			gbl_SUCContainer.columnWidths = new int[]{107, 0};
			gbl_SUCContainer.rowHeights = new int[]{16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_SUCContainer.columnWeights = new double[]{0.0, Double.MIN_VALUE};
			gbl_SUCContainer.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			SUCContainer.setLayout(gbl_SUCContainer);
			
			JLabel lblSystemunitclasses = new JLabel("SystemUnitClasses");
			GridBagConstraints gbc_lblSystemunitclasses = new GridBagConstraints();
			gbc_lblSystemunitclasses.insets = new Insets(0, 0, 5, 0);
			gbc_lblSystemunitclasses.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblSystemunitclasses.gridx = 0;
			gbc_lblSystemunitclasses.gridy = 0;
			SUCContainer.add(lblSystemunitclasses, gbc_lblSystemunitclasses);
		
			/**
			 * Folgend werden die SystemUnitClassen in den vorgesehenen Container geladen
			 * */
			String AllSUCNames[] = SUCGetter.getNames();
			
			ButtonGroup bg = new ButtonGroup();
								
			
			for (int i =0; i<AllSUCNames.length && AllSUCNames[i] != null; i++) {
				
			JRadioButton rdbtnName = new JRadioButton(AllSUCNames[i]);
			GridBagConstraints gbc_rdbtnName = new GridBagConstraints();
			gbc_rdbtnName.anchor = GridBagConstraints.NORTHWEST;
			gbc_rdbtnName.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnName.gridx = 0;
			gbc_rdbtnName.gridy = i+1;
			gbc_rdbtnName.fill = 3;
			SUCContainer.add(rdbtnName, gbc_rdbtnName);
			bg.add(rdbtnName);		
			if (i==0) {
				rdbtnName.setSelected(true);
				selectedSUC = rdbtnName.getText();
			}
			
			rdbtnName.addActionListener(new ActionListener(){
			    public void actionPerformed(ActionEvent e) {
			      System.out.println("Gewählte SUC: "+rdbtnName.getText());
			      selectedSUC = rdbtnName.getText();
			    }
			});
			}
			
			mxGraph graph = new mxGraph();
			Object parent = graph.getDefaultParent();
			
	
			mxGraphComponent graphComponent = new mxGraphComponent(graph);
			
			frame.getContentPane().add(graphComponent,BorderLayout.CENTER);
			
			graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 2) {
						graph.getModel().beginUpdate();
						try
						{
					Object v1 = graph.insertVertex(parent, null, selectedSUC, e.getX(), e.getY(), 80,
							30);
						}
						finally
						{
							graph.getModel().endUpdate();
						}
					}
				}
			});
	}
}
