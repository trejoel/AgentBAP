package agentmodel;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class PatientAgent extends Agent implements Runnable {

	private int age;
	private Boolean gender=false;  // 0-male, 1-female
	private Boolean diabetes=false;//
	private Boolean hipertension=false;
	private Boolean epoc=false;
	private int oxigenation=100; // Default is 100
	private int height=170; // in cms
	private int weight=70; // in kgs


	public PatientAgent(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}


	public PatientAgent(int id, int xage, Boolean xGender, Boolean xDiabetes, Boolean xHiper, Boolean xEpoc, int xoxigenation, int xheight, int xweight) {
		super(id);
		// TODO Auto-generated constructor stub
		this.age=xage;
		this.gender=xGender;
		this.diabetes= xDiabetes;
		this.hipertension= xHiper;
		this.epoc=xEpoc;
		this.oxigenation=xoxigenation;
		this.height=xheight;
		this.weight=xweight;
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

	public int getOxigenation(){
		return this.oxigenation;
	}

	public int getHeight(){
		return this.height;
	}

	public int getWeight(){
		return this.weight;
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

	// Concurrent execution

	public void run(){
		act();
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		imprimePaciente();
	}


	public void imprimePaciente(){
		System.out.print("Patient "+getId()+": ");
		if (this.getGender()) {
			System.out.print("Female patient of " + getAge() + "years old. Diabetes " + this.getDiabetes() + ". Epoc= " + this.getEpoc());
			System.out.println(". Hipertension=" + this.getHipertension() + ". Oxigenation=" + this.getOxigenation() + ".Height=" + this.getHeight() + ".Weight=" + this.getWeight());
		}
		else{
			System.out.print("Male patient of " + getAge() + "years old. Diabetes " + this.getDiabetes() + ". Epoc= " + this.getEpoc());
			System.out.println(". Hipertension=" + this.getHipertension() + ". Oxigenation=" + this.getOxigenation() + ".Height=" + this.getHeight() + ".Weight=" + this.getWeight());
		}
	}

}
