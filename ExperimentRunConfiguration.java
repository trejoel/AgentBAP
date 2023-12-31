package agentmodel;

public class ExperimentRunConfiguration {
    private int numberOfDays;
    private int policyOfAssignment; // 1 Round robin; 2 Best Fit (largest Number of Stay); 3 Worst Fit (smaller Number of days to stay); 4 First Fit (Fist to hold a bias in the number of days)
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