import { Sequelize } from "sequelize";
import dotenv from "dotenv";
import { UserFactory } from "./user";
dotenv.config();

export const sequelize = new Sequelize(process.env.PG_SCHEMA, process.env.PG_LOGIN, process.env.PG_PASSWORD, {
    host: process.env.PG_HOST || 'localhost',
    port: parseInt(process.env.PG_PORT) || 5432,
    dialect: 'postgres',
    pool: {
        min: 0,
        max: 5,
        acquire: 30000,
        idle: 10000,
    }
});

export const User = UserFactory(sequelize);