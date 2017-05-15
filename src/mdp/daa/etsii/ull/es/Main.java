package mdp.daa.etsii.ull.es;

public class Main {
	
	public static void main (String[] args){
		
		MaximumDiversityProblem p1 = new MaximumDiversityProblem(args[0]);
		/*int aux = 1;
		for(int m = 2; m <= 5; m++)	{
			for(int i = 1; i <= 2; i++){
				for(int lrc = 2; lrc <= 3; lrc++){
					long time_start = 0, time_end = 0;
					MaximumDiversityProblem S = null;
					for(int iter = 1; iter <= 10*i; iter++){
						time_start = System.currentTimeMillis();
						S = Algorithm.GRASP(p1, m, lrc);
						time_end = System.currentTimeMillis();
					}
					System.out.println(S);
					System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");

				}
			}
		}*/
		for(int i = 2; i <= 5; i++)
			System.out.println(Algorithm.branchAndBound(p1, i));
		
	}

}
