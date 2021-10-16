import passport from 'passport';
import passportLocal from 'passport-local';
import passportJWT from "passport-jwt";
import dotenv from "dotenv";
import { User } from "./models";
import { UserAttributes } from "./models/user";

dotenv.config();

const LocalStrategy = passportLocal.Strategy;
const JWTStrategy   = passportJWT.Strategy;
const ExtractJWT = passportJWT.ExtractJwt;

passport.use('local', new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password'
    },
     (email, password, cb)  => {
        User.findOne({
            where: {
                email,
                password
            }
        })
            .then((user: any) => {
                if (!user) {
                    return cb(null, false, {message: 'Incorrect email or password.'});
                }
                const userJSON: UserAttributes = user.toJSON();
                delete userJSON.password;
                return cb(null, userJSON, {message: 'Logged In Successfully'});
            })
            .catch(err => cb(err));
    }
));

passport.use('jwt', new JWTStrategy({
        algorithms: ["RS256"],
        jwtFromRequest: ExtractJWT.fromAuthHeaderAsBearerToken(),
        secretOrKey   : process.env.JWT_PUBLIC_KEY
    },
    (jwtPayload, cb) => {
        return cb(null, jwtPayload);
    }
));


export default passport;