/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.project.recepty.dao;

import cz.project.recepty.beans.Kategorie;
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

/**
 *
 * CRUD operace pro tabulku category
 *
 * @author tomas
 */
@Stateless
public class KategorieDAOImpl implements KategorieDAO {

    private DataSource dataSource;

    private static final String TABLE_NAME = "category";

    @Resource(lookup = "java:global/recepty/MyDS")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Kategorie getCategory(long id) {
        Kategorie kat = null;
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
                    final String name = result.getString("path");

                    kat = new Kategorie();
                    kat.setId(idKey);
                    kat.setName(name);
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
        return kat;
    }

    @Override
    public List<Kategorie> getCategories() {
        final List<Kategorie> categories = new ArrayList<>();
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
                    final String name = result.getString("name");
                    
                    Kategorie kategorie = new Kategorie();
                    kategorie.setId(idKey);
                    kategorie.setName(name);
                    categories.add(kategorie);
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
        return categories;
    }

    @Override
    public long save(Kategorie kategorie) {
        final String insertSql = "insert into " + TABLE_NAME + " (id, name) values (?, ?)";
        final String updateSql = "update " + TABLE_NAME + " set name = ? where id = ?";
        long result = 0L;
        PreparedStatement statement = null;
        //vytvorime spojeni s databazi
        try ( Connection connect = dataSource.getConnection()) {
            //rekneme driveru, ze ma ocekavt zahajeni dotazu
            connect.beginRequest();
            if (kategorie.getId() != null) {
                //vytvorime prepared statement, zajistime aby bylz prirazeny spravne hodnoty
                statement = connect.prepareStatement(updateSql);
                //prirazeni hodnot v zleva do prava
                statement.setObject(1, kategorie.getId());
                statement.setString(2, kategorie.getName());
            } else {
                //Statement.RETURN_GENERATED_KEYS - chceme aby nam result set vratil vygenerovane id
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, kategorie.getName());
                statement.setLong(2, kategorie.getId());
            }
            //provedeme dotaz
            statement.execute();
            //projdeme resultSet pro vygenerovane klice    
            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                //pokud obsahuje id
                if (generatedKeys.next()) {
                    //dostaneme id
                    result = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Saving " + TABLE_NAME + " failed, no ID obtained.");
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
        Kategorie kat = null;
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


}
