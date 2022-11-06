package ru.ncallie.JavaCase.configuration.security.JWT;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
class JWTUtilTest {
@Autowired
private JWTUtil jwtUtil;
@Value("${jwt_secret}")
private String secret;

    @Test
    void generateTokenOk() {
        String usrname = "username";
        String token = jwtUtil.generateToken(usrname);
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("ncallie")
                .build();
        DecodedJWT verify = verifier.verify(token.substring(7));
        Assert.assertEquals(usrname, verify.getClaim("username").asString());
    }

    @Test
    void validateTokenAndRetrieveClaimOk() {
        String usrname = "username";
        String token = jwtUtil.generateToken(usrname);
        String s = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7));
        Assert.assertEquals(s, usrname);

    }
}