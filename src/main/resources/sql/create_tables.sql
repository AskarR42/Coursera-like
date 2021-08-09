CREATE TABLE IF NOT EXISTS users (
    id INTEGER AUTO_INCREMENT,
    nickname VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(200),
    surname VARCHAR(200),
    email VARCHAR(200),
    registration_date DATE NOT NULL,
    updated_by INTEGER,
    update_date DATE,
    deleted_by INTEGER,
    delete_date DATE,
    role VARCHAR(200) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (updated_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    created_by INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    deleted_by INTEGER,
    delete_date DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (deleted_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS courses_categories (
    course_id INTEGER,
    category_id INTEGER,
    PRIMARY KEY (course_id),
    PRIMARY KEY (category_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS courses (
    id INTEGER AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    created_by INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    updated_by VARCHAR(200),
    update_date DATE,
    deleted_by INTEGER,
    delete_date DATE,
    duration_hours INTEGER NOT NULL,
    category INTEGER NOT NULL,
    rating DOUBLE,
    tag VARCHAR(200),
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (deleted_by) REFERENCES users(id),
    FOREIGN KEY (category) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS courses_users (
    course_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (course_id, user_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS modules (
    id INTEGER AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    created_by INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    updated_by INTEGER,
    update_date DATE,
    deleted_by INTEGER,
    delete_date DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),
    FOREIGN KEY (deleted_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS courses_modules (
    course_id INTEGER,
    module_id INTEGER,
    PRIMARY KEY (course_id, module_id),
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (module_id) REFERENCES modules(id)
);

CREATE TABLE IF NOT EXISTS themes (
    id INTEGER AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    created_by INTEGER NOT NULL,
    creation_date DATE NOT NULL,
    updated_by INTEGER,
    update_date DATE,
    deleted_by INTEGER,
    delete_date DATE,
    content BLOB,
    PRIMARY KEY (id),
    FOREIGN KEY (created_by) REFERENCES users(id),
    FOREIGN KEY (updated_by) REFERENCES users(id),
    FOREIGN KEY (deleted_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS modules_themes (
    module_id INTEGER,
    theme_id INTEGER,
    PRIMARY KEY (module_id, theme_id),
    FOREIGN KEY (module_id) REFERENCES modules(id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);

CREATE TABLE IF NOT EXISTS tasks (
    id INTEGER AUTO_INCREMENT,
    content BLOB,
    theme_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (theme_id) REFERENCES themes(id)
);