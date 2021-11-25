DELETE
FROM users;

insert into blog_engine.users (id, is_moderator, reg_time, name, email, password, code, photo)
values (10001, 0, '2021-10-01 10:00:00', 'Семен Листов', 'semenListov@Collection.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        'https://techrocks.ru/wp-content/uploads/2018/08/gargoyle-780540_1280-e1535726396204.jpg'),
       (10002, 0, '2021-10-02 10:21:50', 'Матвей Мапов', 'matveiHash@Map.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        'https://cdn.pixabay.com/photo/2017/07/26/12/39/gargoyle-2541562_960_720.jpg'),
       (10003, 0, '2021-10-03 13:31:10', 'Сергей Сетов', 'sergeySet@Collection.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        'https://cdn.pixabay.com/photo/2014/03/02/16/19/fountain-278249_960_720.jpg'),
       (10004, 1, '2021-10-04 15:41:55', 'Александр Модератов', 'alexModer@admin.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        'https://cdn.pixabay.com/photo/2020/12/19/15/38/sculpture-5844698_960_720.png'),
       (10005, 0, '2021-10-05 17:47:05', 'Юстас Стримин', 'UriStream@stream.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        'https://cdn.pixabay.com/photo/2013/09/19/07/06/fountain-183702_960_720.jpg'),
       (10006, 1, '2021-10-05 17:47:05', 'Jordan Exception', 'jordonExcept@Jo.com',
        '$2y$12$n/Xo793w01lr/FGPxzZr5.RBis64hqDVZIhZtVx.iEHFP7cegGC7e', null,
        null);
