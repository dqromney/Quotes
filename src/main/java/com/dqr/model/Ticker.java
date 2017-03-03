package com.dqr.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Ticker model class.
 *
 * Created by dqromney on 3/2/17.
 */
@Data
@Builder
public class Ticker {
    Long id;
    String symbol;
    String exchange;
    Date lastUpdate;
}
