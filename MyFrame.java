import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MyFrame extends JFrame {
    public static void main(String[] args){
        JFrame frame = new MyFrame();
        frame.setTitle("海明码计算");//窗口命名
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400 , 400);
        frame.setVisible(true);//显示
        JButton bt1 = new JButton("生成海明码");
        JButton bt2 = new JButton("接收海明码");
        bt1.setBounds(40, 150, 120, 50);
        bt2.setBounds(200, 150, 120, 50);
        frame.add(bt1);//添加按钮
        frame.add(bt2);//添加按钮
        // 添加事件监听
        bt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new MyFrame();
                f.setSize(240,200);
                f.setTitle("生成海明码");
                //设置属性
                f.setBounds(400, 200, 400, 300);
                JButton bt = new JButton("偶校验生成");
                bt.setBounds(120, 150, 120, 50);
                JButton bt1 = new JButton("奇校验生成");
                bt1.setBounds(120, 200, 120, 50);
                //创建文本框
                TextField tf = new TextField(20);
                f.add(bt);
                f.add(bt1);
                f.add(tf);
                f.setVisible(true);
                bt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tf_str=tf.getText().trim();
                        HaimingCode haimingCode=new HaimingCode();
                        String myCode=haimingCode.generateCode(tf_str);
                        tf.setText(myCode);
                    }
                });
                bt1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tf_str=tf.getText().trim();
                        HaimingCode haimingCode=new HaimingCode(1);
                        String myCode=haimingCode.generateCode(tf_str);
                        tf.setText(myCode);
                    }
                });
            }
        });
        bt2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame f = new MyFrame();
                f.setSize(240,200);
                f.setTitle("检测海明码");
                //设置属性
                f.setBounds(400, 200, 400, 300);
                JButton bt = new JButton("检验(偶)");
                bt.setBounds(120, 150, 120, 50);
                JButton bt1 = new JButton("检验(奇)");
                bt1.setBounds(120, 200, 120, 50);
                TextField tf = new TextField(20);
                f.add(bt);
                f.add(bt1);
                f.add(tf);
                f.setVisible(true);
                bt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tf_str=tf.getText().trim();
                        HaimingCode haimingCode=new HaimingCode();
                        String myCode=haimingCode.decoding(tf_str);
                        if(haimingCode.isErrorCode()){
                            System.out.println("有位数出错");
                            System.out.println("出错位数为："+haimingCode.getErrorIndex());
                        }else{
                            System.out.println("无位数出错");
                        }
                        tf.setText(myCode);
                    }
                });
                bt1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String tf_str=tf.getText().trim();
                        HaimingCode haimingCode=new HaimingCode(1);
                        String myCode=haimingCode.decoding(tf_str);
                        if(haimingCode.isErrorCode()){
                            System.out.println("有位数出错");
                            System.out.println("出错位数为："+haimingCode.getErrorIndex());
                        }else{
                            System.out.println("无位数出错");
                        }
                        tf.setText(myCode);
                    }
                });
            }
        });


    }
}
