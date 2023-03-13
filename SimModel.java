package agentmodel;

import java.util.ArrayList;
import java.util.Random;


public class SimModel {
	private Scheduler scheduler;
	private Environment environment;
	private Random rand;

	SimModel() {
		// TODO Auto-generated constructor stub
		scheduler = new Scheduler();
		environment = new Environment();
		rand=new Random(System.currentTimeMillis());
	}
	
	public SimModel getInstance() {
		return this;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimModel model=new SimModel();
		Agent hospital =  new HospitalAgent(100);
		model.scheduler.addFirst(hospital);
		Agent patient = new PatientAgent(200);
		model.scheduler.addLast(patient);
		ArrayList<Agent> beds = new ArrayList<Agent>();
		for(int i=0; i<10; i++) {
			Agent bed = new BedAgent(i);
			model.scheduler.add(bed);
			beds.add(bed);
		}
		int steps=10;
		while(steps>0) {
			model.scheduler.step(1);
			steps--;
		}
	}

}
