CREATE TABLE IF NOT EXISTS users
    (id INT NOT NULL AUTO_INCREMENT,

    is_moderator TINYINT(1) NOT NULL,
    reg_time DATETIME NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    code VARCHAR(255),
    photo TEXT,
    PRIMARY KEY(id));

CREATE TABLE IF NOT EXISTS posts
    (id INT NOT NULL AUTO_INCREMENT,
    is_active TINYINT(1) NOT NULL,
    moderation_status enum ('NEW', 'ACCEPTED', 'DECLINED') NOT NULL DEFAULT 'NEW',
    moderator_id INT,
    user_id INT NOT NULL,
    time DATETIME NOT NULL,
    title VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    view_count INT  NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS post_votes
    (id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    time DATETIME NOT NULL,
    value TINYINT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS tags
    (id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS tag2post
    (id INT NOT NULL AUTO_INCREMENT,
    post_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS post_comments
    (id INT NOT NULL AUTO_INCREMENT,
    parent_id INT,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    time DATETIME NOT NULL,
    text TEXT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS captcha_codes
    (id INT NOT NULL AUTO_INCREMENT,
    time DATETIME NOT NULL,
    code TINYINT NOT NULL,
    secret_code TINYINT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS global_settings
    (id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    value VARCHAR(255) NOT NULL,
    PRIMARY KEY (id))