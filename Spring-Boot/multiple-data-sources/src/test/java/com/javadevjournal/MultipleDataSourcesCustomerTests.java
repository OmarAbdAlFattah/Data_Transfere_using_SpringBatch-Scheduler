package com.javadevjournal;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import com.javadevjournal.mainSource.data.MainSourceModel;
import com.javadevjournal.mainSource.repo.MainSourceRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultipleDataSourcesCustomerTests {


    @Autowired
    private MainSourceRepository mainSourceRepository;

    @Test
    @Transactional("mysqlTransactionManager")
    public void create_check_row() {

        MainSourceModel mainSourceModel = new MainSourceModel(999,999,999,999);
        mainSourceModel = mainSourceRepository.save(mainSourceModel);

        assertNotNull(mainSourceRepository.findById(mainSourceModel.getId()));
        assertEquals(mainSourceRepository.findById(mainSourceModel.getId()).get().getTime_stamp() ,999);
    }


}
