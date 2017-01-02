# clj-skygear

Clojure(Script) library for the Skygear BaaS

This project aims to provide a functional API to work with Skygear,
simplicity and flexibility are primary design goals.

The API is based on the [promesa][] promise library.

## Project Status

- [x] Promise-Based API
- [x] Lambdas / Cloud Functions
- [ ] Users
  - [x] Sign Up (email/username)
  - [x] Login (email/username)
  - [x] Login (auth provider)
  - [ ] Change Email/Username
  - [x] Change Password
- [ ] Record Query
  - [ ] Query Conditions
  - [ ] Result Sorting
  - [ ] Result Limit & Pagination
- [ ] Record Saving
  - [ ] Public Access Control
  - [ ] User Access Control
  - [ ] Role Access Control
  - [ ] Basic Data Types (bool, int, float, string, inst)
  - [ ] Arbitrary Clojure Data (serialized with transit)
  - [ ] Array Collection Type (contain query)
  - [ ] Reference Type (query by record / cascaded retrieval)
  - [ ] Geolocation Type (distance query)
  - [ ] Asset Type
  - [ ] Sequence Type
- [ ] Events
  - [ ] Init WS Connection
  - [ ] Client Events (online, offline, user change)
  - [ ] Data Events (listen for data changes)
  - [ ] Custom Events (pub/sub)

Note: "User Profile" feature is part of Record Saving with the
record type of `user`.

## Usage



[promesa]: https://github.com/funcool/promesa
