package shop.mtcoding.jdbc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.jdbc.model.Product;
import shop.mtcoding.jdbc.model.ProductRepository;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductRepository productRepository;

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        productRepository.deleteById(id);
        return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateById(@RequestBody Product product, @PathVariable int id){
        return new ResponseEntity<>(productRepository.updateById(product, id), HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> findAll(@RequestBody Product product){
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.CREATED);
    }

    @GetMapping("/product")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return new ResponseEntity<>(productRepository.findById(id), HttpStatus.OK);
    }
}
