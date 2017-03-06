package com.dqr.factory;

import static com.dqr.factory.Webservice.QUANDL_DATABASE_V3;
import static com.dqr.factory.Webservice.QUANDL_DATATABLE_V3;
import static com.dqr.factory.Webservice.WIKI_EOD_FILE_V3;
import static com.dqr.factory.Webservice.WIKI_EOD_V3;

/**
 * Data Source Factory.
 *
 * Created by dqromney on 2/20/17.
 */
public class WebserviceFactory {

    public Webservice getWebservice(String pWebService) {
        if (pWebService == null) {
            return null;
        }
        if (pWebService.equalsIgnoreCase(WIKI_EOD_V3)) {
            return new WikiEodV3();
        }
        if (pWebService.equalsIgnoreCase(WIKI_EOD_FILE_V3)) {
            return new WikiFileEodV3();
        }
        if (pWebService.equalsIgnoreCase(QUANDL_DATABASE_V3)) {
            return new QuandlDatabasesV3();
        }
        if (pWebService.equalsIgnoreCase(QUANDL_DATATABLE_V3)) {
            return new QuandlDatatableV3();
        }
        return null;
    }
}
