package com.javadevjournal;

import com.javadevjournal.mainSource.repo.MainSourceRepository;
import com.javadevjournal.destination.data.DestModel;
import com.javadevjournal.destination.repo.DestRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TheWriter implements ItemWriter<DestModel> {
    private final MainSourceRepository mainSourceRepository;
    private final DestRepository destRepository;

    public TheWriter(MainSourceRepository mainSourceRepository,
                     DestRepository destRepository) {
        this.mainSourceRepository = mainSourceRepository;
        this.destRepository = destRepository;
    }

    @Override
    public void write(Chunk<? extends DestModel> chunk) throws Exception {
        for (DestModel pm :
                chunk) {
            System.out.println("This Write stated on " + printCurrentTime());
            System.out.println(
                    "MyWriter  : Writing data  :"
                    + pm.getUser_id() + " : "
                    + pm.getItem_id() + " : "
                    + pm.getRating()  + " : "
                    + pm.getTime_stamp()
            );
            destRepository.save(pm);
        }
    }

    public String printCurrentTime () {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        return time;
    }
}
