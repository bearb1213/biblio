

INSERT INTO auteur (nom) VALUES 
('J.K. Rowling'),
('George R.R. Martin'),
('J.R.R. Tolkien'),
('Agatha Christie'),
('Stephen King'),
('Haruki Murakami');


INSERT INTO genre (genre) VALUES 
('Fantasy'),
('Science-Fiction'),
('Policier'),
('Horreur'),
('Romance'),
('Aventure'),
('Mystere');

INSERT INTO livre (titre, synopsis, date_premier_edition, langue, nb_page, restriction, id_auteur) VALUES 
('Harry Potter a l ecole des sorciers', 'Un jeune sorcier decouvre son heritage magique', '1997-06-26', 'Français', 320, 0, 1),
('Le Trône de Fer', 'Guerre pour le contrôle des Sept Royaumes', '1996-08-01', 'Français', 694, 16, 2),
('Le Seigneur des Anneaux', 'Quête pour detruire un anneau malefique', '1954-07-29', 'Français', 423, 0, 3),
('Le Crime de l''Orient-Express', 'Meurtre mysterieux dans un train', '1934-01-01', 'Français', 256, 0, 4),
('Shining', 'Un hôtel hante revele des horreurs', '1977-01-28', 'Français', 447, 18, 5),
('Kafka sur le rivage', 'Histoire surrealiste au Japon', '2002-09-12', 'Français', 505, 12, 6);

INSERT INTO livre_genre (id_livre, id_genre) VALUES 
(1, 1), (1, 6),  
(2, 1), (2, 6),  
(3, 1), (3, 6),  
(4, 3), (4, 7),  
(5, 4),          
(6, 1), (6, 5);  

INSERT INTO exemplaire (date_in, id_livre) VALUES 
('2020-01-15', 1),
('2020-01-15', 1),
('2019-05-22', 2),
('2021-03-10', 3),
('2021-03-10', 3),
('2021-03-10', 3),
('2018-11-05', 4),
('2022-02-18', 5),
('2022-06-30', 6),
('2022-06-30', 6);


