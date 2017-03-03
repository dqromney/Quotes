package com.dqr.jobs.eodJob;

import javax.batch.api.chunk.ItemProcessor;

/**
 * Data Item processor.
 *
 * Created by dqromney on 3/2/17.
 */
public class DataItemProcessor implements ItemProcessor {

    /**
     * The processItem method is part of a chunk
     * step. It accepts an input item from an
     * item reader and returns an item that gets
     * passed onto the item writer. Returning null
     * indicates that the item should not be continued
     * to be processed.  This effectively enables processItem
     * to filter out unwanted input items.
     *
     * @param item specifies the input item to process.
     * @return output item to write.
     * @throws Exception thrown for any errors.
     */
    @Override
    public Object processItem(Object item) throws Exception {
        return null;
    }
}
