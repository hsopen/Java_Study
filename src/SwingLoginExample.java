import dao.sqliteUtil;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SwingLoginExample {
   private static JFrame frame;
    public static void main(String[] args) {
        // 创建 JFrame 实例
         frame = new JFrame("登录");
        // Setting the width and height of frame
        frame.setSize(360, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);

        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        JLabel title = new JLabel("图书管理系统");
        title.setBounds(100,0,160,80);
        title.setFont(new Font("华文行楷",Font.BOLD,25));
        title.setForeground(new Color(102, 51, 51));
        panel.add(title);

        // 创建 JLabel
        JLabel userLabel = new JLabel("用户名：");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,100);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */
        JTextField userText = new JTextField(20);
        userText.setBounds(100,55,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(10,50,80,125);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,100,165,25);
        panel.add(passwordText);

        // 创建登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(10, 150, 80, 25);
        panel.add(loginButton);
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                MainFrame d=new MainFrame();
                String username = userText.getText();
                String password=new String (passwordText.getPassword());
                String sqlstr="select useridentity from user where username='"+username+"'and userpassword='"+password+"'";
                System.out.print(sqlstr);

                ResultSet rs = sqliteUtil.selecte(sqlstr);
                try{
                    if(rs.next()){
                        String identity=(String ) rs.getObject(1);
                        System.out.print(identity);
                        MainFrame mf =new MainFrame();
                           mf.setVisible(true);
                        frame.setVisible(false);

                    }
                    else
                    {
                        JOptionPane. showMessageDialog( null, "请输入正确的用户名和密码！");
                    }
                }catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });

        JButton singupButton = new JButton("退出");
        singupButton.setBounds(180, 150, 80, 25);
        panel.add(singupButton);
        singupButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }
}