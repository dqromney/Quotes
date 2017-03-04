package com.dqr.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
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
    Long sharedId;
    String datasetCode;
    String databaseCode;
    String name;
    String description;
    Date refreshedAt;
    Date newestAvailableDate;
    Date oldestAvailableDate;
    Collection columnNames;
    String frequency;
    String type;
    Boolean premium;
    Integer limit;
    String transform;
    Integer columnIndex;
    Date startDate;
    Date endDate;
}
/*
 "id": 9775409,
        "dataset_code": "AAPL",
        "database_code": "WIKI",
        "name": "Apple Inc (AAPL) Prices, Dividends, Splits and Trading Volume",
        "description": "End of day open, high, low, close and volume, dividends and splits, and split/dividend adjusted open, high, low close and volume for Apple Inc. (AAPL). Ex-Dividend is non-zero on ex-dividend dates. Split Ratio is 1 on non-split dates. Adjusted prices are calculated per CRSP (<a href=\"http://www.crsp.com/products/documentation/crsp-calculations\" rel=\"nofollow\" target=\"blank\">www.crsp.com/products/documentation/crsp-calculations</a>)\r\n\r\n<p>This data is in the public domain. You may copy, distribute, disseminate or include the data in other products for commercial and/or noncommercial purposes.</p>\r\n<p>This data is part of Quandl's Wiki initiative to get financial data permanently into the public domain. Quandl relies on users like you to flag errors and provide data where data is wrong or missing. Get involved: <a href=\"mailto:connect@quandl.com\" rel=\"nofollow\" target=\"blank\">connect@quandl.com</a>",
        "refreshed_at": "2017-03-02T22:51:31.791Z",
        "newest_available_date": "2017-03-02",
        "oldest_available_date": "1980-12-12",
        "column_names": [
            "Date",
            "Open",
            "High",
            "Low",
            "Close",
            "Volume",
            "Ex-Dividend",
            "Split Ratio",
            "Adj. Open",
            "Adj. High",
            "Adj. Low",
            "Adj. Close",
            "Adj. Volume"
        ],
        "frequency": "daily",
        "type": "Time Series",
        "premium": false,
        "limit": 1,
        "transform": null,
        "column_index": null,
        "start_date": "1980-12-12",
        "end_date": "2017-03-02",
 */