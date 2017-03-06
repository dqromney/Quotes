package com.dqr.factory;

/**
 * Web Service Interface.
 * <p>
 * Created by dqromney on 2/20/17.
 */
public interface Webservice {
    public static final String API_KEY = "iDys-FbJZTMWF56LXBie";

    public static final String WIKI_EOD_FILE_V3 = "WIKI_EOD_FILE_V3";
    public static final String WIKI_EOD_V3 = "WIKI_EOD_V3";
    public static final String QUANDL_DATABASE_V3 = "QUANDL_DATABASE_V3";
    public static final String QUANDL_DATATABLE_V3 = "QUANDL_DATATABLE_V3";

    String getSource();

    String getApi();

    String getVersion();

    String getDataset();

    String getUrl();
}
