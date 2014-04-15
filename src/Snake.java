import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Snake implements Runnable {
	public static final int UP=1;
	public static final int DOWN=2;
	public static final int LEFT=3;
	public static final int RIGHT=4;
	public static final int SLEEPTIME=20;
	
	private PlayGround playGround;
	public List<Point> body;//snake body array list, do we really need this
	int direction;
	int myId;
	
	public Snake(PlayGround playGround, int x, int y, int direction, int myId){
		this.myId=myId;
		this.direction=direction;	
		this.playGround=playGround;
		body = new ArrayList<Point>();
		body.add(new Point(x+1, y, myId));
		body.add(new Point(x, y, myId));
	}
	
	private class AiAlgorithm {
//		private int direction;
//		private PlayGround playGround;
//		private Snake snake;
//		
//		public AiAlgorithm(Snake snake, PlayGround playGround){
//			this.snake=snake;
//			this.playGround=playGround;
//			
//		}
		
		public void calculateDir(){
			if(playGround.getFoodX()<body.get(0).getX())
			{
				direction=UP;
			}
			else if(playGround.getFoodX()>body.get(0).getX())
			{
				direction=DOWN;
			}
			else if(playGround.getFoodX()==body.get(0).getX()){
				
			if(playGround.getFoodY()<body.get(0).getY())
				direction=LEFT;
			else {
				direction=RIGHT;
			}
			}
//			return direction;
		}

	}
	
	@Override
	public void run() {
		while (!Thread.interrupted()) {
			for (int i = 0; i < body.size(); i++) {
				Point p=body.get(i);
				System.out.println("body num:"+i+" x:"+p.getX()+" y:"+p.getY());
//				g2d.setColor(Color.ORANGE);
//				g2d.drawRect(p.getX()*5, p.getY()*5, 5, 5);
			}
			
//			System.out.println("x:"+body.get(0).getX()+"y:"+body.get(0).getY());
			AiAlgorithm algorithm = new AiAlgorithm();// decide which direction to go
														
			algorithm.calculateDir();
			Point p = newHead();
			int state = playGround.getPoint(p, myId);//what if false
			
			if (state == 100) {// if state==100, move head successfully and hit food!
								
				body.add(0, p); // add head but do not need to delete tail
			} else if (state == myId) {// if state== myId, move head successfully;
										
				int last = body.size() - 1;// delete tail
				playGround.releasePoint(body.get(last), myId);
				body.remove(last);
				body.add(0, p);// add new head
			}
			
			try {
				Thread.sleep(SLEEPTIME);
//				ui.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	private Point newHead(){
		int x=body.get(0).getX();
		int y=body.get(0).getY();
		switch (direction) {
		case UP:
			x=x-1;
			break;
		case DOWN:
			x=x+1;
			break;
		case LEFT:
			y=y-1;
			break;
		case RIGHT:
			y=y+1;
			break;
		}	
		System.out.println(x+y);
		return new Point(x,y,myId);
	
	}

}
