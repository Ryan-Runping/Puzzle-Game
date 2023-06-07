package practise.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {


    // make JMenuItem member variable, so it can be reached in other method
    JMenu changeImageJMenu = new JMenu("Change Image");
    JMenuItem animalSubJMenu = new JMenuItem("Animal");
    JMenuItem sportSubJMenu = new JMenuItem("Sport");
    JMenuItem reStartJMenu = new JMenuItem("Restart Game");
    JMenuItem reLoginJMenu = new JMenuItem("ReLogin");
    JMenuItem stopGameJMenu = new JMenuItem("Stop Game");

    JMenuItem contactInfo = new JMenuItem("contact author");

    // to generate 2D random array
    int [][] arr = new int[4][4];
    int [][] win = {{1,  2,  3,  4  },
                    {5,  6,  7,  8  },
                    {9,  10, 11, 12 },
                    {13, 14, 15, 0  } };

    // indexX and indexY are to record the position of blank image block
    int indexX,indexY;

    int stepCount = 0;
    String path ="..\\jigsawPuzzle\\image\\animal\\animal3\\";

    public GameJFrame() {

        //initiate windows frame
        initJF();

        //initiate menu bar
        initJMenuBar();

        //generate 2D random array
        randomArr();

        //initiate image
        initImage();

        //make windows visible
        this.setVisible(true);
    }

    public void randomArr(){
        Random rm = new Random();
        int[] tempArr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        int index ;
        int temp;

        //generate 1D random array
        for (int i = 0; i < 16; i++) {
            temp = tempArr[i];
            index = rm.nextInt(0,16);
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }

        //count is 1D array index
        int count = 0;
        // transfer 1D random array into 2D random array
        for (int i = 0; i < 4; i++) {
            for (int j = 0 ; j < 4;j++) {
                arr[i][j] = tempArr[count];
                if (tempArr[count] == 0){
                    indexX = i;
                    indexY = j;
//                    System.out.println(""+  indexX + "," + indexY);
                }
                count++;
            }
        }

    }


    private void initImage() {

        //remove all image at first , every time call initImage ,it can be refreshed
        this.getContentPane().removeAll();

        //image that first added, show on the top level, therefor mind the consequence of add image

        if (victory()){
            JLabel jLabelWin = new JLabel(new ImageIcon("..\\jigsawPuzzle\\image\\win.png"));
            jLabelWin.setBounds(203,283,197,73);
            this.getContentPane().add(jLabelWin);
        }

        //step count
        JLabel jLabelStep = new JLabel("Step: " + stepCount);
        jLabelStep.setBounds(50,30,100,20);
        Font font = new Font("Arial", Font.BOLD,16);
        jLabelStep.setFont(font);
        this.getContentPane().add(jLabelStep);

        //place image in order
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = arr[i][j];
                ImageIcon icon = new ImageIcon(path+ number +".jpg");
                JLabel jLabelImage = new JLabel(icon);
                //set image position , add an offset to make it look better
                jLabelImage.setBounds(105 * j + 83 , 105 * i + 134, 105, 105);
                //set board for each piece of image
                jLabelImage.setBorder(new BevelBorder(BevelBorder.RAISED));
                this.getContentPane().add(jLabelImage);
            }
            }

        //add background image
        ImageIcon bgImage = new ImageIcon("..\\jigsawPuzzle\\image\\background.png");
        JLabel jLabelBg = new JLabel(bgImage);
        jLabelBg.setBounds(40,40,508,560);
        this.getContentPane().add(jLabelBg);

        this.getContentPane().repaint();

        }

    private void initJMenuBar() {
        //menu list
        JMenuBar jMenuBar = new JMenuBar();

        JMenu functionJMenu = new JMenu("Function");
        JMenu aboutJMenu = new JMenu("About");


        //add action to each item
        reStartJMenu.addActionListener(this);
        reLoginJMenu.addActionListener(this);
        stopGameJMenu.addActionListener(this);
        contactInfo.addActionListener(this);
        animalSubJMenu.addActionListener(this);
        sportSubJMenu.addActionListener(this);



        //build relation between JMenuBar JMenu and JMenuItem subJMenu
        functionJMenu.add(changeImageJMenu);
        functionJMenu.add(reStartJMenu);
        functionJMenu.add(reLoginJMenu);
        functionJMenu.add(stopGameJMenu);
        changeImageJMenu.add(animalSubJMenu);
        changeImageJMenu.add(sportSubJMenu);

        aboutJMenu.add(contactInfo);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //set JMenuBar
        setJMenuBar(jMenuBar);

    }

    private void initJF() {
        //menu default setting
        this.setSize(603,680);
        this.setTitle("JIGSAW v1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        //set windows close mode
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //cancel default  layout setting
        this.setLayout(null);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //show full size image
        if (code == 65){
            this.getContentPane().removeAll();
            JLabel jLabelAll = new JLabel(new ImageIcon(path + "all.jpg"));
            jLabelAll.setBounds(83,143,420,420);
            this.getContentPane().add(jLabelAll);
            //add background image
            ImageIcon bgImage = new ImageIcon("..\\jigsawPuzzle\\image\\background.png");
            JLabel jLabelBg = new JLabel(bgImage);
            jLabelBg.setBounds(40,40,508,560);
            this.getContentPane().add(jLabelBg);

            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


        // player can't move when game victory
        if (victory()){
            return;
        }

        int code = e.getKeyCode();

        // move blank image block
        if (code == 37){
            System.out.println("left");
            if (indexY == 3){
                return;
            }
            switchImage(indexX,indexY+1);
        } else if (code == 38) {
            System.out.println("up");
            if (indexX == 3){
                return;
            }
            switchImage(indexX+1,indexY);
        } else if (code == 39) {
            System.out.println("right");
            if (indexY == 0){
                return;
            }
            switchImage(indexX,indexY-1);
        } else if (code == 40) {
            System.out.println("down");
            if (indexX == 0){
                return;
            }
            switchImage(indexX-1,indexY);
        }


        //show full size image
        if (code == 65){
            initImage();
        }

        //cheat code : w
        if (code == 87){
            arr = new int[][] {{1,  2,  3,  4},
                               {5,  6,  7,  8},
                               {9,  10, 11, 12},
                               {13, 14, 15, 0}};
            initImage();
        }

    }

    public void switchImage(int x ,int y){
        arr[indexX][indexY] = arr [x][y];
        arr[x][y] = 0;
        indexX = x;
        indexY = y;

        //step count
        stepCount++;

        //update change
        initImage();
    }


    //Judging win or not
    public boolean victory(){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        Object obj = e.getSource();
        if (obj == reStartJMenu){
            System.out.println("restart");

            stepCount = 0;
            randomArr();
            initImage();

        } else if (obj == reLoginJMenu) {
            System.out.println("relogin");

            //close current windows
            this.setVisible(false);
            //open login windows
            new LoginJFrame();
        } else if (obj == stopGameJMenu) {
            System.out.println("stopGame");
            System.exit(0);
        } else if (obj == animalSubJMenu) {
            System.out.println("animal");
            //every time click a new option , is equal to restart with new image
            stepCount = 0;
            randomArr();
            path = "..\\jigsawPuzzle\\image\\animal\\animal" + random.nextInt(1,9) + "\\";
            initImage();
        } else if (obj == sportSubJMenu) {
            System.out.println("sport");
            stepCount = 0;
            randomArr();
            path ="..\\jigsawPuzzle\\image\\sport\\sport" + random.nextInt(1,11) + "\\";
            initImage();
        } else if (obj == contactInfo) {
            System.out.println("contact");

            JDialog jDialogInfo = new JDialog();
            JLabel jLabelInfo = new JLabel("<html>Author: Runping Zhai<br> Thanks for suggestion and opinion <br>Linkedin: <a>https://www.linkedin.com/in/runping-zhai-58a05b251/</a></html>");


            jDialogInfo.setLayout(new GridLayout(2, 2));
            jDialogInfo.getContentPane().add(jLabelInfo);
            jLabelInfo.setSize(jLabelInfo.getPreferredSize());
            jDialogInfo.setSize(400, 200);
            jDialogInfo.setAlwaysOnTop(true);
            jDialogInfo.setLocationRelativeTo(null);
            jDialogInfo.setModal(true);
            jDialogInfo.setVisible(true);
        }

    }
}
