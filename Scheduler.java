package agentmodel;

import java.util.ArrayList;

public class Scheduler {
	private ArrayList<Agent> queue = new ArrayList<Agent>();
	private Agent last=null, first=null;
	private long steps;
	private ExperimentRunConfiguration conf;
	private ArrayList<BedAgent> setNormalBeds;
	private ArrayList<BedAgent> setVentBeds;
	private int unAssignedPatients;
	private int AssignedPatients;
	private int CorrectHospital;
	private int WrongHospital;

	Scheduler() {
		steps=0;
		conf=new ExperimentRunConfiguration(500,1,3);
		unAssignedPatients=0;
		AssignedPatients=0;
		CorrectHospital=0;
		WrongHospital=0;
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

	public int getNumberOfNonAllocated(){
		return this.unAssignedPatients;
	}

	public int getNumberOfAllocated(){
		return AssignedPatients;
	}


	public int getNumberCorrectdHospital(){
		return CorrectHospital;
	}

	public int getNumberWrongHospital(){
		return WrongHospital;
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


	public ArrayList<BedAgent> getListOfBeds(int Type){
		ArrayList<BedAgent> setBeds=new ArrayList<BedAgent>();
		Agent A;
		int i=0;
		while (i<queue.size()){
			A=queue.get(i);
			if (A.getType()==1){
				if (Type==0){
					if (((BedAgent)A).getTypeBed()==0){
						setBeds.add((BedAgent) A);
					}
				}
				else{
					if (((BedAgent)A).getTypeBed()==1){
						setBeds.add((BedAgent) A);
					}
				}
			}
			i++;
		}
		return setBeds;
	}


	public int execute(int day, int pos_Simulation) { //day is the step of simulation
		setNormalBeds=this.getListOfBeds(0);
		setVentBeds= this.getListOfBeds(1);
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
		int posArrayNormalBeds=0;
		int posArrayVentBeds=0;
		int xPosNArray=0; //-1 if it is unassigned
		int xPosVArray=0; //-1 if it is unassigned
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
					if (((PatientAgent)P).getRequiredVentilation()){
						xPosVArray=assignBed(P,conf.getPolicyOfAssignment(),posArrayNormalBeds,posArrayVentBeds);
						posArrayVentBeds=xPosVArray;
						System.out.println("PosArrayVent: " +posArrayVentBeds);
					}
					else{
						xPosNArray=assignBed(P,conf.getPolicyOfAssignment(),posArrayNormalBeds,posArrayVentBeds);
						posArrayNormalBeds=xPosNArray;
						System.out.println("PosArrayNorm: "+posArrayNormalBeds);

					}
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
		System.out.println("Assigned Patients:"+this.getNumberOfAllocated()+" and number of No Allocated Patients:"+getNumberOfNonAllocated());
		System.out.println("Number of Patients Allocated to close hospital:"+getNumberCorrectdHospital());
		System.out.println("Number of Patients Allocated to wrong hospital:"+getNumberWrongHospital());
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
	protected int assignBed(Agent xPatient,int xPolicy,int posArrayNormal,int posArrayVent){
		int assigned=-1; //-1 is assigned is not assigned
		switch (xPolicy){
			case 1: assigned=roundRobin(xPatient,posArrayNormal,posArrayVent);
					if (assigned>=0){
						if (((PatientAgent)xPatient).getRequiredVentilation()){
							System.out.println("Patient "+xPatient.getId()+" whose close hospital is:"+((PatientAgent) xPatient).getCloserHospital()+" assigned to bed "+(setVentBeds.get(assigned)).getId()+ " From HOSPITAL:"+(setVentBeds.get(assigned)).getHospitalOfAllocation()+" at day: "+((PatientAgent)xPatient).getArrivalDay());
						    if (((PatientAgent) xPatient).getCloserHospital()==(setVentBeds.get(assigned)).getHospitalOfAllocation()){
	                           this.CorrectHospital++;
							}
							else{
								this.WrongHospital++;
							}
						}
						else{
							System.out.println("Patient "+xPatient.getId()+" whose close hospital is:"+((PatientAgent) xPatient).getCloserHospital()+" assigned to bed "+(setNormalBeds.get(assigned)).getId()+ " From HOSPITAL:"+(setNormalBeds.get(assigned)).getHospitalOfAllocation()+" at day: "+((PatientAgent)xPatient).getArrivalDay());
							if (((PatientAgent) xPatient).getCloserHospital()==(setNormalBeds.get(assigned)).getHospitalOfAllocation()){
								this.CorrectHospital++;
							}
							else{
								this.WrongHospital++;
							}
						}
						this.AssignedPatients++;
					}else{
						this.unAssignedPatients++;
					  System.out.println("No bed available for patient "+xPatient.getId()+" at day: "+((PatientAgent)xPatient).getArrivalDay());
			        }
					break;
			default:
				System.out.println("No se puede asignar");
				break;
		}
		return assigned;
	}


  protected int roundRobin(Agent xPatient,int posArrayNormal, int posArrayVentilation){
		BedAgent xBed;
		int assigned=-1; //-1 if it si not assigned
	  	int i=0;
	    int xPosArrayNormal;
		int xPosArrayVentilation;
	    if (posArrayNormal>0){
			xPosArrayNormal=posArrayNormal;
		}
		else{
			xPosArrayNormal=0;
		}
	  if (posArrayVentilation>0){
		  xPosArrayVentilation=posArrayVentilation;
	  }
	  else{
		  xPosArrayVentilation=0;
	  }
	  if (((PatientAgent)xPatient).getRequiredVentilation()){
		  while (i<setVentBeds.size()){
			  xBed=setVentBeds.get(xPosArrayVentilation);
			  if (xBed.isAvailable(((PatientAgent)xPatient).getArrivalDay(),((PatientAgent)xPatient).getDepartureDay())){
				  xBed.allocatePatient(((PatientAgent)xPatient));
				  i=setVentBeds.size();
				  return xPosArrayVentilation;
			  }
			  else{
				  xPosArrayVentilation=((xPosArrayVentilation+1)%setVentBeds.size());
				  i++;
			  }
		  }
	  }
	  else{
		  while (i<setNormalBeds.size()){
			  xBed=setNormalBeds.get(xPosArrayNormal);
			  if (xBed.isAvailable(((PatientAgent)xPatient).getArrivalDay(),((PatientAgent)xPatient).getDepartureDay())){
				  xBed.allocatePatient(((PatientAgent)xPatient));
				  i=setNormalBeds.size();
				  return xPosArrayNormal;
			  }
			  else{
				  xPosArrayNormal=((xPosArrayNormal+1)%setNormalBeds.size());
				  i++;
			  }
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
