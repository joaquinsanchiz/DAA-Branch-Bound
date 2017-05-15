package mdp.daa.etsii.ull.es;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.SortedMap;

/**
 * Clase que representa los diferentes algoritmos que se pueden computar.
 * @author joaquinsanchiz
 *
 */
public class Algorithm {
	
	/**
	 * Algoritmo branchAndBound
	 * @param S problema
	 * @param m tamaño de la solucion
	 * @return solucion
	 */
	public static MaximumDiversityProblem branchAndBound(MaximumDiversityProblem S, int m){
		
		int nNodos = 0;
		
		MaximumDiversityProblem greedy = Algorithm.GRASP(S, 2, 2);
		MaximumDiversityProblem aux = new MaximumDiversityProblem();
		aux.setCentroide(S.getCentroide());
		
		double maxDistance = Double.MIN_NORMAL;
		double[] sAux = null;
		for(int i = 0; i < S.getS().size(); i++){
			if(S.euclideanDistance(aux.getCentroide(), S.getS().get(i)) > maxDistance){
				maxDistance = S.euclideanDistance(aux.getCentroide(), S.getS().get(i));
				sAux = S.getS().get(i);
			}
		}
		aux.insert(sAux);
		nNodos++;
		
		//Inicializando primer nivel
		ArrayList<MaximumDiversityProblem> cubo = new ArrayList<MaximumDiversityProblem>();
		for(int i = 0; i < S.getS().size() - 1; i++){
			if(!aux.getS().contains(S.getS().get(i))){
				nNodos++;
				MaximumDiversityProblem temp = new MaximumDiversityProblem(aux);
				temp.insert(S.getS().get(i));
				temp.setZ(S.Z(temp));
				if(temp.getZ() >= greedy.getZ()){
					cubo.add(temp);
				}
			}
		}
		
		
		for(int iter = 0; iter <= m; iter++){
			
			if(cubo.get(0).getS().size() >= m){ 
				break;
			}
			
			int sizeCubo = cubo.size();
			
			for(int i = 0; i < sizeCubo; i++){
				int currentM = 3+iter;
				greedy = Algorithm.GRASP(S, currentM, 2);
								
				//Iteracion con externos
				for(int j = 0; j < S.getS().size(); j++){
					
					MaximumDiversityProblem iterando = new MaximumDiversityProblem(cubo.get(i));
					
					if(!iterando.getS().contains(S.getS().get(j))){
						
						nNodos++;
						
						iterando.insert(S.getS().get(j));
						iterando.setZ(S.Z(iterando));
						
						if(iterando.getZ() >= greedy.getZ()){
							//System.out.println(iterando);
							cubo.add(iterando);		
						}
					}
				}
				
			}
			
			for(int r = 0; r < sizeCubo; r++){
				cubo.remove(0);
			}
		}
		
		
		maxDistance = Double.MIN_NORMAL;
		int solucion = 0;
		for(int i = 0; i < cubo.size(); i++){
			if(cubo.get(i).getZ() > maxDistance){
				maxDistance = cubo.get(i).getZ();
				solucion = i;
			}
		}
		System.out.println("Nodos: " + nNodos);
		return cubo.get(solucion);
		
	}
	
	/**
	 * Algoritmo greedy
	 * @param S problema
	 * @param m tamaño de la solucion
	 * @return solucion
	 */
	public static MaximumDiversityProblem greedy(MaximumDiversityProblem S, int m){
		
		MaximumDiversityProblem s = new MaximumDiversityProblem();
		s.setCentroide(S.centroide());
		s.initChecked(S.getS().size());
		//System.out.println("Primer centroide: " + Arrays.toString(s.getCentroide()));
		
		while(s.getS().size() != m){
			
			double maxDistance = Double.MIN_NORMAL;
			double[] sAux = null;
			int position = 0;
			for(int i = 0; i < S.getS().size(); i++){
				if(!s.getS().contains(S.getS().get(i))){
					if( S.euclideanDistance(s.getCentroide(), S.getS().get(i)) > maxDistance){
						maxDistance = S.euclideanDistance(s.getCentroide(), S.getS().get(i));
						sAux = S.getS().get(i);
						position = i;
						//System.out.println("Current vector: " + Arrays.toString(sAux) + ", distance: " + maxDistance);
						
					}
				}
			}
			
			if(sAux != null){
				//System.out.println("Inserting v: " + Arrays.toString(sAux) + ", distance: " + maxDistance);
				//System.out.println("Prev centroid: " + Arrays.toString(s.getCentroide()));
				s.insert(sAux);
				s.getChecked().set(position, true);
				//System.out.println("Current centroid: " + Arrays.toString(s.getCentroide()));

			}
		
		}
		s.setZ(S.Z(s));
		return s;
	}
	
	/**
	 * Algoritmo greedy destructivo
	 * @param S problema
	 * @param m tamaño de la solucion
	 * @return solucion
	 */
	public static MaximumDiversityProblem myGreedy(MaximumDiversityProblem S, int m){
		
		MaximumDiversityProblem s = new MaximumDiversityProblem(S);
		s.setCentroide(S.centroide());
		//System.out.println("Primer centroide: " + Arrays.toString(s.getCentroide()));
		
		while(s.getS().size() != m){
			
			double minDistance = Double.MAX_VALUE;
			double[] sAux = null;
			int position = 0;
			for(int i = 0; i < S.getS().size(); i++){
				if(s.getS().contains(S.getS().get(i))){
					if( S.euclideanDistance(s.getCentroide(), S.getS().get(i)) < minDistance){
						minDistance = S.euclideanDistance(s.getCentroide(), S.getS().get(i));
						sAux = S.getS().get(i);
						position = i;
						
					}
				}
			}
			
			if(sAux != null){
				//System.out.println("Inserting v: " + Arrays.toString(sAux) + ", distance: " + maxDistance);
				//System.out.println("Prev centroid: " + Arrays.toString(s.getCentroide()));
				s.getS().remove(sAux);
				s.setCentroide(s.centroide());
				//System.out.println("Current centroid: " + Arrays.toString(s.getCentroide()));

			}
		
		}
		s.setZ(S.Z(s));
		return s;
		
	}
	
	/**
	 * Algoritmo para la busqueda local con solucion inicial de greedy
	 * @param S problema
	 * @param m tamaño de la solucion
	 * @return solucion
	 */
	
	public static MaximumDiversityProblem busquedaLocal(MaximumDiversityProblem S, int m){
		
		MaximumDiversityProblem s = Algorithm.greedy(S, m); //solución inicial
		
		for(int i = 0; i < s.getS().size(); i++){
			for(int j = 0; j < s.getS().get(i).length; j++){
				if(!s.getS().contains(S.getS().get(j))){
					
					MaximumDiversityProblem aux = new MaximumDiversityProblem(s);
					aux.getS().remove(i);
					aux.insert(S.getS().get(j));
					
					if( S.Z(aux) > s.getZ()){
						aux.setZ(S.Z(aux));
						s = aux;
						s.getChecked().set(i, false);
						s.getChecked().set(j, true);
					}
					
				}
			}
		}
		
		return s;
		
	}
	
	/**
	 * Algoritmo de busqueda local
	 * @param S problema
	 * @param s solucion parcial
	 * @param m tamaño del problema
	 * @return solucion mejorada
	 */
public static MaximumDiversityProblem busquedaLocal(MaximumDiversityProblem S, MaximumDiversityProblem s, int m){
		
		for(int i = 0; i < s.getS().size(); i++){
			for(int j = 0; j < s.getS().get(i).length; j++){
				if(!s.getS().contains(S.getS().get(j))){
					
					MaximumDiversityProblem aux = new MaximumDiversityProblem(s);
					aux.getS().remove(i);
					aux.insert(S.getS().get(j));
					
					if( S.Z(aux) > s.getZ()){
						aux.setZ(S.Z(aux));
						s = aux;
						s.getChecked().set(j, true);
						s.getChecked().set(i, false);
					}
					
				}
			}
		}
		
		return s;
		
	}

	
	/**
	 * Algoritmo GRASP
	 * @param S problema
	 * @param m tamaño de la solución
	 * @param lrc tamaño de la lista LRC
	 * @return solución
	 */
	public static MaximumDiversityProblem GRASP(MaximumDiversityProblem S, int m, int lrc){
		
		MaximumDiversityProblem s = new MaximumDiversityProblem();
		s.setCentroide(S.centroide());
		s.initChecked(S.getS().size());
		
		double maxDistance = Double.MIN_NORMAL;
		double[] sAux = null;
		int position = 0;
		for(int i = 0; i < S.getS().size(); i++){
			if(!s.getS().contains(S.getS().get(i))){
				if( S.euclideanDistance(s.getCentroide(), S.getS().get(i)) > maxDistance){
					maxDistance = S.euclideanDistance(s.getCentroide(), S.getS().get(i));
					sAux = S.getS().get(i);	
					position = i;
				}
			}
		}
		
		s.insert(sAux);
		s.getChecked().set(position, true);
		
		while(s.getS().size() < m){
			
			java.util.TreeMap<Double, double[]> aux = new java.util.TreeMap<Double, double[]>(Collections.reverseOrder());
			for(int i = 0; i < S.getS().size(); i++){
				if(!s.getS().contains(S.getS().get(i)))
					aux.put(S.euclideanDistance(s.getCentroide(), S.getS().get(i)), S.getS().get(i));
			}
			
			//System.out.println(aux);
			
	        java.util.Iterator iterator = aux.keySet().iterator();
	        double key = 0;
	        for(int i = 0; i <= Math.floor(Math.random()*(lrc+1)); i++){
	        	key = (double) iterator.next();
	        }
	        
	        //System.out.println("To insert: " + key);
	        
	        s.insert( aux.get(key) );
	        position = s.getS().indexOf(aux.get(key));
	        s.getChecked().set(position, true);
	        			
		}
		
		s.setZ(S.Z(s));
		
		return Algorithm.busquedaLocal(S, s, m);
		
	}
	
	

}
