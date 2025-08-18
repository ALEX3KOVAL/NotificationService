CREATE TABLE "public".phone_sending
(
    id         serial PRIMARY KEY,
    recipient  VARCHAR(11) NOT NULL,
    code       smallint    NOT NULL,
    status     smallint    NOT NULL,
    updated_at timestamp   NOT NULL,
    created_at timestamp   NOT NULL
);
