"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.User = exports.sequelize = void 0;
const sequelize_1 = require("sequelize");
const dotenv_1 = __importDefault(require("dotenv"));
const user_1 = require("./user");
dotenv_1.default.config();
exports.sequelize = new sequelize_1.Sequelize(process.env.PG_SCHEMA, process.env.PG_LOGIN, process.env.PG_PASSWORD, {
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
exports.User = user_1.UserFactory(exports.sequelize);
//# sourceMappingURL=index.js.map