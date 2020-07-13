package com.project.sidecarhealth.constant;

public final class ErrorMessages {

  public static final String MISSING_USER_ID_MESSAGE = "User's id cannot be empty";
  public static final String MISSING_USER_NAME_MESSAGE = "User's name cannot be empty";
  public static final String MISSING_USER_LAST_NAME_MESSAGE = "User's last name cannot be empty";
  public static final String MISSING_USER_EMAIL_MESSAGE = "User's email cannot be empty";
  public static final String MISSING_USER_API_KEY_MESSAGE = "User's apiKey cannot be empty";
  public static final String MISSING_POST_TITLE_MESSAGE = "BlogPost's title cannot be empty";
  public static final String MISSING_POST_BODY_MESSAGE = "BlogPost's body cannot be empty";

  public static final String INVALID_JSON_BODY_MESSAGE = "Invalid JSON request";
  public static final String INVALID_ENDPOINT_MESSAGE = "Not a valid endpoint";

  public static final String INVALID_USER_EMAIL_MESSAGE = "Invalid user email format";
  public static final String INVALID_USER_ID_MESSAGE =
      "Invalid user id format '%s' must be numeric";
  public static final String INVALID_POST_TITLE_MESSAGE =
      "Invalid post title, length of title cannot be more than 60 characters long";

  public static final String USER_RECORD_NOT_FOUND_MESSAGE = "User record does not exist";
  public static final String POST_RECORD_NOT_FOUND_MESSAGE = "BlogPost record does not exist";
}
