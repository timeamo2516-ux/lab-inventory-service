package com.lab.inventory;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins="*")
public class ProductController {
  private final ProductRepository repository;
  public ProductController(ProductRepository repository){this.repository=repository;}
  @GetMapping public List<Product> all(){return repository.findAll();}
  @GetMapping("/{id}") public ResponseEntity<Product> one(@PathVariable Long id){
    return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }
  @PostMapping public ResponseEntity<Product> create(@Valid @RequestBody Product p){
    return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(p));
  }
  @PutMapping("/{id}") public ResponseEntity<Product> update(@PathVariable Long id,@Valid @RequestBody Product p){
    return repository.findById(id).map(x->{x.setName(p.getName());x.setCategory(p.getCategory());x.setPrice(p.getPrice());x.setStock(p.getStock());return ResponseEntity.ok(repository.save(x));}).orElse(ResponseEntity.notFound().build());
  }
  @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id){
    if(!repository.existsById(id)) return ResponseEntity.notFound().build();
    repository.deleteById(id); return ResponseEntity.noContent().build();
  }
}
