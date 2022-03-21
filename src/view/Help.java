package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

public class Help extends JFrame {
    private JTextArea area;//文本域对象
    private JScrollPane pane;//滚动条
    private Font font;

    public Help() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle("系统帮助");//设置容器标题
        setSize(450, 450);//设置容器大小
        setLocationRelativeTo(null);//将容器显示在屏幕中央
        helper();
        area.setEditable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//点击右上角的关闭，只关闭本窗口，不影响住窗口
        setVisible(true);//设置窗口可见
        setResizable(true);//设置窗口大小可以改变
    }

    public static void main(String[] args) {
        new Help();
    }

    private void helper() {
        area = new JTextArea();
        File file = new File("./src/docs/学生管理系统.txt");
        Long file_length = file.length();
        byte[] file_content = new byte[file_length.intValue()];

        try (FileInputStream in = new FileInputStream(file)) {

            in.read(file_content);
            area.setText(new String(file_content, "GBK"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        font = new Font("微软雅黑", Font.PLAIN, 15);
        area.setFont(font);
        area.setLineWrap(true);//如果内容过长，自动换行，在文本域加上滚动条，水平和垂直滚动条始终出现。
        area.setWrapStyleWord(true);// 激活断行不断字功能
        pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        area.setForeground(Color.GRAY);
        add(pane);
    }
}
