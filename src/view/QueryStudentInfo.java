package view;

import model.dao.StudentDAO;
import model.entity.Student;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import util.ShowMessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


public class QueryStudentInfo extends JFrame implements ActionListener {

    private final JScrollPane panel;
    private final JButton next;
    private final JButton previous;
    private final JButton first;
    private final JButton last;
    private final JButton query;
    private final JButton queryAll;
    private final JButton modifyBtn;
    private final JButton deleteBtn;
    private final JButton exportExcelBtn;
    private final JButton backBtn;
    private final JLabel studentId;
    private final JLabel studentName;
    private final JLabel studentSex;
    private final JLabel studentProvince;
    private final JTextField studentIdContent;
    private final JTextField studentNameContent;
    private final JTextField studentSexContent;
    private final JTextField studentProvinceContent;
    private final JLabel label1;
    private final JLabel label2;    //	1.显示总页数和当前页数 2.每页显示数
    private final JTable table;
    public int currentPage = 1;        // 当前页
    public int totalPage = 0;            // 总页数
    public int totalRowCount = 0;        // 总行数
    public int pageCount;            // 每页显示数目
    public int column = 0;
    public int restCount;            // 最后一页数目
    public Object[][] resultData;    // 结果集二维数组
    /*JTable表信息相关变量*/
    public List<Student> students = null;
    public String[] columnNames = {"学号", "姓名", "性别", "生日", "省份", "热爱的计算机语言", "电话"};
    public DefaultTableModel model = null;//默认的表格控制模型
    /*声明下拉菜单数据*/
    String[] array = {"20", "30", "40", "50", "60"};
    JComboBox box = new JComboBox(array);//将数组array放到下拉菜单中

    /*
     * 窗体及表的建立
     */
    public QueryStudentInfo() {
        super("学生信息查询统计");
//        if(Student.students==null){
//            ShowMessageUtil.winMessage("数据库初始化未完成！");
//            System.exit(0);
//        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("src/images/logo.png"));
        this.setSize(1040, 680);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");// 使用windows外观
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setResizable(false);

        Font font = new Font("楷体", Font.CENTER_BASELINE, 12);

        studentId = new JLabel("学号");
        studentId.setBounds(100, 30, 40, 30);
        studentId.setFont(font);

        studentIdContent = new JTextField();
        studentIdContent.setBounds(145, 30, 100, 30);


        studentName = new JLabel("学生姓名");
        studentName.setBounds(270, 30, 70, 30);
        studentName.setFont(font);

        studentNameContent = new JTextField();
        studentNameContent.setBounds(341, 30, 100, 30);


        //“姓名”，“性别”，“省份”
        studentProvince = new JLabel("省份");
        studentProvince.setBounds(100, 65, 40, 30);
        studentProvince.setFont(font);

        studentProvinceContent = new JTextField();
        studentProvinceContent.setBounds(145, 65, 100, 30);

        studentSex = new JLabel("学生性别");
        studentSex.setBounds(270, 65, 70, 30);
        studentSex.setFont(font);

        studentSexContent = new JTextField();
        studentSexContent.setBounds(341, 65, 100, 30);

        query = new JButton("查询");
        query.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.lightBlue));
        query.setBounds(500, 30, 95, 30);

        query.setForeground(Color.blue);
        ImageIcon icon1 = new ImageIcon("src/images/query2.png");
        query.setIcon(icon1);

        queryAll = new JButton("查询所有学生");
        queryAll.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
        queryAll.setBounds(700, 30, 150, 30);
        queryAll.setForeground(Color.blue);

        table = new JTable();
        box.setBounds(890, 105, 100, 20);
        label2 = new JLabel("每页显示条数:");
        label2.setBounds(800, 93, 120, 50);
        panel = new JScrollPane();//设置滚动条
        panel.setViewportView(table);
        panel.setBounds(42, 136, 950, 420);


        first = new JButton("第一页");
        first.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        first.setBounds(44, 570, 90, 30);
        previous = new JButton("上一页");
        previous.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        previous.setBounds(164, 570, 90, 30);
        next = new JButton("下一页");
        next.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        next.setBounds(284, 570, 90, 30);
        last = new JButton("最后一页");
        last.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        last.setBounds(404, 570, 90, 30);


        modifyBtn = new JButton("修改");
        modifyBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        modifyBtn.setBounds(524, 570, 90, 30);
        deleteBtn = new JButton("删除");
        deleteBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        deleteBtn.setBounds(644, 570, 90, 30);
        exportExcelBtn = new JButton("导出到Excel");
        exportExcelBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        exportExcelBtn.setBounds(764, 570, 120, 30);
        backBtn = new JButton("返回");
        backBtn.setUI(new BEButtonUI()
                .setNormalColor(BEButtonUI.NormalColor.green));
        backBtn.setBounds(908, 570, 90, 30);



        /*添加监听*/
        previous.addActionListener(this);
        next.addActionListener(this);


        first.addActionListener(this);
        last.addActionListener(this);
        query.addActionListener(this);
        queryAll.addActionListener(this);
        backBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });


        exportExcelBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                OutputExcel excel = new OutputExcel();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });
        QueryStudentInfo info = this;
        modifyBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {


                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "请在表格中选中一条数据", "消息提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sid = table.getValueAt(selectedRowIndex, 0).toString();
                    String name = table.getValueAt(selectedRowIndex, 1).toString();
                    String sex = table.getValueAt(selectedRowIndex, 2).toString();
                    String birthday = table.getValueAt(selectedRowIndex, 3).toString();
                    String province = table.getValueAt(selectedRowIndex, 4).toString();
                    String hobby = table.getValueAt(selectedRowIndex, 5).toString();
                    String phone = table.getValueAt(selectedRowIndex, 6).toString();

                    EditStudentInfo editStudentInfo = new EditStudentInfo(info, sid, name, sex, birthday, province, hobby, phone);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        });


        deleteBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("===========================================>用户点击了查询菜单中的‘删除’按钮");

                int selectedRowIndex = table.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "请在表格中选中一条数据", "消息提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String sid = table.getValueAt(selectedRowIndex, 0).toString();

                    StudentDAO studentDAO = StudentDAO.getInstance(true);
                    int cnt = studentDAO.deleteStudentBySid(sid);
                    if (cnt == 1) {
                        JOptionPane.showMessageDialog(null, "删除学生信息成功", "消息提示", JOptionPane.QUESTION_MESSAGE);
                        showAllStudent();
                    } else {
                        JOptionPane.showMessageDialog(null, "删除学生信息失败", "消息提示", JOptionPane.WARNING_MESSAGE);
                    }
                    studentDAO.close();
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
        });

        label1 = new JLabel();
        label1.setBounds(420, 400, 180, 60);

        this.getContentPane().setLayout(null);

        this.getContentPane().add(box);//获取内容面板，再对其加入组件
        this.getContentPane().add(label2);
        this.getContentPane().add(panel);
        this.getContentPane().add(previous);
        this.getContentPane().add(next);
        this.getContentPane().add(first);
        this.getContentPane().add(last);
        this.getContentPane().add(label1);


        this.getContentPane().add(studentId);
        this.getContentPane().add(studentIdContent);
        this.getContentPane().add(studentName);
        this.getContentPane().add(studentNameContent);
        this.getContentPane().add(studentSex);
        this.getContentPane().add(studentSexContent);
        this.getContentPane().add(studentProvince);
        this.getContentPane().add(studentProvinceContent);

        this.getContentPane().add(modifyBtn);
        this.getContentPane().add(deleteBtn);
        this.getContentPane().add(exportExcelBtn);
        this.getContentPane().add(backBtn);

        this.getContentPane().add(query);
        this.getContentPane().add(queryAll);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        students = new ArrayList<>();       //TODO 手动实现深拷贝
        for (Student student : Student.students) {
            students.add(student);
        }

        /**
         * 事件监听
         */
        /*下拉菜单事件监听*/
        box.addActionListener(e -> {
            String Str = (String) box.getSelectedItem();
            pageCount = Integer.parseInt(Str);
            initTable();
            System.out.println(pageCount);

        });

        initTable();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        QueryStudentInfo q = new QueryStudentInfo();
    }

    /**
     * 获取下一页
     */
    public int getNextPage() {
        if (this.currentPage != this.totalPage) {
            return ++currentPage;
        }
        return -1;
    }

    /**
     * 获取上一页
     */
    public int getPreviousPage() {
        if (this.currentPage != 1) {
            return --currentPage;
        }
        return -1;
    }

    /**
     * 获取最后一页
     */
    public int getLastPage() {
        currentPage = totalPage;
        return currentPage;
    }

    /**
     * 获取第一页
     */
    public int getFirstPage() {
        currentPage = 1;
        return currentPage;
    }

    /**
     * 获取总页数
     */
    public int getTotolPage() {
        return this.totalPage;
    }

    /**
     * 获取当前页
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * 获得原始数据集
     *
     * @param students
     * @return String sId, String sName, String sSex, String sBirthday,
     * String sProvince, String sHobby, String sPhone
     */
    public Object[][] getData(List<Student> students) {
        if (students.size() > 0) {
            Object[][] data = new Object[students.size()][4];
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                Object[] a = {s.getSid(), s.getName(), s.getSex(), s.getBirthday(), s.getProvince(), s.getHobby(), s.getPhone()};//把List**的数据赋给Object数组
                data[i] = a;//把数组的值赋给二维数组的一行
            }
            return data;
        }
        return null;
    }

    /**
     * 初始化结果集
     *
     * @param data
     */
    public void initResultData(Object[][] data) {
        if (data != null) {
            String Str = (String) box.getSelectedItem();
            pageCount = Integer.parseInt(Str);
            resultData = data;//总的结果集
            column = data[0].length;//表的列数
            totalRowCount = data.length;//表的长度
            totalPage = totalRowCount % pageCount == 0 ? totalRowCount / pageCount : totalRowCount / pageCount + 1;//结果集的总页数
            restCount = totalRowCount % pageCount == 0 ? pageCount : totalRowCount % pageCount;//最后一页的数据数
            label1.setText("总共" + totalRowCount + "记录|当前第" + currentPage + "页");
        } else {
            restCount = 0;
        }
    }

    /**
     * 获取分页数据
     *
     * @return
     */
    public Object[][] getPageData() {
        Object[][] currentPageData = new Object[pageCount][column];//构造每页数据集
        if (this.getCurrentPage() < this.totalPage) {//如果当前页数小于总页数，那么每页数目应该是规定的数pageCount
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage()); i++) {
                for (int j = 0; j < column; j++) {
                    //TODO 把结果集中对应每页的每一行数据全部赋值给当前页的每一行的每一列
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        } else {
            //TODO 在动态改变数据结果集的时候，如果当前页没有数据了，则回到前一页（一般针对最后一页而言）
            if (pageCount * (this.getCurrentPage() - 1) >= totalRowCount) this.currentPage--;
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {
                for (int j = 0; j < column; j++) {
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        }
        return currentPageData;
    }

    public void showAllStudent() {
        students.clear();
        for (Student student : Student.students) {
            students.add(student);
        }
        initTable();
    }

    /**
     * 初始化表格数据
     */
    public void initTable() {
        Object[][] data = getData(students);
        if (data != null) {
            initResultData(data);
            model = new DefaultTableModel(getPageData(), columnNames);
        } else {
            //如果结果集中没有数据，那么就用空来代替数据集中的每一行
            initResultData(data);
            Object[][] nothing = {{}, {}, {}, {}, {}};
            model = new DefaultTableModel(nothing, columnNames);
            totalRowCount = 0;
        }
        table.setModel(model);
        table.setRowHeight(20);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
    }

    /**
     * 按钮事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] data = new String[4];
        JButton button = (JButton) e.getSource();
        if (button.equals(first)) {
            int i = getFirstPage();
            if (i == -1) return;
        }
        if (button.equals(previous)) {
            int i = getPreviousPage();
            if (i == -1) return;
        }
        if (button.equals(next)) {
            int i = getNextPage();
            if (i == -1) return;
        }
        if (button.equals(last)) {
            int i = getLastPage();
            if (i == -1) return;
        }

        if (button.equals(modifyBtn)) {

        }
        if (button.equals(deleteBtn)) {

        }
        if (button.equals(exportExcelBtn)) {

        }
        if (button.equals(backBtn)) {
            setVisible(false);
        }

        if (button.equals(query)) {
            data[0] = studentIdContent.getText();
            data[1] = studentNameContent.getText();
            data[2] = studentProvinceContent.getText();
            data[3] = studentSexContent.getText();

            if (data[0].isEmpty() && data[1].isEmpty() && data[2].isEmpty() && data[3].isEmpty()) {
                ShowMessageUtil.winMessage("筛选条件不能为空！");
                return;
            }
            students.clear();
            for (Student student : Student.students) {  //TODO 筛选符合条件的Student更新students
                if (!data[0].isEmpty() && !student.getSid().equals(data[0])) {
                    continue;
                }
                if (!data[1].isEmpty() && !student.getName().equals(data[1])) {
                    continue;
                }
                if (!data[2].isEmpty() && !student.getProvince().equals(data[2])) {
                    continue;
                }
                if (!data[3].isEmpty() && !student.getSex().equals(data[3])) {
                    continue;
                }
                students.add(student);
            }
            initTable();
        }
        if (button.equals(queryAll)) {
            showAllStudent();
        }
        Object[][] currentPageData = new Object[pageCount][column];//构造每页数据集

        if (this.getCurrentPage() == 1) {
            for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {

                for (int j = 0; j < column; j++) {
                    currentPageData[i % pageCount][j] = resultData[i][j];
                }
            }
        } else {
            if (this.getCurrentPage() < this.totalPage) {//如果当前页数小于总页数，那么每页数目应该是规定的数pageCount
                for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + pageCount; i++) {
                    for (int j = 0; j < column; j++) {
                        //把结果集中对应每页的每一行数据全部赋值给当前页的每一行的每一列
                        currentPageData[i % pageCount][j] = resultData[i][j];
                    }
                }
            } else {
                //在动态改变数据结果集的时候，如果当前页没有数据了，则回到前一页（一般针对最后一页而言）
                System.out.println(this.getCurrentPage());
                if (pageCount * (this.getCurrentPage() - 1) >= totalRowCount) this.currentPage--;
                for (int i = pageCount * (this.getCurrentPage() - 1); i < pageCount * (this.getCurrentPage() - 1) + restCount; i++) {
					/*if(i==-20){
						System.out.println("i=-20 ");
						i=0;

					}*/
                    for (int j = 0; j < column; j++) {
                        currentPageData[i % pageCount][j] = resultData[i][j];
                    }
                }
            }
        }


        DefaultTableModel model = new DefaultTableModel(currentPageData, columnNames);
        table.setModel(model);
        label1.setText("总共" + totalRowCount + "记录|当前第" + currentPage + "页");
    }
}  

