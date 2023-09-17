package agentmodel;

import java.util.ArrayList;
import java.util.Random;
import agentmodel.*;


public class SimModel {
	private Scheduler scheduler;
	private Environment environment;
	private Random rand;
	private ExperimentRunConfiguration conf;

	SimModel() {
		// TODO Auto-generated constructor stub
		scheduler = new Scheduler();
		environment = new Environment();
		rand=new Random(System.currentTimeMillis());
		conf=new ExperimentRunConfiguration(500,1);
	}

	
	public SimModel getInstance() {
		return this;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		int newDay=0;
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
			Agent bed = new BedAgent(i,xTime);
			bed.setType(1);
			((BedAgent)bed).setTypeBed(2);
			model.scheduler.add(bed);
			beds.add(bed);
		}
		ArrayList<Agent> patients = new ArrayList<Agent>();
		xTime=0;
		int number_of_patients=100;
		for(int i=0; i<number_of_patients; i++) {
			//generate a random number to determine if it is a new day
			newDay=ran.nextInt(10);
			//if (i % 9 ==0 ){
			if (newDay>8 ){
				xTime++;
				Agent patient = new PatientAgent(i,xTime,true);
				patient.setType(2);
				((PatientAgent)patient).setArrivalDay(xTime);
				((PatientAgent)patient).setDepartureDay(xTime+ran.nextInt(10));
				//patient.act();
				model.scheduler.add(patient);
				patients.add(patient);
			}
			else{
				Agent patient = new PatientAgent(i,xTime,true);
				patient.setType(2);
				((PatientAgent)patient).setArrivalDay(xTime);
				((PatientAgent)patient).setDepartureDay(xTime+ran.nextInt(10));
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
