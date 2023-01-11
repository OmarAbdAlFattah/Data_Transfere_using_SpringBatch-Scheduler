package com.javadevjournal;

import com.javadevjournal.mainSource.data.MainSourceModel;
import com.javadevjournal.destination.data.DestModel;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TheProcessor implements ItemProcessor<MainSourceModel, DestModel> {

    @Override
    public DestModel process(MainSourceModel mainSourceModel) throws Exception {

        System.out.println("MyBatchProcessor : Processing data : "+ mainSourceModel);
        return new DestModel(mainSourceModel.getUser_id(), mainSourceModel.getItem_id(), mainSourceModel.getRating(), mainSourceModel.getTime_stamp());
    }
}
