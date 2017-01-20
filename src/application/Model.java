package application;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import bean.Airport;
import bean.Route;
import db.Dao;

public class Model {
	
	private Dao dao = new Dao();
	private DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo = null;
	
	
	public List<Airport> allA(){
		List<Airport> all= dao.getAereoporti();
		return all;
	}
	
	public double getCalcolaDistanzaTraAereoporti(int c1, int c2){  //calcolo la distanza tra ogni coppia
		double dist=-1;
		if(c1!= 0 && c2!= 0){
			Airport a1 = getAereoportoByCodice(c1);
			Airport a2 = getAereoportoByCodice(c2);
			if(a1!=null && a2!= null){
			   LatLng l1 = new LatLng( a1.getLatitude(), a1.getLongitude());  
			   LatLng l2 = new LatLng(a2.getLatitude(), a2.getLongitude());
		       dist = LatLngTool.distance(l1, l2, LengthUnit.KILOMETER);
		       }
		}
		//System.out.println(dist);
		return dist;
	}
	
	public List<Route> getRotte(){
		List<Route> rotte = dao.getRotte();
		return rotte;
	}
	
	public void buildGraph(double km){
		grafo = new DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge> (DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, allA());
		double distanza = -1;
		for(Airport a1 : grafo.vertexSet()){
		for(Airport a2 : grafo.vertexSet()){
		   if(!a1.equals(a2)){
			   for(Route r : getRotte()){
			   if(r.getDestinationAirportId()== a1.getAirportId() && r.getSourceAirportId()== a2.getAirportId()  || 
				  r.getDestinationAirportId()== a2.getAirportId() && r.getSourceAirportId()==a1.getAirportId()){
			   
			   //se esiste almeno una rotta tra i due aereoporti, allora calcolo la distanza e metto l arco
		       distanza = getCalcolaDistanzaTraAereoporti(a1.getAirportId(), a2.getAirportId());
			}}
		   }
		   if(distanza < km){     
			   double tempo= (distanza/800);              //ore
			   Graphs.addEdge(grafo,  a1,  a2,  tempo);
		    }
		}
		}
		System.out.println(grafo.toString());
	}
	
	
	
	
	//determino se nel grafo da ogni aereoporto è possibile raggiungere ogni altro == grafo connesso???
	//1) lista di aereoporti non raggiungibili e vedo se è vuota o no
	//2) calcolo numero di componenti connesse, se è una sola allora posso raggiungere ogni aereoporto
	
	
	
	//det se da ogni aereoporto è possibile raggiungere ogni altro aereo
	
	public int getNumConn(Airport a ){    //il grafo è diretto  //2
		return grafo.inDegreeOf(a);      //archi entranti
	}
	
	public List<Airport> getNonRagg(){
		List<Airport> non = new LinkedList<>();    //creo lista di aereoporti (vuota)
		for(Airport a : grafo.vertexSet()){       //x ogni vertice del grafo
			if(getNumConn(a)==0){                //se quel vertice ha grado 0, vuol dire ke non è collegato
				non.add(a);
			}
		}
		System.out.println(non);          //genera  eccez
		return non;
	}
	
	public Airport getAereoportoByCodice(int codice){
		Airport a = dao.getAereoportoByCodice(codice);
		return a;
	}
	
	
	public Airport getCamm(Airport start,DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge>grafo ){  //fiumicino fiumicino
		double distMax=-1;
		Airport trovatoMax=null;
		for(Airport v : grafo.vertexSet()){
		     DijkstraShortestPath<Airport, DefaultWeightedEdge> di = new DijkstraShortestPath<Airport, DefaultWeightedEdge>(grafo, start, v);
		     double lc = di.getPathLength();                     //lung cammino
		     if(lc > distMax){
			 distMax=lc;
		     trovatoMax=v ;
		     }
		}
		return trovatoMax;
		}
	
	
	//escludo aereoporti che non hanno rotte:
	//lista di TUTTI  aereoporti
	//dalla quale tolgo gli aereoporti ke hanno almeno una rotta
	
	public List<Airport> aereoportiSenzaRotte(){
		List<Airport> questi = new LinkedList<>();
		questi.addAll(allA());                          //aggiungo tutti 
		return questi;
	}
	


//	public void buildGraph(double km){    //non parte
//		grafo = new DefaultDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//		Graphs.addAllVertices(grafo, getAllAerei());
//		for(Airport a1: grafo.vertexSet()){
//			for(Airport a2 : grafo.vertexSet()){               //x ogni coppia di vertici
//				if(getDistanza(a1, a2)<km) {                 //se la distanza è inferiore
//					for(Route r : getRotte()){              //se esiste almeno una rotta tra i due aereoporti
//						if(a1.getAirportId()==r.getDestinationAirportId() && a2.getAirportId()==r.getSourceAirportId() ||
//								a1.getAirportId()==r.getSourceAirportId() && a2.getAirportId()==r.getDestinationAirportId()){
//							double durataVolo = getDistanza(a1, a2) / 800 ; //800 KM/H          //durata in ore
//							Graphs.addEdge(grafo, a1,  a2,  durataVolo);
//						}
//					}
//				}
//			}
//			
//	}
	
	
	public static void main(String [] args){
		Model m = new Model();
		m.buildGraph(200);
	}
}
