drop keyspace if exists fe_teKeyspace;
create keyspace fe_teKeyspace
    with replication = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    }
    and durable_writes = true;

CREATE TABLE fe_teKeyspace.trn_event_value (
	log_date timestamp,
	log_id text,
	value text,
	log_date_time timestamp,
	original_value text,
	subject text,
	updated_at timestamp,
	updated_by text,
	PRIMARY KEY (( log_date, log_id ), value, log_date_time)
);
CREATE INDEX event_value_subject_index ON fe_teKeyspace.trn_event_value (subject);