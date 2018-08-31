package cn.more;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;


public class FileChoose extends JFrame implements AttributeSet  {

    //final JTextArea ta1 = new JTextArea();
    //final JTextArea ta2 = new JTextArea();
    //JTextArea ta3 = new JTextArea();

    JTextPane tp1 = new JTextPane();
    JTextPane tp2 = new JTextPane();
    JTextPane tp3 = new JTextPane();

    String[] lineTxtL = new String[0];
    String[] lineTxtR = new String[0];
    String tempL = null;
    String tempR = null;
    //int indexL = 0;
    //int indexR = 0;

    public FileChoose(){
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.cyan);

        JButton b1 = new JButton("Choose1");
        JButton b2 = new JButton("Choose2");
        JButton bSaveL = new JButton("SaveL");
        JButton bSaveR = new JButton("SaveR");
        JButton b3 = new JButton("Compare");

        JScrollPane spL = new JScrollPane(tp1);
        JScrollPane spR = new JScrollPane(tp2);
        JScrollPane spB = new JScrollPane(tp3);

        b1.setBounds(200,10,150,50);
        b2.setBounds(750,10,150,50);
        bSaveL.setBounds(200,430,150,50);
        bSaveR.setBounds(750,430,150,50);
        b3.setBounds(475,430,150,50);
        spL.setBounds(20,70,500,350);
        spR.setBounds(570,70,500,350);
        spB.setBounds(20,490,1050,165);

        c.add(b1);
        c.add(b2);
        c.add(bSaveL);
        c.add(bSaveR);
        c.add(b3);
        c.add(spL);
        c.add(spR);
        c.add(spB);

        //按钮事件
        //保存文本信息
        bSaveL.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                /*//文件保存路径
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogType(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("选择文件保存路径");
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.showSaveDialog(fileChooser);
                if(fileChooser.getSelectedFile()==null){
                    return null;
                }
                return fileChooser.getSelectedFile().getPath();*/

                //保存写入的文件
                File file = new File("D:/JavaSource/t1.txt");
                try{
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bufw = new BufferedWriter(fw);
                    bufw.write(tp1.getText());
                    //bufw.newLine();//以单行的形式写入文件中
                    JOptionPane.showMessageDialog(null,"保存成功！");
                    bufw.close();
                    fw.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        bSaveR.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                File file = new File("D:/JavaSource/t2.txt");
                try{
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bufw = new BufferedWriter(fw);
                    bufw.write(tp2.getText());
                    //bufw.newLine();//以单行的形式写入文件中
                    JOptionPane.showMessageDialog(null,"保存成功！");
                    bufw.close();
                    fw.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        //获取文件内容
        b1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                //文件选择对话框
                JFileChooser fileChooser = new JFileChooser();//创建文件选择对话框
                int i = fileChooser.showOpenDialog(getContentPane());//显示文件选择对话框
                //设置只选择文件路径
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                String filePath = fileChooser.getSelectedFile().getPath();
                //System.out.println(filePath);
                //设置可选文件类型
                //FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
                //fileChooser.setFileFilter(filter);
                //判断是否单机了点击按钮
                if(i==JFileChooser.APPROVE_OPTION){
                    //获得选中的文件对象
                    File selectedFile = fileChooser.getSelectedFile();
                }

                //获取文件信息
                try {
                    String encoding="GBK";
                    File file=new File(filePath);
                    if(file.isFile() && file.exists()){ //判断文件是否存在
                        InputStreamReader read = new InputStreamReader(
                                new FileInputStream(file),encoding);//考虑到编码格式
                        BufferedReader bufferedReader = new BufferedReader(read);
                        lineTxtL = new String[0];
                        //int line = 1;
                        while((tempL = bufferedReader.readLine()) != null){
                            //System.out.println(lineTxt);
                            //tempL[indexL] = lineTxtL +"\n";
                            //indexL++;
                            lineTxtL = insert(lineTxtL,tempL.trim());
                            //lineTxtL[indexL] = lineTxtL[indexL]+lineTxtL[indexL+1];
                            //line++;
                            //将字符串数组转换成字符串
                            StringBuffer sb = new StringBuffer();
                            for(int j = 0; j < lineTxtL.length; j++){
                                sb.append(lineTxtL[j]);
                            }
                            String newLineTxtL = sb.toString();
                            //将最后的字符串添加到TextPane面板上
                            tp1.setText(newLineTxtL);
                        }
                        read.close();
                    }else{
                        //System.out.println("找不到指定的文件");
                        JOptionPane.showMessageDialog(null, "找不到指定文件");
                    }
                } catch (Exception e1) {
                    //System.out.println("读取文件内容出错");
                    JOptionPane.showMessageDialog(null, "读取文件内容失败");
                    e1.printStackTrace();
                }
            }

            //将字符串添加到字符串数组的方法
            private String[] insert(String[] arr, String str) {
                // TODO Auto-generated method stub
                int size = arr.length;

                String[] tmp = new String[size + 1];

                System.arraycopy(arr, 0, tmp, 0, size);

                tmp[size] = str;

                return tmp;
            }
        });
        b2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JFileChooser fileChooser = new JFileChooser();
                int i = fileChooser.showOpenDialog(getContentPane());
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                String filePath = fileChooser.getSelectedFile().getPath();
                if(i==JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                }

                try{
                    File file = new File(filePath);
                    if(file.isFile() && file.exists()){
                        InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                        BufferedReader bufferedReader = new BufferedReader(read);
                        lineTxtR = new String[0];
                        while((tempR = bufferedReader.readLine()) != null){
                            lineTxtR = insert(lineTxtR,tempR.trim());
                            StringBuffer sb = new StringBuffer();
                            for(int j = 0; j < lineTxtR.length; j++){
                                sb.append(lineTxtR[j]);
                            }
                            String newLineTxtR = sb.toString();
                            tp2.setText(newLineTxtR);
                        }
                        read.close();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "找不到指定文件");
                    }
                }catch(Exception e1){
                    JOptionPane.showMessageDialog(null,"读取文件内容失败");
                    e1.printStackTrace();
                }

            }

            private String[] insert(String[] arr, String str) {
                // TODO Auto-generated method stub
                int size = arr.length;

                String[] tmp = new String[size + 1];

                System.arraycopy(arr, 0, tmp, 0, size);

                tmp[size] = str;

                return tmp;
            }
        });
        //比较文件内容
        b3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                char[] strL;
                char[] strR;
                String sL = "";
                String sR = "";
                //获取TextPane文本面板的文本信息
                String s1 = tp1.getText().toString();
                String s2 = tp2.getText().toString();

                int i,j,wrongWordIndex,wrongLineIndex = 0;
                int count = 0;
                int countEnd = 0;//获得最后的索引值，在添加颜色的时候调用
                int midCount = 0;
                String resultText = "";
                //判断左右两边行数的多少，选择行数少的作为参数
                if(lineTxtL.length<=lineTxtR.length){
                    for(i=0;i<lineTxtL.length;i++){//对文件以行为单位进行遍历
                        //boolean a = true;
                        strL = lineTxtL[i].toCharArray();
                        strR = lineTxtR[i].toCharArray();
                        boolean isSame = true;
                        //判断左右两边字符串的长度，取长度值小的作为参数
                        if(strL.length<=strR.length){
                            for(j=0;j<strL.length;j++){//对每一行的文本以单个字符为单位进行遍历
                                if(!(strL[j]==strR[j])){
                                    if(isSame){
                                        isSame = false;
                                        count = j+1;
                                    }
                                    wrongWordIndex = j+1;//将索引值从1开始
                                    wrongLineIndex = i+1;//将索引值从1开始
                                    //清空上一次的合并的字符串，避免下一次的合成的字符串重复上一次的
                                    sL = "";
                                    sR = "";
                                    //resultText += "第"+wrongLineIndex+"行：";/会重复错误字符出现次数次/
                                }else{
                                    if(!isSame){
                                        countEnd = j;
                                        for(int k=count-1;k<j;k++){//将字符数组中的字符合并成字符串
                                            sL += strL[k];
                                            sR += strR[k];
                                        }
                                        if(count == j){//如果是单个的字符不同
                                            resultText += "第"+wrongLineIndex+"行：第"+count+"个字符不同，"
                                                    +"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }else{//如果是连续的字符不同
                                            resultText += "第"+wrongLineIndex+"行：从第"+count+"到第"+j
                                                    +"个字符不同，"+"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }
                                        //System.out.println(resultText);
                                        isSame = true;
                                    }
                                }
                            }
                        }else{
                            for(j=0;j<strR.length;j++){//对每一行的文本以单个字符为单位进行遍历
                                if(!(strL[j]==strR[j])){
                                    if(isSame){
                                        isSame = false;
                                        count = j+1;
                                    }
                                    wrongWordIndex = j+1;//将索引值从1开始
                                    wrongLineIndex = i+1;//将索引值从1开始
                                    //清空上一次的合并的字符串，避免下一次的合成的字符串重复上一次的
                                    sL = "";
                                    sR = "";
                                    //resultText += "第"+wrongLineIndex+"行：";/会重复错误字符出现次数次/
                                }else{
                                    if(!isSame){
                                        countEnd = j;
                                        for(int k=count-1;k<j;k++){//将字符数组中的字符合并成字符串
                                            sL += strL[k];
                                            sR += strR[k];
                                        }
                                        if(count == j){//如果是单个的字符不同
                                            resultText += "第"+wrongLineIndex+"行：第"+count+"个字符不同，"
                                                    +"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }else{//如果是连续的字符不同
                                            resultText += "第"+wrongLineIndex+"行：从第"+count+"到第"+j
                                                    +"个字符不同，"+"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }
                                        //System.out.println(resultText);
                                        isSame = true;
                                    }
                                }
                            }
                        }
                        //System.out.println(midCount+" "+count+" "+countEnd);
                        resultText += "\n";
                        //每行结束之后清空这一行的信息，避免下一行输出的内容重复上一行的信息
                        sL = "";
                        sR = "";
                    }
                }else{
                    for(i=0;i<lineTxtR.length;i++){//对文件以行为单位进行遍历
                        //boolean a = true;
                        strL = lineTxtL[i].toCharArray();
                        strR = lineTxtR[i].toCharArray();
                        boolean isSame = true;
                        //判断左右两边字符串的长度，取长度值小的作为参数
                        if(strL.length<=strR.length){
                            if(i>0){//使用while会出错//////////////////////
                                //因为添加颜色时的索引是不循环的，索引添加一个中间量来递增每一行的字符数
                                midCount += strL.length;
                            }
                            for(j=0;j<strL.length;j++){//对每一行的文本以单个字符为单位进行遍历
                                if(!(strL[j]==strR[j])){
                                    if(isSame){
                                        isSame = false;
                                        count = j+1;
                                    }
                                    wrongWordIndex = j+1;//将索引值从1开始
                                    wrongLineIndex = i+1;//将索引值从1开始
                                    //清空上一次的合并的字符串，避免下一次的合成的字符串重复上一次的
                                    sL = "";
                                    sR = "";
                                    //resultText += "第"+wrongLineIndex+"行：";/会重复错误字符出现次数次/
                                }else{
                                    if(!isSame){
                                        countEnd = j;
                                        for(int k=count-1;k<j;k++){//将字符数组中的字符合并成字符串
                                            sL += strL[k];
                                            sR += strR[k];
                                        }
                                        if(count == j){//如果是单个的字符不同
                                            resultText += "第"+wrongLineIndex+"行：第"+count+"个字符不同，"
                                                    +"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }else{//如果是连续的字符不同
                                            resultText += "第"+wrongLineIndex+"行：从第"+count+"到第"+j
                                                    +"个字符不同，"+"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }
                                        //System.out.println(resultText);
                                        isSame = true;
                                    }
                                }
                            }
                        }else{
                            if(i>0){//使用while会出错//////////////////////
                                //因为添加颜色时的索引是不循环的，索引添加一个中间量来递增每一行的字符数
                                midCount += strR.length;
                            }
                            for(j=0;j<strR.length;j++){//对每一行的文本以单个字符为单位进行遍历
                                if(!(strL[j]==strR[j])){
                                    if(isSame){
                                        isSame = false;
                                        count = j+1;
                                    }
                                    wrongWordIndex = j+1;//将索引值从1开始
                                    wrongLineIndex = i+1;//将索引值从1开始
                                    //清空上一次的合并的字符串，避免下一次的合成的字符串重复上一次的
                                    sL = "";
                                    sR = "";
                                    //resultText += "第"+wrongLineIndex+"行：";/会重复错误字符出现次数次/
                                }else{
                                    if(!isSame){
                                        countEnd = j;
                                        for(int k=count-1;k<j;k++){//将字符数组中的字符合并成字符串
                                            sL += strL[k];
                                            sR += strR[k];
                                        }
                                        if(count == j){//如果是单个的字符不同
                                            resultText += "第"+wrongLineIndex+"行：第"+count+"个字符不同，"
                                                    +"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }else{//如果是连续的字符不同
                                            resultText += "第"+wrongLineIndex+"行：从第"+count+"到第"+j
                                                    +"个字符不同，"+"不同的字符是：左边的：“"+sL+"”和右边的：“"+sR+"”\n";
                                        }
                                        //System.out.println(resultText);
                                        isSame = true;
                                    }
                                }
                            }
                        }
                        //System.out.println(midCount+" "+count+" "+countEnd);
                        resultText += "\n";
                        //每行结束之后清空这一行的信息，避免下一行输出的内容重复上一行的信息
                        sL = "";
                        sR = "";
                    }
                }

                tp3.setText(resultText);
            }
        });
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        init(new FileChoose(),1100,700);
    }

    private static void init(FileChoose frame, int width, int height) {
        // TODO Auto-generated method stub
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setTitle("文件选择和比较");
        frame.setSize(width,height);
        frame.setVisible(true);
    }

    @Override
    public boolean containsAttribute(Object arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAttributes(AttributeSet arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AttributeSet copyAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getAttributeCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Enumeration<?> getAttributeNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AttributeSet getResolveParent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDefined(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEqual(AttributeSet arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}