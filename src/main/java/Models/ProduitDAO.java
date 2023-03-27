package Models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit>{

    public ProduitDAO(){
        super();
    }

    @Override
    public void save(Produit object) throws SQLException {
        String req = "INSERT INTO produit (nom , prix) values (?,?)";

        this.preparedStatement = this.connection.prepareStatement(req);
        this.preparedStatement.setString(1,object.getNom());
        this.preparedStatement.setFloat(2 , object.getPrix());

        this.preparedStatement.execute();

    }

    @Override
    public Produit read(int id) throws SQLException {
        String req = "SELECT * FROM Produit WHERE id_produit = ?";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1,id);
        this.resultSet = this.preparedStatement.executeQuery();

        Produit result = null;
        if(resultSet.next()){
            result = new Produit(
                    resultSet.getInt("id_produit"),
                    resultSet.getString("nom"),
                    resultSet.getFloat("prix")
            );
        }
        return result;

    }

    @Override
    public void update(Produit object) throws SQLException {
        String req = "UPDATE Produit SET nom = ? , prix = ?  WHERE id_produit = ?";
        ProduitDAO c = new ProduitDAO();
        if (c.getOne(object.getId_produit()) == null) {
            //create a static method to check the existence of a client to use in other CRUD methods
            throw new SQLException("Produit With id : " + object.getId_produit() + " is not found");
        } else {
            this.preparedStatement = this.connection.prepareStatement(req);
            this.preparedStatement.setString(1, object.getNom());
            this.preparedStatement.setFloat(2, object.getPrix());
            this.preparedStatement.setInt(3, object.getId_produit());

            this.preparedStatement.execute();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
            String req = "DELETE FROM Produit WHERE id_produit = ?";
            MarketAdminDAO c = new MarketAdminDAO();

            if (c.getOne(id) == null){
                throw new SQLException("Produit With id : "+ id+" is not found");
            }

            this.preparedStatement = this.connection.prepareStatement(req);
            this.preparedStatement.setInt(1,id);
            this.preparedStatement.execute();

        }

    @Override
    public Produit getOne(int id) throws SQLException {
        String req = "SELECT * FROM Produit WHERE id_produit = ?";

        this.preparedStatement = this.connection.prepareStatement(req);

        this.preparedStatement.setLong(1,id);
        this.resultSet = this.preparedStatement.executeQuery();

        Produit result = null;
        if(resultSet.next()){
            result = new Produit(
                    resultSet.getInt("id_produit"),
                    resultSet.getString("nom"),
                    resultSet.getFloat("prix")
            );
        }
        return result;
    }

    @Override
    public List<Produit> getAll() throws SQLException {
        List<Produit> SQLCleint = new ArrayList<Produit>();
        String req = "SELECT * FROM MarketAdmin";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery(req);

        while (this.resultSet.next()){
            SQLCleint.add(
                    new Produit(
                            this.resultSet.getInt(1),
                            this.resultSet.getString(2),
                            this.resultSet.getFloat(3)
                    )
            );
        }
        return SQLCleint;
    }
}
