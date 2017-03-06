package com.dqr.jobs.eodJob;

/**
 * Wiki EOD Datatable Bulk Download.
 *
 * Created by dqromney on 3/6/17.
 */
public class DatatableBuildDownload {
    private String link;
    private String status;
    private String dataSnapshotTime;
    private String lastRefreshedTime;

    public DatatableBuildDownload() {
        // Empty
    }

    public DatatableBuildDownload(String link, String status, String dataSnapshotTime, String lastRefreshedTime) {
        this.link = link;
        this.status = status;
        this.dataSnapshotTime = dataSnapshotTime;
        this.lastRefreshedTime = lastRefreshedTime;
    }

// ----------------------------------------------------------------
    // Accessor methods
    // ----------------------------------------------------------------

    public String getLink() {
        return link;
    }

    public String getDataSnapshotTime() {
        return dataSnapshotTime;
    }

    public void setDataSnapshotTime(String dataSnapshotTime) {
        this.dataSnapshotTime = dataSnapshotTime;
    }

    public String getLastRefreshedTime() {
        return lastRefreshedTime;
    }

    public void setLastRefreshedTime(String lastRefreshedTime) {
        this.lastRefreshedTime = lastRefreshedTime;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DatatableBuildDownload{");
        sb.append("link='").append(link).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", dataSnapshotTime='").append(dataSnapshotTime).append('\'');
        sb.append(", lastRefreshedTime='").append(lastRefreshedTime).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
