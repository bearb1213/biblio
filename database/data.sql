

INSERT INTO auteur (nom) VALUES 
('Victor Hugo'),
('Albert Camus'),
('J.K. Rowling');


INSERT INTO genre (genre) VALUES 
('Litterature classique'),
('philosophie'),
('Jeunnesse'),
('Fantastique');

INSERT INTO livre (titre, synopsis, date_premier_edition, langue, nb_page, restriction, id_auteur) VALUES 
('Les Miserable', 'Histoire ecrit par VH', '1996-08-01', 'Français',100,0, 1),
('L Etranger', 'Histoire ecrit par AC', '1954-07-29', 'Français', 100, 0, 2),
('Harry Potter a l ecole des sorciers', 'Un jeune sorcier decouvre son heritage magique', '1997-06-26', 'Français', 320, 0, 3);

INSERT INTO livre_genre (id_livre, id_genre) VALUES 
(1, 1),  
(2, 2),   
(3, 3), (3, 4);  

INSERT INTO exemplaire (date_in, id_livre) VALUES 
('2020-01-15', 1),
('2020-01-15', 1),
('2019-05-22', 1),
('2021-03-10', 2),
('2021-03-10', 2),
('2021-03-10', 3);

INSERT INTO adhesion (date_in,date_fin,id_utilisateur,id_type) VALUES
('2025-02-01' , '2025-07-24' , 2 ,1),
('2025-04-01' , '2025-12-01' , 4 ,1),
('2025-07-01' , '2026-07-01' , 5 ,2),
('2025-07-01' , '2026-06-01' , 7 ,2),
('2025-06-01' , '2025-12-01' , 8 ,3);


INSERT INTO adhesion (date_in,date_fin,id_utilisateur,id_type) VALUES
('2025-02-01' , '2025-07-01' , 3 ,1),
('2025-08-01' , '2026-05-01' , 6 ,2),
('2024-10-01' , '2025-06-01' , 9 ,3);

