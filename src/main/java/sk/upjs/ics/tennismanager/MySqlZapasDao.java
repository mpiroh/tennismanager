package sk.upjs.ics.tennismanager;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class MySqlZapasDao implements ZapasDao {
    private JdbcTemplate jdbcTemplate;
    
    public MySqlZapasDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public List<Zapas> dajPodlaTurnaja(int id) {
        String sql = "SELECT * FROM zapas JOIN hrac h1 ON hrac1 = h1.id JOIN hrac h2 ON hrac2 = h2.id" +
            " JOIN turnaj ON turnaj = turnaj.id WHERE turnaj = ?";
        ZapasRowCallbackHandler handler = new ZapasRowCallbackHandler();
        jdbcTemplate.query(sql, handler, id);
        
        return handler.getZapasy();
    }
    
    @Override
    public void pridaj(Zapas zapas) {
        String sql = "INSERT INTO zapas VALUES (?, ?, ?, ?, ?, ?, ?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, null, zapas.getHrac1().getId(), zapas.getHrac2().getId(),
                zapas.getTurnaj().getId(), zapas.getStavHrac1(), zapas.getStavHrac2(),
                zapas.getNajrychlejsiePodanie(), zapas.getNajrychlejsiePodanieHrac().getId(),
                zapas.getCas(), zapas.getEsaHrac1(), zapas.getEsaHrac2(), zapas.getOkoHrac1(),
                zapas.getOkoHrac2(), zapas.getUspesnostPodaniaHrac1(), zapas.getUspesnostPodaniaHrac2(),
                zapas.getTyp(), zapas.getSety());
    }
    
    @Override
    public int dajPocetZapasov() {
        String sql = "SELECT COUNT(*) FROM zapas";
        
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
