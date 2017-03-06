package com.dqr.factory;

/**
 * Wiki Database list Web Service.
 *
 * Created by dqromney on 2/21/17.
 */
public class QuandlDatabasesV3 implements Webservice {
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
        return "databases.csv";
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
