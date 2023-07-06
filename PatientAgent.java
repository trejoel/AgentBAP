package agentmodel;


public class PatientAgent extends Agent implements Runnable {

	private int age;
	private Boolean gender=false;  // 0-male, 1-female
	private Boolean diabetes=false;//
	private Boolean hipertension=false;
	private Boolean epoc=false;
	private int oxigenation=100; // Default is 100
	private int height=170; // in cms
	private int weight=70; // in kgs
	private boolean newDay;
	private boolean allocated;



	public PatientAgent(int id, int timeStep,boolean xNewDay) {
		super(id,timeStep);
		this.newDay=xNewDay;
		// TODO Auto-generated constructor stub
		allocated = false;
	}


	public PatientAgent(int id, int timeStep, int xage, Boolean xGender, Boolean xDiabetes, Boolean xHiper, Boolean xEpoc, int xoxigenation, int xheight, int xweight) {
		super(id,timeStep);
		// TODO Auto-generated constructor stub
		this.age=xage;
		this.gender=xGender;
		this.diabetes= xDiabetes;
		this.hipertension= xHiper;
		this.epoc=xEpoc;
		this.oxigenation=xoxigenation;
		this.height=xheight;
		this.weight=xweight;
		this.newDay=false;
		this.allocated=false;
	}

	//getters

	public int getAge(){
		return this.age;
	}

	public Boolean getGender(){
		return this.gender;
	}

	public Boolean getDiabetes(){
		return this.diabetes;
	}

	public Boolean getHipertension(){
		return this.hipertension;
	}

	public boolean getEpoc(){
		return this.epoc;
	}

	public boolean getAllocation(){return this.allocated;}

	public int getOxigenation(){
		return this.oxigenation;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWeight(){
		return this.weight;
	}

	public boolean isNewDay(){
		return this.newDay;
	}

	public void setNewDay(){
		this.newDay=true;
	}

	public void allocate(){
		this.allocated=true;
	}

	// Setters

public void setAge(int xAge){
		this.age=xAge;
}


public void setGender(Boolean xGender){
		this.gender=xGender;
}


	public void setDiabetes(Boolean xDiabetes){
		this.diabetes=xDiabetes;
	}

	public void setHipertension(Boolean xHiper){
		this.hipertension=xHiper;
	}

	public void setEpoc(Boolean xEpoc){
		this.epoc=xEpoc;
	}

	public void setOxigenation(int xOxi){
		this.oxigenation=xOxi;
	}

	public void setHeight(int xHeight){
		this.height=xHeight;
	}

	public void setWeight(int xWeight){
		this.weight=xWeight;
	}

	public void setDelay(int x){
		try {
				Thread.sleep(x);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	// Concurrent execution


	@Override
	public void run() {
			act();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
			imprimePaciente();
	}


	public void imprimePaciente(){
	//	System.out.println("Patient "+getId());
		if (this.getGender()) {
			System.out.println("Female patient of " + getAge() + " years old. Arriving the day: "+this.getTime()+ " Diabetes:"+ this.getDiabetes() + ". Epoc= " + this.getEpoc());
			//System.out.println(". Hipertension=" + this.getHipertension() + ". Oxigenation=" + this.getOxigenation() + ".Height=" + this.getHeight() + ".Weight=" + this.getWeight());
		}
		else{
			System.out.println("Male patient of " +  getAge() + " years old. Arriving the day: "+this.getTime()+ " Diabetes: " + this.getDiabetes() + ". Epoc= " + this.getEpoc());
			//System.out.println(". Hipertension=" + this.getHipertension() + ". Oxigenation=" + this.getOxigenation() + ".Height=" + this.getHeight() + ".Weight=" + this.getWeight());
		}
	}



}
