package com.dqr.factory;

/**
 * Wiki End of Day Quotes Web Service.
 *
 * Created by dqromney on 2/20/17.
 */
public class WikiEodV3 implements Webservice {

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
        return "datasets/WIKI/";
    }

    public String getUrl() {
        return new StringBuilder()
                .append(getSource())
                .append(getApi())
                .append(getVersion())
                .append(getDataset()).toString();
    }

}
