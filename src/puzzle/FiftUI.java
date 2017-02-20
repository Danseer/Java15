
package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;


public class FiftUI extends JFrame {

    private JPanel workPanel=new JPanel();
    private JPanel scorePanel=new JPanel(new GridLayout(2,1,4,4));
    private JPanel gamePanel=new JPanel(new GridLayout(4,4,4,4));
    
    private JLabel name=new JLabel("Name: Konstantin");
    private JLabel step=new JLabel("Step: 0");
    
   
    
    private int matrix [][]=new int[4][4];
    private int Etalon []=new  int []  {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
     
    private int countStep=0;
    
    public FiftUI() {
        setTitle("Пятнашки");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0,0,550,700);
        setResizable(false);
        initScorePanel();
        initGamePanel();
        initWorkPanel();
        createMenu();
        Generate();
        ReDrawField();
    }
    
    
    
    //----------------------- Score Panel -------------------------------------
    public void initScorePanel(){
        Font F = new Font("TimesRoman", Font.BOLD, 30);
        
        name.setFont(F);
        name.setForeground(Color.GREEN);
        
        step.setFont(F);
        step.setForeground(Color.GREEN);
        
        scorePanel.setBackground(Color.black);
        scorePanel.add(name);
        scorePanel.add(step);
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    
    //------------------------- Game Panel ------------------------------------
    public void initGamePanel(){
        gamePanel.setBorder(BorderFactory.createCompoundBorder(
              BorderFactory.createMatteBorder(20, 0, 0, 0, new ImageIcon("images/derevo.jpg")),
              BorderFactory.createEmptyBorder(4, 3, 4, 3)));
    }
    
    //----------------------- Work Panel --------------------------------------
    public void initWorkPanel(){
        workPanel.setLayout(new BorderLayout());
        
        workPanel.setBorder(BorderFactory.createCompoundBorder(
              BorderFactory.createMatteBorder(10, 10, 10, 10, new ImageIcon("images/derevo.jpg")),
              BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        workPanel.add(scorePanel,BorderLayout.NORTH);
        workPanel.add(gamePanel);
        add(workPanel); 
    }
    //-----------------------  MENU  ---------------------------------------
    public void createMenu(){
        JMenuBar menu=new JMenuBar();
        JMenu menuFiles=new JMenu("Menu");
        menu.add(menuFiles);
        setJMenuBar(menu);
        
        //-----------Action New Game-----------------------
        JMenuItem newGame=new JMenuItem("New Game");
        menuFiles.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new FiftUI().setVisible(true);          
            }
        });
        //-------------------------------------------------
        
        
        //--------------Action EXIT------------------------
        JMenuItem exit=new JMenuItem("Exit");
        menuFiles.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 System.exit(0);     
            }
        });
        //-------------------------------------------------    
    }
    
    
    //-----------------Random Generate --------------------------
    public void Generate(){
         
        Random generator = new Random();
        int k;
        int[] RandomArr = new int[16];
         
        for(int a=0;a<16;a++) RandomArr[a]=-1;//init array arr -1
         
        do{   
            for(int b=0;b<16;b++){

                do {
                    k=generator.nextInt(16);// random numb 0-15
                }

                while(RandomArr[k]!=-1) ; 

                RandomArr[k]=b;
            }
        } 
        while(!Opportunity(RandomArr)); 
        
         
        int c=0;
        for(int i=0;i<4;i++){//fill field from rand array
            for(int j=0;j<4;j++){
                
                matrix[i][j]=RandomArr[c];
                c++;
            }
        }
    }
    
    //----------------------  Opportunity  ---------------------
    public boolean Opportunity (int Array[]){
        return true;//if possible fold
        //return false if impossible
    }
    
    
    //-------------------- Check Win -------------------------------
    public boolean CheckWin(){
       int e=0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(matrix[i][j]!=Etalon[e]) 
                {
                    return false;
                }
                else e++;
            }
        }
        return true;
    }
    
    //----------------------- Step Counter ------------------------------
    public void changeCounter(){
        countStep++;
        step.setText("Step: "+Integer.toString(countStep));
    } 
    
    //----------------------- Draw Field -----------------------------
    public void ReDrawField(){
        
        gamePanel.removeAll();
        
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                ImageIcon icon=new ImageIcon("images/derevo.jpg");
                JButton button=new JButton(Integer.toString(matrix[i][j]),icon);
                button.setDoubleBuffered(true);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                Font F = new Font("TimesRoman", Font.BOLD, 50);
                button.setFont(F);
                button.setForeground(Color.BLACK);
                
                if(matrix[i][j]==0) button.setVisible(false);
                    else button.addMouseListener(new ClickListener());

                gamePanel.add(button);
            }
        }
        if(CheckWin()) gamePanel.setBackground(Color.green);
                else gamePanel.setBackground(Color.black);

    }
    
    //---------------------- Mouse-Button Listeners --------------------------
    public class ClickListener implements MouseListener{
    
        public void mouseClicked(MouseEvent e) {
            
            JButton button = (JButton) e.getSource();
            button.setVisible(false);
            String name = button.getText();
            int num=Integer.parseInt(name);
            spin(num);
            ReDrawField();  
        }
        //-----------------------------------------------------------
        public void mouseEntered(MouseEvent e) {
            
            JButton button = (JButton) e.getSource();
            Font F = new Font("TimesRoman", Font.BOLD, 80);
            button.setFont(F);          
        }
        //-----------------------------------------------------------
        public void mouseExited(MouseEvent e) {
              
            JButton button = (JButton) e.getSource();
            Font F = new Font("TimesRoman", Font.BOLD, 50);
            button.setFont(F);
        }
        //-------------------------------------------------------------
        public void mousePressed(MouseEvent e) {}
     
        public void mouseReleased(MouseEvent e) {}     
          
    }
    
    //------------------------- SPIN ----------------------------------
    private void spin(int num){ //change button on the field
        
        int i = 0, j = 0;
        
        for (int k = 0; k < 4; k++) {
            for (int l = 0; l < 4; l++) {
                if (matrix[k][l] == num) {
                    i = k;
                    j = l;
                            
                }
            }
        }
        
        if (i > 0) {
            if (matrix[i - 1][j] == 0) {
                matrix[i - 1][j] = num;
                matrix[i][j] = 0;
                changeCounter();
            }
        }
        
        if (i < 3) {
            if (matrix[i + 1][j] == 0) {
                matrix[i + 1][j] = num;
                matrix[i][j] = 0;
                changeCounter();
            }
        }
        
        if (j > 0) {
            if (matrix[i][j - 1] == 0) {
                matrix[i][j - 1] = num;
                matrix[i][j] = 0;
               changeCounter();
            }
        }
        if (j < 3) {
            if (matrix[i][j + 1] == 0) {
                matrix[i][j + 1] = num;
                matrix[i][j] = 0;
                changeCounter();
            }
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FiftUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FiftUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FiftUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FiftUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FiftUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
