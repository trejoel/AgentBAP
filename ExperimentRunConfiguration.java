package agentmodel;

public class ExperimentRunConfiguration {
    private int numberOfDays;
    private int policyOfAssignment; // 1 Round robin

    public ExperimentRunConfiguration(int xnumberOfDays, int xpolicyOfAssignment) {
        this.numberOfDays = xnumberOfDays;
        this.policyOfAssignment=xpolicyOfAssignment;
    }

    public int getNumberOfDays() {
        return this.numberOfDays;
    }

    public int getPolicyOfAssignment(){
        return this.policyOfAssignment;
    }

}