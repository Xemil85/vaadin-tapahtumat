INSERT INTO location (name, address) VALUES ('Helsinki', 'Mannerheimintie 1');
INSERT INTO location (name, address) VALUES ('Tampere', 'Hämeenkatu 12');
INSERT INTO location (name, address) VALUES ('Turku', 'Yliopistonkatu 4');

INSERT INTO organizer (name) VALUES ('Kulttuuriyhdistys');
INSERT INTO organizer (name) VALUES ('Musiikkiseura');
INSERT INTO organizer (name) VALUES ('Koulutus Oy');

INSERT INTO app_user (username, password, role)
VALUES ('user', '$2a$10$0oSipKj02nkrk1./Fyp7iO/rJMErvIQPovR8zmcbARQz.GOIskU9S', 'USER');

INSERT INTO app_user (username, password, role) 
VALUES ('admin', '$2a$10$mC7K1tG9gTVzUaSIPFYnqeUdhg/tCZ8VXFODOAXMiD8xRJwbDY2li', 'ADMIN');

INSERT INTO event (name, date, location_id, organizer_id, created_by_id) 
VALUES ('Kevätfestarit', '2025-05-15', 1, 1, 1);

INSERT INTO event (name, date, location_id, organizer_id, created_by_id) 
VALUES ('Kesäkonsertti', '2025-06-10', 2, 2, 2);

INSERT INTO event (name, date, location_id, organizer_id, created_by_id) 
VALUES ('Syysseminaari', '2025-09-21', 3, 3, 2);
