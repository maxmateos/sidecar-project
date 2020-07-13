CREATE TABLE `sidecar_health`.`blog_post` (
  `id` BIGINT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `post_body` TEXT NOT NULL,
  `user_id` BIGINT NULL,
  `created_at` datetime DEFAULT now(),
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `sidecar_health`.`user` (`id`)
    ON DELETE CASCADE
    ON UPDATE RESTRICT);
