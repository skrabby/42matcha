const dotenv = require("dotenv");
dotenv.config();

module.exports = {
  "development": {
    "username": process.env.PG_LOGIN,
    "password": process.env.PG_PASSWORD,
    "database": process.env.PG_SCHEMA,
    "host": process.env.PG_HOST,
    "dialect": "postgres"
  },
  "test": {
    "username": process.env.PG_LOGIN,
    "password": process.env.PG_PASSWORD,
    "database": process.env.PG_SCHEMA,
    "host": process.env.PG_HOST,
    "dialect": "postgres"
  },
  "production": {
    "username": process.env.PG_LOGIN,
    "password": process.env.PG_PASSWORD,
    "database": process.env.PG_SCHEMA,
    "host": process.env.PG_HOST,
    "dialect": "postgres"
  }
}
