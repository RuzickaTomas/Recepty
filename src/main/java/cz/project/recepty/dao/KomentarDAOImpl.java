/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import cz.project.recepty.beans.Komentar;

/**
 *
 * CRUD operace pro tabulku comment
 *
 * @author tomas
 */
@Stateless
public class KomentarDAOImpl implements KomentarDAO {

    private DataSource dataSource;

    private static final String TABLE_NAME = "comment";

    @Resource(lookup = "java:global/recepty/MyDS")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Komentar getComment(long id) {
        Komentar kom = null;
        final String sql = "select * from " + TABLE_NAME + " where id = ?";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                if (result.first()) {
                    final long idKey = result.getLong("id");
                    final String email = result.getString("email");
                    final String text = result.getString("text");
                    final long receptId = result.getLong("recept_id");

                    kom = new Komentar();
                    kom.setId(idKey);
                    kom.setEmail(email);
                    kom.setText(text);
                    kom.setReceptId(receptId);
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
        return kom;
    }

    @Override
    public List<Komentar> getComments() {
        final List<Komentar> comments = new ArrayList<>();
        final String sql = "select * from " + TABLE_NAME + "";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            connect.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            statement = connect.prepareStatement(sql);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                while (result.next()) {
                    final long idKey = result.getLong("id");
                    final String email = result.getString("email");
                    final String text = result.getString("text");
                    final long receptId = result.getLong("recept_id");
                    
                    Komentar komentar = new Komentar();
                    komentar.setId(idKey);
                    komentar.setEmail(email);
                    komentar.setText(text);
                    komentar.setReceptId(receptId);
                    comments.add(komentar);
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
        return comments;
    }

    @Override
    public long save(Komentar komentar)  {
        final String insertSql = "insert into " + TABLE_NAME + " (id, email, text, recept_id) values (?, ?, ?, ?)";
        final String updateSql = "update " + TABLE_NAME + " set text = ?, email = ?, recept_id = ? where id = ?";
        long result = 0L;
         boolean update = false;
        PreparedStatement statement = null;
        //vytvorime spojeni s databazi
        try ( Connection connect = dataSource.getConnection()) {
            //rekneme driveru, ze ma ocekavt zahajeni dotazu
            connect.beginRequest();
            if (komentar.getId() != null) {
                update = true;
                //vytvorime prepared statement, zajistime aby bylz prirazeny spravne hodnoty
                statement = connect.prepareStatement(updateSql);
                //prirazeni hodnot v zleva do prava
                statement.setLong(4, komentar.getId());
                statement.setLong(3, komentar.getReceptId());
                statement.setString(2, komentar.getEmail());
                statement.setString(1, komentar.getText());
            } else {
                update = false;
                //Statement.RETURN_GENERATED_KEYS - chceme aby nam result set vratil vygenerovane id
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(4, komentar.getReceptId());
                statement.setString(3, komentar.getText());
                statement.setString(2, komentar.getEmail());
                statement.setObject(1, komentar.getId());
            }
            //provedeme dotaz
            statement.executeUpdate();
            //projdeme resultSet pro vygenerovane klice    
              if (!update) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Saving "+TABLE_NAME+" failed, no ID obtained.");
                }

            }
            //pokud nastala necekana chyba ve spojeni chytime vyjimku
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //nakonec uzavirame statement
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean remove(long id) {
        final String sql = "delete from "+TABLE_NAME+" where id = ?";
        boolean deleted = false;
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            if (statement.execute()) {
                deleted = true;
                ResultSet result = statement.getResultSet();
                result.deleteRow();
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
        return deleted;
    }

	@Override
	public List<Komentar> getCommentsByRecept(long id) {
		 final List<Komentar> comments = new ArrayList<>();
	        final String sql = "select * from " + TABLE_NAME + " where recept_id = ?";
	        PreparedStatement statement = null;
	        try ( Connection connect = dataSource.getConnection()) {
	            connect.beginRequest();
	            connect.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	            statement = connect.prepareStatement(sql);
	            statement.setLong(1, id);
	            if (statement.execute()) {
	                ResultSet result = statement.getResultSet();
	                while (result.next()) {
	                    final long idKey = result.getLong("id");
	                    final String email = result.getString("email");
	                    final String text = result.getString("text");
	                    final long receptId = result.getLong("recept_id");
	                    
	                    Komentar komentar = new Komentar();
	                    komentar.setId(idKey);
	                    komentar.setEmail(email);
	                    komentar.setText(text);
	                    komentar.setReceptId(receptId);
	                    comments.add(komentar);
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
	        return comments;
	}


}
