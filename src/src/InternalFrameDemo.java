package src;

import src.MyInternalFrame;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
/*
 * InternalFrameDemo.java requires:
 *   MyInternalFrame.java
 */
public class InternalFrameDemo extends JFrame
        implements ActionListener {
	
    JDesktopPane desktop;
    
    JTree tree;
	JTextField textField;
	JScrollPane tableScrollPane;
	JTable table;
	String[] detailMeta = {"Name", "LastModified", "Size"};
	String[][] detailData = {{"", "", ""}};
	
    public InternalFrameDemo() {
        super("CECS544 File Manager");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = new Dimension(1280, 720);
        setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        createFrame(); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, InputEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, InputEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
//        menuItem.addActionListener(this);
//        menu.add(menuItem);
        //file
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem renameFile = new JMenuItem("Rename");
        JMenuItem copyFile = new JMenuItem("Copy");
        JMenuItem deleteFile = new JMenuItem("Delete");
        JMenuItem runFile = new JMenuItem("Run");
        ///////////////////
        JMenuItem exitFile = new JMenuItem("Exit");
        exitFile.setMnemonic(KeyEvent.VK_Q);
        exitFile.setActionCommand("quit");
        exitFile.addActionListener(this);
        ///////////////////

        menu.add(renameFile);
        menu.add(copyFile);
        menu.add(deleteFile);
        menu.add(runFile);
        menu.add(exitFile);
        ////////////////////////////////////
        //tree
        menu = new JMenu("Tree");
        menuBar.add(menu);

        JMenuItem expandBranch = new JMenuItem("Expand Branch");
        JMenuItem collapseBranch = new JMenuItem("Collapse Branch");

        menu.add(expandBranch);
        menu.add(collapseBranch);

        //window
        menu = new JMenu("New");
        menuBar.add(menu);


        JMenuItem newWindow = new JMenuItem("New");
        newWindow.setMnemonic(KeyEvent.VK_N);
        newWindow.setActionCommand("new");
        newWindow.addActionListener(this);
        menu.add(newWindow);

        JMenuItem cascadeWindow = new JMenuItem("Cascade");
        menu.add(cascadeWindow);

        //help
        menu = new JMenu("Help");
        menuBar.add(menu);

        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");

        menu.add(help);
        menu.add(about);
        printDrives();
        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else { //quit
            quit();
        }
    }

    protected void printDrives(){

        File[] rootDrive = File.listRoots();

        for (File sysDrive : rootDrive){
            System.out.println("Drive : " + sysDrive);
        }
    }
    //Create a new internal frame.
    protected void createFrame() {
    	
    	MyInternalFrame frame = new MyInternalFrame();
    	
    	DefaultMutableTreeNode rootDirectory;
    	
    	JScrollPane explorerPane;
    	
    	textField = new JTextField();
        
    	// Left explorer pane and its data
		DefaultMutableTreeNode diskLabel ;
		File[] roots = File.listRoots();
//		for(int i = 0; i < roots.length; i++) {
//			diskLabel = explorerTreeGenerate(new File(roots[i] + ""));
//			rootDirectory.add(diskLabel);
//		}
		rootDirectory = explorerTreeGenerate(new File("File Explorer"));
		
		diskLabel = explorerTreeGenerate(new File(roots[2] + ""));
		rootDirectory.add(diskLabel);
	
		tree = new JTree(rootDirectory);
		explorerPane = new JScrollPane(tree);

		table = new JTable(detailData, detailMeta);
		tableScrollPane = new JScrollPane(table);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                 new JScrollPane(explorerPane), new JScrollPane(tableScrollPane));

        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(250);

        frame.add(splitPane);
        frame.setVisible(true); //necessary as of 1.3
        desktop.add(frame);
        try {
            frame.setSelected(true);

        } catch (java.beans.PropertyVetoException e) {}
        
        tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				doMouseClicked(e, frame, splitPane, explorerPane);
			}
		});
        
        
        
    }
    
    DefaultMutableTreeNode explorerTreeGenerate(File temp) {
		DefaultMutableTreeNode rootName = new DefaultMutableTreeNode(temp);
		
		if(! (temp.exists() && temp.isDirectory()))         	
			return rootName;
		
		String fileName = temp.getPath();
		
		fillTree(rootName, fileName);

		return rootName;
	}
    
    // Recursive function for filling the tree with our files and folders
    void fillTree(DefaultMutableTreeNode root, String fileName) {
		File file = new File(fileName);
		
		File[] listFiles = file.listFiles();
		
		if(listFiles != null) {
			for(int i = 0; i < listFiles.length; i++) {
				final DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(listFiles[i].getName());
				
				root.add(tempNode);
				
				final String newFileNmae = new String(fileName + "\\" + listFiles[i].getName());
				
				Thread filler = new Thread() {
					public void run() {
						fillTree(tempNode, newFileNmae);
					}
				};
				filler.start();
			}
		}
	}
    
    void doMouseClicked(MouseEvent e, MyInternalFrame frame, JSplitPane splitPane, JScrollPane explorerPane) {
		TreePath treePath = tree.getPathForLocation(e.getX(), e.getY());

		if(treePath == null)
			return;
		
		String s = treePath.toString();
		if(s.charAt(s.length() - 1) == ']') {
			s = s.substring(0, s.length() - 1);
		}
				
		s = s.replaceFirst("[\\[\\]]", "");
		s = s.replace(", ", "\\");
		
		textField.setText(s);
		s = s.substring(13);									

		showFiles(s, frame, splitPane, explorerPane);
	}
    
    void showFiles(String fileName, MyInternalFrame frame, JSplitPane splitPane, JScrollPane explorerPane) {
		File tempFile = new File(fileName);
		File[] fileList = tempFile.listFiles();
		int fileCounter = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		if(fileList != null) {
			detailData = new String[fileList.length][5];
			
			for(int i = 0; i < fileList.length; i++) {
				detailData[fileCounter][0] = new String(fileList[i].getName());
				detailData[fileCounter][1] = new String(sdf.format(fileList[i].lastModified()) + "");
				detailData[fileCounter][2] = new String(fileList[i].length() + "");
				fileCounter++;
			}
		}
		splitPane.remove(table);
		splitPane.remove(tableScrollPane);
		
		table = new JTable(detailData, detailMeta);
		tableScrollPane = new JScrollPane(table);
		
		table.setShowGrid(false);
		
		splitPane.setRightComponent(tableScrollPane);
		splitPane.setVisible(true);

	}

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        InternalFrameDemo frame = new InternalFrameDemo();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}