package com.jenish.SpringBootDevCollegeProject.DevCollege.IdGenerator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GenerateCourseId implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String prefix = "CRS";
        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select count(course_id) as Id from Course");

            if (rs.next()) {
                if(rs.getInt(1)<9) {
                    String id =(String.format("%03d", 0)+(rs.getInt(1)+1));
                    String generatedId = prefix + id;
                    return generatedId;
                }else {
                    String id = (String.format("%02d", 0)+(rs.getInt(1)+1));
                    String generatedId = prefix + id;
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
