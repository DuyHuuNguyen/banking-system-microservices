CREATE TABLE "wallets"
(
    "id"               BIGSERIAL PRIMARY KEY NOT NULL,
    "balance"          float                 NOT NULL,
    "currency"         varchar[100] NOT NULL,
    "user_id"          bigint                NOT NULL,
    "wallet_detail_id" bigint,
    "is_active"        boolean               NOT NULL DEFAULT true,
    "version"          bigint                NOT NULL DEFAULT 0,
    "created_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "wallet_details"
(
    "id"          BIGSERIAL PRIMARY KEY NOT NULL,
    "wallet_name" varchar(255)          NOT NULL,
    "description" text,
    "is_active"   boolean               NOT NULL DEFAULT true,
    "version"     bigint                NOT NULL DEFAULT 0,
    "created_at"  bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"  bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "funds"
(
    "id"           BIGSERIAL PRIMARY KEY NOT NULL,
    "fake_balance" float                 NOT NULL,
    "fund_name"    varchar(255)          NOT NULL,
    "description"  text,
    "wallet_id"    bigint,
    "is_active"    boolean               NOT NULL DEFAULT true,
    "version"      bigint                NOT NULL DEFAULT 0,
    "created_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

ALTER TABLE "wallets"
    ADD FOREIGN KEY ("wallet_detail_id") REFERENCES "wallet_details" ("id");

ALTER TABLE "funds"
    ADD FOREIGN KEY ("wallet_id") REFERENCES "wallets" ("id");
