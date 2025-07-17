CREATE TABLE refresh_token (
                               id          BIGSERIAL PRIMARY KEY,
                               token       VARCHAR(255) NOT NULL UNIQUE,
                               expiry      TIMESTAMP   NOT NULL,
                               revoked_at  TIMESTAMP,
                               user_id     BIGINT      NOT NULL,
                               CONSTRAINT fk_refresh_token_user FOREIGN KEY(user_id)
                                   REFERENCES usuario(id)
                                   ON DELETE CASCADE
);
