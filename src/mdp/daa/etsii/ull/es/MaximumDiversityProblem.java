package mdp.daa.etsii.ull.es;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que representa un problema o solución de maxima dispersion
 * @author joaquinsanchiz
 *
 */
public class MaximumDiversityProblem {
	
	/**
	 * Array de vectores
	 */
	private ArrayList<double []> S = new ArrayList<double []>();
	private ArrayList<Boolean> checked = new ArrayList<Boolean>();
	/**
	 * Centroide
	 */
	private double[] centroide;
	private double z = 0;
	
	public MaximumDiversityProblem(){
		
	}
	
	/**
	 * Constructor de copia
	 * @param rhs
	 */
	public MaximumDiversityProblem(MaximumDiversityProblem rhs){
		
		this();
		this.S = (ArrayList<double[]>) rhs.getS().clone();
		this.setCentroide(rhs.getCentroide());
		this.setZ(rhs.getZ());
		this.setChecked(rhs.getChecked());
		
	}
	
	public void initChecked(int n){
		for(int i = 0; i < n; i++){
			this.getChecked().add(false);
		}
	}
	
	/**
	 * Inserta un vector y recalcula el centroide
	 * @param s
	 */
	public void insert(double[] s){
		this.getS().add(s);
		this.setCentroide(this.centroide());
	}
	
	/**
	 * Elimina un vector y recalcula el centroide
	 * @param s
	 */
	public void remove(double[] s){
		this.getS().remove(s);
		this.setCentroide(this.centroide());
	}
	
	/**
	 * Constructor a partir de fichero
	 * @param file
	 */
	public MaximumDiversityProblem(String file){
		File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;

	      try {
	    	  
	         archivo = new File (file);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         int n = Integer.parseInt(br.readLine());
	         int k = Integer.parseInt(br.readLine());
	         
	         for(int i = 0; i < n; i++){
	        	 double[] aux = new double[k];
	        	 String[] values = br.readLine().split("\\s+");
	        	 for(int j = 0; j < k; j++){
	        		 aux[j] = Double.parseDouble(values[j]);
	        	 }
	        	 this.getS().add(aux);
	         }
	         
	         this.setCentroide(this.centroide());
	         
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		
		
	}
	
	public String toString(){
		
		String toString = "";
		toString += "n: " + this.getS().size() + "\n";
		toString += "k: " + this.getS().get(0).length + "\n";
		
		toString += this.getZ() + " ";
		for(int i = 0; i < this.getS().size(); i++){
			toString += "[";
			for(int j = 0; j < this.getS().get(i).length; j++){
				toString += this.getS().get(i)[j] + ",";
			}
			toString += "]";
		}
		
		//toString += "Centroide: " + Arrays.toString(this.getCentroide()) + "\n";
		//toString += "Z: " + this.getZ() + "\n";
		/*for(int i = 0; i < this.getChecked().size(); i++){
			if(this.getChecked().get(i).equals(true)) toString += "1, ";
			else toString += "0, ";
		}
		toString += "]";*/
		
		return toString;
	}
	
	/**
	 * Distancia euclidea entre nodos
	 * @param si
	 * @param sj
	 * @return
	 */
	public static double euclideanDistance(double[] si, double[] sj){
		
		double euclideanDistance = 0;
		
		for(int i = 0; i < si.length; i++){
			
			euclideanDistance += ( (si[i] - sj[i]) * (si[i] - sj[i]) );
			
		}
		
		return Math.sqrt(euclideanDistance);
		
	}
	
	/**
	 * Calcula el centroide del problema o solucion
	 * @return
	 */
	public double[] centroide(){
		
		double[] centroide = new double[this.getS().get(0).length];
		
		for(int i = 0; i < this.getS().get(0).length; i++){
			for(int j = 0; j < this.getS().size(); j++){
				centroide[i] += this.getS().get(j)[i];
			}
			centroide[i] = centroide[i] / this.getS().size();
		}
		
		return centroide;
		
	}
	
	/**
	 * Calcula la z de una solucion
	 * @param s Solucion a calcular
	 * @return
	 */
	public double Z(MaximumDiversityProblem s){
		
		double suma = 0;
		
		for(int i = 0; i < this.getS().size()-1; i++){
			for(int j = i+1; j < this.getS().size(); j++){
				
				int xi = 0, xj = 0;
				if(s.getS().contains(this.getS().get(i))) xi = 1;
				if(s.getS().contains(this.getS().get(j))) xj = 1;
				
				suma += this.euclideanDistance(this.getS().get(i), this.getS().get(j)) * xi * xj;
				
			}
		}
		
		return suma;
	}

	public ArrayList<double[]> getS() {
		return S;
	}

	public void setS(ArrayList<double[]> s) {
		S = s;
	}

	public double[] getCentroide() {
		return centroide;
	}

	public void setCentroide(double[] centroide) {
		this.centroide = centroide;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public ArrayList<Boolean> getChecked() {
		return checked;
	}

	public void setChecked(ArrayList<Boolean> checked) {
		this.checked = checked;
	}
	
	
	

}
