package cz.project.recepty.dao;

import cz.project.recepty.beans.Obrazek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Statement;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.transaction.Transactional;

/**
 *
 * Třída zajišťující spojení mezi tabulkou pictures
 */
@Stateless
public class ObrazkyDAOImpl implements ObrazkyDAO {

    private DataSource dataSource;

    @Resource(lookup = "java:global/recepty/MyDS")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Uloží předaný objekt recept a v závislosti na hodnotě id vytvoří nový
     * záznam, nebo změní stávající
     *
     * @param obr - Objekt obrazku ktery ulozime
     * @return boolean podle toho jestli se uložení povedlo či nikoliv
     */
    @Override
    public long save(Obrazek obr) {
        final String insertSql = "insert into picture (id, path, src, recept_id) values (?, ? ,?, ?)";
        final String updateSql = "update picture set id = ?, path = ?, src = ?, recept_id = ?";
        long result = 0L;
        PreparedStatement statement = null;
        //vytvorime spojeni s databazi
        try ( Connection connect = dataSource.getConnection()) {
            //rekneme driveru, ze ma ocekavt zahajeni dotazu
            connect.beginRequest();
            if (obr.getId() != null) {
                //vytvorime prepared statement, zajistime aby bylz prirazeny spravne hodnoty
                statement = connect.prepareStatement(updateSql);
                //prirazeni hodnot v zleva do prava
                statement.setLong(1, obr.getId());
                statement.setString(2, obr.getPath());
                statement.setString(3, obr.getSrc());
                statement.setLong(4, obr.getRecept_id());
            } else {
                //Statement.RETURN_GENERATED_KEYS - chceme aby nam result set vratil vygenerovane id
                statement = connect.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, obr.getId());
                statement.setString(2, obr.getPath());
                statement.setString(3, obr.getSrc());
                statement.setLong(4, obr.getRecept_id());
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
                    throw new SQLException("Saving picture failed, no ID obtained.");
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

    /**
     * Vrátí záznam podle id
     *
     * @param id - index zaznamu
     * @return Obrazek
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
                    final long receptId = result.getLong("recept_id");
                    obr = new Obrazek();
                    obr.setId(idKey);
                    obr.setPath(path);
                    obr.setSrc(src);
                    obr.setRecept_id(receptId);
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
     * @return List nebo prázdný List
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

    /**
     * Odstranime obrazek podle id
     *
     * @param id - index zaznamu
     */
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

    /**
     * Odstarani obrazek podle id
     *
     * @param id
     * @return Obrazek nebo null
     */
    @Override
    @Transactional
    public Obrazek getPictureByReceptId(long id) {
        Obrazek obr = null;
        final String sql = "select * from picture where recept_id = ?";
        PreparedStatement statement = null;
        try ( Connection connect = dataSource.getConnection()) {
            connect.beginRequest();
            statement = connect.prepareStatement(sql);
            statement.setLong(1, id);
            //if (statement.execute()) {
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                final long idKey = result.getLong("id");
                final String path = result.getString("path");
                final String src = result.getString("src");
                final long receptId = result.getLong("recept_id");

                obr = new Obrazek();
                obr.setId(idKey);
                obr.setPath(path);
                obr.setSrc(src);
                obr.setRecept_id(receptId);
            }
            //}
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
