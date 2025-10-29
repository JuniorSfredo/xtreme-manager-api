SET
FOREIGN_KEY_CHECKS = 0;

DELETE
FROM tb_workout_exercise;
DELETE
FROM tb_workout_register;
DELETE
FROM tb_user_role;
DELETE
FROM tb_workout;
DELETE
FROM tb_subscription;
DELETE
FROM tb_skin_fold;
DELETE
FROM tb_assessment;
DELETE
FROM tb_exercise;
DELETE
FROM tb_plan;
DELETE
FROM tb_role;
DELETE
FROM tb_personal;
DELETE
FROM tb_member;
DELETE
FROM tb_user;

ALTER TABLE tb_member AUTO_INCREMENT = 1;
ALTER TABLE tb_assessment AUTO_INCREMENT = 1;
ALTER TABLE tb_skin_fold AUTO_INCREMENT = 1;
ALTER TABLE tb_exercise AUTO_INCREMENT = 1;
ALTER TABLE tb_plan AUTO_INCREMENT = 1;
ALTER TABLE tb_subscription AUTO_INCREMENT = 1;
ALTER TABLE tb_personal AUTO_INCREMENT = 1;
ALTER TABLE tb_workout AUTO_INCREMENT = 1;
ALTER TABLE tb_workout_exercise AUTO_INCREMENT = 1;
ALTER TABLE tb_workout_register AUTO_INCREMENT = 1;
ALTER TABLE tb_user_role AUTO_INCREMENT = 1;
ALTER TABLE tb_role AUTO_INCREMENT = 1;
ALTER TABLE tb_user AUTO_INCREMENT = 1;

SET
FOREIGN_KEY_CHECKS = 1;

-- Populando tb_role
INSERT INTO tb_role (id, name)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_MEMBER'),
       (3, 'ROLE_PERSONAL');

-- Populando tb_user
INSERT INTO tb_user (id, height, birth_date, weight, cpf, email, address, name, profile_img_url, password)
VALUES (1, 1.75, '1990-01-01', 70.0, '12345678900', 'admin@example.com', 'Rua A, 123', 'Admin', NULL, 'admin123'),
       (2, 1.80, '1995-06-15', 80.0, '98765432100', 'personal@example.com', 'Rua B, 456', 'João', NULL, 'personal123'),
       (3, 1.65, '2000-08-22', 60.0, '45678912300', 'member@example.com', 'Rua C, 789', 'Ana', NULL, 'member123');

-- Populando tb_personal
INSERT INTO tb_personal (id, hire_date, salary)
VALUES (2, '2022-02-01', 3500.0);

-- Populando tb_member
INSERT INTO tb_member (id, is_active, created_at)
VALUES (3, 1, CURRENT_TIMESTAMP);

-- Populando tb_user_role
INSERT INTO tb_user_role (user_id, role_id)
VALUES (1, 1),
       (2, 3),
       (3, 2);

-- Populando tb_plan
INSERT INTO tb_plan (id, plan, value)
VALUES (1, 'MONTHLY', 99.90),
       (2, 'QUARTERLY', 270.00),
       (3, 'ANNUAL', 900.00);

-- Populando tb_subscription
INSERT INTO tb_subscription (id, member_id, plan_id, expiration_date, payment_status, created_at)
VALUES (1, 3, 1, '2025-06-10', 'WAITING_PAYMENT', CURRENT_TIMESTAMP);

-- Populando tb_exercise
INSERT INTO tb_exercise (id, name, max_reps, min_reps, series_number)
VALUES (1, 'Supino Reto', 12, 8, 4),
       (2, 'Agachamento', 15, 10, 4);

-- Populando tb_workout
INSERT INTO tb_workout (id, member_id, personal_id, name)
VALUES (1, 3, 2, 'BACK');

-- Populando tb_workout_exercise
INSERT INTO tb_workout_exercise (workout_id, exercise_id)
VALUES (1, 1),
       (1, 2);

-- Populando tb_workout_register
INSERT INTO tb_workout_register (id, workout_id, start_date, end_date)
VALUES
    (10, 1, '2025-08-11 06:30:00', '2025-08-11 07:15:00'), -- segunda
    (11, 1, '2025-08-12 06:30:00', '2025-08-12 07:15:00'), -- terça
    (12, 1, '2025-08-13 06:30:00', '2025-08-13 07:15:00'), -- quarta
    (13, 1, '2025-08-14 19:30:00', '2025-08-14 20:15:00'); -- quinta

-- Populando tb_assessment
INSERT INTO tb_assessment (id, member_id, personal_id, date, imc, bodyfat_percentage, weight, note, status)
VALUES (1, 3, 2, '2025-05-01 10:00:00', 22.5, 18.0, 60.0, 'Avaliação inicial', 'COMPLETED'),
       (2, 3, 2, '2025-05-04 09:30:00', 23.0, 17.5, 61.5, 'Avaliação de acompanhamento 1', 'COMPLETED'),
       (3, 3, 2, '2025-05-07 08:45:00', 23.5, 17.0, 62.0, 'Avaliação de acompanhamento 2', 'COMPLETED'),
       (4, 3, 2, '2025-05-10 09:15:00', 24.0, 16.5, 62.5, 'Avaliação de acompanhamento 3', 'COMPLETED'),
       (5, 3, 2, '2025-05-13 10:45:00', 24.5, 16.0, 63.0, 'Avaliação de acompanhamento 4', 'COMPLETED'),
       (6, 3, 2, '2025-05-16 08:30:00', 25.0, 15.5, 63.5, 'Avaliação de acompanhamento 5', 'COMPLETED'),
       (7, 3, 2, '2025-05-19 09:00:00', 25.5, 15.0, 64.0, 'Avaliação de acompanhamento 6', 'COMPLETED'),
       (8, 3, 2, '2025-05-22 10:20:00', 26.0, 14.5, 64.5, 'Avaliação de acompanhamento 7', 'COMPLETED'),
       (9, 3, 2, '2025-05-25 09:50:00', 26.5, 14.0, 65.0, 'Avaliação de acompanhamento 8', 'COMPLETED'),
       (10, 3, 2, '2025-06-01 10:00:00', 22.5, 18.0, 60.0, 'Avaliação inicial', 'COMPLETED'),
       (11, 3, 2, '2025-07-01 18:00:00', 22.5, 18.0, 60.0, 'Avaliação inicial', 'COMPLETED');

-- Populando tb_skin_fold
INSERT INTO tb_skin_fold (id, assessment_id, fold, mm)
VALUES (1, 1, 'TRICEPS', 12.0),
       (2, 1, 'ABDOMEN', 18.5);

INSERT INTO tb_skin_fold (id, assessment_id, fold, mm)
VALUES (3, 11, 'TRICEPS', 12.0),
       (4, 11, 'ABDOMEN', 18.5);
