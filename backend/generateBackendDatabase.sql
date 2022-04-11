

drop schema test;
create schema test;
use test;

CREATE TABLE default_nodes (
                       id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                       type            VARCHAR(20),
                       name            VARCHAR(30) ,
                       description     VARCHAR(500),
                       svg_image       VARCHAR(100),
                       x_coord         VARCHAR(5),
                       y_coord         VARCHAR(5),
                       width           VARCHAR(5),
                       height          VARCHAR(5),
                       page_id         INTEGER
);

CREATE TABLE class_nodes (
                        id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                        type            VARCHAR(20),
                        name            VARCHAR(30),
                        description     VARCHAR(500),
                        svg_image       VARCHAR(100),
                        x_coord         VARCHAR(5),
                        y_coord         VARCHAR(5),
                        width           VARCHAR(5),
                        height          VARCHAR(5),
                        page_id         INTEGER

);

CREATE TABLE folder_nodes(
                             id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             name            VARCHAR(30),
                             type            VARCHAR(20),
                             description     VARCHAR(500),
                             svg_image       VARCHAR(100),
                             x_coord         VARCHAR(5),
                             y_coord         VARCHAR(5),
                             width           VARCHAR(5),
                             height          VARCHAR(5),
                             page_id         INTEGER

);

CREATE TABLE life_line_nodes(
                                id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                                name            VARCHAR(30),
                                type            VARCHAR(20),
                                description     VARCHAR(500),
                                svg_image       VARCHAR(100),
                                x_coord         VARCHAR(5),
                                y_coord         VARCHAR(5),
                                width           VARCHAR(5),
                                height          VARCHAR(5),
                                page_id         INTEGER

);

CREATE TABLE loop_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30),
                           type            VARCHAR(20),
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100),
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5),
                           page_id         INTEGER

);


CREATE TABLE note_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30),
                           type            VARCHAR(20),
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100),
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5),
                           page_id         INTEGER

);

CREATE TABLE oval_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30),
                           type            VARCHAR(20),
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100),
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5),
                           page_id         INTEGER

);

CREATE TABLE square_nodes(
                             id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             name            VARCHAR(30),
                             type            VARCHAR(20),
                             description     VARCHAR(500),
                             svg_image       VARCHAR(100),
                             x_coord         VARCHAR(5),
                             y_coord         VARCHAR(5),
                             width           VARCHAR(5),
                             height          VARCHAR(5),
                             page_id         INTEGER

);

CREATE TABLE stick_figure_nodes(
                                   id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                                   name            VARCHAR(30),
                                   type            VARCHAR(20),
                                   description     VARCHAR(500),
                                   svg_image       VARCHAR(100),
                                   x_coord         VARCHAR(5),
                                   y_coord         VARCHAR(5),
                                   width           VARCHAR(5),
                                   height          VARCHAR(5),
                                   page_id         INTEGER

);

CREATE TABLE text_box_nodes(
                               id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                               name            VARCHAR(30),
                               type            VARCHAR(20),
                               description     VARCHAR(500),
                               svg_image       VARCHAR(100),
                               x_coord         VARCHAR(5),
                               y_coord         VARCHAR(5),
                               width           VARCHAR(5),
                               height          VARCHAR(5),
                               page_id         INTEGER

);


CREATE TABLE default_edges (
                        id                     INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                        type                   VARCHAR(20),
                        from_node_id           INTEGER,
                        from_node_type         VARCHAR(20),
                        to_node_id             INTEGER,
                        to_node_type           VARCHAR(20),
                        page_id                INTEGER

);


CREATE TABLE normal_edges(
                             id                     INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             type                   VARCHAR(20),
                             from_node_id           INTEGER,
                             from_node_type         VARCHAR(20),
                             to_node_id             INTEGER,
                             to_node_type           VARCHAR(20),
                             page_id                INTEGER

);
CREATE TABLE abstract_edges(
                             id                     INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             type                   VARCHAR(20),
                             from_node_id           INTEGER,
                             from_node_type         VARCHAR(20),
                             to_node_id             INTEGER,
                             to_node_type           VARCHAR(20),
                             page_id                INTEGER

);

CREATE TABLE pages(
                            id                      INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            name                    VARCHAR(30)
);

CREATE TABLE users(
                            id                      INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            name                    VARCHAR(30)
);

CREATE TABLE users_pages(
                            id                      INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
                            user_id                 INTEGER,
                            page_id                 INTEGER

);




