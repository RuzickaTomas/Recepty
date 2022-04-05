package cz.project.recepty.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cz.project.recepty.beans.Recept;
import cz.project.recepty.ds.Connector;

/**
 *
 * Třída zajišťující spojení mezi tabulkou recepty
 */
public class ReceptyDAOImpl implements ReceptyDAO {

    private final MysqlDataSource dataSource = Connector.getInstance().getDataSource();

    public ReceptyDAOImpl() {
    }

    /**
     * Uloží předaný objekt recept a v závislosti na hodnotě id vytvoří nový
     * záznam, nebo změní stávající
     *
     * @param recept
     * @return boolean podle toho jestli se uložení povedlo či nikoliv
     */
    @Override
    public long save(Recept recept) {
        final String insertSql = "insert into recept (id, name, description) values (?, ?, ?)";
        final String updateSql = "update recept set id = ?, name = ?, description = ?";
        long result = 0L;
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            if (recept.getId() != null) {
                statement = connect.prepareStatement(updateSql);
                statement.setInt(1, recept.getId().intValue());
                statement.setString(2, recept.getName());
                statement.setString(3, recept.getDescription());
            } else {
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, recept.getId());
                statement.setString(2, recept.getName());
                statement.setString(3, recept.getDescription());
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
    public Recept getRecept(long id) {
        Recept recept = null;
        final String sql = "select * from recept where id = ?";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                if (result.first()) {
                    final long idKey = result.getLong("id");
                    final String name = result.getString("name");
                    final String description = result.getString("description");
                    recept = new Recept(idKey, name, description);
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
     *
     * @return List<Recept>
     */
    @Override
    public List<Recept> getRecepts() {
        final List<Recept> recepts = new ArrayList<>();
        final String sql = "select * from recept";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            if (statement.execute()) {
                ResultSet result = statement.getResultSet();
                while (result.next()) {
                    final long idKey = result.getLong("id");
                    final String name = result.getString("name");
                    final String description = result.getString("description");
                    recepts.add(new Recept(idKey, name, description));
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

    @Override
    public void remove(long id) {
        Recept obr = null;
        final String sql = "delete from recept where id = ?";
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

}
