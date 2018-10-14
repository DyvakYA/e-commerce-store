package model.dao.jdbc;

import model.dao.UserDao;
import model.dao.exception.DAOException;
import model.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static model.constants.AttributesHolder.USER_AUTHENTICATE_ATTRIBUTE;
import static model.constants.ErrorMsgHolder.SQL_EXCEPTION;

/**
 * This class is the implementation of User entity DAO
 *
 * @author dyvakyurii@gmail.com
 */
public class JdbcUserDao implements UserDao {

    private static final String SELECT_FROM_USERS_WHERE_USER_ID = "SELECT * FROM users WHERE user_id=?";
    private static final String SELECT_FROM_USERS = "SELECT * FROM users";
    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE lower(email) = ?";
    private static final String CREATE_USER_QUERY = "INSERT INTO users (user_name, email, password, isAdmin ,isBlocked)  " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_QUERY = "UPDATE users " +
            "SET user_name=?, email=?, password=?, isAdmin=?, isBlocked=? WHERE user_id=?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE user_id=?";
    private static final String SELECT_USERS_FROM_USER_ORDERS_UNIQUE = "" +
            "SELECT DISTINCT users.user_id, users.user_name, users.email, " +
            "users.password, users.isAdmin, users.isBlocked\n" +
            "FROM users\n" +
            "INNER JOIN user_orders\n" +
            "ON users.user_id=user_orders.user_id";
    private static final String SELECT_AUTHENTICATE_FROM_USERS_WHERE_USER_ID = "SELECT password FROM users WHERE user_id=?";

    ResultSetExtractor resultSetExtractor = new ResultSetExtractor();
    private Connection connection;

    JdbcUserDao(Connection connection) {
        this.connection = connection;
    }


    public Optional<User> findById(int id) {
        Optional<User> user = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(SELECT_FROM_USERS_WHERE_USER_ID)) {
            query.setInt(1, id);
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(resultSetExtractor.getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return user;
    }


    @Override
    public String getPasswordForUser(User user) {
        String PasswordHash = null;
        try (PreparedStatement query = connection.prepareStatement(SELECT_AUTHENTICATE_FROM_USERS_WHERE_USER_ID)) {
            query.setInt(1, user.getId());
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                PasswordHash = resultSet.getString(USER_AUTHENTICATE_ATTRIBUTE);
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return PasswordHash;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> user = Optional.empty();
        try (PreparedStatement query = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            query.setString(1, email.toLowerCase());
            ResultSet resultSet = query.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(resultSetExtractor.getUserFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
        return user;
    }


    @Override
    public void create(User user) {
        try (PreparedStatement query = connection.prepareStatement(CREATE_USER_QUERY)) {
            query.setString(1, user.getName());
            query.setString(2, user.getEmail());
            query.setString(3, user.getPassword());
            query.setBoolean(4, user.isAdmin());
            query.setBoolean(5, user.isBlocked());
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User findOne(long id) {
        return null;
    }

    public void update(User user, int id) {
        try (PreparedStatement query = connection.prepareStatement(UPDATE_USER_QUERY)) {
            query.setString(1, user.getName());
            query.setString(2, user.getEmail());
            query.setString(3, String.valueOf(user.getPassword()));
            query.setBoolean(4, user.isAdmin());
            query.setBoolean(5, user.isBlocked());
            query.setString(6, String.valueOf(id));
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }

    @Override
    public List<User> findAllUsersWithOrders() {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public void delete(int id) {
        try (PreparedStatement query = connection.prepareStatement(DELETE_USER_QUERY)) {
            query.setInt(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        }
    }
}
