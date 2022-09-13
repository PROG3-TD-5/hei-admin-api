create extension if not exists "uuid-ossp";

create table if not exists "event"
(
    id                varchar
        constraint event_pk primary key                 default uuid_generate_v4(),
    event_type              varchar                  not null,
    start_time               varchar                  not null,
    end_time               varchar                  not null,
    place_id               varchar                  not null
    constraint event_place_id_fk references "place"(id)
);

create index if not exists event_place_id_index on "event" (place_id);