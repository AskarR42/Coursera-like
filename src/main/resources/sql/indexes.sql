CREATE INDEX user_names ON users (name);
CREATE INDEX user_nicknames ON users (nickname);
CREATE INDEX course_titles ON courses (title);
CREATE INDEX course_durations ON courses (duration_hours);
CREATE INDEX module_titles ON modules (title);
CREATE INDEX theme_titles ON themes (title);

SHOW INDEX FROM users;
SHOW INDEX FROM courses;
SHOW INDEX FROM courses_users;
SHOW INDEX FROM categories;
SHOW INDEX FROM modules;
SHOW INDEX FROM course_modules;
SHOW INDEX FROM themes;
SHOW INDEX FROM modules_themes;
SHOW INDEX FROM tasks;
