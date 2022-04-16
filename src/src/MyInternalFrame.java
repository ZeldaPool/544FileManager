package src;

import javax.swing.JInternalFrame;


/* Used by InternalFrameDemo.java. */
public class MyInternalFrame extends JInternalFrame {
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;

    public MyInternalFrame() {
        super("CECS544 File Manager",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        //...Create the GUI and put it in the window...

        //...Then set the window size or call pack...
        setSize(1024,624);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        
        
    }
}