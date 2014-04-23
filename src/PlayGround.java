import java.util.ArrayList;
import java.util.Collections;
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
		 ground=new AtomicInteger[WIDTH][HEIGHT];
		 food= new Point(WIDTH/2,HEIGHT/2,100);
		 occupiedList=Collections.synchronizedList( new ArrayList<Point>());
		 occupiedList.add(food);
		 for(int i=0;i<HEIGHT;i++){
			 for(int j=0;j<WIDTH;j++){
				 ground[j][i]=new AtomicInteger(0);
			 }
		 }
		 ground[food.x][food.y].set(100);
	}
//	private class Food{
//		private Point food;
//		public synchronized boolean getFood(Point p){
//			if(p.getX()==food.getX()&&p.getY()==food.getY()){
//				
//				return true;
//			}
//			return false;
//		}
//		private void randomFood(){
//			Random randomX = new Random();
//			Random randomY = new Random();
//			while (true) {
//		       //random problem
//				int x=randomX.nextInt(WIDTH); 
//		        int y= randomY.nextInt(HEIGHT);
//		        
//		        if(ground[x][y].compareAndSet(0, 100)){
//		        	Point newFood= new Point(x,y,100);
//		        	food=newFood;
//		        	occupiedList.add(0,food);
//		        	break;
//		        }
//			}
//		}
//		
//	}
	public int getFoodX(){
		return food.getX();
	}
	public int getFoodY(){
		return food.getY();
	}
	public int getPoint(Point p, int update){
		if(p.getX()==food.getX()&&p.getY()==food.getY()){
			System.out.println("Get Food:"+" x:"+food.getX()+" y:"+food.getY());
			//need synchronize
			if(ground[p.getX()][p.getY()].compareAndSet(100, update)){
				System.out.println("Get Food Success:"+" x:"+food.getX()+" y:"+food.getY());
			occupiedList.remove(0);
			occupiedList.add(p);
			randomFood();
			return 100;
			}
		}
		else{
			if(ground[p.getX()][p.getY()].compareAndSet(0, update)){
			occupiedList.add(p);
			return update;
			}
		}
		System.out.println("Get Point Fail:"+" x:"+p.getX()+" y:"+p.getY());
		return 101;
		
	}
	
	public void releasePoint(Point p, int expect){
		ground[p.getX()][p.getY()].compareAndSet(expect, 0);
		occupiedList.remove(p);
	}
	
	private void randomFood(){
		Random randomX = new Random();
		Random randomY = new Random();
		while (true) {
	       //random problem
			int x=randomX.nextInt(WIDTH); 
	        int y= randomY.nextInt(HEIGHT);
	        
	        if(ground[x][y].compareAndSet(0, 100)){
	        	Point newFood= new Point(x,y,100);
	        	food=newFood;
	        	occupiedList.add(0,food);
	        	break;
	        }
		}
	}
	
	public static void main(String args[]){
		
		int snakeNum=50;
		Snake snakes[];
		snakes=new Snake[snakeNum];
		
		Thread t[];
		t=new Thread[snakeNum];
		PlayGround playGround= new PlayGround();
		
		Random rand = new Random();
		for(int i=0;i<snakeNum;i++){
			int x=rand.nextInt(WIDTH);
			int y=rand.nextInt(HEIGHT);
			int d=rand.nextInt(4)+1;
			snakes[i]=new Snake(playGround,x,y,d,i+1);
			t[i]= new Thread(snakes[i]);
		}
		
//		Snake snake1= new Snake(playGround,10,10,2,1);
//		Snake snake2= new Snake(playGround,90,90,1,2);
//		Thread t1= new Thread(snake1);
//		Thread t2= new Thread(snake2);
		
		CreateUI ui=new CreateUI(playGround,snakeNum+1);// how to initialize UI?
		
		for(int i=0;i<snakeNum;i++){
			t[i].start();
		}
		
//		t1.start();
//		t2.start();
//		ui.repaint();
		
		try {
//			int i=0;
//			while(i<1000){
//			Thread.sleep(10);
//			ui.repaint();
//			i++;
//			}
			
//			int i=0;
			while(playGround.occupiedList.size()-1<100*100/2+1){
			Thread.sleep(1);
			ui.repaint();
//			i++;
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		while(playGround.occupiedList.size()-1<100*100/2+1){
////			Thread.sleep(1);
//			ui.repaint();
////			i++;
//			}
		
//		t1.interrupt();
//		t2.interrupt();
//		try {
//			t1.join();
//			t2.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
		
		for(int i=0;i<snakeNum;i++){
			t[i].interrupt();
		}
		

		for(int i=0;i<snakeNum;i++){
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
