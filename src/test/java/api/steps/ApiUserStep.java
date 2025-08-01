package api.steps;

import api.helper.SetUpEndpoint;
import api.request.ApiTestProcess;
import api.request.ApiTestUser;
import api.request.Endpoint;
import api.scenario.UserProfile;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.text.ParseException;

public class ApiUserStep {
    ApiTestUser apiUser;
    UserProfile dataTestCreateUser, dataTestUpdateUser;
    String currentUserID;

    public ApiUserStep(){
        apiUser = new ApiTestUser();
    }

    @Given("prepare url for {string}")
    public void prepare_url_for(String endpoint) {
        // prepare end point API
        SetUpEndpoint.prepareURL(endpoint); // prepare end point
    }
    @Then("validation response json with JSONSchema {string}")
    public void validation_response_json_with_json_schema(String dataType) {
        // check response data using Json Schema format
        ApiTestProcess.validationResponseData(apiUser.getRes(), dataType);
    }
    @Then("validation status code api user is equals {int}")
    public void validation_status_code_is_equals(Integer statusCode) {
        // check status response same with param statusCode
        ApiTestProcess.validationStatusCode(apiUser.getRes(), statusCode);
    }

    /***************************** Step Operation For Validation API Get List Users ***************************** */
    @When("hit api get list users")
    public void hit_api_get_list_users() {
        // call API for get all data user
        apiUser.hitAPIGetListUsers();
    }
    @Then("validation response body get list users")
    public void validation_response_body_get_list_users() {
        // check response body not null and field gender filled with female or male, also field status filled with active or inactive
        apiUser.checkResponseBodyListUsers();
    }

    /***************************** Step Operation For Validation API Get Profile User ***************************** */
    @When("hit api get profile user by id {string}")
    public void hitApiGetProfileUserById(String idUser) {
        apiUser.hitAPIGetProfileUser(idUser);
    }
    @Then("validation response body get profile user")
    public void validationResponseBodyGetProfileUser() {
        apiUser.checkResponseBodyProfileUser();
    }
    @Then("validation response body with message {string}")
    public void validationResponseBodyFailedWithMessage(String message) {
        apiUser.checkResponseBodyGetProfileUserFailed(message);
    }

    /***************************** Step Operation For Validation API Post Create User ***************************** */
    @When("hit api post create new user")
    public void hitApiPostCreateNewUser() {
        dataTestCreateUser = ApiTestProcess.prepareDataUserTestPost();
        apiUser.hitAPIPostNewUser(dataTestCreateUser);
    }
    @Then("validation response body post create new user")
    public void validationResponseBodyPostCreateNewUser() throws ParseException {
        apiUser.checkResponseBodyCreateUser(dataTestCreateUser);
    }

    @And("hit api post create new user for manipulation data")
    public void hitApiPostCreateNewUserForManipulationData() {
        dataTestCreateUser = ApiTestProcess.prepareDataUserTestPost();
        currentUserID = apiUser.hitAPIPostNewUser(Endpoint.CREATE_NEW_USER, dataTestCreateUser);
        System.out.println("current id after created : " + currentUserID);
    }

    /***************************** Step Operation For Validation API Put Update User ***************************** */
    @When("hit api update profile user")
    public void hitApiUpdateProfileUserById() {
        dataTestUpdateUser = ApiTestProcess.prepareDataUserTestUpdate();
        apiUser.hitAPIUpdateProfileUser(dataTestUpdateUser, currentUserID);
    }
    @Then("validation response body update user")
    public void validationResponseBodyUpdateUser() throws ParseException {
        apiUser.checkResponseBodyUpdateProfileUser(dataTestUpdateUser, currentUserID);
    }

    /***************************** Step Operation For Validation API Delete User ***************************** */
    @When("hit api delete user")
    public void hitApiDeleteUserForId() {
        apiUser.hitAPIDeleteUserById(currentUserID);
    }
    @Then("validation response body delete user")
    public void validationResponseBodyDeleteUser() {
        apiUser.checkResponseBodyDeleteUser(currentUserID);
    }
    @When("hit api get profile user after deleted")
    public void hitApiGetProfileUserAfterDeleted() {
        apiUser.hitAPIGetProfileUser(currentUserID);
    }

    @When("hit api delete user for id {string}")
    public void hitApiDeleteUserForId(String idUser) {
        apiUser.hitAPIDeleteUserById(idUser);
    }
}
