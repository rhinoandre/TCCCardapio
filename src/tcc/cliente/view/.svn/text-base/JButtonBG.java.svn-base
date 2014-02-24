package tcc.cliente.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class JButtonBG extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Image img;
	private Image imgRollover;
	private String tipoProduto;
	private Font font = new Font("Kozuka Gothic Pro", 1, 15); 
	
	public JButtonBG(Image img, Image imgRollover, String tipoProduto) {
		super();
		this.img = img;
		this.imgRollover = imgRollover;
		this.tipoProduto = tipoProduto;
	    setForeground(Color.WHITE);  
	    setFont(font);
	 
		
	}
	
    public void paintComponent(Graphics g) { 
           super.paintComponent(g);  
           if(getModel().isRollover()){
        	   g.drawImage(imgRollover, 0, 0, this);
        	   FontMetrics fm   = g.getFontMetrics(font);  
               Rectangle2D tipoProdutoRect = fm.getStringBounds(tipoProduto, g);  
         
               int textWidth  = (int)(tipoProdutoRect.getWidth());
               int textHeight  = (int)(tipoProdutoRect.getHeight());  
            
               int panelWidth = this.getWidth();  
               int panelHeight = this.getHeight();
         
               // Center text horizontally and vertically  
               int x = (panelWidth  - textWidth)  / 2;
               int y = (panelHeight - textHeight) / 2 + fm.getAscent();
         
               g.drawString(tipoProduto, x, y);  // Draw the string.    
           }
		   else{
			   g.drawImage(img, 0, 0, this);  
			   font = new Font("Arial", 1, 25);
        	   FontMetrics fm   = g.getFontMetrics(font);  
               Rectangle2D tipoProdutoRect = fm.getStringBounds(tipoProduto, g);  
         
               int textWidth  = (int)(tipoProdutoRect.getWidth());
               int textHeight  = (int)(tipoProdutoRect.getHeight());  
            
               int panelWidth = this.getWidth();  
               int panelHeight = this.getHeight();
         
               // Center text horizontally and vertically  
               int x = (panelWidth  - textWidth)  / 2;
               int y = (panelHeight - textHeight) / 2 + fm.getAscent();
         
               g.drawString(tipoProduto, x, y);  // Draw the string.  
		   }
    }
}
