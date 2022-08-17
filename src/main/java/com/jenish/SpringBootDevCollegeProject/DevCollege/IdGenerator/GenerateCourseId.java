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
                int num = rs.getInt(1) + 1;
                StringBuilder id = new StringBuilder("" + num);
                for (int i = 0; i < 4 - (String.valueOf(num).length()); i++) {
                    id.insert(0, "0");
                }
                id.insert(0, prefix);
                return id.toString();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
