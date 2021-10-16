import express from 'express';
import jwt from 'jsonwebtoken';
import passport from 'passport';
import dotenv from "dotenv";
dotenv.config();

const router = express.Router();

router.post('/login', (req, res, next) => {
    passport.authenticate('local', { session: false }, (err, user) => {
        if (err) {
            return next(err);
        }
        if (!user) {
            return res.status(401).json({message: 'Authorization failed, user not found'})
        }

        req.logIn(user, { session: false }, (error) => {
            if (error) { return next(error); }
            delete user.password;
            const token: any = jwt.sign(user, process.env.JWT_SECRET_KEY, { algorithm: "RS256" });
            return res.json({token});
        });
    })(req, res, next);
});

router.post('/verify',(req, res, next) => {
    passport.authenticate('jwt', { session: false }, (err, user) => {
        if (err) {
            return res.status(400).json({error: err})
        }
        if (!user) {
            return res.status(401).json({errorMessage: 'Invalid token received'})
        }
        user.iat = new Date(user.iat * 1000);
        return res.json(user);
    })(req, res, next);
});

export default router;