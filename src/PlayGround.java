import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayGround {
	
	static final int WIDTH=100;
	static final int HEIGHT=100;
	
	private AtomicInteger[][] ground;//0: free; 1: snake1 occupied; 2: snake2 occupied; 100: food occupied;
	private volatile Point food;
	
	public List<Point> occupiedList;
	
	public PlayGround(){
		 ground=new AtomicInteger[HEIGHT][WIDTH];
		 food= new Point(HEIGHT/2,WIDTH/2,100);
		 occupiedList=new ArrayList<Point>();
		 occupiedList.add(food);
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
			occupiedList.remove(0);
			occupiedList.add(p);
			randomFood();
			return 100;
		}
		else{
			ground[p.getX()][p.getY()].compareAndSet(0, update);
			occupiedList.add(p);
			return update;
		}
		
	}
	
	public void releasePoint(Point p, int expect){
		ground[p.getX()][p.getY()].compareAndSet(expect, 0);
		occupiedList.remove(p);
	}
	
	private void randomFood(){
		Random randomX = new Random();
		Random randomY = new Random();
		while (true) {
	        int x=randomX.nextInt(HEIGHT); 
	        int y= randomY.nextInt(WIDTH);
	        
	        if(ground[x][y].compareAndSet(0, 100)){
	        	Point newFood= new Point(x,y,100);
	        	food=newFood;
	        	occupiedList.add(0,food);
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
		
		CreateUI ui=new CreateUI(playGround);// how to initialize UI?
		
		t1.start();
//		ui.repaint();
		
		try {
			int i=0;
			while(i<1000){
			Thread.sleep(10);
			ui.repaint();
			i++;
			}
			
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
