Feature: Test Automation Rest API User Management

  @api
  Scenario: Test Get List Data User Normal
    Given prepare url for "GET_LIST_USERS"
    When hit api get list users
    Then  validation status code api user is equals 200
    Then  validation response body get list users
    Then  validation response json with JSONSchema "get_list_user_success.json"

  @api
  Scenario: Test get data profile user normal
    Given prepare url for "GET_PROFILE_USER"
    When hit api get profile user by id "60d0fe4f5311236168a109cd"
    Then validation status code api user is equals 200
    Then validation response body get profile user
    Then validation response json with JSONSchema "get_user_success.json"

  @api
  Scenario: Test get data profile user failed not found
    Given prepare url for "GET_PROFILE_USER"
    When hit api get profile user by id "65bb2ac113492cde6cfbd2d8"
    Then validation status code api user is equals 404
    Then validation response body with message "RESOURCE_NOT_FOUND"
    Then validation response json with JSONSchema "get_user_error.json"

  @api
  Scenario: Test create new user normal
    Given prepare url for "CREATE_NEW_USER"
    When hit api post create new user
    Then validation status code api user is equals 200
    Then validation response body post create new user
    Then validation response json with JSONSchema "create_user.json"

  @api
  Scenario: Test update user normal
    Given prepare url for "UPDATE_USER"
    And hit api post create new user for manipulation data
    When hit api update profile user
    Then validation status code api user is equals 200
    Then validation response body update user
    Then validation response json with JSONSchema "update_profile_user_normal.json"

  @api
  Scenario: Test delete user normal
    Given prepare url for "DELETE_USER"
    And hit api post create new user for manipulation data
    When hit api delete user
    Then validation status code api user is equals 200
    Then validation response body delete user
    Then validation response json with JSONSchema "delete_user_normal.json"
    Then hit api get profile user after deleted
    Then validation response body with message "RESOURCE_NOT_FOUND"

  @api
  Scenario: Test delete user with user id not found
    Given prepare url for "DELETE_USER"
    When hit api delete user for id "65bb2c2213492c3665fbd2f2"
    Then validation status code api user is equals 404
    Then validation response body with message "RESOURCE_NOT_FOUND"
    Then validation response json with JSONSchema "delete_user_not_found.json"