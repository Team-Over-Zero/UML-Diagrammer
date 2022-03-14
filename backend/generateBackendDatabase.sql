

drop schema test;
create schema test;
use test;

CREATE TABLE abstract_nodes(
                               id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                               name            VARCHAR(30)     ,
                               title           VARCHAR(30)     ,
                               description     VARCHAR(500),
                               svg_image       VARCHAR(100)    ,
                               x_coord         VARCHAR(5),
                               y_coord         VARCHAR(5),
                               width           VARCHAR(5),
                               height          VARCHAR(5)
);

CREATE TABLE default_nodes (
                               id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                               name            VARCHAR(30)     ,
                               title           VARCHAR(30)     ,
                               description     VARCHAR(500),
                               svg_image       VARCHAR(100)    ,
                               x_coord         VARCHAR(5),
                               y_coord         VARCHAR(5),
                               width           VARCHAR(5),
                               height          VARCHAR(5)
);

CREATE TABLE class_nodes (
                        id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                        name            VARCHAR(30)     ,
                        title           VARCHAR(30)     ,
                        description     VARCHAR(500),
                        svg_image       VARCHAR(100)    ,
                        x_coord         VARCHAR(5),
                        y_coord         VARCHAR(5),
                        width           VARCHAR(5),
                        height          VARCHAR(5)
);

CREATE TABLE folder_nodes(
                             id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             name            VARCHAR(30)     NOT NULL,
                             title           VARCHAR(30)     NOT NULL,
                             description     VARCHAR(500),
                             svg_image       VARCHAR(100)    NOT NULL,
                             x_coord         VARCHAR(5),
                             y_coord         VARCHAR(5),
                             width           VARCHAR(5),
                             height          VARCHAR(5)
);

CREATE TABLE life_line_nodes(
                                id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                                name            VARCHAR(30)     NOT NULL,
                                title           VARCHAR(30)     NOT NULL,
                                description     VARCHAR(500),
                                svg_image       VARCHAR(100)    NOT NULL,
                                x_coord         VARCHAR(5),
                                y_coord         VARCHAR(5),
                                width           VARCHAR(5),
                                height          VARCHAR(5)
);

CREATE TABLE loop_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30)     NOT NULL,
                           title           VARCHAR(30)     NOT NULL,
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100)    NOT NULL,
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5)
);


CREATE TABLE note_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30)     NOT NULL,
                           title           VARCHAR(30)     NOT NULL,
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100)    NOT NULL,
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5)
);

CREATE TABLE oval_nodes(
                           id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                           name            VARCHAR(30)     NOT NULL,
                           title           VARCHAR(30)     NOT NULL,
                           description     VARCHAR(500),
                           svg_image       VARCHAR(100)    NOT NULL,
                           x_coord         VARCHAR(5),
                           y_coord         VARCHAR(5),
                           width           VARCHAR(5),
                           height          VARCHAR(5)
);

CREATE TABLE square_nodes(
                             id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             name            VARCHAR(30)     NOT NULL,
                             title           VARCHAR(30)     NOT NULL,
                             description     VARCHAR(500),
                             svg_image       VARCHAR(100)    NOT NULL,
                             x_coord         VARCHAR(5),
                             y_coord         VARCHAR(5),
                             width           VARCHAR(5),
                             height          VARCHAR(5)
);

CREATE TABLE stick_figure_nodes(
                                   id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                                   name            VARCHAR(30)     NOT NULL,
                                   title           VARCHAR(30)     NOT NULL,
                                   description     VARCHAR(500),
                                   svg_image       VARCHAR(100)    NOT NULL,
                                   x_coord         VARCHAR(5),
                                   y_coord         VARCHAR(5),
                                   width           VARCHAR(5),
                                   height          VARCHAR(5)
);

CREATE TABLE text_box_nodes(
                               id              INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                               name            VARCHAR(30)     NOT NULL,
                               title           VARCHAR(30)     NOT NULL,
                               description     VARCHAR(500),
                               svg_image       VARCHAR(100)    NOT NULL,
                               x_coord         VARCHAR(5),
                               y_coord         VARCHAR(5),
                               width           VARCHAR(5),
                               height          VARCHAR(5)
);


CREATE TABLE default_edges (
                        id             INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                        n1             INTEGER REFERENCES abstract_nodes (id),
                        n2             INTEGER REFERENCES abstract_nodes (id)
);


CREATE TABLE normal_edges(
                             id             INTEGER NOT NULL PRIMARY KEY Auto_Increment,
                             n1             INTEGER REFERENCES abstract_nodes (id),
                             n2             INTEGER REFERENCES abstract_nodes (id)
);


