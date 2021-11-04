import java.util.*;

//Axel Reumann 260945669

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		boolean isEqual =  (double) (a1.weight/a1.completiontime) == (double) (a2.weight/a2.completiontime);
		boolean deadEqual = a2.deadline == a1.deadline;

		//if the weight per hour of an assignment is greater than that of another, then we know its importance is higher.
		//Additionally, if the weight per hour is equal, the compare should sort by the deadline time instead
		//If the deadline is the same for both assignments simply chose the one with lower completion time
		if((double) (a1.weight/a1.completiontime) > (double) (a2.weight/a2.completiontime) || (isEqual && a2.deadline > a1.deadline) || (isEqual && deadEqual && a1.completiontime < a2.completiontime)){
			return -1;
		}else if((double) (a1.weight/a1.completiontime) < (double) (a2.weight/a2.completiontime) || (isEqual && a2.deadline < a1.deadline) || (isEqual && deadEqual && a2.completiontime < a1.completiontime)){
			return  1;
		}

		//If the weight per hour of the assignment is neither greater nor equal to the other than it must be equivalent
		//and they should be considered as equally important assignments
		return 0;
	}
}




public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
    int lastDeadline = 0;
    double grade = 0.0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
        if(size==0){
            throw new Exception("There is no assignment.");
        }
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
        
        //Initialize variable to hold the current time in the homeworkPlan
		int currTime = 0;

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i=0; i< lastDeadline; ++i){
			homeworkPlan.add(-1);
		}

		//Iterate through the assignments
		for (int i=0; i < this.Assignments.size(); ++i) {
			double netGain = 0.0;

			/*
			If the current time is less than the lastDeadline and the current time + completion time of the current assignment
			is also less than the current deadline we can just add the full weight to the grade as nothing is submitted late

			If current time + completion time is greater than the current deadline take off marks according to the 10% per hour late
			 */
			if(currTime < lastDeadline && currTime + this.Assignments.get(i).completiontime <= this.Assignments.get(i).deadline){
				netGain += (double) this.Assignments.get(i).weight;
			}
			else if(currTime < lastDeadline && currTime + this.Assignments.get(i).completiontime > this.Assignments.get(i).deadline){
				netGain += (double) (this.Assignments.get(i).weight - (double) ((currTime + this.Assignments.get(i).completiontime- this.Assignments.get(i).deadline)*(0.1 * this.Assignments.get(i).weight)));
			}
			else if(currTime >= lastDeadline || currTime >= this.Assignments.get(i).deadline) {netGain +=0;} //If the current time is greater than or equal to the deadline than no points are gained


			//If the grade that is yielded after doing the assignment is better than before, then we complete the assignment
			if(netGain + this.grade > this.grade){
				for(int hour = 0; hour < this.Assignments.get(i).completiontime; hour++ ){
					if(currTime< homeworkPlan.size()) {
						homeworkPlan.set(currTime, this.Assignments.get(i).number);
					}else{
						homeworkPlan.add(this.Assignments.get(i).number);
					}
					currTime++;
				}
				this.grade += netGain;
			}
		}
	
		
		return homeworkPlan;
	}

}
	



