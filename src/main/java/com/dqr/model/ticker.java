package com.dqr.model;

import lombok.Builder;

import java.util.Date;

/**
 * Ticker model class.
 *
 * Created by dqromney on 3/2/17.
 */
@lombok.Data
@Builder
public class ticker {
    Long id;
    String symbol;
    String exchange;
    Date lastUpdate;
}
