CREATE TABLE "users"
(
    "id"                                BIGSERIAL PRIMARY KEY NOT NULL,
    "email"                             varchar               NOT NULL,
    "phone"                             varchar               NOT NULL,
    "personal_information_id"           bigint,
    "identify_documnent_information_id" bigint,
    "is_active"                         boolean               NOT NULL DEFAULT true,
    "version"                           bigint                NOT NULL DEFAULT 0,
    "created_at"                        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "personal_information"
(
    "id"                BIGSERIAL PRIMARY KEY NOT NULL,
    "full_name"         varchar(255)          NOT NULL,
    "date_of_birth"     bigint                NOT NULL,
    "sex"               varchar(20)           NOT NULL,
    "personal_photo"    varchar,
    "permanent_address" bigint,
    "is_active"         boolean               NOT NULL DEFAULT true,
    "version"           bigint                NOT NULL DEFAULT 0,
    "created_at"        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "identity_document_information"
(
    "id"                             BIGSERIAL PRIMARY KEY NOT NULL,
    "personal_identification_number" varchar(50)           NOT NULL,
    "issued_at"                      bigint                NOT NULL,
    "issue_place"                    bigint,
    "citizen_id_front"               varchar               NOT NULL,
    "citzen_id_back"                 varchar               NOT NULL,
    "is_active"                      boolean               NOT NULL DEFAULT true,
    "version"                        bigint                NOT NULL DEFAULT 0,
    "created_at"                     bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                     bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "user_location_details"
(
    "id"           BIGSERIAL PRIMARY KEY NOT NULL,
    "contry"       varchar[100] NOT NULL,
    "province"     varchar[100] NOT NULL,
    "district"     varchar[100] NOT NULL,
    "ward"         varchar[100] NOT NULL,
    "street"       varchar[100] NOT NULL,
    "homes_number" varchar[100] NOT NULL,
    "is_active"    boolean               NOT NULL DEFAULT true,
    "version"      bigint                NOT NULL DEFAULT 0,
    "created_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "document_location_details"
(
    "id"           BIGSERIAL PRIMARY KEY NOT NULL,
    "country"      varchar[100] NOT NULL,
    "province"     varchar[100] NOT NULL,
    "district"     varchar[100] NOT NULL,
    "ward"         varchar[100] NOT NULL,
    "street"       varchar[100] NOT NULL,
    "homes_number" varchar[100] NOT NULL,
    "is_active"    boolean               NOT NULL DEFAULT true,
    "version"      bigint                NOT NULL DEFAULT 0,
    "created_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"   bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

ALTER TABLE "users"
    ADD FOREIGN KEY ("personal_information_id") REFERENCES "personal_information" ("id");

ALTER TABLE "users"
    ADD FOREIGN KEY ("identify_documnent_information_id") REFERENCES "identity_document_information" ("id");

ALTER TABLE "personal_information"
    ADD FOREIGN KEY ("permanent_address") REFERENCES "user_location_details" ("id");

ALTER TABLE "identity_document_information"
    ADD FOREIGN KEY ("issue_place") REFERENCES "document_location_details" ("id");
