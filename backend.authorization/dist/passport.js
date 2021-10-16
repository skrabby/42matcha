"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const passport_1 = __importDefault(require("passport"));
const passport_local_1 = __importDefault(require("passport-local"));
const passport_jwt_1 = __importDefault(require("passport-jwt"));
const dotenv_1 = __importDefault(require("dotenv"));
const models_1 = require("./models");
dotenv_1.default.config();
const LocalStrategy = passport_local_1.default.Strategy;
const JWTStrategy = passport_jwt_1.default.Strategy;
const ExtractJWT = passport_jwt_1.default.ExtractJwt;
passport_1.default.use('local', new LocalStrategy({
    usernameField: 'username',
    passwordField: 'password'
}, (email, password, cb) => {
    models_1.User.findOne({
        where: {
            email,
            password
        }
    })
        .then((user) => {
        if (!user) {
            return cb(null, false, { message: 'Incorrect email or password.' });
        }
        const userJSON = user.toJSON();
        delete userJSON.password;
        return cb(null, userJSON, { message: 'Logged In Successfully' });
    })
        .catch(err => cb(err));
}));
passport_1.default.use('jwt', new JWTStrategy({
    algorithms: ["RS256"],
    jwtFromRequest: ExtractJWT.fromAuthHeaderAsBearerToken(),
    secretOrKey: process.env.JWT_PUBLIC_KEY
}, (jwtPayload, cb) => {
    return cb(null, jwtPayload);
}));
exports.default = passport_1.default;
//# sourceMappingURL=passport.js.map