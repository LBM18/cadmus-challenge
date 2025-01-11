CREATE TABLE IF NOT EXISTS tb_artist (
	id_artist	        BIGSERIAL       NOT NULL,
	tx_name             VARCHAR(200)    NOT NULL,
	tx_nationality      VARCHAR(100)    NOT NULL,
	tx_website_address  VARCHAR(300)    NOT NULL,
	tx_profile_image    VARCHAR(300)    NOT NULL,
	CONSTRAINT pk_artist PRIMARY KEY (id_artist)
);

CREATE TABLE IF NOT EXISTS tb_album (
	id_album	        BIGSERIAL       NOT NULL,
	tx_title            VARCHAR(200)    NOT NULL,
	nb_release_year     BIGINT          NOT NULL CHECK (nb_release_year <= EXTRACT(YEAR FROM CURRENT_DATE)),
	tx_cover_image      VARCHAR(300)    NOT NULL,
	id_artist	        BIGINT          NOT NULL,
	CONSTRAINT pk_album  PRIMARY KEY (id_album),
    CONSTRAINT fk_artist FOREIGN KEY (id_artist) REFERENCES tb_artist (id_artist) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tb_music (
	id_music	        BIGSERIAL       NOT NULL,
	tx_title            VARCHAR(200)    NOT NULL,
	nb_duration         BIGINT          NOT NULL,
	nb_track            BIGINT          NOT NULL CHECK (nb_track > 0),
	id_album	        BIGINT          NOT NULL,
	CONSTRAINT pk_music PRIMARY KEY (id_music),
    CONSTRAINT fk_album FOREIGN KEY (id_album) REFERENCES tb_album (id_album) ON UPDATE CASCADE ON DELETE CASCADE
);