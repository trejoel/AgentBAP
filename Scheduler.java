package agentmodel;

import java.util.ArrayList;

public class Scheduler {
	private ArrayList<Agent> queue = new ArrayList<Agent>();
	private Agent last=null, first=null;
	private long steps;

	Scheduler() {
		steps=0;
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


	// Notice that we cannot access methods of patient simulation, only methods of Agent. How can we  use methods of inherated agentss
	public int stepTwo(int day, int pos_Simulation) { //day is the step of simulation
		int i=pos_Simulation;
		int curDay=0;
		int prevDay=0;
		int type=0;
		Thread thread;
		Agent A;
		PatientAgent P;
		BedAgent B;
		HospitalAgent H;
		while (i<queue.size()){
			A=(Agent) (queue.get(i));
			type=A.getType();
			if (type==2){
				P=(PatientAgent) (A);
				curDay=P.getTime();
				if (curDay>prevDay){
					P.setDelay(500);
				}
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
			thread.start();*/
			//A.act();
			i++;
		}
		i--;
		return i;
	}


	public void reset() {
		queue.clear();
	}

	public long getSteps() {
		return steps;
	}

	
}
