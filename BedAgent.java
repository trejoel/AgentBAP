package agentmodel;

public class BedAgent extends Agent implements Runnable{

	public BedAgent(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	//Concurrent Execution

	public void run(){
		act();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		System.out.println("Bed ID = "+getId());
	}

}
