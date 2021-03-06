package cz.project.recepty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cz.project.recepty.beans.Recept;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.transaction.Transactional;

/**
 *
 * Třída zajišťující spojení mezi tabulkou recepty
 */
@Stateless
public class ReceptyDAOImpl implements ReceptyDAO {

    private DataSource dataSource;

    private static final String TABLE_NAME = "recept";
    
    @Resource(lookup = "java:global/recepty/MyDS")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
        final String insertSql = "insert into "+TABLE_NAME+" (id, name, description, kategorie_id) values (?, ?, ?, ?)";
        final String updateSql = "update "+TABLE_NAME+" set name = ?, description = ?, kategorie_id = ? where id = ? ";
        long result = 0L;
        boolean update = false;
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            if (recept.getId() != null) {
                update = true;
                statement = connect.prepareStatement(updateSql, Statement.NO_GENERATED_KEYS);
                statement.setInt(4, recept.getId().intValue());
                statement.setString(1, recept.getName());
                statement.setString(2, recept.getDescription());
                statement.setLong(3, recept.getKategorieId());
                result = recept.getId();
            } else {
                update = false;
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, recept.getId());
                statement.setString(2, recept.getName());
                statement.setString(3, recept.getDescription());
                statement.setLong(4, recept.getKategorieId());
            }
            statement.executeUpdate();
            if (!update) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    result = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Saving recept failed, no ID obtained.");
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
        final String sql = "select * from "+TABLE_NAME+" where id = ?";
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
                    final Long kategorieId = result.getLong("kategorie_id");                   
                    recept = new Recept(idKey, name, description, kategorieId);
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
     * @return List nebo prázdný List
     */
    @Override
    @Transactional
    public List<Recept> getRecepts() {
        final List<Recept> recepts = new ArrayList<>();
        final String sql = "select * from "+TABLE_NAME+"";
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
                    final String description = result.getString("description");
                    final Long kategorieId = result.getLong("kategorie_id");
                    recepts.add(new Recept(idKey, name, description, kategorieId));
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

    /**
     * Odstraní recept podle id
     *
     * @param id
     */
    @Override
    public void remove(long id) {
        Recept obr = null;
        final String sql = "delete from "+TABLE_NAME+" where id = ?";
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
