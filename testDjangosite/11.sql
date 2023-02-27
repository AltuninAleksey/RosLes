--
-- File generated with SQLiteStudio v3.4.1 on Пн фев 20 17:14:40 2023
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: djangoForest_accordancemolodkrandtppl
CREATE TABLE IF NOT EXISTS "djangoForest_accordancemolodkrandtppl" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_of_accordance" varchar(300) NOT NULL);
INSERT INTO djangoForest_accordancemolodkrandtppl (id, name_of_accordance) VALUES (2, 'Молодняк соответствует Правилам лесовосстановления;');
INSERT INTO djangoForest_accordancemolodkrandtppl (id, name_of_accordance) VALUES (3, 'Молодняк не соответствует Правилам лесовосстановления по количеству деревьев главных пород;');
INSERT INTO djangoForest_accordancemolodkrandtppl (id, name_of_accordance) VALUES (4, 'Молодняк не соответствует Правилам лесовосстановления по средней высоте деревьев главных пород.');

-- Table: djangoForest_accordancenoneaccordanceeconomy
CREATE TABLE IF NOT EXISTS "djangoForest_accordancenoneaccordanceeconomy" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_accordance_none_economy" varchar(300) NOT NULL);
INSERT INTO djangoForest_accordancenoneaccordanceeconomy (id, name_accordance_none_economy) VALUES (2, 'соответствует хозяйству при отнесении к землям, на которых расположены леса');
INSERT INTO djangoForest_accordancenoneaccordanceeconomy (id, name_accordance_none_economy) VALUES (3, 'не соответствует хозяйству при отнесении к землям, на которых расположены леса');

-- Table: djangoForest_bonitetorlov
CREATE TABLE IF NOT EXISTS "djangoForest_bonitetorlov" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "age_of_planting" real NOT NULL, "class_bonitet" varchar(300) NOT NULL, "height_planting_for_bonitet_class" varchar(300) NOT NULL);
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (1, 10.0, '1а', '6-5');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (2, 20.0, '1а', '12-10');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (3, 30.0, '1а', '16-14');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (4, 40.0, '1а', '20-18');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (5, 50.0, '1а', '24-21');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (6, 60.0, '1а', '28-24');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (7, 70.0, '1а', '30-26');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (8, 80.0, '1а', '32-28');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (9, 90.0, '1а', '34-30');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (10, 100.0, '1а', '35-31');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (11, 110.0, '1а', '36-32');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (12, 120.0, '1а', '38-34');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (13, 140.0, '1а', '39-35');
INSERT INTO djangoForest_bonitetorlov (id, age_of_planting, class_bonitet, height_planting_for_bonitet_class) VALUES (14, 160.0, '1а', '40-36');

-- Table: djangoForest_branches
CREATE TABLE IF NOT EXISTS "djangoForest_branches" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_branch" varchar(350) NOT NULL);
INSERT INTO djangoForest_branches (id, name_branch) VALUES (1, 'dfghjnxl');
INSERT INTO djangoForest_branches (id, name_branch) VALUES (3, 'zcbnmrw');
INSERT INTO djangoForest_branches (id, name_branch) VALUES (4, 'mncb,mzxcb.,mxnvx.,bvn.,mb,n][pqer');

-- Table: djangoForest_breed
CREATE TABLE IF NOT EXISTS "djangoForest_breed" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_breed" varchar(350) NOT NULL, "is_foliar" bool NULL, "is_pine" bool NULL, "ShortName" varchar(10) NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (1, 'Сосна', 0, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (2, 'Ель', 0, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (3, 'Береза', 0, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (4, 'Осина', 0, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (5, 'Липулька', 0, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (6, 'Береза', 1, 0, NULL);
INSERT INTO djangoForest_breed (id, name_breed, is_foliar, is_pine, ShortName) VALUES (7, 'Пальма', 1, 0, 'П');

-- Table: djangoForest_categorygroundlfinnoneaccordance
CREATE TABLE IF NOT EXISTS "djangoForest_categorygroundlfinnoneaccordance" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_of_category_ground" varchar(300) NOT NULL);
INSERT INTO djangoForest_categorygroundlfinnoneaccordance (id, name_of_category_ground) VALUES (2, 'вырубка');
INSERT INTO djangoForest_categorygroundlfinnoneaccordance (id, name_of_category_ground) VALUES (3, 'гарь');
INSERT INTO djangoForest_categorygroundlfinnoneaccordance (id, name_of_category_ground) VALUES (4, 'прогалины и пустыри');
INSERT INTO djangoForest_categorygroundlfinnoneaccordance (id, name_of_category_ground) VALUES (5, 'погибшие насаждения');
INSERT INTO djangoForest_categorygroundlfinnoneaccordance (id, name_of_category_ground) VALUES (6, 'иное');

-- Table: djangoForest_categoryofforestfundlands
CREATE TABLE IF NOT EXISTS "djangoForest_categoryofforestfundlands" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_category" varchar(350) NOT NULL);
INSERT INTO djangoForest_categoryofforestfundlands (id, name_category) VALUES (2, 'вырубка');
INSERT INTO djangoForest_categoryofforestfundlands (id, name_category) VALUES (3, 'гарь');
INSERT INTO djangoForest_categoryofforestfundlands (id, name_category) VALUES (4, 'прогалины и пустыри');
INSERT INTO djangoForest_categoryofforestfundlands (id, name_category) VALUES (5, 'погибшие насаждения');
INSERT INTO djangoForest_categoryofforestfundlands (id, name_category) VALUES (6, 'иное');

-- Table: djangoForest_checktrigger
CREATE TABLE IF NOT EXISTS "djangoForest_checktrigger" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "bool" bool NOT NULL);

-- Table: djangoForest_districtforestly
CREATE TABLE IF NOT EXISTS "djangoForest_districtforestly" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_district_forestly" varchar(500) NOT NULL, "id_forestly_id" bigint NULL REFERENCES "djangoForest_forestly" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_districtforestly (id, name_district_forestly, id_forestly_id) VALUES (6, 'Четдинское', 5);
INSERT INTO djangoForest_districtforestly (id, name_district_forestly, id_forestly_id) VALUES (7, 'Пупи', 6);
INSERT INTO djangoForest_districtforestly (id, name_district_forestly, id_forestly_id) VALUES (8, 'Айдигидай', 1);
INSERT INTO djangoForest_districtforestly (id, name_district_forestly, id_forestly_id) VALUES (9, 'Айдигидай', 4);

-- Table: djangoForest_economy
CREATE TABLE IF NOT EXISTS "djangoForest_economy" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_economy" varchar(300) NOT NULL);
INSERT INTO djangoForest_economy (id, name_economy) VALUES (6, 'Хвойное');
INSERT INTO djangoForest_economy (id, name_economy) VALUES (7, 'Твердолиственное');
INSERT INTO djangoForest_economy (id, name_economy) VALUES (8, 'мягколиственное');

-- Table: djangoForest_forestareas
CREATE TABLE IF NOT EXISTS "djangoForest_forestareas" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_forest_areas" varchar(300) NOT NULL, "composition_of_forest_areas" varchar(500) NOT NULL, "comm" varchar(500) NOT NULL);
INSERT INTO djangoForest_forestareas (id, name_forest_areas, composition_of_forest_areas, comm) VALUES (1, 'Район притундровых лесов и редкостойной тайги Европейско-Уральской части Российской Федерации', 'Мурманская область:', 'Кольский (к северу от рек Тулома, Лотта, Верхнетуломского водохранилища), Печенгский муниципальные районы, город Мурманск, ЗАТО: поселок Видяево, города Александровск, Заозерск, Островной, Североморск.');
INSERT INTO djangoForest_forestareas (id, name_forest_areas, composition_of_forest_areas, comm) VALUES (2, 'Район притундровых лесов и редкостойной тайги Европейско-Уральской части Российской Федерации', 'Ненецкий автономный округ:', 'Заполярный муниципальный район, город Нарьян-Мар с подведомственной территорией.');
INSERT INTO djangoForest_forestareas (id, name_forest_areas, composition_of_forest_areas, comm) VALUES (3, 'Район притундровых лесов и редкостойной тайги Европейско-Уральской части Российской Федерации', 'Архангельская область:', 'Мезенский, Приморский (северная часть - с южной стороны ограничена координатами от 39°53''37" вд 65°14''38" сш до 41°17''13" вд 65°32''26" сш) муниципальные районы.');
INSERT INTO djangoForest_forestareas (id, name_forest_areas, composition_of_forest_areas, comm) VALUES (4, 'Район притундровых лесов и редкостойной тайги Европейско-Уральской части Российской Федерации', 'Республика Коми:', 'Ижемский (к северу от 65° с.ш.), Печорский (к северу от 65° с.ш.), Усть-Цилемский муниципальные районы, городские округа: город Воркута, город Инта, город Усинск.');

-- Table: djangoForest_forestformingbydefault
CREATE TABLE IF NOT EXISTS "djangoForest_forestformingbydefault" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "id_breed_id" bigint NOT NULL REFERENCES "djangoForest_breed" ("id") DEFERRABLE INITIALLY DEFERRED, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_forestformingbydefault (id, id_breed_id, id_profile_id) VALUES (1, 6, 2);
INSERT INTO djangoForest_forestformingbydefault (id, id_breed_id, id_profile_id) VALUES (2, 1, 1);
INSERT INTO djangoForest_forestformingbydefault (id, id_breed_id, id_profile_id) VALUES (3, 4, 2);
INSERT INTO djangoForest_forestformingbydefault (id, id_breed_id, id_profile_id) VALUES (4, 2, 2);
INSERT INTO djangoForest_forestformingbydefault (id, id_breed_id, id_profile_id) VALUES (5, 1, 1);

-- Table: djangoForest_forestly
CREATE TABLE IF NOT EXISTS "djangoForest_forestly" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_forestly" varchar(500) NOT NULL, "id_subject_rf_id" bigint NULL REFERENCES "djangoForest_subjectrf" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_forestly (id, name_forestly, id_subject_rf_id) VALUES (1, 'Фокинское', 16);
INSERT INTO djangoForest_forestly (id, name_forestly, id_subject_rf_id) VALUES (4, 'Трубчевское', 21);
INSERT INTO djangoForest_forestly (id, name_forestly, id_subject_rf_id) VALUES (5, 'Локчимское', 31);
INSERT INTO djangoForest_forestly (id, name_forestly, id_subject_rf_id) VALUES (6, 'абуба', 31);
INSERT INTO djangoForest_forestly (id, name_forestly, id_subject_rf_id) VALUES (7, 'Балдежное', 16);

-- Table: djangoForest_forestprotectioncategory
CREATE TABLE IF NOT EXISTS "djangoForest_forestprotectioncategory" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_forest_protection_category" varchar(350) NOT NULL);
INSERT INTO djangoForest_forestprotectioncategory (id, name_forest_protection_category) VALUES (2, 'леса, расположенные на особо охраняемых природных территориях;');
INSERT INTO djangoForest_forestprotectioncategory (id, name_forest_protection_category) VALUES (3, 'леса, расположенные в водоохранных зонах;');
INSERT INTO djangoForest_forestprotectioncategory (id, name_forest_protection_category) VALUES (4, 'леса, выполняющие функции защиты природных и иных объектов;');
INSERT INTO djangoForest_forestprotectioncategory (id, name_forest_protection_category) VALUES (5, 'ценные леса;');
INSERT INTO djangoForest_forestprotectioncategory (id, name_forest_protection_category) VALUES (6, 'городские леса.');

-- Table: djangoForest_gps
CREATE TABLE IF NOT EXISTS "djangoForest_gps" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "latitude" real NOT NULL, "longitude" real NOT NULL, "flag_center" integer NOT NULL, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (3, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (4, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (5, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (6, 51.68, 68.54, 1, 90);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (62, 51.68, 68.54, 1, 93);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (63, 51.68, 68.54, 1, 95);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (64, 51.68, 68.54, 1, 96);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (65, 51.68, 68.54, 1, 97);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (66, 51.68, 68.54, 1, 98);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (67, 51.68, 68.54, 1, 99);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (68, 51.69, 68.54, 1, 100);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (69, 51.79, 68.54, 1, 101);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (70, 51.29, 68.1, 1, 102);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (71, 91.28, 38.54, 1, 93);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (72, 51.68, 68.54, 1, 109);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (73, 51.68, 68.54, 1, 110);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (74, 51.68, 68.54, 1, 111);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (75, 51.68, 68.54, 1, 116);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (76, 51.68, 68.54, 1, 118);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (77, 51.68, 68.54, 1, 119);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (78, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (79, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (81, 51.68, 68.54, 1, 120);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (82, 51.68, 68.54, 1, 121);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (83, 51.68, 68.54, 1, 122);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (84, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (88, 51.68, 68.54, 1, 123);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (94, 51.68, 68.54, 1, 128);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (95, 51.68, 68.54, 1, 129);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (96, 51.68, 68.54, 1, 129);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (97, 51.68, 68.54, 1, 131);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (98, 51.68, 68.54, 1, 131);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (99, 51.68, 68.54, 1, 142);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (100, 51.68, 68.54, 1, 143);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (101, 51.68, 68.54, 1, 144);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (102, 51.68, 68.54, 1, 145);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (103, 51.68, 68.54, 1, 146);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (104, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (105, 51.68, 68.54, 1, 148);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (106, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (107, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (108, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (109, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (110, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (111, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (112, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (113, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (114, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (115, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (116, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (117, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (118, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (119, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (120, 51.68, 68.54, 1, NULL);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (121, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (122, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (123, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (124, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (125, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (126, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (127, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (128, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (129, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (130, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (131, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (132, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (133, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (134, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (135, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (136, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (137, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (138, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (139, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (140, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (141, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (142, 51.68, 68.54, 1, 147);
INSERT INTO djangoForest_gps (id, latitude, longitude, flag_center, id_sample_id) VALUES (143, 51.68, 68.54, 1, 147);

-- Table: djangoForest_list
CREATE TABLE IF NOT EXISTS "djangoForest_list" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "to0_2" integer NULL, "from0_21To0_5" integer NULL, "from0_6To1_0" integer NULL, "from1_1to1_5" integer NULL, "from1_5" integer NULL, "max_height" real NULL, "id_breed_id" bigint NULL REFERENCES "djangoForest_breed" ("id") DEFERRABLE INITIALLY DEFERRED, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") DEFERRABLE INITIALLY DEFERRED, "id_type_of_reproduction_id" bigint NULL REFERENCES "djangoForest_reproduction" ("id") DEFERRABLE INITIALLY DEFERRED, "avg_diameter" real NULL, "avg_height" real NULL, "count_of_plants" integer NULL, "id_undergrowth_id" bigint NULL REFERENCES "djangoForest_undergrowth" ("id") DEFERRABLE INITIALLY DEFERRED, "main" bool NULL);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (89, 0, 0, 0, 6, 9, 3.0, 2, 93, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (90, 0, 9, 4, 25, 6, 3.0, 3, 93, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (91, 0, 0, 0, 6, 9, 3.0, 2, 95, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (92, 0, 9, 4, 25, 6, 3.0, 3, 95, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (93, 0, 0, 0, 6, 9, 3.0, 2, 96, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (94, 0, 9, 4, 25, 6, 3.0, 3, 96, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (95, 0, 0, 0, 6, 9, 3.0, 2, 97, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (96, 0, 9, 4, 25, 6, 3.0, 3, 97, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (97, 0, 0, 0, 6, 9, 3.0, 2, 98, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (98, 0, 9, 4, 25, 6, 3.0, 3, 98, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (99, 0, 0, 0, 6, 9, 3.0, 2, 99, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (100, 0, 9, 4, 25, 6, 3.0, 3, 99, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (101, 8, 2, 2, 6, 9, 3.0, 2, 100, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (102, 5, 11, 88, 105, 6, 3.0, 3, 100, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (103, 8, 2, 2, 6, 9, 3.0, 2, 101, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (104, 5, 11, 88, 105, 6, 3.0, 3, 101, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (105, 8, 2, 2, 6, 9, 3.0, 2, 102, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (106, 5, 11, 88, 105, 6, 3.0, 3, 102, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (107, 9, 3, 3, 5, 3, 3.0, 2, 90, 2, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (108, 0, 0, 0, 6, 9, 3.0, NULL, 109, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (109, 0, 9, 4, 25, 6, 3.0, NULL, 109, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (110, 5, 0, 3, 2, 9, 8.0, 2, 93, 2, 2.28, 1.337, 10, 1, 1);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (111, 0, 9, 4, 25, 6, 3.0, NULL, 110, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (112, 0, 0, 0, 6, 9, 3.0, 4, 111, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (113, 0, 9, 4, 25, 6, 3.0, 4, 111, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (114, 0, 0, 0, 6, 9, 3.0, 4, 116, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (115, 0, 9, 4, 25, 6, 3.0, 4, 116, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (116, 0, 0, 0, 6, 9, 3.0, 4, 118, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (117, 0, 9, 4, 25, 6, 3.0, 4, 118, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (118, 0, 0, 0, 6, 9, 3.0, 4, 119, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (119, 0, 9, 4, 25, 6, 3.0, 4, 119, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (125, 0, 0, 0, 6, 9, 3.0, 4, 120, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (126, 0, 9, 4, 25, 6, 3.0, 4, 120, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (127, 0, 0, 0, 6, 9, 3.0, 4, 121, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (128, 0, 9, 4, 25, 6, 3.0, 4, 121, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (129, 0, 0, 0, 6, 9, 3.0, 4, 122, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (130, 0, 9, 4, 25, 6, 3.0, 4, 122, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (131, 0, 0, 0, 6, 9, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (132, 0, 9, 4, 25, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (137, 0, 53246, 123, 6, 11, 3.0, 4, 123, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (138, 0, 9, 4, 228, 6, 3.0, 4, 123, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (142, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (143, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (156, 0, 53246, 123, 6, 11, 3.0, 4, 128, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (157, 0, 9, 4, 228, 6, 3.0, 4, 128, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (158, 0, 53246, 123, 6, 11, 3.0, 4, 129, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (159, 0, 9, 4, 228, 6, 3.0, 4, 129, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (160, 0, 53246, 123, 6, 11, 3.0, 4, 131, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (161, 0, 9, 4, 228, 6, 3.0, 4, 131, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (162, 0, 53246, 123, 6, 11, 3.0, 4, 132, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (163, 0, 9, 4, 228, 6, 3.0, 4, 132, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (164, 0, 53246, 123, 6, 11, 3.0, 4, 133, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (165, 0, 9, 4, 228, 6, 3.0, 4, 133, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (166, 0, 53246, 123, 6, 11, 3.0, 4, 134, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (167, 0, 9, 4, 228, 6, 3.0, 4, 134, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (168, 0, 53246, 123, 6, 11, 3.0, 4, 135, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (169, 0, 9, 4, 228, 6, 3.0, 4, 135, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (170, 0, 53246, 123, 6, 11, 3.0, 4, 136, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (171, 0, 9, 4, 228, 6, 3.0, 4, 136, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (172, 0, 53246, 123, 6, 11, 3.0, 4, 137, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (173, 0, 9, 4, 228, 6, 3.0, 4, 137, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (174, 0, 53246, 123, 6, 11, 3.0, 4, 138, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (175, 0, 9, 4, 228, 6, 3.0, 4, 138, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (176, 0, 53246, 123, 6, 11, 3.0, 4, 139, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (177, 0, 9, 4, 228, 6, 3.0, 4, 139, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (178, 0, 53246, 123, 6, 11, 3.0, 4, 140, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (179, 0, 9, 4, 228, 6, 3.0, 4, 140, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (182, 0, 53246, 123, 6, 11, 3.0, 4, 142, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (183, 0, 9, 4, 228, 6, 3.0, 4, 142, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (184, 0, 53246, 123, 6, 11, 3.0, 4, 143, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (185, 0, 9, 4, 228, 6, 3.0, 4, 143, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (186, 0, 53246, 123, 6, 11, 3.0, 4, 144, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (187, 0, 9, 4, 228, 6, 3.0, 4, 144, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (188, 0, 53246, 123, 6, 11, 3.0, 4, 145, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (189, 0, 9, 4, 228, 6, 3.0, 4, 145, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (190, 0, 53246, 123, 6, 11, 3.0, 4, 146, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (191, 0, 9, 4, 228, 6, 3.0, 4, 146, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (192, 0, 53246, 123, 6, 11, 3.0, 4, 147, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (193, 0, 9, 4, 228, 6, 3.0, 4, 147, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (194, 0, 53246, 123, 6, 11, 3.0, 4, 148, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (195, 0, 9, 4, 228, 6, 3.0, 4, 148, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (196, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (197, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (198, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (199, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (200, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (201, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (202, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (203, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (204, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (205, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (206, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (207, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (208, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (209, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (210, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (211, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (217, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (218, 0, 53246, 123, 6, 11, 3.0, 4, NULL, 3, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (219, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO djangoForest_list (id, to0_2, from0_21To0_5, from0_6To1_0, from1_1to1_5, from1_5, max_height, id_breed_id, id_sample_id, id_type_of_reproduction_id, avg_diameter, avg_height, count_of_plants, id_undergrowth_id, main) VALUES (220, 0, 9, 4, 228, 6, 3.0, 4, NULL, 1, NULL, NULL, NULL, NULL, 0);

-- Table: djangoForest_listregion
CREATE TABLE IF NOT EXISTS "djangoForest_listregion" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "date" date NOT NULL, "sample_region" varchar(300) NOT NULL, "soil_lot" varchar(300) NOT NULL, "id_quarter_id" bigint NULL REFERENCES "djangoForest_quarter" ("id") DEFERRABLE INITIALLY DEFERRED, "mark_del" bool NOT NULL);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (5, '2022-10-28', '12,6га', '21.78', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (7, '2022-10-18', '2', '21.3', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (8, '2022-10-18', '2', '21.3', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (9, '2022-10-18', '2', '21.3', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (10, '2022-10-18', '2', '21.3', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (11, '2022-10-18', '3', '21.32', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (12, '2022-10-28', '3', '21.11', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (13, '2022-10-28', '3', '21.78', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (14, '2022-12-03', '3', '81,08', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (15, '2022-12-04', '9,1га', '21,08', 4, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (16, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (17, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (18, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (19, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (20, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (21, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (22, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (23, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (24, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (25, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (26, '2022-12-04', '3', '21,24', 5, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (27, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (28, '2022-12-04', '3', '21,24', 7, 0);
INSERT INTO djangoForest_listregion (id, date, sample_region, soil_lot, id_quarter_id, mark_del) VALUES (29, '2022-12-04', '3', '21,24', 7, 0);

-- Table: djangoForest_methodofreforestation
CREATE TABLE IF NOT EXISTS "djangoForest_methodofreforestation" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_of_method" varchar(350) NOT NULL);
INSERT INTO djangoForest_methodofreforestation (id, name_of_method) VALUES (2, 'искусственное лесовосстановление');
INSERT INTO djangoForest_methodofreforestation (id, name_of_method) VALUES (3, 'комбинированное лесовосстановление');
INSERT INTO djangoForest_methodofreforestation (id, name_of_method) VALUES (4, 'естественное вследствие мер содействия лесовосстановлению');
INSERT INTO djangoForest_methodofreforestation (id, name_of_method) VALUES (5, 'естественное вследсвие мер природных процессов');

-- Table: djangoForest_photopoint
CREATE TABLE IF NOT EXISTS "djangoForest_photopoint" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "photo" varchar(100) NOT NULL, "id_sample_id" bigint NULL REFERENCES "djangoForest_sample" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (1, 'photos/2022/12/25/_7XxUu2pS6o.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (6, 'photos/2023/02/19/qSCC-qGhXN4_HvW2qUy.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (9, 'photos/2023/02/19/Sad_pepe.png', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (10, 'photos/2023/02/19/Sad_pepe_5glWzj4.png', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (11, 'photos/2023/02/19/qSCC-qGhXN4.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (12, 'photos/2023/02/19/qSCC-qGhXN4_PsvWhQl.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (15, 'photos/2023/02/19/qSCC-qGhXN4_yAYL93U.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (16, 'photos/2023/02/19/qSCC-qGhXN4_dppvgiU.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (19, 'photos/2023/02/19/qSCC-qGhXN4_bXxAW4D.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (20, 'photos/2023/02/19/qSCC-qGhXN4_EzZ7cKc.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (22, 'photos/2023/02/19/qSCC-qGhXN4_5Jd0SCR.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (23, 'photos/2023/02/19/qSCC-qGhXN4_rIPRyLI.jpg', NULL);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (24, 'photos/2023/02/19/qSCC-qGhXN4_JfeLvX0.jpg', 3);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (25, 'photos/2023/02/19/qSCC-qGhXN4_tSp1AsH.jpg', 3);
INSERT INTO djangoForest_photopoint (id, photo, id_sample_id) VALUES (26, 'photos/2023/02/19/qSCC-qGhXN4_Re7AzFx.jpg', 98);

-- Table: djangoForest_post
CREATE TABLE IF NOT EXISTS "djangoForest_post" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "post_name" varchar(255) NOT NULL);
INSERT INTO djangoForest_post (id, post_name) VALUES (1, '[aspf[h''cb');
INSERT INTO djangoForest_post (id, post_name) VALUES (2, 'фывафвар');
INSERT INTO djangoForest_post (id, post_name) VALUES (3, 'ячсимп');
INSERT INTO djangoForest_post (id, post_name) VALUES (4, 'йцуе');
INSERT INTO djangoForest_post (id, post_name) VALUES (5, 'пролк');
INSERT INTO djangoForest_post (id, post_name) VALUES (7, 'asdgg');
INSERT INTO djangoForest_post (id, post_name) VALUES (8, '((((((((((((((((((');
INSERT INTO djangoForest_post (id, post_name) VALUES (9, '((((((((((((((((((');

-- Table: djangoForest_profile
CREATE TABLE IF NOT EXISTS "djangoForest_profile" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "FIO" varchar(255) NOT NULL, "phoneNumber" varchar(30) NOT NULL, "id_branches_id" bigint NULL REFERENCES "djangoForest_branches" ("id") DEFERRABLE INITIALLY DEFERRED, "id_post_id" bigint NULL REFERENCES "djangoForest_post" ("id") DEFERRABLE INITIALLY DEFERRED, "id_role_id" bigint NULL REFERENCES "djangoForest_role" ("id") DEFERRABLE INITIALLY DEFERRED, "id_user_id" bigint NULL REFERENCES "djangoForest_users" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_profile (id, FIO, phoneNumber, id_branches_id, id_post_id, id_role_id, id_user_id) VALUES (1, 'Иванов Павел Петрович', '88005553535', 1, 1, 1, 2);
INSERT INTO djangoForest_profile (id, FIO, phoneNumber, id_branches_id, id_post_id, id_role_id, id_user_id) VALUES (2, 'Чин Чопа Чопик', '88005436535', 1, 1, 1, NULL);
INSERT INTO djangoForest_profile (id, FIO, phoneNumber, id_branches_id, id_post_id, id_role_id, id_user_id) VALUES (3, 'Кузнецов Даниил Дмитриевич', '88005553535', 4, 3, 2, 4);

-- Table: djangoForest_purposeofforests
CREATE TABLE IF NOT EXISTS "djangoForest_purposeofforests" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_purpose" varchar(350) NOT NULL);
INSERT INTO djangoForest_purposeofforests (id, name_purpose) VALUES (2, 'Эксплуатационные');
INSERT INTO djangoForest_purposeofforests (id, name_purpose) VALUES (3, 'Защитные');
INSERT INTO djangoForest_purposeofforests (id, name_purpose) VALUES (4, 'Резервные');

-- Table: djangoForest_quarter
CREATE TABLE IF NOT EXISTS "djangoForest_quarter" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "quarter_name" varchar(50) NOT NULL, "id_district_forestly_id" bigint NULL REFERENCES "djangoForest_districtforestly" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_quarter (id, quarter_name, id_district_forestly_id) VALUES (4, '78', 6);
INSERT INTO djangoForest_quarter (id, quarter_name, id_district_forestly_id) VALUES (5, '111', 8);
INSERT INTO djangoForest_quarter (id, quarter_name, id_district_forestly_id) VALUES (6, '123', 7);
INSERT INTO djangoForest_quarter (id, quarter_name, id_district_forestly_id) VALUES (7, '78', 7);

-- Table: djangoForest_reproduction
CREATE TABLE IF NOT EXISTS "djangoForest_reproduction" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_reproduction" varchar(500) NOT NULL);
INSERT INTO djangoForest_reproduction (id, name_reproduction) VALUES (1, 'Искусственное восстановление');
INSERT INTO djangoForest_reproduction (id, name_reproduction) VALUES (2, 'Естественное восстановление(семенное)');
INSERT INTO djangoForest_reproduction (id, name_reproduction) VALUES (3, 'Естественное восстановление (вегетативное)');

-- Table: djangoForest_role
CREATE TABLE IF NOT EXISTS "djangoForest_role" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_role" varchar(300) NOT NULL);
INSERT INTO djangoForest_role (id, name_role) VALUES (1, 'asdF');
INSERT INTO djangoForest_role (id, name_role) VALUES (2, 'afgf');
INSERT INTO djangoForest_role (id, name_role) VALUES (4, 'ыварр');
INSERT INTO djangoForest_role (id, name_role) VALUES (5, 'ASDAGDFSHZXCCZXCBSD');
INSERT INTO djangoForest_role (id, name_role) VALUES (6, 'ZXCЯЧС');

-- Table: djangoForest_sample
CREATE TABLE IF NOT EXISTS "djangoForest_sample" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "date" date NULL, "sample_area" real NOT NULL, "id_list_region_id" bigint NULL REFERENCES "djangoForest_listregion" ("id") DEFERRABLE INITIALLY DEFERRED, "id_profile_id" bigint NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED, "id_quarter_id" bigint NULL REFERENCES "djangoForest_quarter" ("id") DEFERRABLE INITIALLY DEFERRED, "soil_lot" varchar(300) NOT NULL, "lenght" real NULL, "square" real NULL, "width" real NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (3, '2022-10-18', 0.02, 5, 1, 6, '0', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (90, '2022-10-18', 0.05, 5, 1, 4, '21.78', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (91, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (92, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (93, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (94, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (95, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (96, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (97, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (98, '2022-10-18', 0.05, 5, 1, 4, '21.3', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (99, '2022-10-18', 0.05, 5, 1, 4, '21.32', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (100, '2022-10-28', 0.05, 7, 1, 4, '21.11', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (101, '2022-10-28', 0.05, 5, 1, 4, '21.78', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (102, '2022-10-28', 0.05, 13, 1, 4, '21.78', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (104, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (105, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (106, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (107, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (108, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (109, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (110, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (111, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (112, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (113, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (114, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (115, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (116, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (117, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (118, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (119, '2022-12-04', 0.02, 24, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (120, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (121, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (122, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (123, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (124, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (125, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (126, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (127, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (128, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (129, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (130, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (131, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (132, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (133, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (134, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (135, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (136, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (137, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (138, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (139, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (140, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (141, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (142, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (143, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (144, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (145, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (146, '2022-12-04', 0.02, NULL, 1, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (147, '2022-12-04', 0.02, NULL, 2, 7, '21,24', NULL, NULL, NULL);
INSERT INTO djangoForest_sample (id, date, sample_area, id_list_region_id, id_profile_id, id_quarter_id, soil_lot, lenght, square, width) VALUES (148, '2022-12-04', 0.03, NULL, 2, 7, '21,24', NULL, NULL, NULL);

-- Table: djangoForest_subjectrf
CREATE TABLE IF NOT EXISTS "djangoForest_subjectrf" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_subject_RF" varchar(255) NOT NULL);
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (16, 'Новосибирская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (17, 'Ростовская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (21, 'Белгородская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (22, 'Ленинградская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (23, 'Курская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (24, 'Орловская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (27, 'Московская область');
INSERT INTO djangoForest_subjectrf (id, name_subject_RF) VALUES (31, 'Брянская область');

-- Table: djangoForest_table
CREATE TABLE IF NOT EXISTS "djangoForest_table" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name" varchar(300) NOT NULL, "age" integer NOT NULL);

-- Table: djangoForest_track
CREATE TABLE IF NOT EXISTS "djangoForest_track" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "data" date NOT NULL, "map" varchar(1) NOT NULL, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED);

-- Table: djangoForest_typeforestgrowingconditions
CREATE TABLE IF NOT EXISTS "djangoForest_typeforestgrowingconditions" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "subtypes_of_humidity" varchar(300) NOT NULL, "subtypes_of_rich" varchar(300) NOT NULL, "type_forest_growing_conditions" varchar(300) NOT NULL);
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (2, 'Очень сухие', 'Бедные', 'Боры');
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (3, 'Очень сухие', 'Боры', 'Боры');
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (4, 'Свежие', 'Бедные', 'Боры');
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (5, 'Влажные', 'Бедные', 'Боры');
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (6, 'Сырые', 'Бедные', 'Боры');
INSERT INTO djangoForest_typeforestgrowingconditions (id, subtypes_of_humidity, subtypes_of_rich, type_forest_growing_conditions) VALUES (7, 'Мокрые', 'Бедные', 'Боры');

-- Table: djangoForest_undergrowth
CREATE TABLE IF NOT EXISTS "djangoForest_undergrowth" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name" varchar(350) NOT NULL);
INSERT INTO djangoForest_undergrowth (id, name) VALUES (1, 'С');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (2, 'Е');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (3, 'В');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (4, 'Ос');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (5, 'Д');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (6, 'В');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (7, 'КР');
INSERT INTO djangoForest_undergrowth (id, name) VALUES (8, 'П');

-- Table: djangoForest_undergrowthbydefault
CREATE TABLE IF NOT EXISTS "djangoForest_undergrowthbydefault" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "id_profile_id" bigint NOT NULL REFERENCES "djangoForest_profile" ("id") DEFERRABLE INITIALLY DEFERRED, "id_undergrowth_id" bigint NOT NULL REFERENCES "djangoForest_undergrowth" ("id") DEFERRABLE INITIALLY DEFERRED);
INSERT INTO djangoForest_undergrowthbydefault (id, id_profile_id, id_undergrowth_id) VALUES (1, 2, 4);
INSERT INTO djangoForest_undergrowthbydefault (id, id_profile_id, id_undergrowth_id) VALUES (2, 2, 1);
INSERT INTO djangoForest_undergrowthbydefault (id, id_profile_id, id_undergrowth_id) VALUES (3, 1, 2);
INSERT INTO djangoForest_undergrowthbydefault (id, id_profile_id, id_undergrowth_id) VALUES (4, 2, 5);

-- Table: djangoForest_workingbreeds
CREATE TABLE IF NOT EXISTS "djangoForest_workingbreeds" ("id" integer NOT NULL PRIMARY KEY AUTOINCREMENT, "name_breeds" varchar(255) NOT NULL);
INSERT INTO djangoForest_workingbreeds (id, name_breeds) VALUES (1, 'Сосна');
INSERT INTO djangoForest_workingbreeds (id, name_breeds) VALUES (2, 'Ель');

-- Index: djangoForest_districtforestly_id_forestly_id_8d57b4f6
CREATE INDEX IF NOT EXISTS "djangoForest_districtforestly_id_forestly_id_8d57b4f6" ON "djangoForest_districtforestly" ("id_forestly_id");

-- Index: djangoForest_forestformingbydefault_id_breed_id_4a07abd1
CREATE INDEX IF NOT EXISTS "djangoForest_forestformingbydefault_id_breed_id_4a07abd1" ON "djangoForest_forestformingbydefault" ("id_breed_id");

-- Index: djangoForest_forestformingbydefault_id_profile_id_1a43bad2
CREATE INDEX IF NOT EXISTS "djangoForest_forestformingbydefault_id_profile_id_1a43bad2" ON "djangoForest_forestformingbydefault" ("id_profile_id");

-- Index: djangoForest_forestly_id_subject_rf_id_db160798
CREATE INDEX IF NOT EXISTS "djangoForest_forestly_id_subject_rf_id_db160798" ON "djangoForest_forestly" ("id_subject_rf_id");

-- Index: djangoForest_gps_id_sample_id_6877d75e
CREATE INDEX IF NOT EXISTS "djangoForest_gps_id_sample_id_6877d75e" ON "djangoForest_gps" ("id_sample_id");

-- Index: djangoForest_list_id_breed_id_818099fb
CREATE INDEX IF NOT EXISTS "djangoForest_list_id_breed_id_818099fb" ON "djangoForest_list" ("id_breed_id");

-- Index: djangoForest_list_id_sample_id_a446b4c9
CREATE INDEX IF NOT EXISTS "djangoForest_list_id_sample_id_a446b4c9" ON "djangoForest_list" ("id_sample_id");

-- Index: djangoForest_list_id_type_of_reproduction_id_3f515d72
CREATE INDEX IF NOT EXISTS "djangoForest_list_id_type_of_reproduction_id_3f515d72" ON "djangoForest_list" ("id_type_of_reproduction_id");

-- Index: djangoForest_list_id_undergrowth_id_02d0f36b
CREATE INDEX IF NOT EXISTS "djangoForest_list_id_undergrowth_id_02d0f36b" ON "djangoForest_list" ("id_undergrowth_id");

-- Index: djangoForest_listregion_id_quarter_id_cd3b2ab8
CREATE INDEX IF NOT EXISTS "djangoForest_listregion_id_quarter_id_cd3b2ab8" ON "djangoForest_listregion" ("id_quarter_id");

-- Index: djangoForest_photopoint_id_sample_id_9a160505
CREATE INDEX IF NOT EXISTS "djangoForest_photopoint_id_sample_id_9a160505" ON "djangoForest_photopoint" ("id_sample_id");

-- Index: djangoForest_profile_id_branches_id_ecc09e8c
CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_branches_id_ecc09e8c" ON "djangoForest_profile" ("id_branches_id");

-- Index: djangoForest_profile_id_post_id_3ebc259a
CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_post_id_3ebc259a" ON "djangoForest_profile" ("id_post_id");

-- Index: djangoForest_profile_id_role_id_a92c9632
CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_role_id_a92c9632" ON "djangoForest_profile" ("id_role_id");

-- Index: djangoForest_profile_id_user_id_a5391657
CREATE INDEX IF NOT EXISTS "djangoForest_profile_id_user_id_a5391657" ON "djangoForest_profile" ("id_user_id");

-- Index: djangoForest_quarter_id_district_forestly_id_71afc36f
CREATE INDEX IF NOT EXISTS "djangoForest_quarter_id_district_forestly_id_71afc36f" ON "djangoForest_quarter" ("id_district_forestly_id");

-- Index: djangoForest_sample_id_list_region_id_d4ea853d
CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_list_region_id_d4ea853d" ON "djangoForest_sample" ("id_list_region_id");

-- Index: djangoForest_sample_id_profile_id_326ca965
CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_profile_id_326ca965" ON "djangoForest_sample" ("id_profile_id");

-- Index: djangoForest_sample_id_quarter_id_eb7f4fa2
CREATE INDEX IF NOT EXISTS "djangoForest_sample_id_quarter_id_eb7f4fa2" ON "djangoForest_sample" ("id_quarter_id");

-- Index: djangoForest_track_id_profile_id_07b234ed
CREATE INDEX IF NOT EXISTS "djangoForest_track_id_profile_id_07b234ed" ON "djangoForest_track" ("id_profile_id");

-- Index: djangoForest_undergrowthbydefault_id_profile_id_23b572fe
CREATE INDEX IF NOT EXISTS "djangoForest_undergrowthbydefault_id_profile_id_23b572fe" ON "djangoForest_undergrowthbydefault" ("id_profile_id");

-- Index: djangoForest_undergrowthbydefault_id_undergrowth_id_b789efb4
CREATE INDEX IF NOT EXISTS "djangoForest_undergrowthbydefault_id_undergrowth_id_b789efb4" ON "djangoForest_undergrowthbydefault" ("id_undergrowth_id");

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
