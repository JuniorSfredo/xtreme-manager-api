package integration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestDataLoader {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void resetAndPopulateDatabase() {
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        // Limpeza das tabelas
        em.createNativeQuery("DELETE FROM tb_workout_exercise").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_workout_register").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_user_role").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_workout").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_subscription").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_skin_fold").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_assessment").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_exercise").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_plan").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_role").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_personal").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_member").executeUpdate();
        em.createNativeQuery("DELETE FROM tb_user").executeUpdate();

        // Reset auto-increment
        em.createNativeQuery("ALTER TABLE tb_member AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_assessment AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_skin_fold AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_exercise AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_plan AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_subscription AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_personal AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_workout AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_workout_exercise AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_workout_register AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_user_role AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_role AUTO_INCREMENT = 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE tb_user AUTO_INCREMENT = 1").executeUpdate();

        // Inserções
        em.createNativeQuery("INSERT INTO tb_role (id, name) VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_MEMBER'), (3, 'ROLE_PERSONAL')").executeUpdate();

        em.createNativeQuery(
                "INSERT INTO tb_user (id, height, birth_date, weight, cpf, email, address, name, profile_img_url, password) VALUES " +
                        "(1, 1.75, '1990-01-01', 70.0, '12345678900', 'admin@example.com', 'Rua A, 123', 'Admin', NULL, 'admin123'), " +
                        "(2, 1.80, '1995-06-15', 80.0, '98765432100', 'personal@example.com', 'Rua B, 456', 'João', NULL, 'personal123'), " +
                        "(3, 1.65, '2000-08-22', 60.0, '45678912300', 'member@example.com', 'Rua C, 789', 'Ana', NULL, 'member123')"
        ).executeUpdate();

        em.createNativeQuery("INSERT INTO tb_personal (id, hire_date, salary) VALUES (2, '2022-02-01', 3500.0)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_member (id, is_active, created_at) VALUES (3, 1, CURRENT_TIMESTAMP)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1), (2, 3), (3, 2)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_plan (id, plan, value) VALUES (1, 'MONTHLY', 99.90), (2, 'QUARTERLY', 270.00), (3, 'ANNUAL', 900.00)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_subscription (id, member_id, plan_id, expiration_date, payment_status, created_at) VALUES (1, 3, 1, '2025-06-10', 'PAID', CURRENT_TIMESTAMP)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_exercise (id, name, max_reps, min_reps, series_number) VALUES (1, 'Supino Reto', 12, 8, 4), (2, 'Agachamento', 15, 10, 4)").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_workout (id, member_id, personal_id, name) VALUES (1, 3, 2, 'BACK')").executeUpdate();
        em.createNativeQuery("INSERT INTO tb_workout_exercise (workout_id, exercise_id) VALUES (1, 1), (1, 2)").executeUpdate();

        // workouts_register
        em.createNativeQuery(
                "INSERT INTO tb_workout_register (id, workout_id, start_date, end_date) VALUES " +
                        "(10, 1, '2025-08-11 06:30:00', '2025-08-11 07:15:00'), " +
                        "(11, 1, '2025-08-12 06:30:00', '2025-08-12 07:15:00'), " +
                        "(12, 1, '2025-08-13 06:30:00', '2025-08-13 07:15:00'), " +
                        "(13, 1, '2025-08-14 19:30:00', '2025-08-14 20:15:00')"
        ).executeUpdate();

        // assessments
        em.createNativeQuery(
                "INSERT INTO tb_assessment (id, member_id, personal_id, date, imc, bodyfat_percentage, weight, note, status) VALUES " +
                        "(1,3,2,'2025-05-01 10:00:00',22.5,18.0,60.0,'Avaliação inicial','COMPLETED'), " +
                        "(2,3,2,'2025-05-04 09:30:00',23.0,17.5,61.5,'Avaliação de acompanhamento 1','COMPLETED')"
                // continue inserindo os demais conforme necessário
        ).executeUpdate();

        // skin folds
        em.createNativeQuery(
                "INSERT INTO tb_skin_fold (id, assessment_id, fold, mm) VALUES " +
                        "(1,1,'TRICEPS',12.0), " +
                        "(2,1,'ABDOMEN',18.5), " +
                        "(3,11,'TRICEPS',12.0), " +
                        "(4,11,'ABDOMEN',18.5)"
        ).executeUpdate();

        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }
}
