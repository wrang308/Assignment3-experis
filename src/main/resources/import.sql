--franchise
INSERT INTO franchise (description, name) VALUES ( 'Spider bite human', 'Spider man');
INSERT INTO franchise (description, name) VALUES ( 'Agent shot bad guy', 'James Bond');
INSERT INTO franchise (description, name) VALUES ( 'Boy is Wizard', 'Harry Potter');
INSERT INTO franchise (description, name) VALUES ( 'Dinosaurs eat people', 'Jurassic Park');
INSERT INTO franchise (description, name) VALUES ( 'Hobbits go on a journey to destroy ring', 'Lord of the rings');

--movie
INSERT INTO movie ( director, genre, release_year, movie_title, picture_url, trailer_url, franchise_id)VALUES ( 'Sami Raimi', 'super hero', 2002, 'Spider man 1', 'https://upload.wikimedia.org/wikipedia/en/thumb/f/f3/Spider-Man2002Poster.jpg/220px-Spider-Man2002Poster.jpg', 'https://www.youtube.com/watch?v=dgADsB91UOU', 1);
INSERT INTO movie ( director, genre, release_year, movie_title, picture_url, trailer_url, franchise_id)VALUES ( 'Sami Raimi', 'super hero', 2004, 'Spider man 2', 'https://m.media-amazon.com/images/M/MV5BMzY2ODk4NmUtOTVmNi00ZTdkLTlmOWYtMmE2OWVhNTU2OTVkXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_UY98_CR2,0,67,98_AL_.jpg', 'https://www.imdb.com/video/vi629801241?playlistId=tt0316654&ref_=tt_ov_vi', 1);
INSERT INTO movie ( director, genre, release_year, movie_title, picture_url, trailer_url, franchise_id)VALUES ( 'Sami Raimi', 'super hero', 2007, 'Spider man 3', 'https://www.imdb.com/title/tt0413300/mediaviewer/rm4250081280/', 'https://www.youtube.com/watch?v=wPosLpgMtTY', 1);
INSERT INTO movie ( director, genre, release_year, movie_title, picture_url, trailer_url, franchise_id)VALUES ( 'Steven Spielberg', 'Adventure, Action, Sci fi', 1997, 'Jurassic park', 'https://upload.wikimedia.org/wikipedia/en/e/e7/Jurassic_Park_poster.jpg', 'https://www.youtube.com/watch?v=lc0UehYemQA', 4);

-- character
INSERT INTO "character" ( alias, full_name, gender, picture_url) VALUES ('Spider man', 'Peter parker', 'Male', 'https://www.imdb.com/title/tt0316654/mediaviewer/rm4263983360/');
INSERT INTO "character" ( alias, full_name, gender, picture_url) VALUES ('', 'Mary Jane', 'Female', 'https://m.media-amazon.com/images/M/MV5BMjE3OTA5NDA5Nl5BMl5BanBnXkFtZTYwNDI3NzY3._V1_SX100_CR0,0,100,100_AL_.jpg');
INSERT INTO "character" ( alias, full_name, gender, picture_url) VALUES ('New Goblin', 'Harry Osborn', 'Male', 'https://www.imdb.com/title/tt0413300/mediaviewer/rm2398654465/?ft0=name&fv0=nm0290556&ft1=image_type&fv1=still_frame');
INSERT INTO "character" ( alias, full_name, gender, picture_url) VALUES ('', 'Hammond', 'Male', 'https://static.wikia.nocookie.net/jurassicpark/images/f/f8/JP-JohnHammond.jpg/revision/latest?cb=20080901014643');


--movie_character
INSERT INTO movie_character(character_id, movie_id) VALUES (1, 1);
INSERT INTO movie_character(character_id, movie_id) VALUES (1, 2);
INSERT INTO movie_character(character_id, movie_id) VALUES (1, 3);
INSERT INTO movie_character(character_id, movie_id) VALUES (2, 1);
INSERT INTO movie_character(character_id, movie_id) VALUES (2, 2);
INSERT INTO movie_character(character_id, movie_id) VALUES (2, 3);
INSERT INTO movie_character(character_id, movie_id) VALUES (3, 1);
INSERT INTO movie_character(character_id, movie_id) VALUES (3, 2);
INSERT INTO movie_character(character_id, movie_id) VALUES (3, 3);
INSERT INTO movie_character(character_id, movie_id) VALUES (4, 4);