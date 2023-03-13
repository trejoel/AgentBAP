package agentmodel;


public abstract class Agent {
	private int id;
	
	public Agent(int id) {

		this.setId(id);
	}

	public abstract void act();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
		
}
