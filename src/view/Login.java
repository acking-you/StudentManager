package view;

import model.dao.UserDAO;
import model.entity.User;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.MD5Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class Login extends JFrame implements ActionListener {
    public static String name_cache = null;
    public static String password_cache = null;
    final ImageIcon[] imgs = {
            new ImageIcon("src/images/1.jpg"),
            new ImageIcon("src/images/2.jpg"),
            new ImageIcon("src/images/3.jpg"),
            new ImageIcon("src/images/4.jpg"),
            new ImageIcon("src/images/5.jpg"),
    };
    MyJPanel mp;
    String username;
    String password;
    private JButton load_btn = null;
    private JButton exit_btn = null;
    private JTextField username_text = null;
    private JTextField passward_text = null;
    private MainMenu mainMenu = null;

    public Login() {
        setForeground(new Color(255, 255, 255));
        mp = new MyJPanel();
        getContentPane().add(mp);
        mp.setBounds(0, 0, 1265, 420);
        this.setResizable(false);
        this.setSize(1265, 856);
        this.setTitle("学生信息管理系统——made by L_B__");
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        this.setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }

        //=======================
        JLabel welcome_text = new JLabel("欢迎登陆学生信息管理系统");

        Font font = new Font("楷体", Font.BOLD, 40);
        welcome_text.setFont(font);
        welcome_text.setBounds(400, 440, 650, 50);
        welcome_text.setForeground(Color.DARK_GRAY);
        getContentPane().add(welcome_text);

        load_btn = new JButton();
        load_btn.setBounds(new Rectangle(515, 625, 78, 26));
        load_btn.setText("登录");
        load_btn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        getRootPane().setDefaultButton(load_btn);// 回车登录

        exit_btn = new JButton();
        exit_btn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        exit_btn.setBounds(new Rectangle(610, 626, 78, 26));
        exit_btn.setText("退出");

        username_text = new JTextField(20);
        username_text.setBounds(new Rectangle(520, 530, 200, 33));

        passward_text = new JPasswordField();
        passward_text.setBounds(new Rectangle(520, 570, 200, 33));

        Font font2 = new Font("楷体", Font.BOLD, 15);
        JLabel jLabel_password = new JLabel();
        jLabel_password.setFont(font2);
        jLabel_password.setBounds(new Rectangle(429, 573, 71, 29));
        jLabel_password.setText("密 码：");
        JLabel jLabel_userName = new JLabel();
        jLabel_userName.setBounds(new Rectangle(429, 531, 71, 29));
        jLabel_userName.setFont(font2);
        jLabel_userName.setText("用户名：");
        JLabel jLabel_User = new JLabel();
        jLabel_User.setBounds(new Rectangle(810, 147, 635, 98));


        JLabel jLabel1 = new JLabel();
        Font font1 = new Font("楷体", Font.PLAIN, 10);
        jLabel1.setFont(font1);
        jLabel1.setBounds(new Rectangle(9, 653, 671, 29));
        jLabel1.setText("版权所有： @L_B__");

        JLabel jLabel2 = new JLabel();
        jLabel2.setFont(font1);
        jLabel2.setBounds(new Rectangle(9, 673, 300, 29));
        jLabel2.setText("我的个人笔记小站：https://acking-you.gitee.io/");


        JLabel jLabel3 = new JLabel();
        jLabel3.setFont(font1);
        jLabel3.setBounds(new Rectangle(9, 703, 671, 29));
        jLabel3.setText("我的力扣主页：https://leetcode-cn.com/problemset/all/");

        JLabel jLabel4 = new JLabel();
        jLabel4.setFont(font1);
        jLabel4.setBounds(new Rectangle(9, 733, 671, 29));
        jLabel4.setText("我的GitHub主页：https://github.com/ACking-you");


        JLabel jLabel5 = new JLabel(new ImageIcon("src/images/weixin.png"));
        jLabel5.setBounds(new Rectangle(1050, 633, 124, 119));


        // 新建jPanel面板
        JPanel jContentPane = new JPanel();
        jContentPane.setLayout(null);
        jContentPane.add(jLabel_userName, null);
        jContentPane.add(jLabel_password, null);
        jContentPane.add(load_btn, null);
        jContentPane.add(exit_btn, null);
        jContentPane.add(username_text, null);
        jContentPane.add(passward_text, null);
        jContentPane.add(jLabel_User, null);
        jContentPane.add(jLabel1, null);
        jContentPane.add(jLabel2, null);
        jContentPane.add(jLabel3, null);
        jContentPane.add(jLabel4, null);
        jContentPane.add(jLabel5, null);
        getContentPane().add(jContentPane);

        username_text.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {


            }

            @Override
            public void keyReleased(KeyEvent e) {


            }

            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    passward_text.requestFocus();
                }
            }

        });

        passward_text.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    LoginCheck();
                }
            }
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        final Timer timer = new Timer(2000, (e) -> {
            mp.repaint();
        });
        timer.start();

        username_text.addActionListener(this);
        passward_text.addActionListener(this);
        load_btn.addActionListener(this);
        exit_btn.addActionListener(this);
    }

    static private void LoginFailed() {
        JOptionPane.showMessageDialog(null, "用户名和密码错误", "消息提示", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        new Login();
    }

    private void LoginSuccessful() {
        if (mainMenu != null) {
            username_text.setText("");
            passward_text.setText("");
            mainMenu.setVisible(true);
        } else {
            username_text.setText("");
            passward_text.setText("");
            mainMenu = new MainMenu(this);
        }
    }

    private void LoginCheck() {
        username = username_text.getText();
        password = MD5Util.stringToMD5(passward_text.getText());

        if (username.equals(name_cache) && password.equals(password_cache)) {
            setVisible(false);
            LoginSuccessful();
        } else {
            try (UserDAO userDao = UserDAO.getInstance(true)) {
                if (userDao.isUserExist(new User().setUserName(username).setPassword(password)) > 0) {
                    setVisible(false);
                    LoginSuccessful();
                    name_cache = username;
                    password_cache = password;
                } else {
                    LoginFailed();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == load_btn) {
            LoginCheck();
        } else if (e.getSource() == exit_btn) {
            System.exit(0);
        }
    }

    class MyJPanel extends JPanel {
        int index = 0;

        @Override
        public void paint(Graphics g) {
            if (index > 100000)
                index = 0;
            super.paint(g);
            g.drawImage(imgs[index % imgs.length].getImage(), 0, 0, this);
            index++;
        }
    }
}