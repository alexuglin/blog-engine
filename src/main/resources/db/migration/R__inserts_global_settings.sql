DELETE
FROM blog_engine.global_settings;

INSERT INTO blog_engine.global_settings (code, name, value)
VALUES ('MULTIUSER_MODE', 'Многопользовательский режим', 'YES'),
       ('POST_PREMODERATION', 'Премодерация постов', 'NO'),
       ('STATISTICS_IS_PUBLIC', 'Показывать всем статистику блога', 'YES');