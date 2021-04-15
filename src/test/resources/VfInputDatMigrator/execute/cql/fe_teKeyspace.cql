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

insert into fe_teKeyspace.trn_event_value(log_date, log_id, value, log_date_time, original_value, subject, updated_at, updated_by) values
(
	'2019-12-12 15:00:00+0900'
	,'TD1'
	,'INSPECTION'
	,'2019-12-12 14:59:10+0900'
	,'{
	"place_id":"70100999"
	,"serial_number":"1110002890"
	,"entry_date_time":"2020-01-08 08:51:51.357"
	,"work_status_key":"INSPECTION"
	,"status_reason_key":"その他"
	,"status_reason":"その他"
	,"note":"シナリオ9-2"
	,"entry_worker":"testadmin"
	,"cancel_flg":0
	,"cancel_date_time":null
	,"cancel_reason":""
	,"cancel_worker":""
	,"inspection_flg":0
	,"inspection_updated_at":"2020-01-08 08:51:51.357"
	,"created_by":"testadmin"
	,"created_at":"2020-01-08 08:51:51"
	,"updated_by":"testadmin"
	,"updated_at":"2020-01-08 08:51:51"
	}'
	,'IT00000051'
	,'2020-01-07 10:00:00+0900'
	,'DataMigrator');

insert into fe_teKeyspace.trn_event_value(log_date, log_id, value, log_date_time, original_value, subject, updated_at, updated_by) values
(
	'2019-12-12 15:00:00+0900'
	,'TD2'
	,'EJECTION'
	,'2019-12-12 14:59:20+0900'
	,'{
	"place_id":"70100999"
	,"serial_number":"1110002895"
	,"entry_date_time":"2020-01-09 02:10:22.977"
	,"work_status_key":"EJECTION"
	,"status_reason_key":"その他"
	,"status_reason":"その他"
	,"note":"シナリオ4"
	,"entry_worker":"testadmin"
	,"cancel_flg":1
	,"cancel_date_time":"2020-01-09 02:35:36.989"
	,"cancel_reason":"取消テスト"
	,"cancel_worker":"testadmin"
	,"inspection_flg":0
	,"inspection_updated_at":"2020-01-09 02:10:22.977"
	,"created_by":"testadmin"
	,"created_at":"2020-01-09 02:10:22"
	,"updated_by":"testadmin"
	,"updated_at":"2020-01-09 02:35:36"
	}'
	,'IT00000051'
	,'2020-01-07 10:00:00+0900'
	,'DataMigrator');

insert into fe_teKeyspace.trn_event_value(log_date, log_id, value, log_date_time, original_value, subject, updated_at, updated_by) values
(
	'2019-12-12 15:00:00+0900'
	,'TD3'
	,'CHK_OK'
	,'2019-12-12 14:59:30+0900'
	,'{
	"place_id":"70100999"
	,"serial_number":"IT00000051"
	,"entry_date_time":"2020-01-16 02:49:09.148"
	,"work_status_key":"CHK_OK"
	,"status_reason_key":"その他"
	,"status_reason":"その他"
	,"note":"外観検査保留→取消テスト"
	,"entry_worker":"testadmin"
	,"cancel_flg":0
	,"cancel_date_time":null
	,"cancel_reason":""
	,"cancel_worker":""
	,"inspection_flg":0
	,"inspection_updated_at":"2020-01-16 02:49:09.148"
	,"created_by":"testadmin"
	,"created_at":"2020-01-16 02:49:09"
	,"updated_by":"testadmin"
	,"updated_at":"2020-01-16 02:49:09"
	}'
	,'IT00000051'
	,'2020-01-07 10:00:00+0900'
	,'DataMigrator');

insert into fe_teKeyspace.trn_event_value(log_date, log_id, value, log_date_time, original_value, subject, updated_at, updated_by) values
(
	'2019-12-12 15:00:00+0900'
	,'TD4'
	,'GET_BACK'
	,'2019-12-12 14:59:40+0900'
	,'{
	"place_id":"70100999"
	,"serial_number":"IT00000051"
	,"entry_date_time":"2020-01-16 02:50:57.156"
	,"work_status_key":"GET_BACK"
	,"status_reason_key":"その他"
	,"status_reason":"その他"
	,"note":"外観検査OPチェック→取消テスト"
	,"entry_worker":"testadmin"
	,"cancel_flg":1
	,"cancel_date_time":"2020-01-16 02:51:29.087"
	,"cancel_reason":"外観検査OPチェック→取消テスト"
	,"cancel_worker":"testadmin"
	,"inspection_flg":0
	,"inspection_updated_at":"2020-01-16 02:50:57.156"
	,"created_by":"testadmin"
	,"created_at":"2020-01-16 02:50:57"
	,"updated_by":"testadmin"
	,"updated_at":"2020-01-16 02:51:29"
	}'
	,'IT00000051'
	,'2020-01-07 10:00:00+0900'
	,'DataMigrator');