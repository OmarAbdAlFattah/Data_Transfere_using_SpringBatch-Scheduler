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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TheReader extends JdbcCursorItemReader<MainSourceModel> implements ItemReader<MainSourceModel> {
    public final int fetchSize = 1000;
    public TheReader(@Autowired DataSource mysqlDataSource) {
        setDataSource(mysqlDataSource);
        setSql("SELECT * from destmodel");
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
            mainSourceModel.setTime_stamp(rs.getInt("TIME_STAMP"));
            System.out.println("This read started on " + printCurrentTime());

            return mainSourceModel;
        }
    }

    public String printCurrentTime () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        return time;
    }
}
