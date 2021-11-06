DELETE
FROM blog_engine.post_votes;

INSERT INTO blog_engine.post_votes (id, user_id, post_id, time, value)
VALUES (1003, 10003, 10001, '2021-09-21 00:34:50', 1),
       (1004, 10004, 10001, '2021-09-21 10:34:50', -1),
       (1005, 10004, 10002, '2021-10-30 16:23:03', 1),
       (1006, 10001, 10002, '2021-10-30 13:23:03', -1),
       (1007, 10006, 10003, '2021-10-26 19:23:13', 1),
       (1010, 10001, 10004, '2021-09-12 07:51:42', -1),
       (1011, 10004, 10004, '2021-09-12 15:51:42', 1),
       (1012, 10001, 10005, '2021-09-29 16:02:53', -1),
       (1013, 10006, 10005, '2021-09-29 16:02:53', -1),
       (1014, 10003, 10005, '2021-09-30 07:02:53', 1),
       (1016, 10001, 10006, '2021-11-15 07:40:56', 1),
       (1017, 10004, 10007, '2021-09-25 10:10:56', 1),
       (1019, 10003, 10007, '2021-09-25 11:10:56', -1),
       (1020, 10001, 10008, '2021-11-13 14:56:44', 1),
       (1021, 10004, 10009, '2021-10-09 23:49:51', 1),
       (1022, 10004, 10010, '2021-10-25 04:05:59', 1),
       (1023, 10001, 10010, '2021-10-25 09:05:59', -1),
       (1027, 10001, 10011, '2021-10-09 04:50:12', -1),
       (1029, 10003, 10012, '2021-11-20 04:44:24', -1),
       (1033, 10006, 10013, '2021-10-04 13:45:10', 1),
       (1035, 10003, 10014, '2021-10-28 22:46:45', 1),
       (1037, 10006, 10015, '2021-11-30 15:23:29', 1),
       (1039, 10004, 10015, '2021-12-01 07:23:29', -1),
       (1041, 10006, 10016, '2021-10-29 00:27:53', -1),
       (1042, 10004, 10016, '2021-10-28 15:27:53', 1),
       (1043, 10003, 10017, '2021-11-22 04:31:32', 1),
       (1044, 10001, 10017, '2021-11-21 13:31:32', 1),
       (1048, 10003, 10018, '2021-10-22 17:06:01', 1),
       (1049, 10001, 10018, '2021-10-22 19:06:01', 1),
       (1050, 10006, 10018, '2021-10-22 19:06:01', -1),
       (1053, 10003, 10019, '2021-11-02 21:00:01', 1),
       (1054, 10006, 10019, '2021-11-03 07:00:01', -1),
       (1056, 10004, 10019, '2021-11-02 23:00:01', 1),
       (1058, 10004, 10020, '2021-10-12 22:53:39', 1),
       (1060, 10006, 10021, '2021-10-01 16:09:57', 1),
       (1063, 10004, 10021, '2021-10-02 05:09:57', -1),
       (1064, 10006, 10022, '2021-10-08 03:37:32', -1),
       (1069, 10003, 10023, '2021-09-05 07:37:25', 1),
       (1072, 10006, 10024, '2021-11-29 11:09:26', 1),
       (1073, 10004, 10025, '2021-10-14 00:20:26', 1),
       (1077, 10006, 10027, '2021-11-23 22:54:34', -1),
       (1078, 10004, 10027, '2021-11-23 19:54:34', -1),
       (1079, 10004, 10028, '2021-11-18 03:26:19', 1),
       (1082, 10006, 10028, '2021-11-18 18:26:19', -1),
       (1083, 10006, 10029, '2021-10-14 04:24:02', -1),
       (1085, 10006, 10030, '2021-10-17 11:15:30', -1),
       (1087, 10001, 10030, '2021-10-17 13:15:30', -1),
       (1088, 10003, 10031, '2021-10-15 08:23:58', 1),
       (1090, 10004, 10032, '2021-10-24 06:29:04', 1),
       (1092, 10004, 10033, '2021-09-22 17:59:17', -1),
       (1095, 10001, 10033, '2021-09-23 09:59:17', 1),
       (1096, 10001, 10034, '2021-11-29 21:24:55', -1),
       (1098, 10004, 10034, '2021-11-30 05:24:55', 1),
       (1101, 10001, 10035, '2021-10-30 09:39:55', 1),
       (1103, 10003, 10035, '2021-10-30 17:39:55', -1),
       (1104, 10006, 10035, '2021-10-31 00:39:55', 1),
       (1105, 10003, 10036, '2021-10-17 22:18:55', -1),
       (1109, 10003, 10037, '2021-10-16 08:43:22', 1),
       (1112, 10004, 10039, '2021-10-08 07:22:01', 1),
       (1115, 10004, 10040, '2021-09-29 12:36:33', -1),
       (1118, 10003, 10041, '2021-09-26 17:03:38', 1),
       (1119, 10006, 10041, '2021-09-26 12:03:38', 1),
       (1124, 10004, 10043, '2021-10-13 04:20:04', 1),
       (1127, 10003, 10043, '2021-10-13 19:20:04', -1),
       (1129, 10003, 10044, '2021-11-11 02:14:36', -1),
       (1131, 10004, 10044, '2021-11-11 07:14:36', -1),
       (1132, 10001, 10044, '2021-11-11 11:14:36', -1),
       (1133, 10006, 10045, '2021-10-11 08:58:01', 1),
       (1134, 10004, 10045, '2021-10-11 13:58:01', -1),
       (1137, 10001, 10045, '2021-10-11 10:58:01', -1),
       (1138, 10006, 10046, '2021-12-01 10:45:03', -1),
       (1141, 10001, 10046, '2021-12-01 08:45:03', 1),
       (1143, 10003, 10047, '2021-11-23 22:26:37', -1),
       (1147, 10004, 10049, '2021-11-30 09:45:09', -1),
       (1149, 10001, 10050, '2021-10-08 03:32:51', 1),
       (1151, 10003, 10050, '2021-10-08 14:32:51', -1),
       (1153, 10004, 10050, '2021-10-08 14:32:51', -1);
