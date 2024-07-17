package gift.product.controller;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sort,
      @RequestParam(defaultValue = "asc") String direction) {
    Sort.Direction sortDirection = Sort.Direction.fromString(direction);
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

    Page<ProductDto> products = productService.findAll(pageable);
    return ResponseEntity.ok(products);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
    ProductDto product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }


  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    ProductDto createdProduct = productService.addProduct(productDto);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
      @Valid @RequestBody ProductDto productDto) {
    ProductDto updatedProduct = productService.updateProduct(id, productDto);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }
}
