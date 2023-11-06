package agentmodel;

public class ExperimentRunConfiguration {
    private int numberOfDays;
    private int policyOfAssignment; // 1 Round robin
    private int numberOfHospitals; // by Default is just one hospital

    public ExperimentRunConfiguration(int xnumberOfDays, int xpolicyOfAssignment, int XnumberOfHospitals) {
        this.numberOfDays = xnumberOfDays;
        this.policyOfAssignment=xpolicyOfAssignment;
        this.numberOfHospitals=XnumberOfHospitals;
    }

    public void setNumberOfHospitals(int xNH){
        this.numberOfHospitals=xNH;
    }

    public int getNumberOfDays() {
        return this.numberOfDays;
    }

    public int getPolicyOfAssignment(){
        return this.policyOfAssignment;
    }

    public int getNumberOfHospitals(){return this.numberOfHospitals; }

}