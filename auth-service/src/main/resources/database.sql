CREATE
DATABASE db_auth_banking

CREATE TABLE "roles"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "role_name"  varchar               NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "version"    bigint                NOT NULL DEFAULT 0,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "account_roles"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "user_id"    bigint                NOT NULL,
    "role_id"    bigint                NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "version"    bigint                NOT NULL DEFAULT 0,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "accounts"
(
    "id"                             BIGSERIAL PRIMARY KEY NOT NULL,
    "email"                          varchar               NOT NULL UNIQUE,
    "phone"                          varchar               NOT NULL UNIQUE,
    "is_valid_login"                 bool                  NOT NULL,
    "is_one_device"                  bool                  NOT NULL,
    "password"                       varchar               NOT NULL,
    "personal_identification_number" varchar(50)           NOT NULL UNIQUE,
    "otp"                            varchar,
    "user_id"                        bigint                NOT NULL,
    "is_active"                      boolean               NOT NULL DEFAULT true,
    "version"                        bigint                NOT NULL DEFAULT 0,
    "created_at"                     bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                     bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

ALTER TABLE "account_roles"
    ADD FOREIGN KEY ("user_id") REFERENCES "accounts" ("id");

ALTER TABLE "account_roles"
    ADD FOREIGN KEY ("role_id") REFERENCES "roles" ("id");