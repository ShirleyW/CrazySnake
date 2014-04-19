import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class CreateUI extends JFrame{
	static final int nodesSize=6;//node is a square, its width=height=nodesSize
	final int WIDTH=nodesSize*PlayGround.WIDTH;
	final int HEIGHT=nodesSize*PlayGround.HEIGHT;
	BufferedImage offersetImage= new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_3BYTE_BGR);
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
		this.setBounds(100, 30, WIDTH+15, HEIGHT+30);
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
		g2d.drawRect(0,0,WIDTH,HEIGHT);
	
//		snake.draw(g2d);
//		snake2.draw(g2d);
//		node.draw(g2d);
		drawGround(g2d,playGround);
		g.drawImage(offersetImage, 7, 25, null);
	    
//		 g.setColor(Color.BLACK);
//	    g.drawRect(0,0,120,120);
//	    g.drawString("Test!",40,40);
	 }
	 
		public void drawGround(Graphics2D g2d, PlayGround playGround) {
			for (int i = 0; i < playGround.occupiedList.size(); i++) {
				Point p=playGround.occupiedList.get(i);
//				System.out.println("drawSnake: x:"+p.getX()*5+"y:"+p.getY()*5);
				g2d.setColor(colors[p.getId()%100]);
				g2d.drawRect(p.getX()*nodesSize, p.getY()*nodesSize, nodesSize, nodesSize);
			}
			
		}
	 
}
