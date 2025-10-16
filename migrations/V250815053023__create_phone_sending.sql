CREATE TABLE "public".phone_sending
(
    id         bigserial PRIMARY KEY,
    recipient  VARCHAR(11) NOT NULL,
    model      jsonb       NOT NULL,
    status     smallint    NOT NULL,
    updated_at timestamp   NOT NULL,
    created_at timestamp   NOT NULL
);
