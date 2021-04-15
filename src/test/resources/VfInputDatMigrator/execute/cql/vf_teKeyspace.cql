drop keyspace if exists vf_teKeyspace;
create keyspace if not exists vf_teKeyspace
    with replication = {
        'class': 'SimpleStrategy',
        'replication_factor': 1
    }
    and durable_writes = true;

use vf_teKeyspace;

DROP TABLE if exists trn_event;
CREATE TABLE trn_event (
	log_id text,
	subject text,
	log_date_time timestamp,
	log_date timestamp,
	log_end_date_time timestamp,
	value_key text,
	value text,
	map_value map<timestamp,text>,
	updated_by text,
	updated_at timestamp,
	signal_id text,
	PRIMARY KEY ( (log_date, log_id) , subject, value_key, log_date_time )
);

CREATE INDEX trn_event_subject_index ON trn_event (subject);
CREATE INDEX trn_event_map_index ON trn_event (ENTRIES(map_value));
CREATE INDEX trn_event_value_key_index ON trn_event (value_key);
CREATE INDEX trn_event_signal_id_index ON trn_event (signal_id);