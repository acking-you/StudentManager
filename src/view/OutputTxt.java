package view;

import model.dao.StudentDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ShowMessageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

class OutputTxt implements ActionListener {
    private final JTextArea area = new JTextArea(5, 5);    // 定义文本区
    private final JFrame frame = new JFrame("请选择文件");
    private final JButton open = new JButton("打开文件");
    private final JButton output = new JButton("导出");
    private final JLabel label = new JLabel("现在没有打开的文件");
    private final JPanel butPan = new JPanel();
    private String fileName = null;

    public OutputTxt() {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("数据库初始化未完成！");
            System.exit(0);
        }
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }
        open.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        output.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        this.butPan.add(open);    // 在面板中加入按钮
        this.butPan.add(output);    // 在面板中加入按钮
        this.frame.setLayout(new BorderLayout(3, 3));
        this.frame.add(this.label, BorderLayout.NORTH);
        this.frame.add(this.butPan, BorderLayout.SOUTH);
        this.frame.add(new JScrollPane(this.area), BorderLayout.CENTER);
        this.frame.setSize(600, 600);
        this.frame.setLocation(400, 100);
        frame.getContentPane().setBackground(Color.gray);
        this.frame.setVisible(true);
        this.frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        frame.setVisible(false);
                    }
                }
        );
        this.open.addActionListener(this);
        this.output.addActionListener(this);
    }

    public static void main(String[] args) {
        OutputTxt outputTxt = new OutputTxt();
    }

    public void setVisible(boolean f) {
        frame.setVisible(f);
    }

    public void actionPerformed(ActionEvent e) {
        int result = 0;    // 接收操作状态
        JFileChooser fileChooser = new JFileChooser();    // 文件选择框
        if (e.getSource() == this.open) {    // 表示执行的是打开操作
            this.area.setText("");    // 打开将文字区域的内容清空
            fileChooser.setApproveButtonText("确定");
            fileChooser.setDialogTitle("打开文件");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".txt") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "文件夹或文件(*.txt)";
                }
            });
            result = fileChooser.showOpenDialog(this.frame);
            if (result == JFileChooser.APPROVE_OPTION) {    // 选择的是确定按钮
                File file = fileChooser.getSelectedFile();    // 得到选择的文件
                this.label.setText("选择的文件名称为：" + file.getName());
                fileName = file.getPath();
            } else if (result == JFileChooser.CANCEL_OPTION) {
                this.label.setText("没有选择任何文件");
            } else {
                this.label.setText("操作出现错误");
            }
        }
        if (e.getSource() == this.output && fileName != null) {    // 判断是否是导出操作
            try {
                if (new File(fileName).isDirectory()) {
                    fileName = fileName + "\\导出txt文件.txt";
                }
                int cnt = StudentDAO.ExportTXT(fileName, Student.students);
                if (cnt > 0) {
                    JOptionPane.showMessageDialog(null, "成功导出" + cnt + "条数据到文件中", "消息提示", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "导出失败", "消息提示", JOptionPane.WARNING_MESSAGE);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
}
		
	




