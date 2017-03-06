package com.dqr.factory;

/**
 * Wiki Request End of Day Quote File Web Service.
 *
 * https://www.quandl.com/api/v3/datatables/WIKI/PRICES?qopts.export=true&api_key={{api-key}}
 *
 * Created by dqromney on 2/20/17.
 */
public class WikiFileEodV3 implements Webservice {

    public String getSource() {
        return "https://www.quandl.com/";
    }

    public String getApi() {
        return "api/";
    }

    public String getVersion() {
        return "v3/";
    }

    public String getDataset() {
        return "datatables/WIKI/";
    }

    public String getUrl() {
        return new StringBuilder()
                .append(getSource())
                .append(getApi())
                .append(getVersion())
                .append(getDataset()).toString();
    }

}
