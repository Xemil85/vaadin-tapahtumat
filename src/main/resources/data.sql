INSERT INTO location (name, address) VALUES ('Helsinki', 'Mannerheimintie 1');
INSERT INTO location (name, address) VALUES ('Tampere', 'Hämeenkatu 12');
INSERT INTO location (name, address) VALUES ('Turku', 'Yliopistonkatu 4');

INSERT INTO organizer (name) VALUES ('Kulttuuriyhdistys');
INSERT INTO organizer (name) VALUES ('Musiikkiseura');
INSERT INTO organizer (name) VALUES ('Koulutus Oy');

INSERT INTO event (name, date, location_id, organizer_id) 
VALUES ('Kevätfestarit', '2025-05-15', 1, 1);

INSERT INTO event (name, date, location_id, organizer_id) 
VALUES ('Kesäkonsertti', '2025-06-10', 2, 2);

INSERT INTO event (name, date, location_id, organizer_id) 
VALUES ('Syysseminaari', '2025-09-21', 3, 3);
