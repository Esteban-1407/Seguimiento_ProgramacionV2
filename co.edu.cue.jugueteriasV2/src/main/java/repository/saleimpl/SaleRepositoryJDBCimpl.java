package repository.saleimpl;

import config.DatabaseConnection;
import model.*;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryJDBCimpl implements Repository<sale> {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance();
    }
    private sale createsale(ResultSet resultSet) throws SQLException {
        sale s = new sale();
        s.setId(resultSet.getInt("id"));
        s.setCliente(new cliente(
                resultSet.getInt("id_cliente"),
                resultSet.getString("cliente_name"),
                resultSet.getString("cliente_IDnumber"),
                resultSet.getDate("cliente_date_birth")));
        s.setEmployees(new employees(
                resultSet.getInt("id_employ"),
                resultSet.getString("employe_user"),
                resultSet.getString("employe_password"),
                resultSet.getDate("employe_start_date"))
        );
        return s;
    }
    @Override
    public List<sale> list() {
        List<sale>saleList=new ArrayList<>();
        try(Statement statement=getConnection().createStatement();
            ResultSet resultSet=statement.executeQuery(
                    """
                        
                            SELECT s.*, c.name as cliente_name, c.id as cliente_id, e.user as employees_user, e.id as employees_id
                        FROM sale AS s\s
                        INNER JOIN cliente AS c ON s.id_cliente = c.id
                        INNER JOIN employees AS e
                            ON s.id_employees = e.id;
                        
                        """
            ))
        {
            while (resultSet.next()){
                sale s = createsale(resultSet);
                saleList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleList;
    }

    @Override
    public sale byId(int id) {
        sale s =null;
        try (PreparedStatement preparedStatement=getConnection()
                .prepareStatement(""" 
                                      SELECT s.*, c.name as cliente_name, c.id as cliente_id, e.user as employees_user, e.id as employees_id
                        FROM sale AS s
                        INNER JOIN cliente AS c ON s.id_cliente = c.id
                        INNER JOIN employees AS e
                            ON s.id_employees = e.id
                            where s.id = ?
                            
                                    """)
        ) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                s =createsale(resultSet);
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return s;
    }


    @Override
    public void save(sale sale) {
        try (PreparedStatement pst = getConnection()
                .prepareStatement("""
                                          INSERT INTO sale(id_cliente,id_employees) values (?,?)
                                          """)


        ){
            pst.setInt(1, sale.getCliente().getId());
            pst.setInt(2,sale.getEmployees().getId());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(int id) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                      DELETE FROM sale where id=?
                                      """)
        ){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(sale sale) {
        try(PreparedStatement preparedStatement = getConnection()
                .prepareStatement("""
                                    UPDATE sale SET id_cliente = ?, id_employees = ?, WHERE id = ?;
                                      """
                )
        ){
            preparedStatement.setInt(1,sale.getId());
            preparedStatement.setInt(2, sale.getCliente().getId());
            preparedStatement.setDouble(3, sale.getCliente().getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }
}
