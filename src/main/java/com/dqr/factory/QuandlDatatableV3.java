package com.dqr.factory;

/**
 * Quandl Datatable (V3).
 *
 * https://www.quandl.com/api/v3/datatables/INQ/EE.csv?qopts.columns=isin
 *
 * Created by dqromney on 2/21/17.
 */
public class QuandlDatatableV3 implements Webservice {
    @Override
    public String getSource() {
        return "https://www.quandl.com/";
    }

    @Override
    public String getApi() {
        return "api/";
    }

    @Override
    public String getVersion() {
        return "v3/";
    }

    @Override
    public String getDataset() {
        return "datatables/";
    }

    @Override
    public String getUrl() {
        return new StringBuilder()
                .append(getSource())
                .append(getApi())
                .append(getVersion())
                .append(getDataset()).toString();
    }
}
