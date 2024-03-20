DROP TABLE IF EXISTS Author CASCADE;
DROP TABLE IF EXISTS Book CASCADE;
DROP TABLE IF EXISTS Shop CASCADE;
DROP TABLE IF EXISTS BookShop CASCADE;

CREATE TABLE Author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL
);

CREATE TABLE Book (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      author_id INT REFERENCES Author(id) ON DELETE CASCADE
);

CREATE TABLE Shop (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE BookShop (
                          book_id INT REFERENCES Book(id) ON DELETE CASCADE,
                          shop_id INT REFERENCES Shop(id) ON DELETE CASCADE,
                          PRIMARY KEY (book_id, shop_id)
);
