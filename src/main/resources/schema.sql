CREATE TABLE ticker
(
 id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 symbol VARCHAR(25) NOT NULL,
 exchange VARCHAR(30),
 last_update DATETIME
);
CREATE UNIQUE INDEX ticker_id_uindex ON ticker (id);

CREATE TABLE data
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ticker_id INT(11),
    open DOUBLE,
    high DOUBLE,
    low DOUBLE,
    close DOUBLE,
    volume INT(11),
    adj_open DOUBLE,
    adj_high DOUBLE,
    adj_low DOUBLE,
    adj_close DOUBLE,
    adj_volume INT(11),
    CONSTRAINT data_ticker_id_fk FOREIGN KEY (ticker_id) REFERENCES ticker (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX data_id_uindex ON data (id);
CREATE INDEX data_ticker_id_fk ON data (ticker_id);