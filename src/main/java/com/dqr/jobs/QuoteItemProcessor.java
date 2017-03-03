package com.dqr.jobs;

import javax.batch.api.chunk.ItemProcessor;

/**
 * Quote Item Processor.
 *
 * Created by dqromney on 3/2/17.
 */
public class QuoteItemProcessor implements ItemProcessor {
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
