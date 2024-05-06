package com.example.avis.demo.securite;

import com.example.avis.demo.entites.Utilisateur;
import com.example.avis.demo.services.UtilisateurService;
import com.example.avis.demo.services.UtilisateurServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.lang.String;
import java.util.function.Function;


@AllArgsConstructor
@Service
public class JwtService {

    private final String ENKRITPTION_KEY = "sjHWM3A8Bt70a76UEaCJX8EgLyvFbnNWsPSxqFA7Jh+VkjQuFPG3Fe/8Xa/7CxooBGSmmTlCAfAFjepcgwrVvdbHgC+J5EbzRkjG6qJABikmErVfXxrj4R/X/dJyjyuVuN97dAtcqlRJr/QolGgxl5GRZrk2g+GAT926xuv78sladiBqCe74mBMj7LcgrTpeuwu4SaMAr4g02h9VXhUvoy4V89MVbaRygRKtL97bsH1SiH027qymq3hq8svxRpOka7CPku+mef0guIpxlTPwWtoD/7wazYS3nDk/4Tl+b/YdXpvZuiUWfrn6laDB0h9IRe2fEor7ZGdGqP72BRDaYi0Hj4p6xxAHFJC0y8uXzqs=";
    private UtilisateurServiceImpl utilisateurService;

    public Map<String,String> generate(String username){
        Utilisateur utilisateur = (Utilisateur) utilisateurService.loadUserByUsername(username);
        return this.generateJwt(utilisateur);
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30*60*1000;
        final Map<String,Object> claims = Map.of(
                "nom",utilisateur.getNom(),
                "email",utilisateur.getEmail(),
                Claims.EXPIRATION,new Date(expirationTime),
                Claims.SUBJECT,utilisateur.getEmail()
        );

        String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        byte[] decoder = Decoders.BASE64.decode(ENKRITPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public String extractUsername(String token) {
        return this.getClaim(token , Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());

    }

    private Date getExpirationDateFromToken(String token) {
        return this.getClaim(token , Claims::getExpiration);
    }

    private <T> T getClaim(String token , Function<Claims, T> function){
        Claims claims = getAllClaim(token);
        return function.apply(claims);

    }

    private Claims getAllClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
