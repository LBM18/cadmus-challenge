INSERT INTO tb_artist   (id_artist, tx_name, tx_nationality, tx_website_address, tx_profile_image)
                VALUES  (0, 'James', 'British', 'http://www.james.com', 'British profile image');
INSERT INTO tb_artist   (id_artist, tx_name, tx_nationality, tx_website_address, tx_profile_image)
                VALUES  (-2, 'Joao', 'Brazilian', 'http://www.joao.com.br', 'Joao profile image');

INSERT INTO tb_album    (id_album, tx_title, nb_release_year, tx_cover_image, id_artist)
                VALUES  (0, 'Tranquility', 2025, 'Tranquility cover image', 0);
INSERT INTO tb_album    (id_album, tx_title, nb_release_year, tx_cover_image, id_artist)
                VALUES  (-2, 'Happiness', 2022, 'Happiness cover image', -2);

INSERT INTO tb_music    (id_music, tx_title, nb_duration, nb_track, id_album)
                VALUES  (0, 'Happy day', 90, 1, 0);
INSERT INTO tb_music    (id_music, tx_title, nb_duration, nb_track, id_album)
                VALUES  (-2, 'Be loved today', 60, 1, -2);
INSERT INTO tb_music    (id_music, tx_title, nb_duration, nb_track, id_album)
                VALUES  (-3, 'Day of night', 30, 2, 0);