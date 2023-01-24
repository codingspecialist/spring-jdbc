package shop.mtcoding.jdbc.model;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public Product save(Product product){
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("insert into product(name, price, qty) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getQty());
            return ps;
        }, keyHolder);
        // System.out.println("keyHolder : "+keyHolder.getKey().intValue());
        int pk = keyHolder.getKey().intValue();
        Product productPS = findById(pk);
        return productPS;
    }

    @Transactional
    public Product updateById(Product product, int id){
        jdbcTemplate.update("update product set name = ?, price = ?, qty = ? where id = ?", product.getName(), product.getPrice(), product.getQty(), id);
        Product productPS = findById(id);
        return productPS;
    }
    @Transactional
    public void deleteById(int id){
        jdbcTemplate.update("delete from product where id = ?", id);
    }



    public List<Product> findAll() {
        List<Product> productList = jdbcTemplate.queryForStream("select * from product", (rs, rowNum) -> new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("qty"))).collect(Collectors.toList());
        return productList;
    }

    public Product findById(int id) {
        Object[] param = {id};
        Product product = jdbcTemplate.queryForObject("select * from product where id = ?", param, (rs, rowNum) ->
            new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"), rs.getInt("qty"))
        );
        return product;
    }
}
