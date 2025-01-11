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

-- DROP
DROP TABLE tb_music;
DROP TABLE tb_album;
DROP TABLE tb_artist;

-- CRUD
SELECT * FROM tb_artist;
SELECT * FROM tb_album;
SELECT * FROM tb_music;

INSERT INTO tb_artist   (id_artist, tx_name, tx_nationality, tx_website_address, tx_profile_image)
                VALUES  (0, 'Artist name', 'Artist nationality', 'http://www.example.com', 'Artist profile image');
INSERT INTO tb_album    (id_album, tx_title, nb_release_year, tx_cover_image, id_artist)
                VALUES  (0, 'Album title', 2025, 'Album cover image', 0);
INSERT INTO tb_music    (id_music, tx_title, nb_duration, nb_track, id_album)
                VALUES  (0, 'Music title', 90, 1, 0);

UPDATE tb_artist SET tx_name  = 'Artist new name' WHERE id_artist = 0;
UPDATE tb_album  SET tx_title = 'Album new title' WHERE id_album  = 0;
UPDATE tb_music  SET tx_title = 'Music new title' WHERE id_music  = 0;

DELETE FROM tb_artist WHERE id_artist = 0;
DELETE FROM tb_album  WHERE id_album  = 0;
DELETE FROM tb_music  WHERE id_music  = 0;