CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255),
                       content TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          post_id INT REFERENCES posts(id) ON DELETE CASCADE,
                          author VARCHAR(100),
                          comment TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
