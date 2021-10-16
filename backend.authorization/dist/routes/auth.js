"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const jsonwebtoken_1 = __importDefault(require("jsonwebtoken"));
const passport_1 = __importDefault(require("passport"));
const dotenv_1 = __importDefault(require("dotenv"));
dotenv_1.default.config();
const router = express_1.default.Router();
router.post('/login', (req, res, next) => {
    passport_1.default.authenticate('local', { session: false }, (err, user) => {
        if (err) {
            return next(err);
        }
        if (!user) {
            return res.status(401).json({ message: 'Authorization failed, user not found' });
        }
        req.logIn(user, { session: false }, (error) => {
            if (error) {
                return next(error);
            }
            delete user.password;
            const token = jsonwebtoken_1.default.sign(user, process.env.JWT_SECRET_KEY, { algorithm: "RS256" });
            return res.json({ token });
        });
    })(req, res, next);
});
router.post('/verify', (req, res, next) => {
    passport_1.default.authenticate('jwt', { session: false }, (err, user) => {
        if (err) {
            return res.status(400).json({ error: err });
        }
        if (!user) {
            return res.status(401).json({ errorMessage: 'Invalid token received' });
        }
        user.iat = new Date(user.iat * 1000);
        return res.json(user);
    })(req, res, next);
});
exports.default = router;
//# sourceMappingURL=auth.js.map