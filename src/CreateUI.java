import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class CreateUI extends JFrame{
	static final int WIDTH=500;
	static final int HEIGHT=500;
	BufferedImage offersetImage= new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
	Rectangle rect = new Rectangle(20, 40, 5 * 50, 5 * 35);
	Snake snake;
	PlayGround playGround;
	Color colors[];
	
	 public CreateUI(PlayGround pg, int colorNums){
//		 this.snake=snake;
		 playGround=pg;
		 colors=new Color[colorNums];
		 Random rand = new Random();
		 for(int i=0;i<colorNums;i++){
			 int r=rand.nextInt(255);
			 int g=rand.nextInt(255);
			 int b=rand.nextInt(255);
			 colors[i]=new Color(r,g,b);
		 }
		 
		 
//		JFrame gui= new JFrame();
		this.setTitle("Crazy Snake");
		this.setBounds(100, 100, WIDTH+15, HEIGHT+40);
//		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//repaint();
		this.setVisible(true);		
	 }

	 public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) offersetImage.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawRect(0,0,500,500);
	
//		snake.draw(g2d);
//		snake2.draw(g2d);
//		node.draw(g2d);
		drawGround(g2d,playGround);
		g.drawImage(offersetImage, 10, 30, null);
	    
//		 g.setColor(Color.BLACK);
//	    g.drawRect(0,0,120,120);
//	    g.drawString("Test!",40,40);
	 }
	 
		public void drawGround(Graphics2D g2d, PlayGround playGround) {
			for (int i = 0; i < playGround.occupiedList.size(); i++) {
				Point p=playGround.occupiedList.get(i);
				System.out.println("drawSnake: x:"+p.getX()*5+"y:"+p.getY()*5);
				g2d.setColor(colors[p.getId()%100]);
				g2d.drawRect(p.getX()*5, p.getY()*5, 5, 5);
			}
			
		}
	 
}
