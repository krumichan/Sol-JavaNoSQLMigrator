/* DB生成 -------------------------------------------------------------------------- */

DROP DATABASE IF EXISTS old_del;
CREATE DATABASE IF NOT EXISTS old_del;
USE old_del;

/* --------------------------------------------------------------------------------- */



/* テーブル生成--------------------------------------------------------------------- */

/* interlock_seq */
CREATE TABLE IF NOT EXISTS interlock_seq (
  exec_date_time datetime(6) NOT NULL,
  exec_dml varchar(64) NOT NULL,
  exec_table varchar(64) NOT NULL,
  key1 varchar(128) DEFAULT NULL,
  key2 varchar(128) DEFAULT NULL,
  key3 varchar(128) DEFAULT NULL,
  key4 varchar(128) DEFAULT NULL,
  PRIMARY KEY (exec_date_time,exec_table)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* interlock_visual_inspection */
CREATE TABLE IF NOT EXISTS interlock_visual_inspection (
  exec_date_time datetime(6) NOT NULL,
  exec_data_flag tinyint(1) NOT NULL,
  place_id varchar(16) NOT NULL,
  serial_number varchar(32) NOT NULL,
  entry_date_time datetime(3) NOT NULL,
  entry_worker varchar(64) NOT NULL,
  judgment_key varchar(32) NOT NULL,
  ng_work_status_key varchar(32) DEFAULT NULL,
  status_reason_key varchar(1280) DEFAULT NULL,
  status_reason varchar(1280) DEFAULT NULL,
  note varchar(128) DEFAULT NULL,
  created_by varchar(64) NOT NULL,
  created_at datetime NOT NULL,
  updated_by varchar(64) NOT NULL,
  updated_at datetime NOT NULL,
  PRIMARY KEY (exec_date_time,exec_data_flag,place_id,serial_number,entry_date_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* interlock_work_status */
CREATE TABLE IF NOT EXISTS interlock_work_status (
  exec_date_time datetime(6) NOT NULL,
  exec_data_flag tinyint(1) NOT NULL,
  place_id varchar(16) NOT NULL,
  serial_number varchar(32) NOT NULL,
  entry_date_time datetime(3) NOT NULL,
  work_status_key varchar(32) NOT NULL,
  status_reason_key varchar(1280) DEFAULT NULL,
  status_reason varchar(1280) DEFAULT NULL,
  note varchar(128) DEFAULT NULL,
  entry_worker varchar(128) NOT NULL,
  cancel_flg tinyint(1) NOT NULL,
  cancel_date_time datetime(3) DEFAULT NULL,
  cancel_reason varchar(128) DEFAULT NULL,
  cancel_worker varchar(128) DEFAULT NULL,
  inspection_flg tinyint(1) NOT NULL,
  inspection_updated_at datetime(3) DEFAULT NULL,
  created_by varchar(64) NOT NULL,
  created_at datetime NOT NULL,
  updated_by varchar(64) NOT NULL,
  updated_at datetime NOT NULL,
  PRIMARY KEY (exec_date_time,exec_data_flag,place_id,serial_number,entry_date_time,work_status_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* interlock_data_item */
CREATE TABLE IF NOT EXISTS interlock_data_item (
  exec_date_time datetime(6) NOT NULL,
  exec_data_flag tinyint(1) NOT NULL,
  log_id varchar(64) NOT NULL,
  element_index varchar(128) NOT NULL,
  element_index_key varchar(128) DEFAULT NULL,
  element varchar(128) NOT NULL,
  original_item_name varchar(128) NOT NULL,
  subject_type varchar(16) DEFAULT NULL,
  acquisition varchar(128) NOT NULL,
  calculation_point double DEFAULT NULL,
  generation_flag varchar(16) DEFAULT NULL,
  input_data_type varchar(128) DEFAULT NULL,
  output_data_format varchar(128) DEFAULT NULL,
  unit varchar(16) DEFAULT NULL,
  device varchar(128) NOT NULL,
  master_id varchar(32) DEFAULT NULL,
  changing_point varchar(16) DEFAULT NULL,
  host_name varchar(128) DEFAULT NULL,
  validity_start_on datetime NOT NULL,
  validity_end_on datetime NOT NULL,
  created_by varchar(64) NOT NULL,
  created_at datetime NOT NULL,
  updated_by varchar(64) NOT NULL,
  updated_at datetime NOT NULL,
  PRIMARY KEY (exec_date_time,exec_data_flag,log_id,validity_start_on,validity_end_on),
  KEY mas_ele_sub_index (master_id,element,subject_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* --------------------------------------------------------------------------------- */



/* 古いデータ----------------------------------------------------------------------- */

/* interlock_seq */
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-08 18:00:02.342955', 'INSERT', 'trn_visual_inspection', '70100999', '1110002890', '2020-01-08 09:00:02.342', 'CHK_NG');
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-08 18:00:02.343954', 'INSERT', 'trn_work_status', '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION');
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-09 10:36:13.530996', 'UPDATE', 'mst_data_item', '99999999-70100999-ngInsp', '2017-11-30 15:00:00', '2999-12-31 14:59:59', NULL);
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-15 13:40:55.731523', 'UPDATE', 'trn_work_status', '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION');
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-17 14:55:45.671251', 'INSERT', 'trn_visual_inspection', '70100999', 'IT00000051', '2020-01-17 05:55:45.670', 'CHK_OK');
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-24 10:49:40.589111', 'UPDATE', 'mst_data_item', '99999999-70100999-insp', '2017-11-30 15:00:00', '2999-12-31 14:59:59', NULL);
INSERT INTO interlock_seq (exec_date_time, exec_dml, exec_table, key1, key2, key3, key4) VALUES ('2020-01-27 13:45:44.400741', 'UPDATE', 'trn_work_status', '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION');

/* interlock_visual_inspection */
INSERT INTO interlock_visual_inspection (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, entry_worker, judgment_key, ng_work_status_key, status_reason_key, status_reason, note, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-08 18:00:02.342955', 1, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'testadmin', 'CHK_NG', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-08 09:00:02');
INSERT INTO interlock_visual_inspection (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, entry_worker, judgment_key, ng_work_status_key, status_reason_key, status_reason, note, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-17 14:55:45.671251', 1, '70100999', 'IT00000051', '2020-01-17 05:55:45.670', 't6e', 'CHK_OK', '', '', '', '外観検査OKテスト', 'testadmin', '2020-01-17 05:55:45', 'testadmin', '2020-01-17 05:55:45');

/* interlock_work_status */
INSERT INTO interlock_work_status (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, work_status_key, status_reason_key, status_reason, note, entry_worker, cancel_flg, cancel_date_time, cancel_reason, cancel_worker, inspection_flg, inspection_updated_at, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-08 18:00:02.343954', 1, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', 0, NULL, '', '', 0, '2020-01-08 09:00:02.342', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-08 09:00:02');
INSERT INTO interlock_work_status (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, work_status_key, status_reason_key, status_reason, note, entry_worker, cancel_flg, cancel_date_time, cancel_reason, cancel_worker, inspection_flg, inspection_updated_at, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-15 13:40:55.731523', 0, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', 0, NULL, '', '', 0, '2020-01-08 09:00:02.342', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-08 09:00:02');
INSERT INTO interlock_work_status (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, work_status_key, status_reason_key, status_reason, note, entry_worker, cancel_flg, cancel_date_time, cancel_reason, cancel_worker, inspection_flg, inspection_updated_at, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-15 13:40:55.731523', 1, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', 0, NULL, '', '', 1, '2020-01-15 04:40:55.729', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-15 04:40:55');
INSERT INTO interlock_work_status (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, work_status_key, status_reason_key, status_reason, note, entry_worker, cancel_flg, cancel_date_time, cancel_reason, cancel_worker, inspection_flg, inspection_updated_at, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-27 13:45:44.400741', 0, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', 0, NULL, '', '', 1, '2020-01-15 04:40:55.729', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-15 04:40:55');
INSERT INTO interlock_work_status (exec_date_time, exec_data_flag, place_id, serial_number, entry_date_time, work_status_key, status_reason_key, status_reason, note, entry_worker, cancel_flg, cancel_date_time, cancel_reason, cancel_worker, inspection_flg, inspection_updated_at, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-27 13:45:44.400741', 1, '70100999', '1110002890', '2020-01-08 09:00:02.342', 'INSPECTION', 'その他', 'その他', 'シナリオ9-3', 'testadmin', 1, '2020-01-27 04:45:44.399', '取消テスト', 'testadmin', 1, '2020-01-15 04:40:55.729', 'testadmin', '2020-01-08 09:00:02', 'testadmin', '2020-01-27 04:45:44');

/* interlock_data_item */
INSERT INTO interlock_data_item (exec_date_time, exec_data_flag, log_id, element_index, element_index_key, element, original_item_name, subject_type, acquisition, calculation_point, generation_flag, input_data_type, output_data_format, unit, device, master_id, changing_point, host_name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-09 10:36:13.530996', 0, '99999999-70100999-ngInsp', 'none', 'none', 'NG外観検査結果', 'NG外観検査結果', 'object', 'ProductionControlApi', NULL, '__C_____________', 'string', NULL, NULL, 'VF', '99999999-70100999', 'first', 'ProductionControlApi', '2017-11-30 15:00:00', '2999-12-31 14:59:59', 'ProductionControlApi', '2018-10-29 14:52:25', 'ProductionControlApi', '2020-01-08 18:00:02');
INSERT INTO interlock_data_item (exec_date_time, exec_data_flag, log_id, element_index, element_index_key, element, original_item_name, subject_type, acquisition, calculation_point, generation_flag, input_data_type, output_data_format, unit, device, master_id, changing_point, host_name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-09 10:36:13.530996', 1, '99999999-70100999-ngInsp', 'none', 'none', 'NG外観検査結果', 'NG外観検査結果', 'object', 'ProductionControlApi', NULL, '__C_____________', 'string', NULL, NULL, 'VF', '99999999-70100999', 'first', 'ProductionControlApi', '2017-11-30 15:00:00', '2999-12-31 14:59:59', 'ProductionControlApi', '2018-10-29 14:52:25', 'ProductionControlApi', '2020-01-09 10:36:13');
INSERT INTO interlock_data_item (exec_date_time, exec_data_flag, log_id, element_index, element_index_key, element, original_item_name, subject_type, acquisition, calculation_point, generation_flag, input_data_type, output_data_format, unit, device, master_id, changing_point, host_name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-24 10:49:40.589111', 0, '99999999-70100999-insp', 'none', 'none', '外観検査判定結果', '外観検査判定結果', 'object', 'ProductionControlApi', NULL, '__C_____________', 'string', NULL, NULL, 'VF', '99999999-70100999', 'first', 'ProductionControlApi', '2017-11-30 15:00:00', '2999-12-31 14:59:59', 'ProductionControlApi', '2018-10-29 14:52:25', 'ProductionControlApi', '2020-01-23 16:30:49');
INSERT INTO interlock_data_item (exec_date_time, exec_data_flag, log_id, element_index, element_index_key, element, original_item_name, subject_type, acquisition, calculation_point, generation_flag, input_data_type, output_data_format, unit, device, master_id, changing_point, host_name, validity_start_on, validity_end_on, created_by, created_at, updated_by, updated_at) VALUES ('2020-01-24 10:49:40.589111', 1, '99999999-70100999-insp', 'none', 'none', '外観検査判定結果', '外観検査判定結果', 'object', 'ProductionControlApi', NULL, '__C_____________', 'string', NULL, NULL, 'VF', '99999999-70100999', 'first', 'ProductionControlApi', '2017-11-30 15:00:00', '2999-12-31 14:59:59', 'ProductionControlApi', '2018-10-29 14:52:25', 'ProductionControlApi', '2020-01-24 10:49:40');

/* --------------------------------------------------------------------------------- */



/* V2DB生成 ------------------------------------------------------------------------ */

DROP DATABASE IF EXISTS test_v2;
CREATE DATABASE IF NOT EXISTS test_v2;
USE test_v2;

/* --------------------------------------------------------------------------------- */



/* テーブル生成 ---------------------------------------------------------------------- */

/* mst_signal */
CREATE TABLE IF NOT EXISTS mst_signal (
  log_id varchar(256) NOT NULL,
  signal_class varchar(128) NOT NULL,
  signal_category varchar(128) NOT NULL,
  signal_name varchar(128) NOT NULL,
  data_type varchar(128) NOT NULL,
  value_type varchar(128) NOT NULL,
  time_division varchar(128) NOT NULL,
  value_key_format varchar(128) NOT NULL,
  retention_type varchar(512) NOT NULL,
  retention_period bigint(20) NOT NULL,
  test_code varchar(16) NOT NULL,
  created_by varchar(64) NOT NULL,
  created_at datetime NOT NULL,
  PRIMARY KEY (log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* mst_signal_acquisition */
CREATE TABLE IF NOT EXISTS mst_signal_acquisition (
  signal_id varchar(512) NOT NULL,
  connector varchar(128) NOT NULL,
  connector_instance varchar(128) NOT NULL,
  signal_class varchar(128) NOT NULL,
  signal_category varchar(128) NOT NULL,
  signal_name varchar(128) NOT NULL,
  subject_instance varchar(1024) NOT NULL,
  value_instance varchar(1024) NOT NULL,
  conversion varchar(10000) NOT NULL,
  test_code varchar(16) NOT NULL,
  created_by varchar(64) NOT NULL,
  created_at datetime NOT NULL,
  PRIMARY KEY (signal_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/* --------------------------------------------------------------------------------- */



/* データ挿入 ------------------------------------------------------------------------ */

/* mst_signal */
INSERT INTO mst_signal (log_id, signal_class, signal_category, signal_name, data_type, value_type, time_division, value_key_format, retention_type, retention_period, test_code, created_by, created_at) VALUES
	('log_id_1_1_1_1_1', 'トレサビ', 'ワーク搬出入', 'EJECTION', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_1_2_1', 'トレサビ', 'ワーク搬出入', 'GET_BACK', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_2_3_1', 'トレサビ', '現品入力結果', 'INSPECTION', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_2_4_1', 'トレサビ', '現品入力結果', 'DISCARD', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_2_5_1', 'トレサビ', '現品入力結果', 'NO_RECLOSING', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_2_6_1', 'トレサビ', '現品入力結果', 'OP_CHECK', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_3_3_1', 'トレサビ', '外観検査NG', 'INSPECTION', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_3_4_1', 'トレサビ', '外観検査NG', 'DISCARD', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_3_5_1', 'トレサビ', '外観検査NG', 'NO_RECLOSING', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_3_6_1', 'トレサビ', '外観検査NG', 'OP_CHECK', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_3_7_1', 'トレサビ', '外観検査NG', 'STAY', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19'),
	('log_id_1_1_4_8_1', 'トレサビ', '外観検査OK', 'CHK_OK', 'Cassandra', '', '', '', '{"latestStore": {"store": false,"emptyValueKey": false},"historyStore": {"store": true}}', 86400, '01', 'MstMigrator', '2020-02-04 13:13:19');

/* mst_signal_acquisition */
INSERT INTO mst_signal_acquisition (signal_id, connector, connector_instance, signal_class, signal_category, signal_name, subject_instance, value_instance, conversion, test_code, created_by, created_at) VALUES
	('signal_id_1_1_1_1_1_1_1', 'VF', 'MstMigrator', 'トレサビ', 'ワーク搬出入', 'EJECTION', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_1_2_1_1_1', 'VF', 'MstMigrator', 'トレサビ', 'ワーク搬出入', 'GET_BACK', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_2_3_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '現品入力結果', 'INSPECTION', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_2_4_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '現品入力結果', 'DISCARD', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_2_5_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '現品入力結果', 'NO_RECLOSING', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_2_6_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '現品入力結果', 'OP_CHECK', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_3_3_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査NG', 'INSPECTION', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_3_4_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査NG', 'DISCARD', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_3_5_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査NG', 'NO_RECLOSING', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_3_6_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査NG', 'OP_CHECK', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_3_7_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査NG', 'STAY', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23'),
	('signal_id_1_1_4_8_1_1_1', 'VF', 'MstMigrator', 'トレサビ', '外観検査OK', 'CHK_OK', 'none', 'none', '', '01', 'MstMigrator', '2020-02-04 13:13:23');

/* --------------------------------------------------------------------------------- */