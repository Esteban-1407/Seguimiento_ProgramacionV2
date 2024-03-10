package repository.sale_detailsimpl;

import config.DatabaseConnection;
import model.*;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDetailsRepositoryJDBCimpl implements Repository<sale_details> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    private sale_details createDetails(ResultSet resultSet) throws SQLException {
        sale_details st = new sale_details();
        st.setId(resultSet.getInt("id"));
        sale sale = new sale();
        sale.setId(resultSet.getInt("sale_id"));
        st.setSale(sale);
        toy toy = new toy();
        toy.setId(resultSet.getInt("toy_id"));
        toy.setName(resultSet.getString("toy_name"));
        toy.setPrice(resultSet.getInt("toy_price"));
        st.setToy(toy);
        st.setCantidad(resultSet.getInt("cantidad"));
        st.setPrice(resultSet.getInt("price"));

        return st;
    }
    @Override
    public List<sale_details> list() {
        List<sale_details> st = new ArrayList<>();
        try(Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                """
                         SELECT st.*, s. id AS sale_id, t.id AS toy_id, t.name AS toy_name, t.price AS toy_price
                         FROM sale_details AS st
                         INNER JOIN sale AS s ON st.id_sale = s.id
                         INNER JOIN toy AS t ON st.id_toy = t.id;

"""
        )) {
            while (resultSet.next()){
                sale_details std = createDetails(resultSet);
                st.add(std);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return st;
    }

    @Override
    public sale_details byId(int id) {
        sale_details sd =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                      SELECT sd.*, s.id AS sale_id, t.id AS toy_id
                        FROM sale_details AS sd
                        INNER JOIN sale AS s ON sd.id_sale = s.id
                        INNER JOIN toy AS t
                            ON sd.id_toy = t.id
                            where sd.id = ?
                            
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                sd = createDetails(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sd;
    }

    @Override
    public void save(sale_details saleDetails) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO sale_details(id_sale, id_toy,cantidad,price) values (?,?,?,?)
                                          """)


        ){
            pst.setInt(1, saleDetails.getSale().getId());
            pst.setInt(2,saleDetails.getToy().getId());
            pst.setInt(3,saleDetails.getCantidad());
            pst.setInt(4,saleDetails.getCantidad());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM sale_details where id=?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(sale_details saleDetails) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                    UPDATE sale_details SET id_sale = ?, id_toy = ?, cantidad = ?, price = ? WHERE id = ?;
                                      """
                )
        ){
            preparedStatement.setInt(1,saleDetails.getId());
            preparedStatement.setInt(2, saleDetails.getSale().getId());
            preparedStatement.setInt(3, saleDetails.getToy().getId());
            preparedStatement.setInt(4,saleDetails.getCantidad());
            preparedStatement.setInt(5,saleDetails.getPrice());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
}
