package cz.project.recepty.dao;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sql.DataSource;

import cz.project.recepty.beans.Recept;


/**
 * 
 * Třída zajišťující spojení mezi tabulkou recepty 
 */
public class ReceptyDAOImpl implements ReceptyDAO {

	@Resource(lookup = "java:app/myDS")
	private DataSource dataSource;

        /**
         * Uloží předaný objekt recept a v závislosti na hodnotě id vytvoří nový záznam, nebo změní stávající
         * @param recept
         * @return boolean podle toho jestli se uložení povedlo či nikoliv
         */
	@Override
	public boolean save(Recept recept) {
		final String insertSql = "insert into recept (id, name, description, pictures) values (? , ?, ?, ?)";
		final String updateSql = "update recept set id = ?, name = ?, description = ?, pictures = ?";
		boolean result = false;
		PreparedStatement statement = null;
		try (Connection connect = dataSource.getConnection()) {
			connect.beginRequest();
			if (recept.getId() != null) {
				statement = connect.prepareStatement(updateSql);
				statement.setLong(1, recept.getId());
				statement.setString(2, recept.getName());
				statement.setString(3, recept.getDescription());
				String[] arr = recept.getPictures().stream().map(URL::toString).collect(Collectors.toList())
						.toArray(new String[0]);
				Array varcharArr = connect.createArrayOf("VARCHAR", arr);
				statement.setArray(4, varcharArr);
				result = statement.executeUpdate() == 1;
			} else {
				statement = connect.prepareStatement(insertSql);
				statement.setLong(1, recept.getId());
				statement.setString(2, recept.getName());
				statement.setString(3, recept.getDescription());
				String[] arr = recept.getPictures().stream().map(URL::toString).collect(Collectors.toList())
						.toArray(new String[0]);
				Array varcharArr = connect.createArrayOf("VARCHAR", arr);
				statement.setArray(4, varcharArr);
				result = statement.executeUpdate() == 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

        /**
         * Vrátí záznam podle id
         * @param id
         * @return Recept
         */
	@Override
	public Recept getRecept(long id) {
		Recept recept = null;
		final String sql = "select * from recept where id = ?";
		PreparedStatement statement = null;
		try (Connection connect = dataSource.getConnection()) {
			connect.beginRequest();
			statement = connect.prepareStatement(sql);
			statement.setLong(1, id);
			if (statement.execute()) {
				ResultSet result = statement.getResultSet();
				if (result.first()) {
					final long idKey = result.getLong("id");
					final String name = result.getString("name");
					final String description = result.getString("desription");
					String[] pictures = (String[]) result.getArray("pictures").getArray();
					final List<URL> picturesRes = new ArrayList<>();
					try {
						for (int i = 0; i < pictures.length; i++) {
							picturesRes.add(new URL(pictures[i]));
						}
						recept = new Recept(idKey, name, description, picturesRes);
					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
			}
			connect.endRequest();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recept;
	}

        
        /**
         * Vrátí všechny existující záznamy z tabulky recept
         * @return List<Recept>
         */
	@Override
	public List<Recept> getRecepts() {
		final List<Recept> recepts = new ArrayList<>();
		final String sql = "select * from recept";
		PreparedStatement statement = null;
		try (Connection connect = dataSource.getConnection()) {
			connect.beginRequest();
			statement = connect.prepareStatement(sql);
			if (statement.execute()) {
				ResultSet result = statement.getResultSet();
				while (result.next()) {
					final long idKey = result.getLong("id");
					final String name = result.getString("name");
					final String description = result.getString("desription");
					String[] pictures = (String[]) result.getArray("pictures").getArray();
					final List<URL> picturesRes = new ArrayList<>();
					try {
						for (int i = 0; i < pictures.length; i++) {
							picturesRes.add(new URL(pictures[i]));
						}
						recepts.add(new Recept(idKey, name, description, picturesRes));

					} catch (MalformedURLException ex) {
						ex.printStackTrace();
					}
				}
			}
			connect.endRequest();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return recepts;
	}
}
