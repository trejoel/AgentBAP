package agentmodel;

import java.util.ArrayList;

public class Scheduler {
	private ArrayList<Agent> queue = new ArrayList<Agent>();
	private Agent last=null, first=null;
	private long steps;
	private ExperimentRunConfiguration conf;
	private ArrayList<BedAgent> setBeds;

	Scheduler() {
		steps=0;
		conf=new ExperimentRunConfiguration(500,1);
	}
	
	public void add(Agent agent) {
		queue.add(agent);
	}
	
	public void addLast(Agent agent) {
		last=agent;
	}
	
	public void addFirst(Agent agent) {
		first=agent;
	}


/*
	public void step(long stepsfwd) {
		Thread thread;
		Agent A = null;
		for(int i=0;i<queue.size();i++)
			A=(Agent) (queue.get(i));
			thread = new Thread((Runnable) A);
			thread.start();
		if(last!=null) last.act();
		steps++;
	}
*/

	// The execute method traverses all agents in queue. But, we need to define a method that allow us to retrieve agents by arrival day.
	// We can see the set of agents by day of simulation


	public ArrayList<BedAgent> getListOfBeds(){
		ArrayList<BedAgent> setBeds=new ArrayList<BedAgent>();
		Agent A;
		int i=0;
		while (i<queue.size()){
			A=queue.get(i);
			if (A.getType()==1){
				setBeds.add((BedAgent) A);
			}
			i++;
		}
		return setBeds;
	}


	public int execute(int day, int pos_Simulation) { //day is the step of simulation
		setBeds=this.getListOfBeds();
		ArrayList<Agent> partialAgentList=new ArrayList<Agent>();
		int i=pos_Simulation;
		int curDay=0;
		int prevDay=0;
		int type=0;
		Thread thread;
		Agent A;
		PatientAgent P;
		BedAgent B;
		HospitalAgent H;
		int posArrayBeds=0;
		int xPosArray=0; //-1 if it is unassigned
		for (int xDay=0;xDay<conf.getNumberOfDays();xDay++){
			//An alternative is the second parameter to be prevXday instead of pos_simulation. It requires the queue is sorted by day
			partialAgentList=getAgentsByDay(xDay,pos_Simulation);
			i=0;
			if (partialAgentList.size()==0){
				System.out.println("No agents register the day "+xDay);
			}
			while (i<partialAgentList.size()){
				A=(Agent) (partialAgentList.get(i));
				type=A.getType();
				if (type==2){
					P=(PatientAgent) (A);
					curDay=P.getTime();
					thread = new Thread((Runnable) P);
					if (i==0){
						P.setDelay(10);
					}
					thread.start();
					prevDay=curDay;
					xPosArray=assignBed(P,conf.getPolicyOfAssignment(),posArrayBeds);
					posArrayBeds=xPosArray;
				}
				else if (type==1) { //This is a bed
					B=(BedAgent) (A);
					thread = new Thread((Runnable) B);
					thread.start();
				}
				else {
					H=(HospitalAgent) (A);
					thread = new Thread((Runnable) H);
					thread.start();
				}
				i++;
			}
		}
		/*
		while (i<queue.size()){
			A=(Agent) (queue.get(i));
			type=A.getType();
			if (type==2){
				P=(PatientAgent) (A);
				curDay=P.getTime();
				/*if (curDay>prevDay){
					P.setDelay(100);
				}
* //
				thread = new Thread((Runnable) P);
				thread.start();
				prevDay=curDay;
			}
			else if (type==1) { //This is a bed
				B=(BedAgent) (A);
				thread = new Thread((Runnable) B);
				thread.start();
			}
			else {
				H=(HospitalAgent) (A);
				thread = new Thread((Runnable) H);
				thread.start();
			}
		/*	thread = new Thread((Runnable) A);
			thread.start(); * //
			//A.act();
			i++;
		}*/
		return pos_Simulation;
	}

	private ArrayList<Agent> getAgentsByDay(int day,int pos){
		ArrayList<Agent> partialAgentList=new ArrayList<Agent>();
		int i=pos;
		Agent A;
		while (i<queue.size()){
			A=(Agent) (queue.get(i));
			//System.out.println("Agent at day "+A.getTime());
			if (A.getTime()==day){
				partialAgentList.add(A);
			}
			i++;
		}
		return partialAgentList;
	}



	// In this we assign patient xPatient arriving at ith day is assigned at bed xBed. If it is assigned return true; otherwise return false
	protected int assignBed(Agent xPatient,int xPolicy,int posArrayBed){
		int assigned=-1; //-1 is assigned is not assigned
		switch (xPolicy){
			case 1: assigned=roundRobin(xPatient,posArrayBed);
					if (assigned>=0){
						System.out.println("Patient "+xPatient.getId()+" assigned to bed "+(setBeds.get(assigned)).getId()+" at day: "+((PatientAgent)xPatient).getArrivalDay());
					}else{
					  System.out.println("No bed available for patient "+xPatient.getId()+" at day: "+((PatientAgent)xPatient).getArrivalDay());
			        }

					break;
			default:
				System.out.println("No se puede asignar");
				break;
		}
		return assigned;
	}


  protected int roundRobin(Agent xPatient,int posArray){
		BedAgent xBed;
		int assigned=-1; //-1 if it si not assigned
	  	int i=0;
	    int xPosArray;
	    if (posArray>0){
			xPosArray=posArray;
		}
		else{
			xPosArray=0;
		}
		while (i<setBeds.size()){
			xBed=setBeds.get(xPosArray);
			if (xBed.isAvailable(((PatientAgent)xPatient).getArrivalDay(),((PatientAgent)xPatient).getDepartureDay())){
				xBed.allocatePatient(((PatientAgent)xPatient));
				i=setBeds.size();
				return xPosArray;
			}
			else{
				xPosArray=((xPosArray+1)%setBeds.size());
				i++;
			}
		}
		return -1;
  }

	public void reset() {
		queue.clear();
	}

	public long getSteps() {
		return steps;
	}

	
}
