-- Find user by name
SELECT * FROM users WHERE users.name = 'name';

-- Find user by nickname
SELECT * FROM users WHERE users.nickname = 'nickname';

-- Find course by title
SELECT * FROM courses WHERE courses.title = 'title';

-- Find all courses by category
SELECT * FROM categories
INNER JOIN courses_categories ON categories.id = courses_categories.category_id
INNER JOIN courses ON courses.id = courses_categories.course_id

-- Find all courses by author
SELECT * FROM courses
INNER JOIN users ON users.id = courses.created_by
WHERE courses.created_by = 'author';

-- Find all users of the course
SELECT * FROM users
INNER JOIN courses_users ON users.id = courses_users.user_id
INNER JOIN courses ON courses.id = courses_users.course_id
WHERE courses.id = 'course-id';

-- Find all courses of the user
SELECT * FROM courses
INNER JOIN courses_users ON courses.id = courses_users.course_id
INNER JOIN users ON users.id = courses_users.user_id
WHERE users.id = 'user-id';

-- Find module by title
SELECT * FROM modules WHERE modules.title = 'title';

-- Find all modules of the course
SELECT * FROM modules
INNER JOIN courses_modules ON modules.id = courses_modules.module_id
INNER JOIN courses ON courses.id = courses_modules.course_id
WHERE courses.id = 'course-id';

-- Find theme by title
SELECT * FROM themes WHERE themes.title LIKE 'title';