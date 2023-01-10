package com.javadevjournal;

import com.javadevjournal.customer.data.CustomerModel;
import com.javadevjournal.customer.repo.CustomerRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class TheReader extends JdbcCursorItemReader<CustomerModel> implements ItemReader<CustomerModel> {

    public TheReader(@Autowired DataSource oracleDataSource) {
        setDataSource(oracleDataSource);
        System.out.println("hhhh");
        setSql("SELECT * from dataset100k");
        setFetchSize(100);
        setRowMapper(new CustomerRowMapper());
    }

    public class CustomerRowMapper implements RowMapper<CustomerModel> {
        @Override
        public CustomerModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            System.out.println("I AM HEEERE");
            CustomerModel customerModel = new CustomerModel();
            //customerModel.setId(rs.getInt("ID"));
            customerModel.setUser_id(rs.getInt("USER_ID"));
            customerModel.setItem_id(rs.getInt("ITEM_ID"));
            customerModel.setRating(rs.getInt("RATING"));
            customerModel.setTime_stamp(rs.getInt("TIMESTAMP"));

            return customerModel;
        }
    }
}
