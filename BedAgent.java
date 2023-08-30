package agentmodel;

public class BedAgent extends Agent implements Runnable{

	private int type; // A bed is of type 0 (normal) 1 means
	private boolean available; // 1 is available, 0 is not available

	public BedAgent(int id,int timeStep,int xType) {
		super(id,timeStep);
		this.type=xType;
		this.available=true;
		// TODO Auto-generated constructor stub
	}

	public int getType(){
		return this.type;
	}

	public boolean getAvailable(){
		return this.available;
	}


	public void makeAvailable(){
		this.available=true;
	}

	public void makeUnavailable(){
		this.available=false;
	}
	//Concurrent Execution

	public void run(){
		act();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		System.out.println("Bed ID = "+getId()+" available at day "+this.getTime()+"\n");
	}

}
