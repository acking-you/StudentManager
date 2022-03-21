package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 使用时，由于Label需要依赖JPane或者JFrame来进行显示，
 * 所以鼠标的参照对象为JPanel或者JFrame。
 * 提供getInstance返回用JPanel包装的类
 */
//TODO 鼠标移出为蓝色，移入为粉色
public class LinkLabel extends JLabel {
    static private final Color blue = new Color(0x5EA6C1);
    static private final Color pink = new Color(0xEF3982);
    private URL url; //链接

    private LinkLabel(String vText, String vLink) {
        super(vText);
        setForeground(blue);
        try {
            if (!vLink.startsWith("http://") && !vLink.startsWith("https://"))

                vLink = "http://" + vLink;

            this.url = new URL(vLink);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override   //TODO 鼠标移出事件
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                LinkLabel.this.setForeground(blue);
            }

            @Override   //TODO 鼠标移入事件
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor

                        .getPredefinedCursor(Cursor.HAND_CURSOR));
                LinkLabel.this.setForeground(pink);
            }

            @Override   //TODO 鼠标点击事件

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    private LinkLabel(Icon icon, String vLink) {
        super(icon);
        try {
            if (!vLink.startsWith("http://") && !vLink.startsWith("https://"))

                vLink = "http://" + vLink;

            this.url = new URL(vLink);
        } catch (MalformedURLException err) {
            err.printStackTrace();
        }

        this.addMouseListener(new MouseAdapter() {
            @Override   //TODO 鼠标移出事件
            public void mouseExited(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor
                        .getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override   //TODO 鼠标移入事件
            public void mouseEntered(MouseEvent e) {
                LinkLabel.this.setCursor(Cursor

                        .getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override   //TODO 鼠标点击事件

            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException err) {
                    err.printStackTrace();
                }
            }
        });
    }

    //TODO 得到一个带链接功能的文字
    static public JPanel getInstance(String text, String link) {
        JPanel jPanel = new JPanel();
        jPanel.add(new LinkLabel(text, link));//用JPanel包装的
        return jPanel;
    }

    //TODO 得到一个带链接功能的图片
    static public LinkLabel getInstance(Icon icon, String link) {
        return new LinkLabel(icon, link);
    }

    //TODO 测试功能是否实现
    public static void main(String[] args) {
        LinkLabel linkLabel = new LinkLabel(new ImageIcon("src/images/weixin.png"), "baidu.com");
        JFrame jFrame = new JFrame();
        jFrame.setFont(new Font("楷体", Font.PLAIN, 10));

        jFrame.setLayout(null);
        jFrame.getContentPane().add(linkLabel);
        linkLabel.setBounds(0, 0, 300, 300);
        jFrame.setBounds(0, 0, 1000, 1000);
        jFrame.setVisible(true);
    }

}

