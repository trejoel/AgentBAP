package agentmodel;

public class HospitalAgent extends Agent implements Runnable {

	private ExperimentRunConfiguration conf;

	public HospitalAgent(int id,int timeStep) {
		super(id,timeStep);
		conf=new ExperimentRunConfiguration(500,1);
		// TODO Auto-generated constructor stub
	}


	// Concurrent execution

	public void run(){
	   act();
	}


	@Override
	public void act() {
		// TODO Auto-generated method stub
		System.out.println("Hospital");
	}

}
