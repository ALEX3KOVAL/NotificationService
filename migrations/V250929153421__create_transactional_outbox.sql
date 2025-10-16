CREATE TABLE "public".transactional_outbox
(
    id         bigserial PRIMARY KEY,
    name       varchar(255) NOT NULL,
    topic      varchar(100) NOT NULL,
    status     smallint     NOT NULL,
    json       jsonb        NOT NULL,
    created_at timestamp    NOT NULL,
    updated_at timestamp    NOT NULL
);
