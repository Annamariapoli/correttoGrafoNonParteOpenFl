package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import bean.Airport;
import bean.Route;

public class Dao {
	
	public List<Airport> getAereoporti() {                     //tutti aereoporti
		Connection conn = DBConnect.getConnection();
		String query ="select * from airport";
		List<Airport> aerei = new LinkedList<Airport>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Airport r = new Airport (res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"), res.getDouble("Longitude"),					
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"));
				aerei.add(r);
			}conn.close();
			return aerei;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Route> getRotte(){                          //tutte le rotte
		Connection conn = DBConnect.getConnection();
		String query ="select * from route";
		List<Route> rotte = new LinkedList<>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Route r =new Route(
						res.getString("Airline"),
						res.getInt("Airline_ID"),
						res.getString("Source_airport"),
						res.getInt("Source_airport_ID"),
						res.getString("Destination_airport"),
						res.getInt("Destination_airport_ID"),
						res.getString("Codeshare"),
						res.getInt("stops"),
						res.getString("Equipment"));
				rotte.add(r);
			}conn.close();
			return rotte;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}

	public Airport getAereoportoByCodice(int id) {
		Connection conn = DBConnect.getConnection();
		String query ="select * from airport where Airport_ID=?";
	     Airport r = null;
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,id);
			ResultSet res = st.executeQuery();
			if(res.next()){
				 r = new Airport (res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"), res.getDouble("Longitude"),					
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"));
			}conn.close();
			return r;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	//aereoporti che hanno almeno una rotta  //quindi si tratta di coppie
	
	public List<Airport> getEsisteAlmeno1Rotta(int id1, int id2){
		Connection conn = DBConnect.getConnection();
		String query ="select *    "
				+ "from route r    "
				+ "where r.Source_airport_ID=? and r.Destination_airport_ID=?  "
				+ "or(r.Destination_airport_ID=? and r.Source_airport_ID=?)";
	    List< Airport> all  = new LinkedList<>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,id1);
			st.setInt(2,  id2);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Airport  r = new Airport (res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"), res.getDouble("Longitude"),					
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz"));
				all.add(r);
			}conn.close();
			return all;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	

	
	

//	public boolean  getEsisteRotta(int a1, int a2){   //x vedere se esiste rotta tra 2 aereoporti
//		Connection conn = DBConnect.getConnection();
//		String query = "select * from route r  where r.Source_airport_ID=? and r.Destination_airport_ID=? "
//				+ "or(r.Destination_airport_ID=? and r.Source_airport_ID=?)";
//		try{
//			PreparedStatement st = conn.prepareStatement(query);
//			st.setInt(1, a1);
//			st.setInt(1, a2);
//			ResultSet res = st.executeQuery();
//			if(res.next()){
//				Route r = new Route (res.getString("Airline"),
//						res.getInt("Airline_ID"),
//						res.getString("Source_airport"),
//						res.getInt("Source_airport_ID"),
//						res.getString("Destination_airport"),
//						res.getInt("Destination_airport_ID"),
//						res.getString("Codeshare"),
//						res.getInt("stops"),
//						res.getString("Equipment"));
//			             return true;
//			} else {
//				       return false;
//			}
//		}catch(SQLException e ){
//			e.printStackTrace();
//			return false;
//		}
//	}
}
