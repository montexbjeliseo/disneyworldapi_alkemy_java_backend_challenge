package com.mtx.disneyworld.util;

import static com.mtx.disneyworld.util.Constants.Roles.ADMIN;

public class Constants {

    public static abstract class Roles {

        public static final String ADMIN = "ADMIN";
        public static final String ADMIN_DESCRIPTION = "Can access all endpoint and modify any resource";
        public static final String USER = "USER";
        public static final String USER_DESCRIPTION = "Can access all endpoint allowed for users and modify any owned resource";
        public static final String[] ALL = {ADMIN, USER};
    }

    public static abstract class Endpoints {

        public static final String CHARACTERID = "/{characterId}";
        public static final String MOVIEID = "/{movieId}";
        public static final String GENREID = "/{genreId}";

        public static final String NEW = "/new";

        public static final String AUTH = "/auth";
        public static final String AUTH_ALL = AUTH + "**";
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";
        public static final String AUTH_REGISTER = AUTH + REGISTER;
        public static final String AUTH_LOGIN = AUTH + LOGIN;

        public static final String CHARACTER = "/characters";
        public static final String CHARACTER_ID = CHARACTER + CHARACTERID;

        public static final String MOVIE = "/movies";
        public static final String MOVIE_ID = MOVIE + MOVIEID;
        public static final String ADD_CHARACTER = CHARACTER + CHARACTERID;
        public static final String MOVIE_CHARACTER = MOVIE + ADD_CHARACTER;

        public static final String GENRE = "/genres";
        public static final String GENRE_ID = GENRE + GENREID;
        public static final String ADD_MOVIE = GENREID + MOVIE + MOVIEID;
        public static final String GENRE_MOVIE = GENRE + ADD_MOVIE;

    }

    public static abstract class Params {

        public static final String NAME = "name";
        public static final String GENRE = "genre";
        public static final String ORDER = "order";
        public static final String AGE = "age";
        public static final String MOVIE = "movie";
        public static final String ASC = "asc";
        public static final String DESC = "desc";
        public static final String ASC_DESC = ASC + "|" + DESC;
        public static final String DESC_ASC = DESC + "|" + ASC;
        public static final String DETAILS = "details";
    }
    
    public static abstract class DefaultData {
        public static final String ADMIN_EMAIL = "admin@disneyworld.com";
        public static final String USER_EMAIL = "user@disneyworld.com";
    }
}
