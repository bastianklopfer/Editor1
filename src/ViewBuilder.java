import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Panel;
import javax.swing.JScrollBar;

public class ViewBuilder implements ActionListener{
	
	static JFrame frame;
	public static int sucLibCounter = 0;
	private final static JPanel SucContainer = new JPanel();
	private final static JPanel IeContainer = new JPanel();
	public static JList SucNameList = new JList<String>();
	static JScrollPane SucScrollPane = new JScrollPane(new JPanel());
	static JScrollPane IeScrollPane = new JScrollPane(new JPanel());
	
	
	JMenuItem mntmffnen = new JMenuItem("\u00D6ffnen...");
	JMenuItem mntmSucLaden = new JMenuItem("SUC laden...");
	JMenuItem mntmSpeichern = new JMenuItem("Speichern...");
	
	static JList previous = new JList();
	static JList current = new JList();
	public static String selectedSuc = new String();
	
	static JTable IeTable;
	
	static List<String> ieList = new ArrayList<String>();
	static JPanel view = new JPanel();
	String cellId;
	static ImageIcon deleteIcon = new ImageIcon ("images/delete.png");
	static ImageIcon editIcon = new ImageIcon ("images/pencil.png");
	public ViewBuilder() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new GraphHandler();
		frame.setTitle("AML Tool");
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
				
			
			mxGraph graph = new mxGraph();
			Object parent = graph.getDefaultParent();
			
	
			mxGraphComponent graphComponent = new mxGraphComponent(graph);
			
			frame.getContentPane().add(graphComponent,BorderLayout.CENTER);
			
			SucScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			
			SucContainer.setLayout(new BorderLayout());
			IeContainer.setLayout(new BorderLayout());
			
			frame.getContentPane().add(SucContainer,BorderLayout.WEST);
			frame.getContentPane().add(IeContainer,BorderLayout.EAST);
			
			JLabel lblSucLabel = new JLabel("SystemUnitClasses");
			lblSucLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			SucContainer.add(lblSucLabel, BorderLayout.NORTH);
			
			view = ((JPanel)IeScrollPane.getViewport().getView());
			
			JLabel lblIeLabel = new JLabel("Selected InternalElements");
			lblIeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			JButton closeIe = new JButton(deleteIcon);
			closeIe.addActionListener(new ActionListener() { 
				  public void actionPerformed(ActionEvent e) { 
					    System.out.println("Delete clicked ");
					    view.removeAll();
					    SwingUtilities.updateComponentTreeUI(frame);
					    ieList.clear();
					  } 
					} );
			JPanel ieHeader = new JPanel();
			ieHeader.add(lblIeLabel);
			ieHeader.add(closeIe);
			IeContainer.add(ieHeader, BorderLayout.NORTH);
			
			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);
			
			
			JMenu mnDatei = new JMenu("Datei");
			menuBar.add(mnDatei);
			
			mnDatei.add(mntmffnen);
			mntmffnen.addActionListener(this);
			
			mnDatei.add(mntmSpeichern);
			mntmSpeichern.addActionListener(this);
			
			JMenu mnSuc = new JMenu("SUC");
			menuBar.add(mnSuc);
			
			
			mnSuc.add(mntmSucLaden);
			mntmSucLaden.addActionListener(this);
			
			graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 2 && !selectedSuc.isEmpty()) {
						graph.getModel().beginUpdate();
						try
						{
					Object v1 = graph.insertVertex(parent, null, selectedSuc, e.getX(), e.getY(), 80,
							30);
						}
						finally
						{
							graph.getModel().endUpdate();
						}
					}
					else if (e.getClickCount() == 2 && selectedSuc.isEmpty()) System.out.println("Keine gewhlte SUC");
					
					if(e.getButton() == MouseEvent.BUTTON3) {
					Object cell = graphComponent.getCellAt(e.getX(), e.getY());
					cellId= cell.toString();
					if (cell != null && !ieList.contains(cellId)) 
					{System.out.println(cellId);
					fillIeContainer(cellId);
					ieList.add(cellId);
					}
					}
					
				}
			});
			
	}
			
	
	public static void addSucContainer (String title, String[] names)
	{		
		SucNameList = new JList<String>( names );
		SucNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		((JPanel)SucScrollPane.getViewport().getView()).add(new JLabel(title));
		 
		 JPanel view = ((JPanel)SucScrollPane.getViewport().getView());
		 view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
         view.add(SucNameList);
         view.validate();
         
         SucNameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				current = (JList) e.getSource();
				if (!current.isSelectionEmpty()) {
				selectedSuc = current.getSelectedValue().toString();
				System.out.println("Auswahl: "+selectedSuc);				
				}
				if (current != previous && !previous.isSelectionEmpty()) {
					
							System.out.println("cleared");
							previous.clearSelection();
						}					
				previous = current;
				
			}
         });
		
		SucContainer.add(SucScrollPane, BorderLayout.CENTER);
		
		sucLibCounter ++;
		System.out.println("Number of loaded SUC: "+sucLibCounter);
		SwingUtilities.updateComponentTreeUI(frame);
	}
	
	public static void fillIeContainer(String name) 
	{		
		((JPanel)IeScrollPane.getViewport().getView()).add(new JLabel(name));
		
		
		String [] columns = {"Attribut", "Wert"};
		String [] attributesAndValues = new String [200];
		try {
			attributesAndValues = SUCGetter.getAttributes(name);
			System.out.println(attributesAndValues);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*String [][] data = {{"x", "500"},
		{"y", "500"},
		{"Farbe", "grün"}};*/
		List<String> list = new ArrayList<String>(Arrays.asList(attributesAndValues));
		list.removeAll(Arrays.asList("", null));
		
		String [][] data = new String [list.size()/2][list.size()/2];
		for (int i = 0; i < attributesAndValues.length; i=i+2) {
			data [i/2][0]= attributesAndValues[i];
			data [i/2][1]= attributesAndValues[i+1];
		}
		
		
		IeTable = new JTable(data, columns);
		
		JButton editButton = new JButton(editIcon);
		editButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				    System.out.println("Edit clicked, name of Ie: " + name);
				    EditIeDialog ed = new EditIeDialog();
				    ed.setDefaultCloseOperation(EditIeDialog.DISPOSE_ON_CLOSE);
				    ed.setModal(true);
				    ed.setVisible(true);

				  } 
				} );
		
		 view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
		 view.add(editButton);
		 view.add(IeTable);
         view.validate();
		
		IeContainer.add(IeScrollPane, BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(frame);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(mntmffnen))
		{
			System.out.println("clicked Datei öffnen");
		}
		if (e.getSource().equals(mntmSpeichern))
		{
			System.out.println("clicked Datei speichern");
		}
		if (e.getSource().equals(mntmSucLaden))
		{
			System.out.println("clicked SUC laden");
			try {
				SUCGetter.readFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
