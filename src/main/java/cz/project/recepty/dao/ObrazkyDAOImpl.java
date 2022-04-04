package cz.project.recepty.dao;

import cz.project.recepty.beans.Obrazek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.Statement;

/**
 *
 * Třída zajišťující spojení mezi tabulkou pictures
 */
public class ObrazkyDAOImpl implements ObrazkyDAO {

    @Resource(lookup = "java:app/myDS")
    private DataSource dataSource;

    /**
     * Uloží předaný objekt recept a v závislosti na hodnotě id vytvoří nový
     * záznam, nebo změní stávající
     *
     * @param recept
     * @return boolean podle toho jestli se uložení povedlo či nikoliv
     */
    @Override
    public long save(Obrazek obr) {
        final String insertSql = "insert into picture (id, path) values (? ,?)";
        final String updateSql = "update picture set id = ?, path = ?";
        long result = 0L;
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            if (obr.getId() != 0L) {
                statement = connect.prepareStatement(updateSql);
                statement.setLong(1, obr.getId());
                statement.setString(2, obr.getPath());
            } else {
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, obr.getId());
                statement.setString(2, obr.getPath());
            }
            statement.execute();

            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                     result =  generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Saving picture failed, no ID obtained.");
                }
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
     *
     * @param id
     * @return Recept
     */
    @Override
    public Obrazek getPicture(long id) {
        Obrazek obr = null;
        final String sql = "select * from picture where id = ?";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                if (result.first()) {
                    final long idKey = result.getLong("id");
                    final String path = result.getString("path");
                    obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
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
        return obr;
    }

    /**
     * Vrátí všechny existující záznamy z tabulky picture
     *
     * @return List<Obrazek>
     */
    @Override
    public List<Obrazek> getPictures() {
        final List<Obrazek> pictures = new ArrayList<>();
        final String sql = "select * from picture";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                while (result.next()) {
                    final long idKey = result.getLong("id");
                    final String path = result.getString("path");
                    var obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
                    pictures.add(obr);
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
        return pictures;
    }
}
