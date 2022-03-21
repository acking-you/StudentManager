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
import java.sql.SQLException;

public class ImportExcel implements ActionListener/*,PropertyChangeListener*/ {
    private final JFrame jf = new JFrame("导入到数据库");
    private final TextField textpath = new TextField();
    private final Font font2 = new Font("微软雅黑", 0, 20);
    private final Toolkit toolkit = Toolkit.getDefaultToolkit(); // 获得系统默认工具类
    private final Dimension sc = toolkit.getScreenSize(); // 获得屏幕尺寸
    private final JButton imbtn = new JButton("导入到数据库");
    private final JLabel fname = new JLabel("导入文件路径：");
    private final Container con = jf.getContentPane(); // 获得面板
    private final JButton open = new JButton("打开");
    public String filepath;

    public ImportExcel() {
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
        ImportExcel importExcel = new ImportExcel();
    }

    public void setVisible(boolean f) {
        jf.setVisible(f);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.imbtn) {  //TODO 开始导入操作
            filepath = textpath.getText();
            try {
                StudentDAO.importFromExcle(filepath, Student.students);
            } catch (IOException | SQLException | InterruptedException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == this.open) {  //TODO 得到选择文件的路径
            JFileChooser fileChooser = new JFileChooser();
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
                    return "Excle表格程序(*.xls)";
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
        
	

