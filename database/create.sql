\c postgres
DROP DATABASE IF EXISTS biblio ;
CREATE DATABASE biblio;
\c biblio

CREATE TABLE adhesion_type(
   id SERIAL,
   type VARCHAR(50),
   PRIMARY KEY(id)
);

INSERT INTO adhesion_type (type) VALUES 
('Etudiant'),
('Professeur'),
('Professionnel');


CREATE TABLE utilisateur_type(
   id SERIAL,
   type VARCHAR(50),
   PRIMARY KEY(id)
);

INSERT INTO utilisateur_type (type) VALUES 
('Bibliothecaire'),
('User');


CREATE TABLE auteur(
   id SERIAL,
   nom VARCHAR(255),
   PRIMARY KEY(id)
);

CREATE TABLE genre(
   id SERIAL,
   genre VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE livre(
   id SERIAL,
   titre VARCHAR(255),
   synopsis TEXT,
   date_premier_edition DATE,
   langue VARCHAR(50),
   nb_page INTEGER,
   restriction INTEGER,
   id_auteur INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_auteur) REFERENCES auteur(id)
);

CREATE TABLE livre_genre(
   id SERIAL,
   id_livre INTEGER NOT NULL,
   id_genre INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_livre) REFERENCES livre(id),
   FOREIGN KEY(id_genre) REFERENCES genre(id)
);

CREATE TABLE exemplaire(
   id SERIAL,
   date_in DATE,
   id_livre INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_livre) REFERENCES livre(id)
);

CREATE TABLE quotas(
   id SERIAL,
   action VARCHAR(50),
   nb INTEGER,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES adhesion_type(id)
);

INSERT INTO quotas (action,nb,id_type) VALUES
('reservation', 1, 1),
('reservation', 2, 2),
('reservation', 2, 3),
('pret', 1, 1),
('pret', 2, 2),
('pret', 2, 3),
('prolongement', 1, 1),
('prolongement', 2, 2),
('prolongement', 2, 3);




CREATE TABLE penalite_type(
   id SERIAL,
   nb_jour INTEGER,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES adhesion_type(id)
);
INSERT INTO penalite_type(nb_jour,id_type) VALUES 
(3,1) , (2,2) ,(2,3);


CREATE TABLE utilisateur(
   id SERIAL,
   nom VARCHAR(255),
   prenom VARCHAR(255),
   email VARCHAR(255),
   mdp VARCHAR(255),
   date_naissance DATE,
   date_in DATE,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES utilisateur_type(id)
);
INSERT INTO utilisateur (nom,prenom,email,mdp,date_naissance,date_in,id_type) VALUES
('biblio' , 'biblio' , 'biblio@gmail.com' , 'biblio' , '2000-01-01' , now() , 1 );

CREATE TABLE adhesion(
   id SERIAL,
   date_in DATE,
   date_fin DATE,
   id_utilisateur INTEGER NOT NULL,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id),
   FOREIGN KEY(id_type) REFERENCES adhesion_type(id)
);

CREATE TABLE reservation(
   id SERIAL,
   date_in TIMESTAMP,
   date_reservation DATE,
   motif TEXT,
   id_utilisateur INTEGER NOT NULL,
   id_exemplaire INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id),
   FOREIGN KEY(id_exemplaire) REFERENCES exemplaire(id)
);

CREATE TABLE reservation_status(
   id SERIAL,
   statu VARCHAR(50),
   date_in TIMESTAMP,
   id_reservation INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_reservation) REFERENCES reservation(id)
);

CREATE TABLE pret(
   id SERIAL,
   date_in TIMESTAMP,
   id_utilisateur INTEGER NOT NULL,
   id_exemplaire INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id),
   FOREIGN KEY(id_exemplaire) REFERENCES exemplaire(id)
);

CREATE TABLE pret_status(
   id SERIAL,
   statu VARCHAR(50),
   date_in TIMESTAMP,
   date_fin TIMESTAMP,
   id_pret INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_pret) REFERENCES pret(id)
);

CREATE TABLE prolongement(
   id SERIAL,
   statu VARCHAR(50),
   date_in TIMESTAMP,
   id_pret INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_pret) REFERENCES pret(id)
);

CREATE TABLE penalite(
   id SERIAL,
   date_in TIMESTAMP,
   date_fin TIMESTAMP,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id)
);


CREATE TABLE pret_nbJour (
   id SERIAL,
   nb_jour INTEGER ,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES adhesion_type(id)
);

INSERT INTO pret_nbJour(nb_jour,id_type) VALUES 
(2,1),(3,2),(3,3);
