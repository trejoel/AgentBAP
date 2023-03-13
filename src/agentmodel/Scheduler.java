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
		if(first!=null) first.act();
		for(int i=0;i<queue.size();i++)
			((Agent)(queue.get(i))).act();
		if(last!=null) last.act();
		steps++;
	}	

	public void reset() {
		queue.clear();
	}

	public long getSteps() {
		return steps;
	}

	
}
