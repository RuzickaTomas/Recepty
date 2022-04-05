package cz.project.recepty.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import cz.project.recepty.beans.Obrazek;
import cz.project.recepty.ds.Connector;
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

    private final MysqlDataSource dataSource = Connector.getInstance().getDataSource();

    /**
     * Uloží předaný objekt recept a v závislosti na hodnotě id vytvoří nový
     * záznam, nebo změní stávající
     *
     * @param recept
     * @return boolean podle toho jestli se uložení povedlo či nikoliv
     */
    @Override
    public long save(Obrazek obr) {
        final String insertSql = "insert into picture (id, path, src, recept_id) values (?, ? ,?, ?)";
        final String updateSql = "update picture set id = ?, path = ?, src = ?, recept_id = ?";
        long result = 0L;
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            if (obr.getId() != null) {
                statement = connect.prepareStatement(updateSql);
                statement.setLong(1, obr.getId());
                statement.setString(2, obr.getPath());
                statement.setString(3, obr.getSrc());
                statement.setLong(4, obr.getRecept_id());
            } else {
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, obr.getId());
                statement.setString(2, obr.getPath());
                statement.setString(3, obr.getSrc());
                statement.setLong(4, obr.getRecept_id());
            }
            statement.execute();

            try ( ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
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
                    final String src = result.getString("src");
                    obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
                    obr.setSrc(src);
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
                    final String src = result.getString("src");

                    var obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
                    obr.setSrc(src);
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

    @Override
    public void remove(long id) {
        Obrazek obr = null;
        final String sql = "delete from picture where id = ?";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            if (statement.execute()) {
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
    }

    @Override
    public Obrazek getPictureByReceptId(long id) {
        Obrazek obr = null;
        final String sql = "select * from picture where recept_id = ?";
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
                    final String src = result.getString("src");

                    obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
                    obr.setSrc(src);
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
}
