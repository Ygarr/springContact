package com.borrowed.contact.dao.impl;

import com.borrowed.contact.dao.ContactDAO;
import com.borrowed.contact.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ContactDAOImpl() {

    }

    @Override
    public void saveOrUpdate(User user) {
        if (user.getId() > 0) {
            // update
            String sql = "UPDATE contact SET name=?, email=?, address=?, "
                    + "telephone=? WHERE contact_id=?";
            jdbcTemplate.update(sql, user.getName(), user.getEmail(),
                    user.getAddress(), user.getTelephone(), user.getId());
        } else {
            // insert
            String sql = "INSERT INTO contact (name, email, address, telephone)"
                    + " VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getName(), user.getEmail(),
                    user.getAddress(), user.getTelephone());
        }

    }

    @Override
    public void delete(int contactId) {
        String sql = "DELETE FROM contact WHERE contact_id=?";
        jdbcTemplate.update(sql, contactId);
    }

    @Override
    public List<User> list() {
        String sql = "SELECT * FROM contact";
        List<User> listUser = jdbcTemplate.query(sql, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User aUser = new User();

                aUser.setId(rs.getInt("contact_id"));
                aUser.setName(rs.getString("name"));
                aUser.setEmail(rs.getString("email"));
                aUser.setAddress(rs.getString("address"));
                aUser.setTelephone(rs.getString("telephone"));

                return aUser;
            }

        });

        return listUser;
    }

    @Override
    public User get(int contactId) {
        String sql = "SELECT * FROM contact WHERE contact_id=" + contactId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

            @Override
            public User extractData(ResultSet rs) throws SQLException,
                    DataAccessException {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("contact_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setTelephone(rs.getString("telephone"));
                    return user;
                }

                return null;
            }

        });
    }

}
