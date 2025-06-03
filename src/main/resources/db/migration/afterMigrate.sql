SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM tb_workout_exercise;
DELETE FROM tb_workout_register;
DELETE FROM tb_user_role;
DELETE FROM tb_workout;
DELETE FROM tb_subscription;
DELETE FROM tb_skin_fold;
DELETE FROM tb_assessment;
DELETE FROM tb_exercise;
DELETE FROM tb_plan;
DELETE FROM tb_role;
DELETE FROM tb_personal;
DELETE FROM tb_member;
DELETE FROM tb_user;

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

SET FOREIGN_KEY_CHECKS = 1;

-- Populando tb_role
INSERT INTO tb_role (id, name) VALUES
                                   (1, 'ROLE_ADMIN'),
                                   (2, 'ROLE_MEMBER'),
                                   (3, 'ROLE_PERSONAL');

-- Populando tb_user
INSERT INTO tb_user (id, height, birth_date, weight, cpf, email, address, name, profile_img_url, password) VALUES
                                                                                                               (1, 1.75, '1990-01-01', 70.0, '12345678900', 'admin@example.com', 'Rua A, 123', 'Admin', NULL, 'admin123'),
                                                                                                               (2, 1.80, '1995-06-15', 80.0, '98765432100', 'personal@example.com', 'Rua B, 456', 'João', NULL, 'personal123'),
                                                                                                               (3, 1.65, '2000-08-22', 60.0, '45678912300', 'member@example.com', 'Rua C, 789', 'Ana', NULL, 'member123');

-- Populando tb_personal
INSERT INTO tb_personal (id, hire_date, salary) VALUES
    (2, '2022-02-01', 3500.0);

-- Populando tb_member
INSERT INTO tb_member (id, is_active, created_at) VALUES
    (3, 1, NOW());

-- Populando tb_user_role
INSERT INTO tb_user_role (user_id, role_id) VALUES
                                                (1, 1),
                                                (2, 2),
                                                (3, 2);

-- Populando tb_plan
INSERT INTO tb_plan (id, plan, value) VALUES
                                          (1, 'MONTHLY', 99.90),
                                          (2, 'QUARTERLY', 270.00),
                                          (3, 'ANNUAL', 900.00);

-- Populando tb_subscription
INSERT INTO tb_subscription (id, member_id, plan_id, expiration_date, payment_status) VALUES
    (1, 3, 1, '2025-06-10', 'PAGO');

-- Populando tb_exercise
INSERT INTO tb_exercise (id, name, max_reps, min_reps, number_of_sets) VALUES
                                                                           (1, 'Supino Reto', 12, 8, 4),
                                                                           (2, 'Agachamento', 15, 10, 4);

-- Populando tb_workout
INSERT INTO tb_workout (id, member_id, personal_id, name) VALUES
    (1, 3, 2, 'Treino A');

-- Populando tb_workout_exercise
INSERT INTO tb_workout_exercise (workout_id, exercise_id) VALUES
                                                              (1, 1),
                                                              (1, 2);

-- Populando tb_workout_register
INSERT INTO tb_workout_register (id, workout_id, start_date, end_date)
    VALUES
        (1, 1, '2025-05-01 08:00:00', '2025-05-01 09:00:00');

-- Populando tb_assessment
INSERT INTO tb_assessment (id, member_id, personal_id, date, imc, bodyfat_percentage, weight, note, status)
    VALUES
        (1, 3, 2, '2025-05-01 10:00:00', 22.5, 18.0, 60.0, 'Avaliação inicial', 'COMPLETED');

-- Populando tb_skin_fold
INSERT INTO tb_skin_fold (id, assessment_id, fold, mm)
    VALUES
        (1, 1, 'TRICEPS', 12.0),
        (2, 1, 'ABDOMEN', 18.5);
