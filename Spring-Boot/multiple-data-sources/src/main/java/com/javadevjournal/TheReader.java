package com.javadevjournal;

import com.javadevjournal.mainSource.data.MainSourceModel;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TheReader extends JdbcCursorItemReader<MainSourceModel> implements ItemReader<MainSourceModel> {
    public final int fetchSize = 1000;
    public TheReader(@Autowired DataSource oracleDataSource) {
        setDataSource(oracleDataSource);
        setSql("SELECT * from dataset100k");
        setFetchSize(fetchSize);
        setRowMapper(new CustomerRowMapper());
    }

    public class CustomerRowMapper implements RowMapper<MainSourceModel> {
        @Override
        public MainSourceModel mapRow(ResultSet rs, int rowNum) throws SQLException {
//            System.out.println("I AM HEEERE");
            MainSourceModel mainSourceModel = new MainSourceModel();
            //customerModel.setId(rs.getInt("ID"));
            mainSourceModel.setUser_id(rs.getInt("USER_ID"));
            mainSourceModel.setItem_id(rs.getInt("ITEM_ID"));
            mainSourceModel.setRating(rs.getInt("RATING"));
            mainSourceModel.setTime_stamp(rs.getInt("TIMESTAMP"));

            return mainSourceModel;
        }
    }
}
