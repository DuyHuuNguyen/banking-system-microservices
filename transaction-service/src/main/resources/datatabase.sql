CREATE TABLE "transactions"
(
    "id"                         BIGSERIAL PRIMARY KEY NOT NULL,
    "transaction_balance"        float                 NOT NULL,
    "transaction_currency"       varchar               NOT NULL,
    "transaction_status"         varchar               NOT NULL,
    "transaction_type"           varchar               NOT NULL,
    "reference_code"             varchar               NOT NULL,
    "originator_information_id"  bigint                NOT NULL,
    "beneficiary_information_id" bigint                NOT NULL,
    "is_active"                  boolean               NOT NULL DEFAULT true,
    "version"                    bigint                NOT NULL DEFAULT 0,
    "created_at"                 bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric,
    "updated_at"                 bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric
);

CREATE TABLE "transaction_fees"
(
    "id"                    BIGSERIAL PRIMARY KEY NOT NULL,
    "transaction_id"        bigint,
    "transaction_detail_id" bigint,
    "is_active"             boolean               NOT NULL DEFAULT true,
    "version"               bigint                NOT NULL DEFAULT 0,
    "created_at"            bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric,
    "updated_at"            bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric
);

CREATE TABLE "transaction_methods"
(
    "id"                BIGSERIAL PRIMARY KEY NOT NULL,
    "transaction_id"    bigint                NOT NULL,
    "payment_method_id" bigint                NOT NULL,
    "is_active"         boolean               NOT NULL DEFAULT true,
    "version"           bigint                NOT NULL DEFAULT 0,
    "created_at"        bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric,
    "updated_at"        bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric
);

CREATE TABLE "transaction_method_details"
(
    "id"                  BIGSERIAL PRIMARY KEY NOT NULL,
    "payment_method_name" varchar               NOT NULL,
    "is_active"           boolean               NOT NULL DEFAULT true,
    "version"             bigint                NOT NULL DEFAULT 0,
    "created_at"          bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric,
    "updated_at"          bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric
);

CREATE TABLE "transaction_fee_details"
(
    "id"              BIGSERIAL PRIMARY KEY NOT NULL,
    "name_fee"        varchar               NOT NULL,
    "transaction_fee" float                 NOT NULL,
    "currency"        varchar               NOT NULL,
    "is_active"       boolean               NOT NULL DEFAULT true,
    "version"         bigint                NOT NULL DEFAULT 0,
    "created_at"      bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric,
    "updated_at"      bigint                NOT NULL DEFAULT EXTRACT(epoch FROM now()) * 1000::numeric
);

ALTER TABLE "transaction_fees"
    ADD FOREIGN KEY ("transaction_id") REFERENCES "transactions" ("id");

ALTER TABLE "transaction_fees"
    ADD FOREIGN KEY ("transaction_detail_id") REFERENCES "transaction_fee_details" ("id");

ALTER TABLE "transaction_methods"
    ADD FOREIGN KEY ("transaction_id") REFERENCES "transactions" ("id");

ALTER TABLE "transaction_methods"
    ADD FOREIGN KEY ("payment_method_id") REFERENCES "transaction_method_details" ("id");
