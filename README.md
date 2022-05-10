# User API

## What?
This service acts as an MVP for a user creation service, for some arbitrary deposit-taking institute.

I've tried to make it as production-y as possible, altho I haven't bothered really containerising the app,

## Why?
Everyone always bangs on about Spring, so I figured I'd give myself a chance to learn it.
This is very much six-hour project.

Is it perfect Spring? God no.

I still think Ktor is superior 😌

## API Functionality

* Doesn't allow more than one user with the same email address
* Allows credit for up to $1000, thus, if `monthly salary - monthly expenses` is less than $1000: *NO SOUP FOR YOU*

The following Endpoints are provided:
* Create user
  * Fields to be provided and persist
    * Name
    * Email Address (Must be unique)
    * Monthly Salary (Must be a positive number - for simplicity, pretend there is no such thing as income tax)
    * Monthly Expenses (Must be a positive number)
* List users
* Get user
* Create an account
  * Given a user, create an account
  * See functionality spec above
* List Accounts

## Environment Variables

* `ZIPKIN_BASE_URL=true` will enable the Zipkin Tracing UI for Spring Cloud Sleuth. If no environment variable is set,
  then the Zipkin UI will not be available.

## Building

* Build: `./gradlew build`
* Clean: `./gradlew clean`

## Convenience Containers

### Development

* `mongo-dev` Is configured to run locally on port 27017
* `zipkin-dev` Is configured to run locally on port 9411

## Running

In order to run, the `mongo-dev` container must be running. To run for development just as standard:

    # Fire up a mongo container and then run using gradle
    docker-compose up -d mongo-dev zipkin-dev && ./gradlew run

In development, the api will be available on `http://localhost:8080/api`

### Some Useful examples

Get all Users:

    curl --location --request GET 'http://localhost:8080/api/users'

Create a new user:

    curl --location --request POST 'http://localhost:8080/api/users' \
    --header 'Content-Type: application/json' \
    --data-raw '{"emailAddress": "test@test.com", "name": "Tester", "monthlySalary": 10000,"monthlyExpenses": 1000 }'`

Create an account for a user:

    curl --location --request POST \
    'http://localhost:8080/api/users/{userId}/accounts' \
    --header 'Content-Type: application/json' --data-raw '{}'

## Testing

When running tests ensure that a MongoDB instance is available on port `27017` (or alternatively adjust configuration).
Just `docker-compose up mongo-dev` and you're off to the races.

The unit tests have been set to use a new database called `test` as to not nuke genuine testing data (aka not from unit
tests).

## Additional Information

### Database

* The database has been configured to employ an index on the emailAddress
  field in the User document. 

### Api Spec

* `/docs` will return the JSON formatted API documentation
* `/swagger` will provide the OpenApi UI of the api documentation contained in `/docs`

### Tracing

* Tracing is enabled in all cases using Spring Cloud Sleuth
* The development environment comes with Zipkin, and allows for a UI request tracing

## Future Work

* In-memory (embedded) mongoDB for integration tests
* Service level unit tests
* Deployed container for metrics (or 3rd party via REST)