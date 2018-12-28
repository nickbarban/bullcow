-- launch as root postgres user
CREATE user bullcow;
DROP DATABASE bullcow;
CREATE DATABASE bullcow;
CREATE SCHEMA bullcow;
grant all on database bullcow to bullcow;
grant all on all tables in schema bullcow to bullcow;
grant all on all sequences in schema bullcow to bullcow;