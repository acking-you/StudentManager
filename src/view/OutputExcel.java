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
import java.io.File;
import java.io.IOException;

public class OutputExcel implements ActionListener {
    private final JFrame jf = new JFrame("从数据库导出");
    private final TextField textpath = new TextField();
    private final Font font2 = new Font("微软雅黑", 0, 20);
    private final Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获得系统默认工具类
    private final Dimension sc = toolkit.getScreenSize(); // 获得屏幕尺寸
    private final JButton imbtn = new JButton("导出到数据库");
    private final JLabel fname = new JLabel("导出文件路径：");
    private final Container con = jf.getContentPane(); // 获得面板
    private final JButton open = new JButton("打开");
    public String filepath;

    public OutputExcel() {
        if (Student.students == null) {
            ShowMessageUtil.winMessage("数据库初始化未完成！");
            System.exit(0);
        }
        jf.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }
        con.setLayout(null);
        jf.setSize(650, 400);
        jf.setLocation((sc.width - 650) / 2, (sc.height - 400) / 2);
        jf.setResizable(false);// 窗口大小不可变
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        con.setVisible(true);
        con.add(imbtn);
        con.add(open);
        con.add(textpath);
        con.add(fname);
        textpath.setBounds(200, 120, 250, 25);
        textpath.setFont(font2);
        fname.setBounds(60, 120, 150, 25);
        fname.setFont(font2);

        open.setBounds(470, 120, 100, 25);
        open.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        open.addActionListener(this);

        imbtn.setBounds(220, 300, 200, 30);
        imbtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        imbtn.addActionListener(this);
    }

    public static void main(String[] args) {
        OutputExcel outputExcel = new OutputExcel();
    }

    public void setVisible(boolean f) {
        jf.setVisible(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == imbtn) {
            if (new File(textpath.getText()).isDirectory()) {
                filepath = textpath.getText() + "\\导出表格.xls";
            } else {
                filepath = textpath.getText();
            }
            try {
                int cnt = StudentDAO.ExportExcle(Student.students, filepath);
                if (cnt > 0) ShowMessageUtil.winMessage("导出成功！");
                else {
                    System.out.println("导出失败");
                    ShowMessageUtil.winMessage("导出失败！");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.open) {  //TODO 得到选择文件的路径
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fileChooser.setApproveButtonText("确定");
            fileChooser.setDialogTitle("打开文件");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(".xls") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "文件夹或者(*.xls)";
                }
            });
            int ret = fileChooser.showOpenDialog(this.open);
            if (ret == JFileChooser.APPROVE_OPTION) {    //TODO 选择的是确定按钮
                File file = fileChooser.getSelectedFile();    //TODO 得到选择的文件
                textpath.setText(file.getPath());
            }
        }
    }
}
