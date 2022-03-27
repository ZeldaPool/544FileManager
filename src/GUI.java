import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    private static Point point = new Point();
    public GUI(){
        startGUI();
    }

    public static void main(String[] args) {
        new GUI();
    }

    public void startGUI(){
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("CECS544 File Manager");
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.pack();
        menuBar(frame);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menuBar(JFrame frame){


        JMenuBar menuBar = new JMenuBar();

        //file
        JMenu menu = new JMenu("File");
        menuBar.add(menu);

        JMenuItem renameFile = new JMenuItem("Rename");
        JMenuItem copyFile = new JMenuItem("Copy");
        JMenuItem deleteFile = new JMenuItem("Delete");
        JMenuItem runFile = new JMenuItem("Run");
        JMenuItem exitFile = new JMenuItem("Exit");


        menu.add(renameFile);
        menu.add(copyFile);
        menu.add(deleteFile);
        menu.add(runFile);
        menu.add(exitFile);

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
        JMenuItem cascadeWindow = new JMenuItem("Cascade");

        menu.add(newWindow);
        menu.add(cascadeWindow);

        //help
        menu = new JMenu("Help");
        menuBar.add(menu);

        JMenuItem help = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");

        menu.add(help);
        menu.add(about);

        frame.setJMenuBar(menuBar);


    }

}
