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
		Agent hospital =  new HospitalAgent(100,0);
		hospital.setType(3);
		model.scheduler.add(hospital);
		//Agent patient = new PatientAgent(200,0);
		//model.scheduler.addLast(patient);
		ArrayList<Agent> beds = new ArrayList<Agent>();
		int xTime=0;
		int number_of_beds=10;
		for(int i=0; i<number_of_beds; i++) {
			Agent bed = new BedAgent(i,xTime,0);
			bed.setType(1);
			model.scheduler.add(bed);
			beds.add(bed);
		}
		ArrayList<Agent> patients = new ArrayList<Agent>();
		xTime=-1;
		int number_of_patients=100;
		for(int i=0; i<number_of_patients; i++) {
			if (i % 9 ==0 ){
				xTime++;
				Agent patient = new PatientAgent(i,xTime,true);
				patient.setType(2);
				//patient.act();
				model.scheduler.add(patient);
				patients.add(patient);
			}
			else{
				Agent patient = new PatientAgent(i,xTime,false);
				patient.setType(2);
				//patient.act();
				model.scheduler.add(patient);
				patients.add(patient);
			}
		}
		int numSteps=0;
		int simulationSteps=11;
		int posSimulation=model.scheduler.execute(0,0);
	}

}
