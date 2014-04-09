import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
//hhhhh

public class PlayGround {
	
	static final int width=100;
	static final int height=100;
	
	private AtomicInteger[][] ground;//0: free; 1: snake1 occupied; 2: snake2 occupied; 100: food occupied;
	private Point food;
	
	public PlayGround(){
		 ground= new AtomicInteger[height][width];
	}
	
	public void getPoint(Point p, int update){
		if(p.getX()==food.getX()&&p.getY()==food.getY()){
			ground[p.getX()][p.getY()].compareAndSet(100, update);	
			randomFood();
		}
		else{
			ground[p.getX()][p.getY()].compareAndSet(0, update);
		}
		
	}
	
	public void releasePoint(Point p, int expect){
		ground[p.getX()][p.getY()].compareAndSet(expect, 0);
	}
	
	private void randomFood(){
		
	}
	
	public static void main(String args[]){
		
		PlayGround playGround= new PlayGround();
		Snake snake1= new Snake();
		Snake snake2= new Snake();
		Thread t1= new Thread(snake1);
		Thread t2= new Thread(snake2);
		
		CreateUI ui=new CreateUI();// how to initialize UI?
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
