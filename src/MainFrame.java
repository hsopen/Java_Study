import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super();
        setTitle("图书管理系统");
        setBounds(150,300,800,800);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        panel1 p1=new panel1();
        panel2 p2=new panel2();
        panel3 p3=new panel3();
        panel4 p4=new panel4();
        tabbedPane.add("入库", p1);
        tabbedPane.add("借书", p2);
        tabbedPane.add("还书", p3);
        tabbedPane.add("查询", p4);
        getContentPane().setLayout(new CardLayout(0, 0));
        getContentPane().add(tabbedPane, "");

    }
}
