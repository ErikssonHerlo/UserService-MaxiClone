CREATE TABLE IF NOT EXISTS `user` (
    `email` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `auth_code` VARCHAR(255) DEFAULT NULL,
    `auth_code_expiration` TIMESTAMP NULL DEFAULT NULL,
    `role` ENUM('STORE', 'SUPERVISOR', 'WAREHOUSE', 'ADMINISTRATOR') NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted_at` TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (`email`),
    UNIQUE KEY `email_UNIQUE` (`email`),
    INDEX `idx_user_email` (`email`),
    INDEX `idx_user_auth_code` (`auth_code`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
