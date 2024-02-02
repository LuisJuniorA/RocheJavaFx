CREATE TABLE TESTGARAGE 
(
  ID NUMBER NOT NULL 
, NOMGARAGE VARCHAR2(40 BYTE) NOT NULL 
, ADRESSE VARCHAR2(60 BYTE) NOT NULL 
, COMPLEMENTADRESSE VARCHAR2(20 BYTE) 
, CP CHAR(5 BYTE) NOT NULL 
, VILLE VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT TESTGARAGE_PK PRIMARY KEY 
  (
    ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX TESTGARAGE_PK ON TESTGARAGE (ID ASC) 
      LOGGING 
      TABLESPACE SYSTEM 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE SYSTEM 
PCTFREE 10 
PCTUSED 40 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  FREELISTS 1 
  FREELIST GROUPS 1 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;

CREATE TABLE TESTMARQUE 
(
  ID NUMBER NOT NULL 
, NOM VARCHAR2(20 BYTE) NOT NULL 
, NATIONALITE VARCHAR2(20 BYTE) NOT NULL 
, CONSTRAINT TESTMARQUE_PK PRIMARY KEY 
  (
    ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX TESTMARQUE_PK ON TESTMARQUE (ID ASC) 
      LOGGING 
      TABLESPACE SYSTEM 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE SYSTEM 
PCTFREE 10 
PCTUSED 40 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  FREELISTS 1 
  FREELIST GROUPS 1 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;



CREATE TABLE TESTVOITURE 
(
  ID NUMBER NOT NULL 
, CAPACITERESERVOIR FLOAT(126) NOT NULL 
, COULEUR VARCHAR2(20 BYTE) NOT NULL 
, ANNEEMISEENSERVICE NUMBER(4, 0) NOT NULL 
, NBKILOMETRESCOMPTEUR NUMBER DEFAULT 0 
, NBLITRESCONTENUS FLOAT(126) DEFAULT 0 
, PRIXACHAT NUMBER NOT NULL 
, IDMARQUE NUMBER NOT NULL 
, IDGARAGE NUMBER 
, CONSTRAINT TESTVOITURE_PK PRIMARY KEY 
  (
    ID 
  )
  USING INDEX 
  (
      CREATE UNIQUE INDEX TESTVOITURE_PK ON TESTVOITURE (ID ASC) 
      LOGGING 
      TABLESPACE SYSTEM 
      PCTFREE 10 
      INITRANS 2 
      STORAGE 
      ( 
        INITIAL 65536 
        NEXT 1048576 
        MINEXTENTS 1 
        MAXEXTENTS UNLIMITED 
        FREELISTS 1 
        FREELIST GROUPS 1 
        BUFFER_POOL DEFAULT 
      ) 
      NOPARALLEL 
  )
  ENABLE 
) 
LOGGING 
TABLESPACE SYSTEM 
PCTFREE 10 
PCTUSED 40 
INITRANS 1 
STORAGE 
( 
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1 
  MAXEXTENTS UNLIMITED 
  FREELISTS 1 
  FREELIST GROUPS 1 
  BUFFER_POOL DEFAULT 
) 
NOCOMPRESS 
NO INMEMORY 
NOPARALLEL;

ALTER TABLE TESTVOITURE
ADD CONSTRAINT TESTVOITURE_FK1 FOREIGN KEY
(
  IDGARAGE 
)
REFERENCES TESTGARAGE
(
  ID 
)
ON DELETE SET NULL ENABLE;

ALTER TABLE TESTVOITURE
ADD CONSTRAINT TESTVOITURE_FK2 FOREIGN KEY
(
  IDMARQUE 
)
REFERENCES TESTMARQUE
(
  ID 
)
ENABLE;




--Si TESTMARQUE crée:

INSERT ALL
INTO testmarque (ID, nom, nationalite) VALUES (1, 'Toyota', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (2, 'Ford', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (3, 'Volkswagen', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (4, 'Honda', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (5, 'Chevrolet', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (6, 'Hyundai', 'Coree')
INTO testmarque (ID, nom, nationalite) VALUES (7, 'Nissan', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (8, 'Mercedes-Benz', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (9, 'BMW', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (10, 'Audi', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (11, 'Ferrari', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (12, 'Lamborghini', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (13, 'Subaru', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (14, 'Tesla', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (15, 'Mazda', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (16, 'Porsche', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (17, 'Volvo', 'Suede')
INTO testmarque (ID, nom, nationalite) VALUES (18, 'Lexus', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (19, 'Jeep', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (20, 'Aston Martin', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (21, 'Buick', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (22, 'Jaguar', 'Royaume-Uni')
INTO testmarque (ID, nom, nationalite) VALUES (23, 'Land Rover', 'Royaume-Uni')
INTO testmarque (ID, nom, nationalite) VALUES (24, 'Chrysler', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (25, 'Mitsubishi', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (26, 'Infiniti', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (27, 'Alfa Romeo', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (28, 'Maserati', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (29, 'Rolls-Royce', 'Royaume-Uni')
INTO testmarque (ID, nom, nationalite) VALUES (30, 'Dodge', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (31, 'Kia', 'Coree')
INTO testmarque (ID, nom, nationalite) VALUES (32, 'Bentley', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (33, 'McLaren', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (34, 'Bugatti', 'France')
INTO testmarque (ID, nom, nationalite) VALUES (35, 'Lotus', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (36, 'Ram', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (37, 'Suzuki', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (38, 'Mini', 'Royaume-Uni')
INTO testmarque (ID, nom, nationalite) VALUES (39, 'Acura', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (40, 'Genesis', 'Coree')
INTO testmarque (ID, nom, nationalite) VALUES (41, 'Smart', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (42, 'Hummer', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (43, 'Maybach', 'Allemagne')
INTO testmarque (ID, nom, nationalite) VALUES (44, 'Scion', 'Japon')
INTO testmarque (ID, nom, nationalite) VALUES (45, 'Citroen', 'France')
INTO testmarque (ID, nom, nationalite) VALUES (46, 'Abarth', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (47, 'Lada', 'Russie')
INTO testmarque (ID, nom, nationalite) VALUES (48, 'Geely', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (49, 'Dacia', 'France')
INTO testmarque (ID, nom, nationalite) VALUES (50, 'BYD', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (51, 'Chery', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (52, 'Great Wall', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (53, 'Tata', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (54, 'Mahindra', 'Inde')
INTO testmarque (ID, nom, nationalite) VALUES (55, 'Haval', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (56, 'NIO', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (57, 'Proton', 'Malaisie')
INTO testmarque (ID, nom, nationalite) VALUES (58, 'Perodua', 'Malaisie')
INTO testmarque (ID, nom, nationalite) VALUES (59, 'SsangYong', 'Coree')
INTO testmarque (ID, nom, nationalite) VALUES (60, 'Saab', 'Suede')
INTO testmarque (ID, nom, nationalite) VALUES (61, 'Spyker', 'Pays-Bas')
INTO testmarque (ID, nom, nationalite) VALUES (62, 'Hennessey', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (63, 'Rivian', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (64, 'Lucid', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (65, 'Polestar', 'Suede')
INTO testmarque (ID, nom, nationalite) VALUES (66, 'Faraday Future', 'Chine')
INTO testmarque (ID, nom, nationalite) VALUES (67, 'Fisker', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (68, 'Rivian', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (69, 'Koenigsegg', 'Suede')
INTO testmarque (ID, nom, nationalite) VALUES (70, 'Pininfarina', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (71, 'Bollinger', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (72, 'Rimac', 'Croatie')
INTO testmarque (ID, nom, nationalite) VALUES (73, 'Cupra', 'Espagne')
INTO testmarque (ID, nom, nationalite) VALUES (74, 'Coda', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (75, 'DeLorean', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (76, 'Elio', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (77, 'Fisker', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (78, 'GMC', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (79, 'Iveco', 'Italie')
INTO testmarque (ID, nom, nationalite) VALUES (80, 'Karma', 'USA')
INTO testmarque (ID, nom, nationalite) VALUES (81, 'Lykan', 'Liban')
INTO testmarque (ID, nom, nationalite) VALUES (82, 'Mullen', 'USA')
SELECT * FROM dual;