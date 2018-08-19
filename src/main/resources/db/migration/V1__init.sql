create table transactions (
  id                  bigserial primary key,
  name                varchar,
  difference          float,
  attributes_map_json varchar
);
