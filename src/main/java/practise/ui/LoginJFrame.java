package practise.ui;

import User.User;
import util.VerifyCode;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LoginJFrame extends JFrame implements MouseListener {
    static ArrayList<User> userArrayList = new ArrayList<User>();

    static {
        User player1 = new User("Ryan","1234");
        User player2 = new User("Sophia","5678");
        userArrayList.add(player1);
        userArrayList.add(player2);
    }


    //next three variable use to store String that user input
    String inputName;
    String inputPassword;
    String inputCode;

    // use to store verify
    String verifyCode;


    //make them public to use .getText in other function
    JTextField  usernameInput = new JTextField();
    JPasswordField passwordInput = new JPasswordField();
    JTextField code = new JTextField();



    //make two button public , so that it can be reached by MouseListener
    ImageIcon loginImage = new ImageIcon("..\\jigsawPuzzle\\image\\login\\登录按钮.png");
    JButton loginJButton = new JButton();


    ImageIcon registerImage = new ImageIcon("..\\jigsawPuzzle\\image\\login\\注册按钮.png");
    JButton registerJButton = new JButton();

    JLabel showCode = new JLabel();




    public LoginJFrame(){

        initLoginJF();

        initLoginView();

        //make windows visible
        this.setVisible(true);

    }

    public void initLoginJF(){
        this.setSize(488,430);
        this.setTitle("LOGIN");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void initLoginView(){

        //remove all image at first , every time call initImage ,it can be refreshed
        this.getContentPane().removeAll();

        // arrange all item and add MouseListener
        ImageIcon usernameImage = new ImageIcon("..\\jigsawPuzzle\\image\\login\\用户名.png");
        JLabel usernameJLable = new JLabel(usernameImage);
        usernameJLable.setBounds(116, 135, 47, 17);
        this.getContentPane().add(usernameJLable);


        usernameInput.setBounds(195, 134, 200, 30);
        this.getContentPane().add(usernameInput);

        ImageIcon passwordImage = new ImageIcon("..\\jigsawPuzzle\\image\\login\\密码.png");
        JLabel passwordJlable = new JLabel(passwordImage);
        passwordJlable.setBounds(130, 195, 32, 16);
        this.getContentPane().add(passwordJlable);

        passwordInput.setBounds(195, 195, 200, 30);
        this.getContentPane().add(passwordInput);

        JLabel codeText = new JLabel(new ImageIcon("..\\jigsawPuzzle\\image\\login\\验证码.png"));
        codeText.setBounds(133, 256, 50, 30);
        this.getContentPane().add(codeText);


        code.setBounds(195, 256, 100, 30);
        this.getContentPane().add(code);

        verifyCode = new VerifyCode().Code();
        showCode.setText(verifyCode);
        showCode.setBounds(300, 256, 50, 30);
        showCode.addMouseListener(this);
        this.getContentPane().add(showCode);


        loginJButton.setIcon(loginImage);
        loginJButton.setBorderPainted(false);
        loginJButton.setContentAreaFilled(false);
        loginJButton.setBounds(123, 310, 128, 47);
        //add MouseListener to login button
        loginJButton.addMouseListener(this);
        this.getContentPane().add(loginJButton);

        registerJButton.setIcon(registerImage);
        registerJButton.setBorderPainted(false);
        registerJButton.setContentAreaFilled(false);
        registerJButton.setBounds(256, 310, 128, 47);
        //add MouseListener to register button
        registerJButton.addMouseListener(this);
        this.getContentPane().add(registerJButton);

        ImageIcon bgImage = new ImageIcon("..\\jigsawPuzzle\\image\\login\\background.png");
        JLabel bgJlabel = new JLabel(bgImage);
        bgJlabel.setBounds(0, 0, 470, 390);
        this.getContentPane().add(bgJlabel);

        this.getContentPane().repaint();

    }

    public void showDialog(String content){
        JDialog jDialog = new JDialog();
        jDialog.setSize(200,150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);

        JLabel warning = new JLabel(content);
        warning.setBounds(0, 0, 200, 150);
        jDialog.getContentPane().add(warning);

        jDialog.setVisible(true);

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Object obj = e.getSource();
        if (obj == loginJButton){

            System.out.println("click login");

            //get input username
            inputName = usernameInput.getText();
            //get input code
            inputCode = code.getText();
            //get input password
            inputPassword = passwordInput.getText();


            //when there is blank left
            if (inputName.isEmpty() || inputPassword.isEmpty() || inputCode.isEmpty()){
                showDialog("information is not complete yet!");
                //verify code change
                verifyCode = new VerifyCode().Code();
                initLoginView();
                return;
            }

            //when verify code can not match
            if (!inputCode.equals(verifyCode)){
//                System.out.println("inputCode = " + inputCode);
//                System.out.println("verifyCode = " + verifyCode);

                //verify code change
                verifyCode = new VerifyCode().Code();
                initLoginView();
                showDialog("verify code is wrong, input again");
                return;
            }

            //when username is not included in Arraylist
            if (getIndex(inputName)== -1){
                //verify code change
                verifyCode = new VerifyCode().Code();
                initLoginView();
                showDialog("username is not exist");
                return;
            }


            //when username can not match with password
            if (!ifMatch(inputName,inputPassword)){
                //verify code change
                verifyCode = new VerifyCode().Code();
                initLoginView();
                showDialog("password is wrong");
                return;
            }

//            System.out.println("login successfully");

            new GameJFrame();

        } else if (obj == registerJButton) {
            System.out.println("click register");
            new RegisterJFrame();
        }else if (obj == showCode ){
            System.out.println("change code");
            verifyCode = new VerifyCode().Code();
            initLoginView();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int getIndex(String name){
        for (int i = 0; i < userArrayList.size(); i++) {
            if (userArrayList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean ifMatch(String name, String password){
        if (getIndex(name) == -1){
            System.out.println("username not exist");
            return false;
        }
        else {
            if (userArrayList.get(getIndex(name)).getPassword().equals(password)) {
                return true;
            }
            else {
                return false;
            }
        }

    }
}
