"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const body_parser_1 = __importDefault(require("body-parser"));
const dotenv_1 = __importDefault(require("dotenv"));
const auth_1 = __importDefault(require("./routes/auth"));
const passport_1 = __importDefault(require("./passport"));
dotenv_1.default.config();
const port = process.env.SERVER_PORT;
const app = express_1.default();
app.use(body_parser_1.default.urlencoded({ extended: false })); // parse application/x-www-form-urlencoded
app.use(body_parser_1.default.json()); // parse application/json
app.use(passport_1.default.initialize());
app.use('/auth', auth_1.default);
// start the Express server
app.listen(port, () => {
    // tslint:disable-next-line:no-console
    console.log(`Authentication server started at http://localhost:${port}`);
});
//# sourceMappingURL=index.js.map