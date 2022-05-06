package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, Citta localita) {
		final String sql = "SELECT DATA, umidita "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA)=? and localita=? "
				+ "GROUP BY data " ;

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			//assegno ai ? i parametri richiesti
			st.setInt(1, mese);
			st.setString(2,localita.getNome());
			
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(localita.getNome(), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public HashMap <String, Double> getUmidità (int mese) {
		
		final String sql = "SELECT AVG(umidita) as uMedia, localita "
				+ "from meteo.situazione "
				+ "WHERE MONTH(DATA) = ? "
				+ "GROUP BY localita" ;

		HashMap<String, Double> umidita = new HashMap<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese); //per impostare il mese
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Double media= rs.getDouble("uMedia");
				String localita= rs.getString("localita");
				umidita.put(localita, media);
			}

			conn.close();
			return umidita;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		public List<Citta> getAllCitta() {

			final String sql = "SELECT DISTINCT localita FROM situazione ORDER BY localita";

			List<Citta> result = new ArrayList<Citta>();

			try {
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);

				ResultSet rs = st.executeQuery();

				while (rs.next()) {

					Citta c = new Citta(rs.getString("localita"));
					result.add(c);
				}

				conn.close();
				return result;

			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
	}



