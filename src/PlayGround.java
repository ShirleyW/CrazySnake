import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayGround {
	
	static final int WIDTH=100;
	static final int HEIGHT=100;
	
	private AtomicInteger[][] ground;//0: free; 1: snake1 occupied; 2: snake2 occupied; 100: food occupied;
	private volatile Point food;
	
	public PlayGround(){
		 ground=new AtomicInteger[HEIGHT][WIDTH];
		 food= new Point(HEIGHT/2,WIDTH/2);
		 for(int i=0;i<HEIGHT;i++){
			 for(int j=0;j<WIDTH;j++){
				 ground[i][j]=new AtomicInteger(0);
			 }
		 }
	}
	public int getFoodX(){
		return food.getX();
	}
	public int getFoodY(){
		return food.getY();
	}
	public int getPoint(Point p, int update){
		if(p.getX()==food.getX()&&p.getY()==food.getY()){
			//need synchronize
			ground[p.getX()][p.getY()].compareAndSet(100, update);	
			randomFood();
			return 100;
		}
		else{
			ground[p.getX()][p.getY()].compareAndSet(0, update);
			return update;
		}
		
	}
	
	public void releasePoint(Point p, int expect){
		ground[p.getX()][p.getY()].compareAndSet(expect, 0);
	}
	
	private void randomFood(){
		Random randomX = new Random();
		Random randomY = new Random();
		while (true) {
	        int x=randomX.nextInt(HEIGHT); 
	        int y= randomY.nextInt(WIDTH);
	        
	        if(ground[x][y].compareAndSet(0, 100)){
	        	Point newFood= new Point(x,y);
	        	food=newFood;
	        	break;
	        }
		}
	}
	
	public static void main(String args[]){
		
		PlayGround playGround= new PlayGround();
		Snake snake1= new Snake(playGround,0,0,2,1);
//		Snake snake2= new Snake();
		Thread t1= new Thread(snake1);
//		Thread t2= new Thread(snake2);
		
//		CreateUI ui=new CreateUI();// how to initialize UI?
		
		t1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t1.interrupt();
		
//		t2.start();
		try {
			t1.join();
//			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
